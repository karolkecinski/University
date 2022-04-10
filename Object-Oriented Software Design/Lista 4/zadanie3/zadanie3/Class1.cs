using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace zadanie3
{
    public interface IPlane { }

    public class Plane : IPlane { }

    // Object pool
    public class Airport
    {
        private int capacity;
        private List<Plane> ready = new List<Plane>();
        private List<Plane> released = new List<Plane>();

        public Airport(int capacity)
        {
            if(capacity <= 0)
            {
                throw new ArgumentException();
            }
            this.capacity = capacity;
        }

        public Plane AcquirePlane()
        {
            if(released.Count() == this.capacity)
            {
                throw new ArgumentException();
            }

            if(ready.Count() == 0)
            {
                Plane newPlane = new Plane();
                ready.Add(newPlane);
            }

            var plane = ready[0];
            ready.Remove(plane);
            released.Add(plane);

            return plane;
        }

        public void ReleasePlane(Plane plane)
        {
            if( !released.Contains(plane))
            {
                throw new ArgumentException();
            }

            released.Remove(plane);
            ready.Add(plane);
        }
    }
}
