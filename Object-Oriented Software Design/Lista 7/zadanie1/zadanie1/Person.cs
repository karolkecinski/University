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

        public string[] Data
        {
            get
            {
                return new string[]
                {
                    Surname, Name, DateOfBirth.ToShortDateString(), Address
                };
            }
        }
    }

    public class Student : Person
    {
        public Student(String name, String surname, int id, DateTime dateOfBirth, string address)
        {
            Name = name;    
            Surname = surname;
            Id = id;
            DateOfBirth = dateOfBirth;
            Address = address;
        }
    }

    public class Lecturer : Person
    {
        public Lecturer(String name, String surname, int id, DateTime dateOfBirth, string address)
        {
            Name = name;
            Surname = surname;
            Id = id;
            DateOfBirth = dateOfBirth;
            Address = address;
        }
    }
}
