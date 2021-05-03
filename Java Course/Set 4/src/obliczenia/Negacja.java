package obliczenia;

public class Negacja extends Wyrazenie
{
    Wyrazenie w;

    public Negacja(Wyrazenie w)
    {
        this.w = w;
    }

    @Override
    public double oblicz()
    {
        return -w.oblicz();
    }

    @Override
    public String toString()
    {
        return String.format("(-%s)", w.toString());
    }

    @Override
    public boolean equals(Object o)
    {
        return o instanceof Negacja
                && ((Negacja) o).w.equals(this.w);
    }
}
