import java.util.Hashtable;

public class Zadanie2
{
  public static void main(String[] args)
  {
    Hashtable<String, Integer> Hash_tab = new Hashtable<String, Integer>();
    try
    {
      Hash_tab.put("x",1);
      Hash_tab.put("y",4);

      Wyrazenie zmienna = new Add(new Stala(2), new Zmienna("x", Hash_tab));
      System.out.println(zmienna.toString() + "=" + zmienna.Oblicz());
      
      Wyrazenie mnozenie = new Mult(new Zmienna("y", Hash_tab), new Stala(5));
      System.out.println(mnozenie.toString() + "=" + mnozenie.Oblicz());

      Wyrazenie odj = new Substract(new Zmienna("y", Hash_tab), new Stala(4));
      System.out.println(odj.toString() + "=" + odj.Oblicz());

      Wyrazenie Div = new DivideBy(new Stala(8), new Stala(4));
      System.out.println(Div.toString() + "=" + Div.Oblicz());
    }
    catch(ArithmeticException err)
    {
      System.out.print("Error");
    }
  }
}