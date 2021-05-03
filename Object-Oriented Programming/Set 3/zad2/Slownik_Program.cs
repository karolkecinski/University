using System;

namespace Slownik
{
  class program
  {
        static void Main()
        {
            Slownik<int, string> Slownik = new Slownik<int, string>();

            int choose = 1;

            Console.WriteLine("Wybierz:");
            Console.WriteLine("1.Dodaj element do słownika");
            Console.WriteLine("2.Usun element do słownika");
            Console.WriteLine("3.Wypisz elementy słownika");
            Console.WriteLine("4.Szukaj elementu w słowniku");
            Console.WriteLine("5.Zakończ program");

            while (choose == 1)
            {
                string wybór = Console.ReadLine();
                switch (wybór)
                {
                    case "1":
                        {
                            Console.WriteLine("Podaj klucz (numer w Słowniku) elementu");
                            int miejsce = Int32.Parse(Console.ReadLine());
                            Console.WriteLine("Podaj wartość elementu:");
                            string napis = Console.ReadLine();
                            Slownik.Dodaj(miejsce, napis);
                            break;
                        }
                    case "2":
                        {
                            Console.WriteLine("Podaj numer elementu do usunięcia");
                            int miejsce = Int32.Parse(Console.ReadLine());
                            Slownik.Usun(miejsce);
                            break;
                        }
                    case "3":
                        {
                            Slownik.Wypisz();
                            break;
                        }
                    case "4":
                        {
                            Console.WriteLine("Podaj klucz elementu:");
                            int miejsce = Int32.Parse(Console.ReadLine());
                            Slownik.Szukaj(miejsce);
                            break;
                        }
                    case "5":
                        {
                            choose = 0;
                            break;
                        }
                    default:
                        {
                            System.Console.WriteLine("Błąd");
                            break;
                        }
                }
            }
        }
  }
}