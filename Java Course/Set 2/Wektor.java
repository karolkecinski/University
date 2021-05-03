public class Wektor
{
    public final double dx;
    public final double dy;

    public Wektor(double dx, double dy)
    {
        this.dx = dx;
        this.dy = dy;
    }

    public static Wektor addVectors(Wektor w, Wektor v)
    {
        Wektor newVector = new Wektor((w.dx + v.dx), (w.dy + v.dy));
        return newVector;
    }

}