import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;

public class Samochod extends Pojazd implements Serializable
{
  String kolor;
  String marka;

  public Samochod(String x, String y, String z)
  {
    marka = x;
    kolor = y;
    predkosc = z;
  }

  @Override
  public String toString()
  {
    String str ="Marka: " + marka + " kolor: " + kolor + " Predkosc: " + predkosc + " km/h";
    return str;
  }
}