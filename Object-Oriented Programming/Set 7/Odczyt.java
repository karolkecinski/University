import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("unchecked")

class Odczyt extends Pojazd implements ActionListener
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
    {
      System.out.println(Get_Objects);
    }
  }
}