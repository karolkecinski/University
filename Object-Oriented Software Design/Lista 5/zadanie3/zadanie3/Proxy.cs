using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using zadanie3;

namespace Proxies
{
    public class AirportProtectingProxy : IAirport
    {
        private Airport _airport;
        private int OPENING_HOUR = 8;
        private int CLOSING_HOUR = 22;

        public AirportProtectingProxy(int capacity)
        {
            _airport = new Airport(capacity);
        }

        public void EnsureOpen()
        {
            int NOW = DateTime.Now.Hour;
            if(NOW >= CLOSING_HOUR || NOW < OPENING_HOUR)
            {
                throw new Exception(string.Format("Airport closed."));
            }
        }

        public Plane AcquirePlane()
        {
            EnsureOpen();
            return this._airport.AcquirePlane();
        }

        public void ReleasePlane(Plane plane)
        {
            EnsureOpen();
            this._airport.ReleasePlane(plane);
        }
    }

    public class LoggingProxy : IAirport
    {
        private Airport _airport;

        public LoggingProxy(int capacity)
        {
            _airport = new Airport(capacity);
        }

        public Plane AcquirePlane()
        {
            Console.WriteLine("Logging Proxy: Method called\nDATE: {0}\nMETHOD: AcquirePlane()\nPARAMETERS: None", DateTime.Now);
            Plane plane = _airport.AcquirePlane();
            Console.WriteLine("Logging Proxy: Method executed\nDATE: {0}\nRETURNED: {1}", DateTime.Now, plane);
            return plane;
        }

        public void ReleasePlane(Plane plane)
        {
            Console.WriteLine("Logging Proxy: Method called\nDATE: {0}\nMETHOD: ReleasePlane()\nPARAMETERS: {1}", DateTime.Now, plane);
            _airport.ReleasePlane(plane);
            Console.WriteLine("Logging Proxy: Method executed\nDATE: {0}\nRETURNED: None", DateTime.Now);
        }
    }
}
