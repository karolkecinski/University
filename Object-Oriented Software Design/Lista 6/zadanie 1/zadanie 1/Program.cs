using System;
using System.IO;

namespace zadanie1
{
    public interface ILogger
    {
        void Log(string Message);
    }
    public enum LogType { None, Console, File }

    public class FileLogger : ILogger
    {
        private string _path;

        public FileLogger(string path)
        {
            this._path = path;
        }

        private async Task Write(string Message)
        {
            await System.IO.File.AppendAllTextAsync(this._path, Message);
        }

        public void Log(string Message)
        {
            _ = Write(Message);
        }
    }

    public class ConsoleLogger : ILogger
    {
        public void Log(string Message)
        {
            Console.WriteLine(Message);
        }
    }

    public class NullLogger : ILogger
    {
        public void Log(string Message) { }
    }

    public class LoggerFactory
    {
        private static LoggerFactory _instance;
        public ILogger GetLogger(LogType LogType, string Parameters = null)
        {
            if(LogType == LogType.File)
            {
                return new FileLogger(Parameters);
            } 
            else if (LogType == LogType.Console)
            {
                return new ConsoleLogger();
            } 
            else if (LogType == LogType.None)
            {
                return new NullLogger();
            } 
            else
            {
                throw new Exception();
            }

        }

        public static LoggerFactory Instance() {
            if(_instance == null)
            {
                return _instance = new LoggerFactory();
            }
            return _instance;
        }
    }

    class Program
    {
        static void Main(string[] args)
        {
            //File.WriteAllText()
            var loggerFactory = LoggerFactory.Instance();
            // klient:
            ILogger logger1 = loggerFactory.GetLogger(LogType.File, "C:\\foo.txt");
            logger1.Log("foo bar 2"); // logowanie do pliku
            ILogger logger2 = loggerFactory.GetLogger(LogType.Console);
            logger2.Log("qux"); // brak logowania
        }
    }
}
