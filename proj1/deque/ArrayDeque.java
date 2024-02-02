package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T>, Iterable<T> {
    private T[] items;

    private int size;

    private int nextFirst;

    private int nextLast;

    private void getIndex(int newFirst, int newLast) {
        this.nextFirst = newFirst;
        this.nextLast = newLast;
    }

    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        getIndex(4, 5);
    }

    public int size() {
        return size;
    }

    private int getFirstIndex() {
        return (nextFirst + 1) % items.length;
    }

    private int getLastIndex() {
        return (nextLast - 1 + items.length) % items.length;
    }

    private T getFirst() {
        return items[getFirstIndex()];
    }

    private T getLast() {
        return items[getLastIndex()];
    }

    private void forwardFirst() {
        nextFirst = (nextFirst - 1 + items.length) % items.length;
    }

    private void backwardFirst() {
        nextFirst = (nextFirst + 1) % items.length;
    }

    private void forwardLast() {
        nextLast = (nextLast - 1 + items.length) % items.length;
    }

    private void backwardLast() {
        nextLast = (nextLast + 1) % items.length;
    }

    private void resize(int capacity) {
        T[] a = (T[]) new Object[capacity];
        int first = getFirstIndex();
        int last = getLastIndex();
        if (first < last){
            System.arraycopy(items, first, a, 0, size);
        }
        else if (first <= items.length && last >= 0) {
            System.arraycopy(items, first, a, 0, items.length - first);
            System.arraycopy(items, 0, a, items.length - first, last + 1);
        }
        items = a;
        getIndex(items.length - 1,size);
    }

    private void checkResize() {
        if ((size < items.length / 4 + 1) && (items.length >= 16)) {
            resize(items.length / 2);
        }
    }
    public void addFirst(T item) {
        if (size == items.length) {
            resize(size * 2);
        }
        items[nextFirst] = item;
        forwardFirst();
        size += 1;
    }

    public void addLast(T item) {
        if (size == items.length) {
            resize(size * 2);
        }
        items[nextLast] = item;
        backwardLast();
        size += 1;
    }

    public T get(int index) {
        return items[(nextFirst + 1 + index) % items.length];
    }
    public void printDeque() {
        for(int i = 0; i < size; i++) {
            System.out.print(get(i) + " ");
        }
        System.out.println();
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        checkResize();
        T x = getFirst();
        items[getFirstIndex()] = null;
        backwardFirst();
        size -= 1;
        return x;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        checkResize();
        T x = getLast();
        items[getLastIndex()] = null;
        forwardLast();
        size -= 1;
        return x;
    }

    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    private class ArrayDequeIterator implements Iterator<T> {
        private int pos;

        ArrayDequeIterator() {
            pos = 0;
        }

        public boolean hasNext() {
            return pos < size;
        }

        public T next() {
            T returnItem = (T) get(pos);
            pos += 1;
            return returnItem;
        }
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }

        if (!(o instanceof Deque)) {
            return false;
        }

        Deque<T> array = (Deque<T>) o;

        if (size() != array.size()) {
            return false;
        }
        for (int i = 0; i < size(); i++) {
            T item1 = get(i);
            T item2 = array.get(i);
            if (!item1.equals(item2)) {
                return false;
            }
        }
        return true;
    }
}
