import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class BinaryTrie implements Serializable {
    private CodeNode root;

    private class CodeNode implements Comparable<CodeNode>, Serializable {
        private char c;
        private int frequency;
        private CodeNode left, right;

        CodeNode(char c, int f){
            this.c = c;
            this.frequency = f;
        }

        CodeNode(CodeNode left, CodeNode right){
            this.left = left;
            this.right = right;
            this.frequency = left.frequency + right.frequency;
        }

        @Override
        public int compareTo(CodeNode o){
            return this.frequency - o.frequency;
        }

        public Map<Character, BitSequence> getMap(){
            Map<Character, BitSequence> lookUp = new HashMap<>();
            updateMap("", this, lookUp);
            return lookUp;
        }

        private void updateMap(String s, CodeNode p, Map<Character, BitSequence> lookUp){
            if (p.left == null && p.right == null) {
                lookUp.put(p.c, new BitSequence(s));
                return;
            }
            updateMap(s + "0", p.left, lookUp);
            updateMap(s + "1", p.right, lookUp);
        }

    }

    public BinaryTrie(Map<Character, Integer> frequencyTable){
        Queue<CodeNode> queue = new PriorityQueue<>();
        for (Character c : frequencyTable.keySet())
            queue.add(new CodeNode(c, frequencyTable.get(c)));
        while (queue.size() > 1)
            queue.add(new CodeNode(queue.poll(), queue.poll()));
        root = queue.poll();
    }

    public Match longestPrefixMatch(BitSequence querySequence){
        int i = 0;
        String s = "";
//        for (char c : lookUp.keySet()){
//            BitSequence s = lookUp.get(c);
//            int l = s.length();
//            if (l > i && l <= querySequence.length() && s.equals(querySequence.firstNBits(l))){
//                i = l;
//                result = new Match(s, c);
//            }
//        }
        CodeNode p = root;
        while (p.left != null && p.right != null){
            int nextCode = querySequence.bitAt(i);
            s += nextCode;
            if (nextCode == 0)
                p = p.left;
            else
                p = p.right;
            i++;
        }
        return new Match(new BitSequence(s), p.c);
    }

    public Map<Character, BitSequence> buildLookupTable(){
        return root.getMap();
    }
}
