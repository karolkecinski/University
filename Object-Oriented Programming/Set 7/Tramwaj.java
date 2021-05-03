import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;

public class Tramwaj extends Pojazd implements Serializable
{
  String nazwa;
  String ilosc_miejsc;

  public Tramwaj(String x, String y, String z)
  {
    nazwa = x;
    ilosc_miejsc = y;
    predkosc = z;
  }

  @Override
  public String toString()
  {
    String str = "Tramwaj: " + nazwa + " Ilosc miejsc: " + ilosc_miejsc + " Predkosc: " + predkosc + " km/h";
    return str;
  }
}