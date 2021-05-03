import java.io.*;


public class Prostokat extends Figury implements Serializable
{

  double a;
  double b;

  public Prostokat(double x, double y)
  {
    a = x;
    b = y;
  }

  @Override
  public double pole()
  { return a * b; }

  @Override
  public double obwod()
  { return 2 * a + 2 * b; }

  @Override
  public String toString()
  {
    String str = "Prostokat: dlugosc: " + a + " szerokosc: " + b;
    str = str +" obwod: " + obwod() + " pole: " + pole();
    return str;
  }
}