/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 *
 */
public class RadixSort {
    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     */
    public static String[] sort(String[] asciis) {
//        int m = 0;
//        for (String s : asciis)
//            if (s.length() > m)
//                m = s.length();
//        String[] result = asciis.clone();
//        for (int i = m - 1; i >= 0; i--)
//            sortHelperLSD(result, i);
//        return result;

        sortHelperMSD(asciis, 0, asciis.length, 0);
        return asciis;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     * @param asciis Input array of Strings
     * @param index The position to sort the Strings on.
     */
    private static void sortHelperLSD(String[] asciis, int index) {
        String[] temp = new String[asciis.length];
        int[] num = new int[257];
        getIndArr(asciis, num, index, 0, asciis.length);
        for (String s : asciis)
            temp[num[getAscii(s, index)]++] = s;
        System.arraycopy(temp, 0, asciis, 0, asciis.length);
    }

    private static void getIndArr(String[] asciis, int[] num, int index, int start, int end){
        for (int i = start; i < end; i++){
            num[getAscii(asciis[i], index) + 1]++;
        }
        for (int i = 0; i < num.length - 1; i++){
            num[i + 1] = num[i] + num[i + 1];
        }
    }

    private static int getAscii(String s, int index){
        int i = 0;
        if (s.length() > index)
            i = s.charAt(index);
        return i;
    }

    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start int for where to start sorting in this method (includes String at start)
     * @param end int for where to end sorting in this method (does not include String at end)
     * @param index the index of the character the method is currently sorting on
     *
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        if (start + 1 >= end)
            return;
        String[] temp = new String[end - start];
        int[] num = new int[257];
        getIndArr(asciis, num, index, start, end);
        int[] numCopy = num.clone();
        for (int i = start; i < end; i++)
            temp[num[getAscii(asciis[i], index)]++] = asciis[i];
        System.arraycopy(temp, 0, asciis, start, temp.length);
        for (int i = 0; i < numCopy.length - 1; i++)
            sortHelperMSD(asciis, numCopy[i], numCopy[i + 1], index + 1);
    }

    public static void main(String[] args){
        String[] raw = {"Arsenal", "Liverpool", "Chelsea", "Manchester", "Arsene"};
        arrPrint(raw);
        arrPrint(sort(raw));
        arrPrint(raw);
    }

    private static void arrPrint(String[] arr){
        for (String s : arr)
            System.out.print(s + " ");
        System.out.println();
    }
}
