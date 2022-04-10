using System;
using System.Collections.Generic;
using System.Threading;

namespace Singleton
{
    public class FiveSecondSingleton {
        private static FiveSecondSingleton instance;
        private static object _lock = new object();
        private static DateTime timer;
        
        private FiveSecondSingleton() { }

        private static bool checkIfCreatable() {
            if (instance == null) {
                return true;
            }
            TimeSpan time = DateTime.Now - timer;
            return time.Seconds >= 5;
        }

        public static FiveSecondSingleton Instance() {
            if (checkIfCreatable()) {
                lock (_lock) {
                    if (checkIfCreatable()) {
                        timer = DateTime.Now;
                        instance = new FiveSecondSingleton();
                    }
                }
            }
            return instance;
        }
    }
}