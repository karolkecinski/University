using Microsoft.VisualStudio.TestTools.UnitTesting;
using Shapes;

namespace Tests
{
    [TestClass]
    public class UnitTests
    {
        [TestMethod]
        public void TestSquareArea()
        {
            int x = 11;
            ShapeFactory factory = new ShapeFactory();
            IShape square = factory.CreateShape("Square", x);
            Assert.AreEqual(square.area(), x * x);
        }

        [TestMethod]
        public void TestRectangleArea()
        {
            int x = 8, y = 9;
            ShapeFactory factory = new ShapeFactory();
            factory.RegisterWorker(new RectangleFactoryWorker());
            IShape rectangle = factory.CreateShape("Rectangle", x, y);
            Assert.AreEqual(rectangle.area(), x * y);
        }

        [TestMethod]
        public void TestCompareShapes()
        {
            int x = 5, y = 6;
            ShapeFactory factory = new ShapeFactory();
            factory.RegisterWorker(new RectangleFactoryWorker());
            IShape square = factory.CreateShape("Square", x);
            IShape rectangle = factory.CreateShape("Rectangle", x, y);
            Assert.AreNotEqual(square, rectangle);
        }

        [TestMethod]
        public void TestCompareAreas()
        {
            int x = 5, y = 6;
            ShapeFactory factory = new ShapeFactory();
            factory.RegisterWorker(new RectangleFactoryWorker());
            IShape square = factory.CreateShape("Square", x);
            IShape rectangle = factory.CreateShape("Rectangle", x, y);
            Assert.AreNotEqual(square.area(), rectangle.area());
        }
    }
}
