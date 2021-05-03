package obliczenia;

import java.util.HashMap;

public class Zmienna extends Wyrazenie
{
    private static HashMap<String, Double> variables = new HashMap<>();
    private String z;

    public Zmienna(String z)
    {
        this.z = z;
    }

    public static void dodajZmienna(String z, Double val)
    {
        variables.put(z, val);
    }

    @Override
    public double oblicz()
    {
        return variables.get(z);
    }

    @Override
    public String toString()
    {
        return z;
    }

    @Override
    public boolean equals(Object o)
    {
        return o instanceof Zmienna
                && ((Zmienna) o).z.equals(z);
    }
}
