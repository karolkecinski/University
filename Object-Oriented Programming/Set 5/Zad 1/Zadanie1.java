import java.util.Collections;
import java.util.Scanner;

// Main
public class Zadanie1
{

  public static void main(String[] args)
  {
    Zadanie1 generate = new Zadanie1();
    generate.generate();
  }

  public void generate()
  {
    Lista<Integer> lista = new Lista<Integer>();
    Scanner s = new Scanner(System.in);
    int choice=5, program=1, liczba = 0;
    while(program != 0)
    {
      System.out.print("\nWybierz, co chcesz zrobic:\n");
      System.out.print("1.Add element to list\n");
      System.out.print("2.Get element from list\n");
      System.out.print("3.Print list\n");
      System.out.print("4.Exit\n\n");
      choice = s.nextInt();

      switch(choice)
      {
        case 1: System.out.print("Enter value\n");
        liczba = s.nextInt();
        lista.Add(liczba);
        break;

        case 2:
        try
        {
          liczba = lista.Pop();
        }
        catch(NullPointerException err)
        {
          System.out.print("error: Empty List\n");
          break;
        }

        System.out.print("Got Element: " + liczba + "\n");
        break;

        case 3: lista.PrintCollection();
        break;

        case 4: program = 0;
        break;

        default:
        System.out.println("error: Input Error");
        break;
      }
    }
  }

  public class Lista<T extends Comparable>
  {

    private Lista<T> top;
    private Lista<T> next;
    private T value;

    public void Add(T a)
    {
      if(top == null)
      {
        top = new Lista<T>();
        top.value = a;
        top.next = null;
      }
      else
      {
        if(top.value.compareTo(a) > 0)
        {

          Lista<T> nowy = new Lista<T>();
          nowy.value = top.value;
          top.value = a;
          nowy.next = top.next;
          top.next = nowy;

        }else{

          Lista<T> auxiliary = top;

          while(auxiliary.next != null)
          {
            if(auxiliary.next.value.compareTo(a) > 0)
              break;
            auxiliary = auxiliary.next;
          }

          Lista<T> nowy = new Lista<T>();
          nowy.value = a;
          nowy.next = auxiliary.next;
          auxiliary.next = nowy;

        }
      }
    }

    public T Pop()
    {
      T get_val = top.value;

      if(top.next != null)
      {
        top = top.next;
      }else
        top = null;
      return get_val;
    }

    public void PrintCollection()
    {
      if(top == null)
      {
          System.out.print("Empty list");
          return;
      }

      Lista<T> get_val = top;
      System.out.print("List:\n");

      while (get_val != null)
      {
          System.out.print(get_val.value + "\n");
          get_val = get_val.next;
      }
    }
  }
}