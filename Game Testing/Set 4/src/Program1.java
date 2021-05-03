import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Collection;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

public class Program1
{
    public static ArrayList<String> Cut(ArrayList<String> l, String s) throws IllegalArgumentException
    {
        if(l == null && s == null)
            throw new IllegalArgumentException("Arguments: l, s: both are null");
        if(s == null)
            throw new IllegalArgumentException("Argument s is null");
        if(l == null)
            throw new IllegalArgumentException("Argument l is null");

        ArrayList<String> list = new ArrayList<String>();

        for(String str : l)
        {
            if(!str.equals(s))
            {
                list.add(str);
            }
        }

        return list;
    }

    @Test
    public void test1() throws Exception {
        ArrayList<String> list = new ArrayList<String>();
        list.add("XxXyYy");
        Exception e = assertThrows(Exception.class, () -> {Cut(list, null);});
    }

    @Test
    public void test2() throws Exception {
        String s = "XxXyYy";
        Exception e = assertThrows(Exception.class, () -> {Cut(null, s);});
    }

    @Test
    public void test3() throws Exception {
        Exception e = assertThrows(Exception.class, () -> {Cut(null, null);});
    }

    @Test
    public void test4() throws Exception {
        ArrayList<String> list = new ArrayList<String>();
        list.add("ABCDE");
        list.add("BC");
        list.add("BAC");
        list.add("BCASD");

        ArrayList<String> result = new ArrayList<String>();
        result.add("ABCDE");
        result.add("BAC");
        result.add("BCASD");
        assertEquals(Cut(list, "BC"), result);
    }
}
