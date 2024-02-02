package deque;

import java.util.Iterator;

/**import java.util.LinkedList;*/

public class LinkedListDeque<T> implements Deque<T>, Iterable<T> {
    private class Node {
        private T item;
        private Node next;
        private Node prev;
        Node(T item,Node next,Node prev) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }
    private int size;
    private Node sentinel;
    public LinkedListDeque() {
        sentinel = new Node(null,null,null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    public void addFirst(T item) {
        Node prevFirstNode = sentinel.next;
        Node firstNode = new Node(item,prevFirstNode,sentinel);
        prevFirstNode.prev = firstNode;
        sentinel.next = firstNode;
        size += 1;
    }

    public void addLast(T item) {
        Node prevLastNode = sentinel.prev;
        Node lastNode = new Node(item,sentinel,prevLastNode);
        prevLastNode.next = lastNode;
        sentinel.prev = lastNode;
        size += 1;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        Node p = sentinel.next;
        while (p!=sentinel) {
            System.out.print(p.item+" ");
            p = p.next;
        }
        System.out.println();
    }

    private Node getFirstNode() {
        return sentinel.next;
    }

    public T removeFirst() {
        Node prevFirstNode = getFirstNode();
        if (prevFirstNode == sentinel) {
            return null;
        }
        Node firstNode = prevFirstNode.next;
        T item = prevFirstNode.item;
        sentinel.next = firstNode;
        firstNode.prev = sentinel;
        size -= 1;
        return item;
    }

    private Node getLastNode() {
        return sentinel.prev;
    }

    public T removeLast() {
        Node prevLastNode = getLastNode();
        if (prevLastNode == sentinel) {
            return null;
        }
        Node lastNode = prevLastNode.prev;
        T item = prevLastNode.item;
        sentinel.prev = lastNode;
        lastNode.next = sentinel;
        size -= 1;
        return item;
    }

    public T get(int index) {
        Node p = getFirstNode();
        while (p!=sentinel) {
            if (index == 0) {
                return p.item;
            }
            index -= 1;
            p = p.next;
        }
        return null;
    }

    private T getRecursiveHelper(int index,Node p) {
        if (p == sentinel) {
            return null;
        }
        if (index == 0) {
            return p.item;
        }
        return getRecursiveHelper(index - 1, p.next);
    }
    public T getRecursive(int index) {
        return getRecursiveHelper(index,sentinel.next);
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListDequeIterator();
    }

    private class LinkedListDequeIterator implements Iterator<T> {
        private Node iterNode;

        LinkedListDequeIterator() {
            iterNode = sentinel.next;
        }

        public boolean hasNext() {
            return iterNode == sentinel;
        }

        public T next() {
            T returnItem = iterNode.item;
            iterNode = iterNode.next;
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
        for (int i = 0;i < size();i++) {
            T item1 = get(i);
            T item2 = array.get(i);
            if (item1 != item2) {
                return false;
            }
        }
        return true;
    }
}
