using System;
using System.Collections.Generic;
using System.Threading;
namespace Singleton
{
    public class SingletonOneForThread {
        private static ThreadLocal<SingletonOneForThread> instance = 
            new ThreadLocal<SingletonOneForThread>();
        private static object _lock = new object();

        private SingletonOneForThread() { }

        public static SingletonOneForThread Instance() {
            if (instance.Value == null) {
                lock (_lock) {
                    if (instance.Value == null) {
                        instance.Value = new SingletonOneForThread();
                    }
                }
            }
            return instance.Value;
        }
    }
}