using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using zadanie3;
using Proxies;

public class Program
{
    public static void Main(string[] args)
    {
        LoggingProxy HamburgFlughafen = new LoggingProxy(3);

        Plane plane = HamburgFlughafen.AcquirePlane();
        HamburgFlughafen.ReleasePlane(plane);

        AirportProtectingProxy LuebeckFlughafen = new AirportProtectingProxy(1);

        Plane plane2 = LuebeckFlughafen.AcquirePlane();
        LuebeckFlughafen.ReleasePlane(plane2);
    }
}
