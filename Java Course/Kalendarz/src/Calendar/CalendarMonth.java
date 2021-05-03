package Calendar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class CalendarMonth extends JPanel
{
    GUI window;
    JLabel month;
    JPanel dayTable;
    Date date;

    public CalendarMonth(GregorianCalendar calendar, GUI parent)
    {
        this.window = parent;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        this.setBackground(new Color(200, 200, 200));
        
        date = calendar.getTime();

        month = new JLabel(new SimpleDateFormat("MMMM", Locale.ENGLISH).format(calendar.getTime()));
        month.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        month.setAlignmentX(Component.CENTER_ALIGNMENT);

        dayTable = new JPanel(new GridLayout(6, 7, 3, 3));

        for (int i = 0; i < 42; i++)
        {
            CalendarDay day = new CalendarDay(i % 7);
            dayTable.add(day);
        }

        setMonth(calendar);
        this.add(month);
        this.add(dayTable);

        this.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                calendar.set(Calendar.MONTH, date.getMonth());
                window.fireContentsChanged();
                window.switchTab(1);
            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
                CalendarMonth.this.setBackground(new Color(180, 150, 150));
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                CalendarMonth.this.setBackground(new Color(200, 200, 200));
            }
        });
    }

    public void setMonth(GregorianCalendar calendar)
    {
        for (int i = 0; i < 42; i++)
            dayTable.getComponent(i).setVisible(false);

        Date original = calendar.getTime();
        int start = (calendar.get(Calendar.DAY_OF_WEEK) + 5) % 7;

        while (calendar.get(Calendar.MONTH) == original.getMonth())
        {
            dayTable.getComponent(start).setVisible(true);
            ((CalendarDay) dayTable.getComponent(start)).setNumber(calendar.get(Calendar.DAY_OF_MONTH));
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            start++;
        }

        calendar.setTime(original);
    }
}
