import java.util.Hashtable;

public class Zmienna implements Wyrazenie
{
  Hashtable<String, Integer> Val;
  String Calculation;

  public Zmienna(String Calculation, Hashtable<String, Integer> sign)
  {
    this.Calculation = Calculation; 
    this.Val = sign;
  }

  public int Oblicz()
  { return Val.get(Calculation); }

  public String toString()
  { return Calculation; }
}