package Calendar;

import javax.swing.*;
import java.awt.*;

public class App
{
    public static void main(String[] args)
    {
        GUI window = new GUI("Calendar");

        window.pack();
        window.setVisible(true);
        window.setSize(new Dimension(1300, 900));
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
