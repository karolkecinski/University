import java.util.Calendar;
import structures.OrderedList;

public class Main {
    public static void main(String args[]) {
        OrderedList<Integer> lista = new OrderedList<Integer>(1);
        lista.insert(2);
        lista.insert(3);
        lista.insert(5);
        System.out.println(lista);

        lista.insert(4);

        System.out.println(lista);

        System.out.println(lista.min());
        System.out.println(lista.max());

        System.out.println(lista.search(2));
        System.out.println(lista.search(6));

        System.out.println(lista.index(2));
        System.out.println(lista.index(4));

        System.out.println(lista.at(2));
        System.out.println(lista.at(3));

        lista.remove(2);
        System.out.println(lista);

        for (Integer el : lista)
            System.out.print(el + " ");
        System.out.println();

        System.out.println();
        System.out.println();

        OrderedList<String> lista2 = new OrderedList<String>("a");

        lista2.insert("ab");
        lista2.insert("bc");
        lista2.insert("bcd");
        lista2.insert("bcde");

        System.out.println(lista2);

        System.out.println(lista2.min());
        System.out.println(lista2.max());

        System.out.println(lista2.search("bc"));
        System.out.println(lista2.search("x"));

        System.out.println(lista2.index("bc"));
        System.out.println(lista2.index("x"));

        System.out.println(lista2.at(2));
        System.out.println(lista2.at(7));

        lista2.remove("bc");
        System.out.println(lista2);

        for (String el : lista2)
            System.out.print(el + " ");
        System.out.println();

        System.out.println();
        System.out.println();
    }
}