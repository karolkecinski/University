using System;
using System.Collections.Generic;

namespace Shapes
{
    public interface IShape
    {
        int area();
    }

    public interface IFactoryWorker
    {
        IShape Create(params object[] parameters);
        bool acceptsParameters(string ShapeName);
    }

    public class Square : IShape
    {
        public int side { get; set; }

        public Square(int side)
        {
            this.side = side;
        }

        public int area()
        {
            return side * side;
        }
    }

    public class SquareFactoryWorker : IFactoryWorker
    {
        public IShape Create(params object[] parameters)
        {
            return new Square((int)parameters[0]);
        }
        public bool acceptsParameters(string ShapeName)
        {
            return ShapeName == "Square";
        }
    }

    public class Rectangle : IShape
    {
        public int x { get; set; }
        public int y { get; set; }

        public Rectangle(int x, int y)
        {
            this.x = x;
            this.y = y;
        }

        public int area()
        {
            return x * y;
        }
    }

    public class RectangleFactoryWorker : IFactoryWorker
    {
        public IShape Create(params object[] parameters)
        {
            return new Rectangle((int)parameters[0], (int)parameters[1]);
        }
        public bool acceptsParameters(string ShapeName)
        {
            return ShapeName == "Rectangle";
        }
    }

    public class ShapeFactory
    {
        private List<IFactoryWorker> workers = new List<IFactoryWorker>();

        public ShapeFactory()
        {
            workers.Add(new SquareFactoryWorker());
        }

        public void RegisterWorker(IFactoryWorker worker)
        {
            workers.Add(worker);
        }

        public IShape CreateShape(string Shapename, params object[] parameters)
        {
            foreach (var worker in workers)
            {
                if (worker.acceptsParameters(Shapename))
                {
                    return worker.Create(parameters);
                }
            }
            throw new ArgumentException();
        }
    }
}
