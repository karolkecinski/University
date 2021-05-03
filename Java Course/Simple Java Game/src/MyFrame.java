import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

public class MyFrame extends JFrame
{
    MyPanel panel = new MyPanel(Mikolaj.n, Mikolaj.m, Mikolaj.x + 50, Mikolaj.y + 50);
    public static Dziecko dzieci[] = new Dziecko [Mikolaj.liczbaDzieci];

    public MyFrame()
    {
        this.setTitle("Mikołaj i dzieciaki");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(Mikolaj.x+57, Mikolaj.y+76);
        this.setResizable(false);

        this.add(panel);
        ActionListener l = new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                repaint();
            }
        };

        Timer T = new Timer(50, l);
        T.start();

        for(int i = 0; i < Mikolaj.liczbaDzieci; i++)
        {
            dzieci[i] = new Dziecko(i+1);
            new Thread(dzieci[i]).start();
        }

        this.setVisible(true);
    }
}
