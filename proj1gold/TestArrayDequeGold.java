import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {
    @Test
    public void testArrayDeque(){
        StudentArrayDeque<Integer> arr1 = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> arr2 = new ArrayDequeSolution<>();
        ArrayDequeSolution<String> arr = new ArrayDequeSolution<>();

        for (int i = 0; i < 30; i++){
            double randNum = StdRandom.uniform();
            if (randNum < 0.5){
                arr1.addFirst(i);
                arr2.addFirst(i);
            }
            else{
                arr1.addLast(i);
                arr2.addLast(i);
            }
        }
        for (int i = 0; i < 30; i++){
            double randNum = StdRandom.uniform();
            if (randNum < 0.5) {
                arr.addLast("removeFirst()\n");
                assertEquals(arr1.removeFirst(), arr2.removeFirst());
            }
            else {
                arr.addLast("removeLast()\n");
                String a = arr.get(arr.size() - 3), b = arr.get(arr.size() - 2), c = arr.get(arr.size() - 1);
                assertEquals(a+b+c,arr1.removeLast(), arr2.removeLast());
            }
        }
    }
}
