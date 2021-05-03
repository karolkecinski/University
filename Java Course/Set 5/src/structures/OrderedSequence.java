package structures;

public interface OrderedSequence<T extends Comparable<T>> {
    public void insert(T el);
    public void remove(T el);
    public T min();
    public T max();
    public T at(int pos);
    public boolean search(T el);
    public int index(T el);
}
