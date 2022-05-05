using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Receivers;

namespace Commands
{
    public interface ICommand
    {
        void Execute();
    }
    public class CopyCommand : ICommand
    {
        public string src;
        public string dst;
        private IReceiver receiver;

        public CopyCommand(string src, string dst)
        {
            this.src = src;
            this.dst = dst;
            this.receiver = new CopyReceiver();
        }

        public void Execute()
        {
            receiver.Action(this.dst);
        }
    }

    public class CreateCommand : ICommand
    {
        public string path;
        private IReceiver receiver;

        public CreateCommand(string path)
        {
            this.path = path;
            this.receiver = new CreateReceiver();
        }

        public void Execute()
        {
            receiver.Action(this.path);
        }
    }

    public class HTTPCommand : ICommand
    {
        public string endpoint;
        private IReceiver receiver;

        public HTTPCommand(string endpoint)
        {
            this.endpoint = endpoint;
            this.receiver = new HTTPReceiver();
        }

        public void Execute()
        {
            receiver.Action(this.endpoint);
        }
    }

    public class FTPCommand : ICommand
    {
        public string endpoint;
        private IReceiver receiver;

        public FTPCommand(string endpoint)
        {
            this.endpoint = endpoint;
            this.receiver = new FTPReceiver();
        }

        public void Execute()
        {
            receiver.Action(this.endpoint);
        }
    }
}
