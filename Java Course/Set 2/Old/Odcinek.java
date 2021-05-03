public class Odcinek
{
    Punkt p1;
    Punkt p2;

    public Odcinek(Punkt p1, Punkt p2)
    {
        if(p1.x != p2.x || p1.y != p2.y)
        {
            this.p1 = p1;
            this.p2 = p2;
        }
        else
        {
            throw new IllegalArgumentException("Failed to create object: p1, p2 are the same point.");
        }
    }

    public Odcinek(double x1, double y1, double x2, double y2)
    {
        if(x1 != x2 || y1 != y2)
        {
            this.p1 = new Punkt(x1, y1);
            this.p2 = new Punkt(x2, y2);
        }
        else
        {
            throw new IllegalArgumentException("Failed to create object: p1, p2 are the same point.");
        }
    }

    public void przesun(Wektor w)
    {
        this.p1.x = this.p1.x + w.dx;
        this.p1.y = this.p1.y + w.dy;
        this.p2.x = this.p2.x + w.dx;
        this.p2.y = this.p2.y + w.dy;
    }

    public void odbij(Prosta p)
    {
        p1.odbij(p);
        p2.odbij(p);
    }

    public void obroc(Punkt p, double alpha)
    {
        p1.obroc(p, alpha);
        p2.obroc(p, alpha);
    }
}