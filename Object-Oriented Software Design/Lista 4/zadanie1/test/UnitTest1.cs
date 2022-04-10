using System;
using System.Threading;
using Singleton;

using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace Tests
{
    [TestClass]
    public class SingletonTest
    {
        [TestMethod]
        public void testSingletonOneForProcess()
        {
            SingletonOneForProcess s1 = SingletonOneForProcess.Instance();
            SingletonOneForProcess s2 = SingletonOneForProcess.Instance();

            Assert.IsNotNull(s1);
            Assert.IsNotNull(s2);

            Assert.AreSame(s1, s2);
        }

        [TestMethod]
        public void testSingletonOneForProcess_threads()
        {
            Thread t1, t2;
            SingletonOneForProcess s1 = null, s2 = null;

            t1 = new Thread(() => {
                s1 = SingletonOneForProcess.Instance();
            });

            t2 = new Thread(() => {
                s2 = SingletonOneForProcess.Instance();
            });

            t1.Start();
            t2.Start();
            t1.Join();
            t2.Join();

            Assert.IsNotNull(s1);
            Assert.IsNotNull(s2);

            Assert.AreSame(s1, s2);
        }

        [TestMethod]
        public void testSingletonOneForThread()
        {
            SingletonOneForThread s1 = SingletonOneForThread.Instance();
            SingletonOneForThread s2 = SingletonOneForThread.Instance();

            Assert.IsNotNull(s1);
            Assert.IsNotNull(s2);

            Assert.AreSame(s1, s2);
        }

        [TestMethod]
        public void testSingletonOneForThread_threads()
        {
            Thread t1, t2;
            SingletonOneForThread s1 = null, s2 = null;

            t1 = new Thread(() => {
                s1 = SingletonOneForThread.Instance();
            });
            t2 = new Thread(() => {
                s2 = SingletonOneForThread.Instance();
            });

            t1.Start();
            t2.Start();
            t2.Join();
            t1.Join();

            Assert.IsNotNull(s1);
            Assert.IsNotNull(s2);

            Assert.AreNotSame(s1, s2);
        }

        [TestMethod]
        public void testFiveSecondSingleton()
        {
            FiveSecondSingleton s1 = FiveSecondSingleton.Instance();
            FiveSecondSingleton s2 = FiveSecondSingleton.Instance();

            Assert.IsNotNull(s1);
            Assert.IsNotNull(s2);
            Assert.AreSame(s1, s2);
        }

        [TestMethod]
        public void testFiveSecondSingleton_threads()
        {
            Thread t1, t2;
            FiveSecondSingleton s1 = null, s2 = null;

            t1 = new Thread(() => {
                s1 = FiveSecondSingleton.Instance();
            });
            t2 = new Thread(() => {
                s2 = FiveSecondSingleton.Instance();
            });

            t1.Start();
            t2.Start();
            t2.Join();
            t1.Join();

            Assert.IsNotNull(s1);
            Assert.IsNotNull(s2);

            Assert.AreSame(s1, s2);
        }

        [TestMethod]
        public void TestFiveSecondSingleton_wait()
        {
            FiveSecondSingleton s1, s2;

            s1 = FiveSecondSingleton.Instance();
            Thread.Sleep(TimeSpan.FromSeconds(6));
            s2 = FiveSecondSingleton.Instance();

            Assert.IsNotNull(s1);
            Assert.IsNotNull(s2);

            Assert.AreNotSame(s1, s2);
        }
    }
}