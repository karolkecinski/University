package obliczenia;

import java.lang.Math.*;

public class Silnia extends Wyrazenie
{
    Wyrazenie w;

    public Silnia(Wyrazenie w)
    {
        this.w = w;
    }

    @Override
    public double oblicz()
    {
        double res = 1;
        double limit = Math.floor(w.oblicz());

        for(double i = 2.0; i <= limit; i += 1.0)
        {
            res *= i;
        }

        return res;
    }

    @Override
    public String toString()
    {
        return String.format("!%s", w.toString());
    }

    @Override
    public boolean equals(Object o)
    {
        return o instanceof Silnia
                && ((Silnia) o).w.equals(this.w);
    }
}
