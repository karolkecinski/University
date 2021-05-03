using System;

namespace Lista
{
    public class Elem<T>
    {
        public T value;
        public Elem<T> kolejny;
        public Elem<T> poprzedni;

        public Elem()
        {
            kolejny = null;
            poprzedni = null;
            value = default(T);
        }
    }


    public class Lista<T>
      where T : IComparable<T>
    {

        public Elem<T> first;
        public Elem<T> last;

        public Lista()
        {
            first = null;
            last = null;
        }

        public void AddFirst(T x)
        {
            if (first == null)
            {
                first = new Elem<T>();
                last = new Elem<T>();
                first.value = x;
                last = first;
            }
            else
            {
                Elem<T> nowy = new Elem<T>();
                nowy.value = x;
                nowy.kolejny = first;
                nowy.poprzedni = null;
                first.poprzedni = nowy;
                if (first.value.CompareTo(last.value) == 0)
              {
                    last = nowy.kolejny;
                }
                first = nowy;
            }
        }

        public void AddLast(T x)
        {
            if (last == null)
            {
                first = new Elem<T>();
                last = new Elem<T>();
                first.value = x;
                last = first;
            }
            else
            {
                Elem<T> nowy = new Elem<T>();
                nowy.value = x;
                nowy.kolejny = null;
                last.kolejny = nowy;
                nowy.poprzedni = last;
                last = nowy;
            }

        }

        public T DelFirst()
        {
            if (first == null)
            {
                Console.WriteLine("Nie mozna usunac elementu z listy pustej!");
                return default(T);
            }
            else
            {
                T temp = first.value;
                if (first == last)
                {
                    last = null;
                    first = null;
                    return temp;
                }
                first = first.kolejny;
                if (first != null)
                    first.poprzedni = null;
                return temp;
            }
        }

        public T DelLast()
        {
            if (last == null)
            {
                Console.WriteLine("Nie mozna usunac elementu z listy pustej!");
                return default(T);
            }
            else
            {
                T temp = last.value;
                if (first == last)
                {
                    last = null;
                    first = null;
                    return temp;
                }
                last = last.poprzedni;
                if (last != null)
                    last.kolejny = null;
                return temp;
            }
        }

        public bool Empty()
        {
            if (first == null)
                return true;
            else
                return false;
        }

        public void WriteAll()
        {
            if (first == null)
            {
                Console.WriteLine("Lista jest pusta!");
                return;

            }
            Elem<T> temp = first;
            Console.WriteLine("Lista zawiera:");
            while (temp != null)
            {
                Console.WriteLine(temp.value);
                temp = temp.kolejny;
            }
        }

        public void FirstElem()
        {
            if (first != null)
                Console.WriteLine("Pierwszy element to: " + first.value);
        }

        public void LastElem()
        {
            if (last != null)
                Console.WriteLine("Ostatni element to: " + last.value);
        }
    }
}
