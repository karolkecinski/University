package Calendar;

import javax.swing.*;
import java.awt.*;

public class CalendarDay extends JPanel
{
    JLabel number;
    JLabel description;
    String dayNames[] = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};

    public CalendarDay(int dayNum)
    {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        if (dayNum == 6) {
            this.setBackground(new Color(255,100,100));
        }

        number = new JLabel("#");
        number.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        number.setAlignmentX(Component.CENTER_ALIGNMENT);

        description = new JLabel(dayNames[dayNum]);
        description.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
        description.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.add(number);
        this.add(description);
    }

    public void setNumber(int number) {
        this.number.setText("" + number);
    }
}
