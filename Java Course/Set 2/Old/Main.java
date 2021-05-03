public class Main
{
    public static void main(String[] args)
    {
        Punkt p1 = new Punkt (1.0, 4.0);
        Odcinek odc = new Odcinek(1.0,1.0,2.0,2.0);
        Wektor w = new Wektor(5.0, 9.0);
        Prosta l = new Prosta(1.0, 2.0, 3.0);

        odc.przesun(w);
        System.out.println(odc.p1);
        System.out.println(odc.p2);
        System.out.println("\n");

        odc.obroc(p1, 90.0);
        System.out.println(odc.p1);
        System.out.println(odc.p2);
        System.out.println("\n");

        odc.odbij(l);

        System.out.println(odc.p1);
        System.out.println(odc.p2);
        System.out.println("\n");

        Punkt t1 = new Punkt(1.0, 6.0);
        Punkt t2 = new Punkt(7.0, -1.0);
        Punkt t3 = new Punkt(-6.0, 5.0);

        Trojkat tr = new Trojkat(t1, t2, t3);

        System.out.println(tr.p1);
        System.out.println(tr.p2);
        System.out.println(tr.p3);
        System.out.println("\n");

        tr.odbij(l);

        System.out.println(tr.p1);
        System.out.println(tr.p2);
        System.out.println(tr.p3);
        System.out.println("\n");

        tr.przesun(w);

        System.out.println(tr.p1);
        System.out.println(tr.p2);
        System.out.println(tr.p3);
        System.out.println("\n");

        tr.obroc(t1, 215.0);

        System.out.println(tr.p1);
        System.out.println(tr.p2);
        System.out.println(tr.p3);
        System.out.println("\n");

    }
}