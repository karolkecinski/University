package obliczenia;

public class Stala extends Wyrazenie
{
    double c;

    public Stala(double c)
    {
        this.c = c;
    }

    @Override
    public double oblicz() { return c; }

    @Override
    public String toString()
    {
        return Double.toString(c);
    }

    @Override
    public boolean equals(Object o)
    {
        return o instanceof Stala && ((Stala) o).c == this.c;
    }
}