import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.io.*;
import java.util.Scanner;

@SuppressWarnings("unchecked")

public abstract class Figury implements Comparable<Figury>
{
    public static void main(String[] args)
    {
        List<Figury> figury = new ArrayList<Figury>();

        figury.add(new Kwadrat(2));
        figury.add(new Kwadrat(7));
        figury.add(new Prostokat(2,4));
        figury.add(new Kolo(10));
        Collections.sort(figury);

        try
        {
        FileOutputStream fos= new FileOutputStream("figury.txt");
        ObjectOutputStream oos= new ObjectOutputStream(fos);
        oos.writeObject(figury);
        oos.flush();
        oos.close();
        }
        catch(IOException ioe)
        {
            ioe.printStackTrace();
        }
        odczyt();
    }


    public abstract double pole();
	public abstract double obwod();

    @Override
	public int compareTo(Figury o)
    {
		return (int) Math.signum(pole() - o.pole());
	}

    public static void odczyt() 
    {
        FileInputStream fis = null;
        ObjectInputStream ois = null;

        List<Figury> fig = null;

        try
        {
            fis = new FileInputStream("figury.txt");
            ois = new ObjectInputStream(fis);
            fig = (List<Figury>) ois.readObject();
        }
        catch (FileNotFoundException err)
        {
            err.printStackTrace();
        }
        catch (IOException err)
        {
            err.printStackTrace();
        }
        catch (ClassNotFoundException err)
        {
            err.printStackTrace();
        }

        for(Figury obj : fig)
            System.out.println(obj);
  }
}