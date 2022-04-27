using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Shapes
{
    public abstract class Shape
    {
    }

    public class Triangle : Shape
    {
        float a, b, c;
    }

    public class Rectangle : Shape
    {
        public virtual float Width { get; set; }
        public virtual float Height { get; set; }

        public Rectangle(float width, float height)
        {
            this.Width = width;
            this.Height = height;
        }
    }

    public class Square: Rectangle
    {
        public float Side { get; set; }

        public override float Width 
        {
            get { return Side; }
            set { this.Side = value; }
        }
        public override float Height
        {
            get { return Side; }
            set { this.Side = value; }
        }

        public Square(float Side) : base(Side, Side)
        {
            this.Side = Side;
        }
    }
}
