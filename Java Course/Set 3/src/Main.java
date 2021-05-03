import struktury.*;
import java.io.*;

public class Main
{
    public static void main(String[] args) throws Exception
    {

            Para p1 = new Para("x", 2.0);
            Para p2 = new Para("y", 5.0);

            System.out.println(p1);
            ZbiorNaTablicy zbior = new ZbiorNaTablicy(4);
            zbior.wstaw(p1);

            System.out.println(zbior.szukaj("x"));
            //System.out.println(zbior.szukaj("y"));

            zbior.wstaw(p2);
            System.out.println(zbior.szukaj("y"));

            zbior.usun("y");
            //System.out.println(zbior.szukaj("y"));

            Para p3 = new Para("x", 1.0);
            /*try{
            zbior.ustaw(p3);
            }
            catch (Exception e)
            {
                System.out.println(e);
            }*/

            System.out.println(zbior.szukaj("x"));
    }
}
