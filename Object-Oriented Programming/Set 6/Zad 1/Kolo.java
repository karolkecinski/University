import java.io.*;

public class Kolo extends Figury implements Serializable
{
  double radius;

  public Kolo(double r)
  {
    radius = r;
  }

  @Override
  public double pole()
  { return radius * radius * 3.14159265359; }

  @Override
  public double obwod()
  { return 2 * radius * 3.14159265359; }

  @Override
  public String toString()
  {
    String str = "Kolo: promien: " + radius ;
    str = str +" obwod: " + obwod() + " pole: " + pole();
    return str;
  }
}