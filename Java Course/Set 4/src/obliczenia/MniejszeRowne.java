package obliczenia;

public class MniejszeRowne extends Wyrazenie
{
    Wyrazenie w1;
    Wyrazenie w2;

    public MniejszeRowne(Wyrazenie w1, Wyrazenie w2)
    {
        this.w1 = w1;
        this.w2 = w2;
    }

    @Override
    public double oblicz()
    {
        if(w1.oblicz() <= w2.oblicz())
            return 1.0;

        return 0.0;
    }

    @Override
    public String toString()
    {
        return String.format("(%s <= %s)", w1.toString(), w2.toString());
    }

    @Override
    public boolean equals(Object o)
    {
        return o instanceof MniejszeRowne
                && ((MniejszeRowne) o).w1.equals(this.w1)
                && ((MniejszeRowne) o).w2.equals(this.w2);
    }
}
