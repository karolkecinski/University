using System;
using finance;
class Program
{
    static void Main(string[] args) 
    {
        CashRegister cr = new CashRegister();

        Item[] items = new Item[] {
        new Item(1.00, "pear"),
        new Item(2.00, "apple"),
        new Item(1.50, "plum")
        };

        Console.WriteLine("Cakowita wartość:");
        Console.WriteLine(Decimal.Round(cr.CalculatePrice(items), 2));

        cr.tax = 0.50m;
        Console.WriteLine("Cakowita wartość po podwyżce podatku do 50%:");
        Console.WriteLine(Decimal.Round(cr.CalculatePrice(items), 2));

        Console.WriteLine("Produkty Alfabetycznie:");
        cr.PrintBill(items, new OrderByName());

        Console.WriteLine("Produkty według ceny:");
        cr.PrintBill(items, new OrderByPrice());
    }
}