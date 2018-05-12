public class ArrayDeque<T> implements Deque<T> {
    /** using array to store data */
    private T[] arr;
    private int size, nextFirst, nextLast, n;

    public ArrayDeque(){
        arr = (T[]) new Object[8];
        size = 8;
        nextLast = 0;
        nextFirst = 7;
        n = 0;
    }

    /** update the array to save memory */
    private void sizeUpdate(){
        int newSize, num = (nextLast - nextFirst - 1 + size) % size;

        if (num <= 2)
            newSize = 8;
        else if (num <= 0.25 * size)
            newSize = 4 * num;
        else if (num == size)
            newSize = 2 * num;
        else
            return;

        T[] newArr = (T[]) new Object[newSize];
        if (nextLast >= nextFirst)
            System.arraycopy(arr, nextFirst + 1, newArr, 0, num);
        else {
            System.arraycopy(arr, nextFirst + 1, newArr, 0, size - 1 - nextFirst);
            System.arraycopy(arr, 0, newArr, size - 1 - nextFirst, nextLast);
        }
        this.arr = newArr;
        nextLast = num;
        nextFirst = newSize - 1;
        size = newSize;
    }

    @Override
    public void addFirst(T item){
        arr[nextFirst] = item;
        nextFirst = (nextFirst + size - 1) % size;
        this.sizeUpdate();
        n++;
    }

    @Override
    public void addLast(T item){
        arr[nextLast] = item;
        nextLast = (nextLast + 1) % size;
        this.sizeUpdate();
        n++;
    }

    @Override
    public boolean isEmpty(){
        if (n == 0)
            return true;
        return false;
    }

    @Override
    public int size(){
        return size;
    }

    @Override
    public void printDeque(){
        int count = (nextFirst + 1) % size;
        while (count != nextLast){
            System.out.print(arr[count] + " ");
            count = (count + 1) % size;
        }
        System.out.println();
    }

    @Override
    public T removeFirst(){
        if (this.n == 0)
            return null;
        int ind = (nextFirst + 1) % size;
        T result = arr[ind];
        arr[ind] = null;
        nextFirst = ind;
        this.sizeUpdate();
        return result;
    }

    @Override
    public T removeLast(){
        if (this.n == 0)
            return null;
        int ind = (nextLast + size - 1) % size;
        T result = arr[ind];
        arr[ind] = null;
        nextLast = ind;
        this.sizeUpdate();
        return result;
    }

    @Override
    public T get(int index){
        if (index >= size)
            return null;
        return arr[(nextFirst + 1 + index) % size];
    }
}