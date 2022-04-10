namespace Singleton
{
    public class SingletonOneForProcess
    {
        private static SingletonOneForProcess instance;
        private static object _lock = new object();

        private SingletonOneForProcess() { }
        public static SingletonOneForProcess Instance() {
            if (instance == null) {
                lock (_lock) {
                    if (instance == null) {
                        instance = new SingletonOneForProcess();
                    }
                }
            }
            return instance;
        }
    }
}