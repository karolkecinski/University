import java.util.Arrays;
import java.math.*;

public class Punkt
{
    double x, y;

    public Punkt(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public static boolean checkTriangle(Punkt p1, Punkt p2, Punkt p3)
    {
        double area =   p1.x * (p2.y - p3.y) +
                p2.x * (p3.y - p1.y) +
                p3.x * (p1.y - p2.y);

        if (Math.abs(area) < 0.01) return false;

        return true;
    }

    static double square(double x)
    {
        return x * x;
    }

    static double euclidDistSquare(Punkt p1, Punkt p2)
    {
        return square(p1.x - p2.x) + square(p1.y - p2.y);
    }

    // Method to classify side
    static String getSideClassification(double a, double b, double c)
    {
        if (Math.abs(a-b) < 0.0001 && Math.abs(b-c) < 0.0001)
            return "Równoboczny";

        else if (Math.abs(a-b) < 0.0001 || Math.abs(b-c) < 0.0001)
            return "Równoramienny";

        else
            return "Różnoboczny";
    }

    public static String classifyTriangle(Punkt p1, Punkt p2, Punkt p3) throws Exception
    {
        if(!checkTriangle(p1, p2, p3)) {
            throw new Exception("Not a triangle");
        }

        // Find squares of distances between Punkts
        double a = euclidDistSquare(p1, p2);
        double b = euclidDistSquare(p1, p3);
        double c = euclidDistSquare(p2, p3);

        // Sort all squares of distances in increasing order
        double []copy = new double[3];
        copy[0] = a;
        copy[1] = b;
        copy[2] = c;
        Arrays.sort(copy);
        a = copy[0];
        b = copy[1];
        c = copy[2];

        return getSideClassification(a, b, c);
    }

    public String toString()
    {
        return "(" + String.valueOf(x) + ", " + String.valueOf(y) + ") ";
    }
}