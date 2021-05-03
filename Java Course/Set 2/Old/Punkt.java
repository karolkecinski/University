public class Punkt
{
    double x, y;

    public Punkt(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public void przesun(Wektor w)
    {
        this.x = this.x + w.dx;
        this.y = this.y + w.dy;
    }

    public void obroc(Punkt p, double angle)
	{
        double dx = this.x - p.x; // "Przesuwamy" wyjkres do punktu p
        double dy = this.y - p.y;
        
        angle = Math.toRadians(angle); 
        
		x = dx * Math.cos(angle) - dy*Math.sin(angle) + p.x; // Obliczamy i dodajemy spowrotem przesunięcie.
		y = dx * Math.sin(angle) + dy*Math.cos(angle) + p.y;
    }
    
    public void odbij(Prosta p)
	{
        double newY = ((p.a * p.a * y) - (2*p.a * p.b * x) - (2*p.c * p.b) - (p.b*p.b*y)) / (p.a * p.a + p.b * p.b);
        
        this.x = (p.a * (newY - y)) / p.b + x;
        this.y = newY;
    }
    
    @Override 
    public String toString()
	{
		return "(" + String.valueOf(x) + ", " + String.valueOf(y) + ") ";
	}
}