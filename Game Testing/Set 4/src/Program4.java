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

public class Program4
{
    @ParameterizedTest
    @ValueSource(ints = {2, 3})
    void test_wierzcholkow1(int x) throws Exception {
        assertEquals("Różnoboczny",
                Punkt.classifyTriangle(new Punkt(x, 0), new Punkt(0, 0), new Punkt(7, 1)));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, -1})
    void test_wierzcholkow2(int y) throws Exception {
        assertEquals("Równoramienny",
                Punkt.classifyTriangle(new Punkt(0, y), new Punkt(0, 0), new Punkt(1, 0)));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 5, 7})
    void test_wierzcholkow3(int x) throws Exception {
        assertEquals("Równoboczny",
                Punkt.classifyTriangle(new Punkt(0, 0), new Punkt(2*x, 0), new Punkt(x, x*1.73205081)));
    }

    @Test
    void test_wierzcholkowException() throws Exception {
        Exception e = assertThrows(Exception.class, () -> {
                Punkt.classifyTriangle(new Punkt(1, 2), new Punkt(2, 4), new Punkt(3, 6));
        });
        assertEquals("Not a triangle", e.getMessage());
    }

    @TestFactory
    Collection<DynamicTest> simpleDynamicTestExample() throws Exception {
        return Arrays.asList(
                dynamicTest("Różnoboczny", () -> assertEquals("Różnoboczny", Punkt.classifyTriangle(new Punkt(2, 0), new Punkt(0, 0),new Punkt(7, 1)))),
                dynamicTest("Równoramienny", () -> assertEquals("Równoramienny", Punkt.classifyTriangle(new Punkt(1, 0), new Punkt(0, 0),new Punkt(0,1)))),
                dynamicTest("Równoboczny", () -> assertEquals("Równoboczny", Punkt.classifyTriangle(new Punkt(0,0), new Punkt(2, 0),new Punkt(1, 1.73205081))))
        );
    }
}
