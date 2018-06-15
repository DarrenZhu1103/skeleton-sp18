public class HuffmanDecoder {
    public static void main(String[] args){
        ObjectReader reader = new ObjectReader(args[0]);

        BinaryTrie tire = (BinaryTrie) reader.readObject();
        int size = (int) reader.readObject();
        BitSequence code = (BitSequence) reader.readObject();

        char[] chars = new char[size];

        for (int i = 0; i < size; i++){
            Match c = tire.longestPrefixMatch(code);
            chars[i] = c.getSymbol();
            code = code.allButFirstNBits(c.getSequence().length());
        }

        FileUtils.writeCharArray(args[1], chars);
    }
}
