import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class HuffmanEncoder {
    public static Map<Character, Integer> buildFrequencyTable(char[] inputSymbols){
        Map<Character, Integer> result = new HashMap<>();
        for (char c : inputSymbols) {
            if (result.containsKey(c))
                result.put(c, result.get(c) + 1);
            else
                result.put(c, 1);
        }
        return result;
    }

    public static void main(String[] args){
        char[] inputSymbols = FileUtils.readFile(args[0]);
        Map<Character, Integer> frequencyTable = buildFrequencyTable(inputSymbols);
        BinaryTrie trie = new BinaryTrie(frequencyTable);
        Map<Character, BitSequence> dict = trie.buildLookupTable();

        ObjectWriter writer = new ObjectWriter(args[0] + ".hug");
        writer.writeObject(trie);
        writer.writeObject(inputSymbols.length);

        List<BitSequence> list = new LinkedList<>();
        for (char c : inputSymbols)
            ((LinkedList<BitSequence>) list).addLast(dict.get(c));

        writer.writeObject(BitSequence.assemble(list));
    }
}
