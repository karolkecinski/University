public class Prosta
{
    public double a, b, c;

    public static boolean rownolegle(Prosta k, Prosta l)
    {
        if((k.a/k.b) == (l.a/l.b))
            return true;

        return false;
    }

    public static boolean prostopadle(Prosta k, Prosta l)
    {
        if((k.a/k.b) * (l.a/l.b) == -1)
            return true;

        return false;
    }

    public static Punkt punktPrzeciecia(Prosta k, Prosta l)
    {
        if(!rownolegle(k, l))
        {
            double x = ((k.b*l.c)-(l.b*k.c)/(k.a*l.b)-(l.a*k.b));
            double y = ((l.a*k.c)-(k.a*l.c)/(k.a*l.b)-(l.a*k.b));

            return new Punkt(x, y);
        }
        else
        {
            //throw new Exception("Parallel lines.");  
            return new Punkt(0,0);
        }
    }

    public Prosta(double a, double b, double c)
    {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public void przesunProsta(Wektor w)
    {
        this.c = c - (a * w.dx) - (b * w.dy);
    }
}