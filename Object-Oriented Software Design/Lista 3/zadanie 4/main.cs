/*
Zasada LSP nie jest zachowana, ponieważ klasa Square ma settery i gettery zachowujące się
inaczej niż w klasie Rectangle. Rozwiązaniem byłoby stworzenie klasy nadrzędnej Parallelogram 
z której obie te klasy będą dziedziczyły.
*/
using System; 
namespace zadanie4 {
    public abstract class Parallelogram {
        public abstract int Area();
    }

    public class Rectangle : Parallelogram {
        public int Width { get; set ;}
        public int Height { get; set; }

        public override int Area() {
            return Width * Height;
        }
    }

    public class Square : Parallelogram {
        public int Side { get; set; }

        public override int Area() {
            return Side * Side;
        }
    }

    public class AreaCalculator {
        public int CalculateArea(Parallelogram p) {
            return p.Area();
        }
    }
}