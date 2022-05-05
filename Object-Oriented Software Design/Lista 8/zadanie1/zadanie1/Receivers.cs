using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Receivers
{
    public interface IReceiver
    {
        public void Action(string arg);
    }

    public class CopyReceiver : IReceiver
    {
        public void Action(string arg)
        {
            Console.WriteLine("Copied file to: " + arg);
        }
    }
    public class CreateReceiver : IReceiver
    {
        public void Action(string arg)
        {
            Console.WriteLine("Created file: " + arg);
        }
    }
    public class HTTPReceiver : IReceiver
    {
        public void Action(string arg)
        {
            Console.WriteLine("HTTP: Downloaded a file from " + arg);
        }
    }
    public class FTPReceiver : IReceiver
    {
        public void Action(string arg)
        {
            Console.WriteLine("FTP: Downloaded a file from " + arg);
        }
    }
}
