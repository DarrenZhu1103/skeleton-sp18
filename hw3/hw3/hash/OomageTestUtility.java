package hw3.hash;

import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        int[] counts = new int[M];
        for (Oomage item : oomages)
            counts[(item.hashCode() & 0x7FFFFFFF) % M]++;
        for (int i = 0; i < M; i++){
            double count = counts[i];
            if (count < oomages.size() / 50 || count > oomages.size() / 2.5)
                return false;
        }
        return true;
    }
}
