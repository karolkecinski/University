package Calendar;

import javax.swing.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class MonthList extends AbstractListModel
{
    int year;
    int month;

    public MonthList() { }

    public void changeDate(Date date)
    {
        if (date == null)
        {
            year = 0;
        }
        else
        {
            year = date.getYear() + 1900;
            month = date.getMonth();
        }

        fireContentsChanged(this, 0, getSize() - 1);
    }

    @Override
    public int getSize()
    {
        if (year == 0)
            return 0;
        if (year == 1582 && month == 9) //Numeracja 0 - 11
            return 21;

        Calendar c = Calendar.getInstance();
        c.set(year, month, 1);
            return c.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    @Override
    public Object getElementAt(int i)
    {
        Calendar c = Calendar.getInstance();
        c.set(year, month, 1);

        while (i-- != 0)
            c.add(Calendar.DAY_OF_MONTH, 1);

        return "" + new SimpleDateFormat("EEE", Locale.ENGLISH).format(c.getTime())
                + ", "
                + c.get(Calendar.DAY_OF_MONTH)
                + " "
                + new SimpleDateFormat("MMMM", Locale.ENGLISH).format(c.getTime());
    }
}
