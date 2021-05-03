package structures;

import java.util.Iterator;

public class OrderedList<T extends Comparable<T>> implements OrderedSequence<T>, Iterable<T>
{
    private class Node <T extends Comparable<T>>
    {
        private Node<T> next;
        private T data;

        public Node(T data)
        {
            this.data = data;
        }

        public Node() { }
    }

    private Node<T> start;

    public OrderedList(T data)
    {
        this.start = new Node<T>(data);
    }

    public OrderedList() { }

    @Override
    public void insert(T el) throws NullPointerException
    {
        if(el == null)
            throw new NullPointerException();

        if(this.start == null)
        {
            this.start = new Node<T>(el);
            return;
        }

        Node<T> whereAreWeNow = start;

        if(el.compareTo(start.data) < 0)
        {
            Node<T> newBeginning = new Node<T>(el);
            newBeginning.next = start;
            start = newBeginning;
            return;
        }

        while(whereAreWeNow.next != null)
        {
            if(el.compareTo(whereAreWeNow.next.data) < 0)
            {
                Node<T> newBeginning = new Node<T>(el);
                newBeginning.next = whereAreWeNow.next;
                whereAreWeNow.next = newBeginning;
                return;
            }
            whereAreWeNow = whereAreWeNow.next;
        }
        if(el.compareTo(whereAreWeNow.data) > 0)
            whereAreWeNow.next = new Node<T>(el);
    }

    @Override
    public void remove(T el)
    {
        if(!this.search(el))
            return;

        if(start.data == el)
        {
            start = start.next;
            return;
        }

        Node<T> whereAreWeNow = start;
        while(whereAreWeNow.next != null)
        {
            if(el == whereAreWeNow.next.data)
            {
                whereAreWeNow.next = whereAreWeNow.next.next;
                return;
            }
            whereAreWeNow = whereAreWeNow.next;
        }

        if (whereAreWeNow.data == el)
            whereAreWeNow = null;
    }

    @Override
    public T min()
    {
        return start.data;
    }

    @Override
    public T max()
    {
        Node<T> whereAreWeNow = start;
        while(whereAreWeNow.next != null)
        {
            whereAreWeNow = whereAreWeNow.next;
        }
        return whereAreWeNow.data;
    }

    @Override
    public T at(int pos)
    {
        int ind = 0;
        Node<T> whereAreWeNow = start;

        while(whereAreWeNow.next != null)
        {
            if(ind == pos)
                return whereAreWeNow.data;

            whereAreWeNow = whereAreWeNow.next;
            ind++;
        }

        if(ind == pos)
            return whereAreWeNow.data;

        return null;
    }

    @Override
    public boolean search(T el)
    {
        Node<T> whereAreWeNow = start;

        while(whereAreWeNow.next != null)
        {
            if(whereAreWeNow.data == el)
                return true;

            whereAreWeNow = whereAreWeNow.next;
        }

        if(whereAreWeNow.data == el)
            return true;

        return false;
    }

    @Override
    public int index(T el)
    {
        if(!this.search(el))
            return -1;

        int ind = 0;

        Node<T> whereAreWeNow = start;

        while (whereAreWeNow.next != null)
        {
            if(whereAreWeNow.data == el)
                return ind;

            whereAreWeNow = whereAreWeNow.next;
            ind++;
        }
        return ind;
    }

    @Override
    public String toString()
    {
        Node<T> whereAreWeNow = start;
        String s = "";

        while(whereAreWeNow.next != null)
        {
            s += whereAreWeNow.data + ", ";
            whereAreWeNow = whereAreWeNow.next;
        }

        s += whereAreWeNow.data;
        return s;
    }

    private class MyIterator implements Iterator<T>
    {
        Node<T> whereAreWeNow;
        OrderedList<T> l;

        public MyIterator(OrderedList<T> l)
        {
            this.l = l;
            this.whereAreWeNow = l.start;
        }

        @Override
        public boolean hasNext()
        {
            return whereAreWeNow != null;
        }

        @Override
        public T next()
        {
            T data = whereAreWeNow.data;
            whereAreWeNow = whereAreWeNow.next;
            return data;
        }

        @Override
        public void remove()
        {
            l.remove(whereAreWeNow.data);
        }
    }

    public Iterator<T> iterator()
    {
        return new MyIterator(this);
    }
}