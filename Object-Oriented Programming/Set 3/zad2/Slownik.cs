using System;

namespace Slownik
{
  public class Slownik<K, V>
    where K : IComparable<K>
  {
    public Slownik<K, V> elem;
    public K klucz;
    public V D_value;

    public void Dodaj(K klucz, V war)
    {
        if (this.elem != null)
        {
            if (klucz.CompareTo(this.elem.klucz) == 0)
            {
                Console.WriteLine("Element o takim indeksie juz istnieje!");
                return;
            }
            this.elem.Dodaj(klucz, war);
        }
        else
        {
            this.elem = new Slownik<K, V>();
            this.elem.klucz = klucz;
            this.elem.D_value = war;
            Console.WriteLine("Dodano " + war + " na miejsce " + klucz);
        }
    }

    public void Wypisz()
    {
        if (this.elem != null)
        {
            Console.WriteLine(this.elem.klucz + " " + this.elem.D_value);
            this.elem.Wypisz();
        }
    }

    public void Usun(K klucz)
    {
        if (this.elem != null)
        {
            if (klucz.CompareTo(this.elem.klucz) == 0)
            {
                Console.WriteLine("Element usuwany: " + this.elem.D_value);
                this.elem = this.elem.elem;
            }
            else
                this.elem.Usun(klucz);
        }
        else
            Console.WriteLine("Nie ma elementu o indeksie: " + klucz);
    }

    public void Szukaj(K klucz)
    {
        if (klucz.CompareTo(this.klucz) != 0 && this.elem != null)
            this.elem.Szukaj(klucz);
        else
        {
            if (klucz.CompareTo(this.klucz) == 0)
                Console.WriteLine("Element o numerze " + klucz + " to " +
                this.D_value);
            else
            {
                Console.WriteLine("Nie ma takiego elementu!");
            }
        }
    }
  }
}