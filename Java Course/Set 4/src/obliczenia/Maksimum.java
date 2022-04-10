package obliczenia;

public class Maksimum extends Wyrazenie
{
    Wyrazenie w1;
    Wyrazenie w2;

    public Maksimum(Wyrazenie w1, Wyrazenie w2)
    {
        this.w1 = w1;
        this.w2 = w2;
    }

    @Override
    public double oblicz()
    {
        if(w1.oblicz() > w2.oblicz())
            return w1.oblicz();

        return w2.oblicz();
    }

    @Override
    public String toString()
    {
        return String.format("max(%s, %s)", w1.toString(), w2.toString());
    }

    @Override
    public boolean equals(Object o)
    {
        return o instanceof Maksimum
                && ((Maksimum) o).w1.equals(this.w1)
                && ((Maksimum) o).w2.equals(this.w2);
    }
}