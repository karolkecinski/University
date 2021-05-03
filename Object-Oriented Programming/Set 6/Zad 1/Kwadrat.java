import java.io.*;

public class Kwadrat extends Figury implements Serializable
{
  double x;

  public Kwadrat(double a)
  {
    x = a;
  }

  @Override
  public double pole()
  { return x * x; }

  @Override
  public double obwod()
  { return 4 * x; }

  @Override
  public String toString()
  {
    String str = "Kwadrat: dlugosc boku: " + x ;
    str = str +" obwod: " + obwod() + " pole: " + pole();
    return str;
  }
}