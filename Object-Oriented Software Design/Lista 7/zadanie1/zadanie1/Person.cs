using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using EventHandling;
using Notifications;

namespace Person
{
    public abstract class Person
    {
        public string Name { get; set; }
        public string Surname { get; set; }
        public int Id { get; set; }
        public DateTime DateOfBirth { get; set; }
        public string Address { get; set; }
    }

    public class Student : Person
    {

    }

    public class Lecturer : Person
    {

    }
}
