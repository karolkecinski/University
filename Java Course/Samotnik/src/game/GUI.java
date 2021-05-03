package game;

import jdk.jfr.events.ExceptionThrownEvent;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GUI {

    private JFrame frame;
    private JLabel label;
    private JPanel panel;

    public GUI()
    {
        frame = new JFrame();

        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(500, 500, 500, 500));
        panel.setLayout(new GridLayout(0, 1));

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Samotnik");
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args)
    {
        GUI gui = new GUI();
        try {
            Thread.sleep(1000);
        } catch(Exception e) { }
        gui.drawBoard();
    }

    public void drawBoard(/*int[][] b*/)
    {
        Graphics g;
        g = panel.getGraphics();
        g.setColor(Color.red);
        g.fillRect(100, 100, 50, 50);
    }
}
