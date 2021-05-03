package obliczenia;

import java.lang.Math.*;

public class Logarytm extends Wyrazenie
{
    Wyrazenie w1;
    Wyrazenie w2;

    public Logarytm(Wyrazenie w1, Wyrazenie w2)
    {
        this.w1 = w1;
        this.w2 = w2;
    }

    @Override
    public double oblicz()
    {
        return Math.log(w2.oblicz())/Math.log(w1.oblicz());
    }

    @Override
    public String toString()
    {
        return String.format("log_%s(%s)", w1.toString(), w2.toString());
    }

    @Override
    public boolean equals(Object o)
    {
        return o instanceof Logarytm
                && ((Logarytm) o).w1.equals(this.w1)
                && ((Logarytm) o).w2.equals(this.w2);
    }
}
