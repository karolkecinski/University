package obliczenia;

public class Dziel extends Wyrazenie {

    Wyrazenie w1;
    Wyrazenie w2;

    public Dziel(Wyrazenie w1, Wyrazenie w2)
    {
        this.w1 = w1;
        this.w2 = w2;
    }

    @Override
    public double oblicz()
    {
        return w1.oblicz() / w2.oblicz();
    }

    @Override
    public String toString()
    {
        return String.format("(%s / %s)", w1.toString(), w2.toString());
    }

    @Override
    public boolean equals(Object o)
    {
        return o instanceof Dziel
                && ((Dziel) o).w1.equals(this.w1)
                && ((Dziel) o).w2.equals(this.w2);
    }
}
