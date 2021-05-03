package obliczenia;

public class ResztaZDzielenia extends Wyrazenie
{
    Wyrazenie w1;
    Wyrazenie w2;

    public ResztaZDzielenia(Wyrazenie w1, Wyrazenie w2)
    {
        this.w1 = w1;
        this.w2 = w2;
    }

    @Override
    public double oblicz()
    {
        return w1.oblicz() % w2.oblicz();
    }

    @Override
    public String toString()
    {
        return String.format("(%s mod %s)", w1.toString(), w2.toString());
    }

    @Override
    public boolean equals(Object o)
    {
        return o instanceof ResztaZDzielenia
                && ((ResztaZDzielenia) o).w1.equals(this.w1)
                && ((ResztaZDzielenia) o).w2.equals(this.w2);
    }
}
