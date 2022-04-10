using Microsoft.VisualStudio.TestTools.UnitTesting;
using System;
using zadanie3;

namespace UnitTests
{
    [TestClass]
    public class UnitTest1
    {
        [TestMethod]
        public void InvalidCapacity()
        {
            Assert.ThrowsException<ArgumentException>(
                () =>
                {
                    Airport Tempelhof = new Airport(0);
                });
        }

        [TestMethod]
        public void ValidCapacity()
        {
            Airport Schoenefeld = new Airport(1);
            Plane plane = Schoenefeld.AcquirePlane();

            Assert.IsNotNull(plane);
        }

        [TestMethod]
        public void CapacityDepleted()
        {
            Airport Schoenefeld = new Airport(1);
            Plane plane = Schoenefeld.AcquirePlane();

            Assert.ThrowsException<ArgumentException>(
                () =>
                {
                    Plane plane2 = Schoenefeld.AcquirePlane();
                });
        }

        [TestMethod]
        public void ReusedPlane()
        {
            Airport Schoenefeld = new Airport(1);
            Plane plane = Schoenefeld.AcquirePlane();

            Schoenefeld.ReleasePlane(plane);

            Plane plane2 = Schoenefeld.AcquirePlane();

            Assert.AreEqual(plane, plane2);
        }

        [TestMethod]
        public void ReleaseInvalidPlane()
        {
            Airport Schoenefeld = new Airport(1);
            Plane plane = new Plane();

            Assert.ThrowsException<ArgumentException>(
                () =>
                {
                    Schoenefeld.ReleasePlane(plane);
                });
        }

        [TestMethod]
        public void PlanesFromDistinctAirports()
        {
            Airport Tegel = new Airport(1);
            Airport Schoenefeld = new Airport(1);

            Plane plane1 = Tegel.AcquirePlane();
            Plane plane2 = Schoenefeld.AcquirePlane();

            Assert.AreNotSame(plane1, plane2);
        }
    }
}