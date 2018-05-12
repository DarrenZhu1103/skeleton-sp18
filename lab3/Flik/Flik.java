/** An Integer tester created by Flik Enterprises. */
import static org.junit.Assert.*;

public class Flik {
    public static boolean isSameNumber(Integer a, Integer b) {
        assertEquals(a, b);
        boolean c = a == b;
        return c;
    }
}
