import obliczenia.*;

public class Main {

    public static void main(String[] args) {

        //3 + 5
        Wyrazenie w1 = new Dodaj(new Liczba(3), new Liczba(5));
        System.out.println(w1.toString());
        System.out.println(w1.oblicz());
        System.out.println();

        //-(2 - x) * 7
        Zmienna.dodajZmienna("x", 4.0);
        Wyrazenie w2 = new Mnoz(new Negacja(new Odejmij(
                                                    new Liczba(2),
                                                    new Zmienna("x"))),
                                new Liczba(7));
        System.out.println(w2.toString());
        System.out.println(w2.oblicz());
        System.out.println();

        //(3*11-1)/(7+5)
        Wyrazenie w3 = new Dziel(new Odejmij(
                                            new Mnoz(new Liczba(3), new Liczba(11)),
                                            new Liczba(1)),
                                 new Dodaj(new Liczba(7), new Liczba(5)));
        System.out.println(w3.toString());
        System.out.println(w3.oblicz());
        System.out.println();

        //min((x+13) * x, (1-x) mod 2)
        Wyrazenie w4 = new Minimum(
                                    new Mnoz(
                                             new Dodaj(new Zmienna("x"), new Liczba(13)),
                                             new Zmienna("x")),
                                    new ResztaZDzielenia(
                                                         new Odejmij(new Liczba(1), new Zmienna("x")),
                                                         new Liczba(2)));
        System.out.println(w4.toString());
        System.out.println(w4.oblicz());
        System.out.println();

        //pow(2, 5)+x*log(2, y)
        Zmienna.dodajZmienna("y", 8.0);
        Wyrazenie w5 = new Mniejsze(
                            new Dodaj(
                                new Potegowanie(
                                        new Liczba(2),
                                        new Liczba(5)),
                                new Mnoz(
                                        new Zmienna("x"),
                                        new Logarytm(
                                                new Liczba(2),
                                                new Zmienna("y")))),
                            new Liczba(50));
        System.out.println(w5.toString());
        System.out.println(w5.oblicz());
    }
}