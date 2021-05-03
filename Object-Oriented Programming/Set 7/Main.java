import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("unchecked")

// klasa z obsługą okienka
public class Main
{
  List<Pojazd> pojazd = new ArrayList<Pojazd>();
  public static void main(String[] args)
  {
    Main start = new Main();
    start.start();
  }

  public void start()
  {
    JFrame frame = new JFrame("Interfejs do edycji");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    Container Storage = frame.getContentPane();
    GridLayout layout = new GridLayout(6, 2);
    Storage.setLayout(layout);


    JLabel D1 = new JLabel("Marka/nazwa");
    Storage.add(D1);
    JTextField Data1 = new JTextField(20);
    Storage.add(Data1);

    JLabel D2 = new JLabel("Kolor samochodu/Ilosc miejsc w tramwaju");
    Storage.add(D2);
    JTextField Data2 = new JTextField(20);
    Storage.add(Data2);

    JLabel D3 = new JLabel("Osiagana predkosc");
    Storage.add(D3);
    JTextField Data3 = new JTextField(20);
    Storage.add(Data3);

    JLabel D4 = new JLabel("Typ: S - samochod / T - tramwaj");
    Storage.add(D4);
    JTextField Data4 = new JTextField(20);
    Storage.add(Data4);

    JButton b = new JButton("Dodaj");
    b.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        String x = Data1.getText();
        String y = Data2.getText();
        String z = Data3.getText();
        String Typ = Data4.getText();

        if(Typ.equals("S"))
        {
          pojazd.add(new Samochod(x,y,z));
          System.out.print("Dodano samochod: ");
          System.out.println(x);
          System.out.print(" koloru ");
          System.out.println(y);
          System.out.print(" Maksymalna predkosc: ");
          System.out.print(z);
          System.out.print(" km/h\n");
        }

        if(Typ.equals("T"))
        {
          pojazd.add(new Tramwaj(x,y,z));
          System.out.print("Dodano tramwaj: ");
          System.out.println(x);
          System.out.print("Posiada on ");
          System.out.print(y);
          System.out.print(" miejsc \n Maksymalnie osiaga ");
          System.out.print(z);
          System.out.print(" km/h\n");
        }
      }
    });
    Storage.add(b);

    JButton LoadButton = new JButton("Wczytaj");
    LoadButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        FileInputStream fis = null;
        ObjectInputStream ois = null;

        List<Pojazd> Get_Objects = null;

        try
        {
          fis = new FileInputStream("ProgramData.bin");
          ois = new ObjectInputStream(fis);
          Get_Objects = (List<Pojazd>) ois.readObject();
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

        for(Pojazd obj : Get_Objects)
          System.out.println(Get_Objects);
      }
    });
    Storage.add(LoadButton);

    JButton SaveButton = new JButton("Zapisz");
    SaveButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        try
        {
          FileOutputStream fos= new FileOutputStream("ProgramData.bin");
          ObjectOutputStream oos= new ObjectOutputStream(fos);
          oos.writeObject(pojazd);
          oos.flush();
          oos.close();
          System.out.println("\nSuccessfully Saved");
        }
        catch(IOException ioe)
        {
             ioe.printStackTrace();
        }
      }
    }
    );
    Storage.add(SaveButton);

    frame.pack();
    frame.setVisible(true);
  }
}