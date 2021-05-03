class Add implements Wyrazenie
{
  Wyrazenie a;
  Wyrazenie b;

  public Add(Wyrazenie a, Wyrazenie b)
  {
    this.a = a;
    this.b = b;
  }

  public int Oblicz()
  {
    return a.Oblicz() + b.Oblicz();
  }

  public String toString()
  { return "(" + a + "+" + b + ")"; }
}



class Substract implements Wyrazenie
{
  Wyrazenie a;
  Wyrazenie b;

  public Substract(Wyrazenie a, Wyrazenie b)
  {
    this.a = a;
    this.b = b;
  }

  public int Oblicz()
  {
    return a.Oblicz() - b.Oblicz();
  }

  public String toString()
  { return "(" + a + "-" + b + ")"; }
}

class Mult implements Wyrazenie
{
  Wyrazenie a;
  Wyrazenie b;

  public Mult(Wyrazenie a, Wyrazenie b)
  {
    this.a = a;
    this.b = b;
  }

  public int Oblicz()
  {
    return a.Oblicz() * b.Oblicz();
  }

  public String toString()
  { return "(" + a + "*" + b + ")"; }
}

class DivideBy implements Wyrazenie
{
  Wyrazenie a;
  Wyrazenie b;

  public DivideBy(Wyrazenie a, Wyrazenie b)
  {
    this.a = a;
    this.b = b;
  }

  public int Oblicz()
  {
      return a.Oblicz() / b.Oblicz();
  }


  public String toString()
  { return "(" + a + "/" + b + ")"; }
}