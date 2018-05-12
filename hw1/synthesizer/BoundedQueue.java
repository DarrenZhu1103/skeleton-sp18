package synthesizer;

import java.util.Iterator;

public interface BoundedQueue<T> extends Iterable<T> {
    int capacity();    // return the size of the queue
    int fillCount();   // return the number of items in the queue
    void enqueue(T x); // add an item to the end
    T dequeue();       // delete and return the front item
    T peek();          // return the front item

    @Override
    Iterator<T> iterator();

    /** return true if the queue is empty */
    default boolean isEmpty(){
        return fillCount() == 0;
    }

    /** return true if the queue is full */
    default boolean isFull(){
        return capacity() == fillCount();
    }

}
