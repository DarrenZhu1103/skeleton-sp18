package lab9;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Your name here
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */

    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /** Returns the value mapped to by KEY in the subtree rooted in P.
     *  or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p) {
        if (p == null)
            return null;
        else if (key.compareTo(p.key) == 0)
            return p.value;
        else if (key.compareTo(p.key) > 0)
            return getHelper(key, p.left);
        else
            return getHelper(key, p.right);
    }

    /** Returns the value to which the specified key is mapped, or null if this
     *  map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        if (key == null)
            throw new IllegalArgumentException("key must not br null");
        return getHelper(key, root);
    }

    /** Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
      * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {
        if (p == null) {
            size++;
            return new Node(key, value);
        }
        else if (key.compareTo(p.key) == 0){
            p.value = value;
            return p;
        }
        else if (key.compareTo(p.key) > 0) {
            p.left = putHelper(key, value, p.left);
            return p;
        }
        else {
            p.right = putHelper(key, value, p.right);
            return p;
        }
    }

    /** Inserts the key KEY
     *  If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        if (key == null)
            throw new IllegalArgumentException("key must not br null");
        root = putHelper(key, value, root);
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        return keySetHelper(root);
    }

    private Set<K> keySetHelper(Node p){
        if (p == null)
            return null;
        else{
            Set<K> result = new HashSet<>();
            result.add(p.key);
            result.addAll(keySetHelper(p.left));
            result.addAll(keySetHelper(p.right));
            return result;
        }
    }

    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */
    @Override
    public V remove(K key) {
        V result = get(key);
        removeHelper(key, root);
        return result;
    }

    private Node removeHelper(K key, Node p) {
        if (p == null)
            return null;
        else if (p.key.compareTo(key) > 0) {
            p.left = removeHelper(key, p.left);
            return p;
        }
        else if (p.key.compareTo(key) < 0) {
            p.right = removeHelper(key, p.right);
            return p;
        }
        else {
            if (p.right == null)
                return p.left;
            if (p.left == null)
                return p.right;
            Node newP = findMax(p.left);
            remove(newP.key);
            newP.left = p.left;
            newP.right = p.right;
            return newP;
        }
    }

    private Node findMax(Node p) {
        while (p.right != null)
            p = p.right;
        return p;
    }

    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value.  Returns the VALUE removed,
     *  null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        if (value == get(key))
            return remove(key);
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }
}
