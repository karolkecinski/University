package obliczenia;

public class Liczba extends Wyrazenie
{
    double x;

    public Liczba(double x)
    {
        this.x = x;
    }

    @Override
    public double oblicz() { return x; }

    @Override
    public String toString()
    {
        return Double.toString(x);
    }

    @Override
    public boolean equals(Object o)
    {
        return o instanceof Liczba && ((Liczba) o).x == this.x;
    }
}
