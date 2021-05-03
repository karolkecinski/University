import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class KalendarzTest
{
    Kalendarz k = new Kalendarz();

    @Test 
    public void dodajWydarzenie_1() throws Exception
    {
        Termin t = new Termin(new Date(2021,3,15), new Date(2021,4,15));
        k.dodajWydarzenie("Test1", t);
        assertEquals(t.toString(), k.zwrocTermin("Test1").toString());
    }
    @Test 
    public void dodajWydarzenie_2() throws Exception
    {
        Date d = new Date(2021,4,15);
        Termin t = new Termin(d, d);
        k.dodajWydarzenie("Test2", t);
        assertEquals(t.toString() , k.zwrocTermin("Test2").toString());
    }
    @Test 
    public void dodajWydarzenie_3() throws Exception
    {
        Date d = new Date(2020,3,20);
        k.dodajWydarzenie("Test3", d);
        Termin t = new Termin(d, d);
        assertEquals(t.toString(), k.zwrocTermin("Test3").toString());
    }
    @Test
    public void modyfikujWydarzenie_1() throws Exception
    {
        Termin t = new Termin(new Date(2022,5,22), new Date(2022,6,1));
        k.modyfikujWydarzenie("Test1", t);
        assertEquals(t.toString(), k.zwrocTermin("Test1").toString());
    }
    @Test 
    public void modyfikujWydarzenie_2() throws Exception
    {
        Termin t = new Termin(new Date(2021,5,20), new Date(2021,5,20));
        k.modyfikujWydarzenie("Test4", t);
        assertEquals(t.toString(), k.zwrocTermin("Test4").toString());
    }
    @Test  
    public void usunWydarzenie_1() throws Exception
    {
        k.usunWydarzenie("Test1");
        assertEquals(null, k.zwrocTermin("Test1"));
    }

}
