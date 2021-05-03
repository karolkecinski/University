package struktury;

import java.util.Objects;

public class Para
{
    public final String klucz;
    private double wartosc;

    public Para(String klucz, double wartosc)
    {
        this.klucz = klucz;
        this.wartosc = wartosc;
    }

    public double get()
    {
        return wartosc;
    }

    public void set(double val)
    {
        this.wartosc = val;
    }

    @Override
    public String toString() {
        return  "<" + klucz + ": " +
                wartosc + '>';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Para para = (Para) o;

        /*
        return Double.compare(para.wartosc, wartosc) == 0 &&
                Objects.equals(klucz, para.klucz);
         */

        return para.wartosc == this.wartosc;
    }

    /*
    @Override
    public int hashCode() {
        return Objects.hash(klucz, wartosc);
    }
     */
}
