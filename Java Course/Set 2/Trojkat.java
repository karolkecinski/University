public class Trojkat
{
    Punkt p1, p2, p3;

    static boolean checkTriangle(Punkt p1, Punkt p2, Punkt p3) 
    {  
        double area =   p1.x * (p2.y - p3.y) + 
                        p2.x * (p3.y - p1.y) + 
                        p3.x * (p1.y - p2.y); 
  
    if (area == 0) return false; 
    
    return true;
} 

    public Trojkat(Punkt p1, Punkt p2, Punkt p3)
    {
        if(checkTriangle(p1, p2, p3))
        {
            this.p1 = p1;
            this.p2 = p2;
            this.p3 = p3;
        }
        else
        {
            //throw new Exception("Failed to create object: p1, p2, p3 cannot form triangle.");
            this.p1 = new Punkt(0.0, 0.0);
            this.p2 = new Punkt(0.0, 0.0);
            this.p3 = new Punkt(0.0, 0.0);
        }
    }

    public Trojkat(double x1, double y1, double x2, double y2, double x3, double y3)
    {
        Punkt a1 = new Punkt(x1, y1);
        Punkt a2 = new Punkt(x2, y2);
        Punkt a3 = new Punkt(x3, y3);

        if(checkTriangle(a1, a2, a3))
        {
            this.p1 = a1;
            this.p2 = a2;
            this.p3 = a3;
        }
        else
        {
            //throw new Exception("Failed to create object: p1, p2, p3 cannot form triangle.");
            this.p1 = new Punkt(0.0, 0.0);
            this.p2 = new Punkt(0.0, 0.0);
            this.p3 = new Punkt(0.0, 0.0);
        }
    }

    public void przesun(Wektor w)
    {
        this.p1.x = this.p1.x + w.dx;
        this.p1.y = this.p1.y + w.dy;
        this.p2.x = this.p2.x + w.dx;
        this.p2.y = this.p2.y + w.dy;
        this.p3.x = this.p3.x + w.dx;
        this.p3.y = this.p3.y + w.dy;
    }

    public void odbij(Prosta p)
    {
        p1.odbij(p);
        p2.odbij(p);
        p3.odbij(p);
    }

    public void obroc(Punkt p, double alpha)
    {
        p1.obroc(p, alpha);
        p2.obroc(p, alpha);
        p3.obroc(p, alpha);
    }
}