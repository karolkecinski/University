using System;

namespace Lista
{
    class program
    {
        static void Main()
        {
            Lista<int> lista = new Lista<int>();
            string choose = "1";
            while (choose != "7")
            {
                Console.WriteLine("Wybierz akcję:");
                Console.WriteLine("1.Dodaj element na początek");
                Console.WriteLine("2.Dodaj element na koniec");
                Console.WriteLine("3.Usuń element z początku");
                Console.WriteLine("4.Usuń element z końca");
                Console.WriteLine("5.Sprawdź czy lista jest pusta");
                Console.WriteLine("6.Wypisz elementy listy");
                Console.WriteLine("7.Zakończ program");

                choose = Console.ReadLine();

                switch (choose)
                {
                    case "1":
                        {
                            Console.WriteLine("Podaj liczbę, którą chcesz wstawić na początek listy");
                            int element = Int32.Parse(Console.ReadLine());
                            lista.AddFirst(element);
                            break;
                        }
                    case "2":
                        {
                            Console.WriteLine("Podaj liczbę, którą chcesz wstawić na koniec listy");
                            int element = Int32.Parse(Console.ReadLine());
                            lista.AddLast(element);
                            break;
                        }
                    case "3":
                        {
                            int deleted_element = lista.DelFirst();
                            if (deleted_element != 0)
                                Console.WriteLine("Usunięto element " + deleted_element + " z listy");
                            break;
                        }
                    case "4":
                        {
                            int deleted_element = lista.DelLast();
                            if (deleted_element != 0)
                                Console.WriteLine("Usunieto element " + deleted_element + " z listy");
                            break;
                        }
                    case "5":
                        {
                            if (lista.Empty())
                                Console.WriteLine("Lista jest pusta");
                            else
                                Console.WriteLine("Lista zawiera jakie� elementy");
                            break;
                        }
                    case "6":
                        {
                            lista.WriteAll();
                            Console.WriteLine("");
                            lista.FirstElem();
                            lista.LastElem();
                            break;
                        }
                    case "7":
                        {
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