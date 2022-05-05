using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Commands;

namespace InvokerCode
{
    public class Invoker
    {
        public int limit = 10;
        private Queue<ICommand> queue = new Queue<ICommand>();

        public void Run()
        {
            Thread creator = new Thread(new ThreadStart(Create));
            Thread executor1 = new Thread(new ThreadStart(Execute));
            Thread executor2 = new Thread(new ThreadStart(Execute));

            try
            {
                creator.Start();
                executor1.Start();
                executor2.Start();

                creator.Join();
                executor1.Join();
                executor2.Join();

            }
            catch (Exception e)
            {
                Console.WriteLine(e);
                Environment.ExitCode = 1;
            }

        }

        private void Create()
        {
            ICommand command;
            while (true)
            {
                Random rand = new Random();
                switch (rand.Next() % 4)
                {
                    case 0:
                        command = new CopyCommand("/dev", "/def");
                        break;
                    case 1:
                        command = new CreateCommand("/home/newdir");
                        break;
                    case 2:
                        command = new HTTPCommand("192.168.0.1");
                        break;
                    default:
                        command = new FTPCommand("8.8.8.8");
                        break;

                }

                lock (this)
                {
                    while (queue.Count() > limit)
                    {
                        Monitor.Wait(this, 500);
                    }
                    queue.Enqueue(command);
                }
                Thread.Sleep(1000);
            }
        }

        public void Execute()
        {
            while (true)
            {
                Random rand = new Random();
                lock (this)
                {
                    while (queue.Count == 0)
                    {
                        Monitor.Wait(this, 500);
                    }
                    queue.Dequeue().Execute();
                }
                Thread.Sleep(2000);
            }

        }
    }
}
