// TODO: Make sure to make this class a part of the synthesizer package
package synthesizer;

import java.util.Iterator;

//TODO: Make sure to make this class and all of its methods public
//TODO: Make sure to make this class extend AbstractBoundedQueue<t>
public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;

    class rbIterator implements Iterator<T> {
        private int ind;

        public rbIterator(){
            ind = first;
        }

        @Override
        public boolean hasNext() {
            return ind == last;
        }

        @Override
        public T next(){
            ind++;
            return rb[ind - 1];
        }
    }
    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        this.capacity = capacity;
        fillCount = 0;
        rb = (T[]) new Object[capacity];
        first = capacity / 2;
        last = first;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    @Override
    public void enqueue(T x) {
        if (isFull())
            throw new RuntimeException("Ring Buffer Overflow");
        rb[last] = x;
        last = (last + 1) % capacity;
        fillCount++;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    @Override
    public T dequeue() {
        if (isEmpty())
            throw new RuntimeException("Ring Buffer Underflow");
        T result = rb[first];
        rb[first] = null;
        fillCount--;
        first = (first + 1) % capacity;
        return result;
    }

    /**
     * Return oldest item, but don't remove it.
     */
    @Override
    public T peek() {
        return rb[first];
    }

    @Override
    public Iterator<T> iterator(){
        return new rbIterator();
    }
}
