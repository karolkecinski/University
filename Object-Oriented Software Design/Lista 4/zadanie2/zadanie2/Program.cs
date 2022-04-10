using Shapes;
public class Program
{
    public static void Main(string[] args)
    {
        ShapeFactory factory = new ShapeFactory();
        IShape square = factory.CreateShape("Square", 5);

        factory.RegisterWorker(new RectangleFactoryWorker());
        IShape rect = factory.CreateShape("Rectangle", 3, 5);
    }
}