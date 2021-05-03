using System;
using System.Collections;

public class Primes : IEnumerator
{
    private int iter;
    public int max;

    private bool IsPrime(int n)
    {
        if (n < 2) return false;
        for (int i = 2; i < n; i++)
        {
            if (n % i == 0) return false;
        }
        return true;
    }

    public Primes()
    {
        iter = 1;
        max = int.MaxValue;
    }

    public Primes(int limit)
    {
        iter = 1;
        max = limit;
    }

    public bool MoveNext()
    {
        iter++;
        while (!IsPrime(iter)) iter++;
        return iter < max;
    }

    public void Reset()
    {
        iter = 1;
    }

    public object Current
    {
        get
        {
            return iter;
        }
    }
}
class PrimeCollection : IEnumerable
{
    private int limit = int.MaxValue;

    public IEnumerator GetEnumerator()
    {
        return new Primes(limit);
    }

    public PrimeCollection(int limiter)
    {
        limit = limiter;
    }
}

class MainClass
{
    public static void Main()
    {
        int limit;
        Primes obj = new Primes();
        bool stan = true;
        while (stan)
        {
            obj.Reset();
            Console.WriteLine("Aby wypisać liczby pierwsze, wpisz 1");
            Console.WriteLine("Aby zakończyć program, podaj liczbę 0");

            limit = Int32.Parse(Console.ReadLine());
            
            if (limit == 0)
            {
                stan = false;
                continue;
            }

            limit = 10000;

            PrimeCollection pc = new PrimeCollection(limit);
            Console.WriteLine("");
            foreach (int p in pc)
                Console.WriteLine(p);
        }
    }
}