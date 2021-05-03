import org.jetbrains.annotations.NotNull;

import java.util.Date;

public class Termin
{
    @NotNull Date start;
    @NotNull Date koniec;

    public Termin(@NotNull Date start, @NotNull Date koniec) throws  IllegalArgumentException
    {
        if(koniec.before(start))
            throw  new IllegalArgumentException("Data końcowa nie może być wcześniej niż data początkowa.");
        this.start = start;
        this.koniec = koniec;
    }
    public boolean czyNachodzą(@NotNull Termin termin)
    {
        if(this.start.before(termin.start))
        {
           if(this.koniec.after(termin.start))
                return false;
        }
        else
        {
            if(termin.koniec.after(this.start))
                return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "{" + start + ", " + koniec + "}";
    }
}
