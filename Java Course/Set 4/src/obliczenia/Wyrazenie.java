package obliczenia;

public abstract class Wyrazenie implements Obliczalny {

    public static double suma (Wyrazenie... wyr)
    {
        double suma = 0;

        for(Wyrazenie w : wyr)
        {
            suma += w.oblicz();
        }

        return suma;
    }

    public static double iloczyn (Wyrazenie... wyr)
    {
        double iloczyn = 1;

        for(Wyrazenie w : wyr)
        {
            iloczyn *= w.oblicz();
        }

        return iloczyn;
    }
}
