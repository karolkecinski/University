public class Stala implements Wyrazenie
{
  int value;

  public Stala(int x)
  {
     this.value = x;
  }

  public int Oblicz()
  { return value; }

  public String toString()
  { return "" + value; }
}