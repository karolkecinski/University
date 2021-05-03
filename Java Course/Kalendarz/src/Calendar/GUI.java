package Calendar;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;

public class GUI extends JFrame
{
    private JTabbedPane calendarTabbedPane;
    private JPanel mainPanel;
    private JPanel monthsInYearTab;
    private JPanel daysInMonthTab;
    private JToolBar toolbar;

    private JSpinner yearSpinner;
    private JPanel currentYearPanel;
    private JScrollBar monthScrollBar;

    private GregorianCalendar calendar;
    private CalendarMonth months[];

    private JList next;
    private JList current;
    private JList previous;

    public GUI(String title) throws HeadlessException
    {
        super(title);
        setContentPane(mainPanel);

        calendar = (GregorianCalendar) GregorianCalendar.getInstance();
        yearSpinner.setValue(calendar.getTime().getYear() + 1900);
        monthScrollBar.setValue(calendar.getTime().getMonth() + 1);

        months = new CalendarMonth[12];

        populateYearPanel();
        registerListeners();
        fireContentsChanged();
    }

    private void createUIComponents()
    {
        currentYearPanel = new JPanel(new GridLayout(3, 4, 5, 5));
        previous = new JList(new MonthList());
        current = new JList(new MonthList());
        next = new JList(new MonthList());

        previous.setCellRenderer(new MonthCellRenderer());
        current.setCellRenderer(new MonthCellRenderer());
        next.setCellRenderer(new MonthCellRenderer());

        previous.setBackground(Color.decode("#E0E0FF"));
        current.setBackground(Color.decode("#E3E3E5"));
        next.setBackground(Color.decode("#E0E0FF"));

        previous.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        current.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        next.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));

        yearSpinner = new JSpinner(new SpinnerNumberModel(1, 1, null, 1));
        monthScrollBar = new JScrollBar();
    }

    private void registerListeners()
    {
        yearSpinner.addChangeListener(e -> {
            if ((int) yearSpinner.getValue() < 1)
                return;
            calendar.set(Calendar.YEAR, (int) yearSpinner.getValue());
            fireContentsChanged();
        });

        monthScrollBar.addAdjustmentListener(e -> {
            calendar.set(Calendar.MONTH, monthScrollBar.getValue() - 1);
            fireContentsChanged();
        });

        previous.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                if (calendar.get(Calendar.YEAR) == 1 && calendar.get(Calendar.MONTH) == Calendar.JANUARY)
                    return;

                calendar.add(Calendar.MONTH, -1);
                fireContentsChanged();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                previous.setBackground(Color.decode("#CCCCE0"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                previous.setBackground(Color.decode("#E0E0FF"));
            }
        });

        next.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseReleased(MouseEvent e)
            {
                calendar.add(Calendar.MONTH, 1);
                fireContentsChanged();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                next.setBackground(Color.decode("#CCCCE0"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                next.setBackground(Color.decode("#E0E0FF"));
            }
        });
    }

    private void populateYearPanel()
    {
        Date originalDate = calendar.getTime();
        calendar.set(calendar.get(Calendar.YEAR), Calendar.JANUARY, 1);

        for (int i = 0; i < 12; i++)
        {
            months[i] = new CalendarMonth(calendar, this);
            currentYearPanel.add(months[i]);
            calendar.add(Calendar.MONTH, 1);
        }

        calendar.setTime(originalDate);
    }

    private void updateYearPanel()
    {
        Date originalDate = calendar.getTime();
        calendar.set(calendar.get(Calendar.YEAR), Calendar.JANUARY, 1);

        for (CalendarMonth month : months)
        {
            month.setMonth(calendar);
            calendar.add(Calendar.MONTH, 1);
        }

        calendar.setTime(originalDate);
    }

    private void updateMonthLists()
    {
        Date now = calendar.getTime();
        ((MonthList) current.getModel()).changeDate(now);

        if (now.getYear() + 1900 != 1 || now.getMonth() != 0)
        {
            now.setMonth(now.getMonth() - 1);
            ((MonthList) previous.getModel()).changeDate(now);
        } else
            ((MonthList) previous.getModel()).changeDate(null);

        if (now.getYear() != 1 || now.getMonth() != 1)
            now.setMonth(now.getMonth() + 1);
        now.setMonth(now.getMonth() + 1);
        ((MonthList) next.getModel()).changeDate(now);
    }

    public void fireContentsChanged()
    {
        updateYearPanel();
        updateMonthLists();
        calendarTabbedPane.setTitleAt(0, "" + yearSpinner.getValue());
        calendarTabbedPane.setTitleAt(1, new SimpleDateFormat("MMMM", Locale.ENGLISH).format(calendar.getTime()));
        yearSpinner.setValue(calendar.get(Calendar.YEAR));
        monthScrollBar.setValue(calendar.get(Calendar.MONTH) + 1);
    }

    public void switchTab(int i) {
        calendarTabbedPane.setSelectedIndex(i);
    }

    class MonthCellRenderer extends JLabel implements ListCellRenderer
    {
        public MonthCellRenderer() {
            setHorizontalAlignment(CENTER);
        }

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            String string = (String) value;
            if (string.contains("Sun"))
                setForeground(Color.RED);
            else
                setForeground(Color.BLACK);
            setText(string);
            return this;
        }
    }

}
