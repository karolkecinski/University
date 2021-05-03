import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class DodajListy extends Pojazd implements ActionListener
{
  String name;
  String colseatnum;
  String speed;
  String typ;

  DodajListy(String a, String b ,String c, String d)
  {
    name = a;
    colseatnum = b;
    speed = c;
    typ = d;
  }
}