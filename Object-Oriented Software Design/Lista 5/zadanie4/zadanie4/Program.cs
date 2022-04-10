using System.Collections;

public class ComparisonToIComparerAdapter<T> : IComparer
{
    private readonly Comparison<T> _comparison;

    public ComparisonToIComparerAdapter(Comparison<T> comparison)
    {
        this._comparison = comparison;
    }
    public int Compare(object x, object y)
    {
        return _comparison((T)x, (T)y);
    }
}
class Program
{
    static int IntComparer(int x, int y)
    {
        return x.CompareTo(y);
    }
    static void Main(string[] args)
    {
        ArrayList a = new ArrayList() { 1, 5, 3, 3, 2, 4, 3 };
        IComparer comparer = new ComparisonToIComparerAdapter<int>(IntComparer);
        a.Sort(comparer);

        foreach (var x in a)
        {
            Console.Write("{0}, ", x);
        }

        Console.ReadLine();
    }
}