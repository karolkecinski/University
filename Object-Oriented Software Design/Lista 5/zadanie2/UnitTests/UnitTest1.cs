using Microsoft.VisualStudio.TestTools.UnitTesting;
using System;
using System.IO;
using System.Text;
using zadanie2;

namespace UnitTests
{
    [TestClass]
    public class UnitTests
    {
        [TestMethod]
        public void TestWrite()
        {
            string teststring = "Test";
            FileStream file = File.Create("Test");
            CaesarStream caesarStream = new CaesarStream(file, 3);
            caesarStream.Write(Encoding.UTF8.GetBytes(teststring), 0, teststring.Length);
            caesarStream.Close();
            file.Close();

            FileStream readFileStream = File.Open("Test", FileMode.Open);
            StreamReader reader = new StreamReader(readFileStream);
            string readText = reader.ReadToEnd();

            Assert.AreEqual("Whvw", readText);
        }

        [TestMethod]
        public void TestRead()
        {
            string teststring = "Defg";
            FileStream file = File.Create("Test2");
            file.Write(Encoding.UTF8.GetBytes(teststring), 0, teststring.Length);
            file.Close();

            FileStream readFileStream = File.Open("Test2", FileMode.Open);
            CaesarStream caesarStream = new CaesarStream(readFileStream, -3);
            byte[] buffer = new byte[4];
            caesarStream.Read(buffer, 0, teststring.Length);

            Assert.AreEqual("Abcd", Encoding.UTF8.GetString(buffer));
        }
    }
}