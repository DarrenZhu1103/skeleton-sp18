import edu.princeton.cs.algs4.In;

public class Trie {
    private static int R = 27;
    private Node root;
    int length;

    private class Node {
        boolean exist;
        Node[] links;

        Node(){
            exist = false;
            links = new Node[R];
        }
    }

    Trie(String dictPath){
        root = new Node();
        length = 0;
        initialize(dictPath);
    }

    public void initialize(String dictPath){
        In in = new In(dictPath);
        while (in.hasNextLine()) {
            String s = in.readLine();
            root = insert(root, s, 0);
            if (s.length() > length)
                length = s.length();
        }
    }

    public boolean contains(String word){
        return contains(root, word, 0);
    }

    public boolean hasPath(String word){
        return hasPath(root, word, 0);
    }

    private boolean hasPath(Node p, String word, int deep){
        if (p == null)
            return false;
        if (deep == word.length())
            return true;
        int i = getIndex(word.charAt(deep));
        return hasPath(p.links[i], word, deep + 1);
    }

    private boolean contains(Node p, String word, int deep){
        if (p == null)
            return false;
        if (deep == word.length())
            return p.exist;
        int i = getIndex(word.charAt(deep));
        return contains(p.links[i], word, deep + 1);
    }

    private Node insert(Node p, String word, int deep){
        if (p == null){
            p = new Node();
        }
        if (deep == word.length()){
            p.exist = true;
            return p;
        }
        int i = getIndex(word.charAt(deep));
        p.links[i] = insert(p.links[i], word, deep + 1);
        return p;
    }

    private int getIndex(char c){
        if (c >= 'a' && c <= 'z')
            return c - 'a';
        return 26;
    }
}
