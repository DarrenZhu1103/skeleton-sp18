public class ArrayDeque<T> implements Deque<T> {
    /** using array to store data */
    private T[] arr;
    private int size, nextFirst, nextLast;

    public ArrayDeque(){
        arr = (T[]) new Object[8];
        size = 8;
        nextLast = 0;
        nextFirst = 7;
    }

    /** update the array to save memory */
    private void sizeUpdate(){
        int num, newSize;
        if (nextLast >= nextFirst)
            num = nextLast - nextFirst - 1;
        else
            num = arr.length - 1 - nextFirst + nextLast;

        if (num <= 2)
            newSize = 8;
        else if (num <= 0.25 * arr.length)
            newSize = 4 * num;
        else if (num == arr.length)
            newSize = 2 * num;
        else
            return;

        T[] newArr = (T[]) new Object[newSize];
        if (nextLast >= nextFirst)
            System.arraycopy(arr, nextFirst + 1, newArr, 0, num);
        else {
            System.arraycopy(arr, nextFirst + 1, newArr, 0, arr.length - 1 - nextFirst);
            System.arraycopy(arr, 0, newArr, arr.length - 1 - nextFirst, nextLast);
        }
        this.arr = newArr;
        nextLast = num;
        nextFirst = newSize - 1;
        size = newSize;
    }

    private void convertAddNextLast(){
        if (nextLast != size - 1)
            nextLast++;
        else
            nextLast = 0;
    }

    private void convertAddNextFirst(){
        if (nextFirst != 0)
            nextFirst--;
        else
            nextFirst = size - 1;
    }

    @Override
    public void addFirst(T item){
        arr[nextFirst] = item;
        convertAddNextFirst();
        this.sizeUpdate();
    }

    @Override
    public void addLast(T item){
        arr[nextLast] = item;
        convertAddNextLast();
        this.sizeUpdate();
    }

    @Override
    public boolean isEmpty(){
        if (nextLast - nextFirst == 1 || nextLast == 0 && nextFirst == size - 1)
            return true;
        return false;
    }

    @Override
    public int size(){
        return size;
    }

    @Override
    public void printDeque(){
        if (isEmpty()) {
            System.out.println();
            return;
        }
        int count;
        if (nextFirst == size - 1)
            count = 0;
        else
            count = nextFirst + 1;
        while (count != nextLast - 1){
            System.out.print(arr[count] + " ");
            count++;
            if (count == size)
                count = 0;
        }
        System.out.println(arr[count]);
    }

    @Override
    public T removeFirst(){
        if (this.size == 0)
            return null;
        T result;
        int ind;
        if (nextFirst == size - 1)
            ind = 0;
        else
            ind = nextFirst + 1;
        result = arr[ind];
        arr[ind] = null;
        nextFirst = ind;
        return result;
    }

    @Override
    public T removeLast(){
        if (this.size == 0)
            return null;
        T result;
        int ind;
        if (nextLast == 0)
            ind = size - 1;
        else
            ind = nextLast - 1;
        result = arr[ind];
        arr[ind] = null;
        nextLast = ind;
        return result;
    }

    @Override
    public T get(int index){
        int count = 0, ind;
        if (nextFirst == size - 1)
            ind = 0;
        else
            ind = nextFirst + 1;
        while (ind != nextLast){
            if (count == index)
                return arr[ind];
            count++;
        }
        return null;
    }
}