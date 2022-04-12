using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace zadanie5
{
    public class Person
    {
        public Person()
        {

        }

    }
    public interface IPersonNotifier
    {
        public void NotifyPersons(IEnumerable<Person> persons);
    }
    public abstract class PersonRegistry
    {
        IPersonNotifier _notifier;
        public PersonRegistry(IPersonNotifier notifier)
        {
            this._notifier = notifier;
        }
        public abstract IEnumerable<Person> GetPersons();

        public void NotifyPersons()
        {
            this._notifier.NotifyPersons(this.GetPersons());
        }
    }

    public class XMLPersonRegistry : PersonRegistry
    {
        public XMLPersonRegistry(IPersonNotifier notifier) : base(notifier)
        {

        }

        public override IEnumerable<Person> GetPersons()
        {
            return new List<Person>() { new Person() };
        }
    }

    public class SQLPersonRegistry : PersonRegistry
    {
        public SQLPersonRegistry(IPersonNotifier notifier) : base(notifier)
        {

        }

        public override IEnumerable<Person> GetPersons()
        {
            return new List<Person>() { new Person() };
        }
    }

    public class MailPersonNotifier : IPersonNotifier
    {
        public void NotifyPersons(IEnumerable<Person> persons)
        {
            foreach (Person p in persons)
            {
                Console.WriteLine("Notified person");
            }
        }
    }

    public class SMSPersonNotifier : IPersonNotifier
    {
        public void NotifyPersons(IEnumerable<Person> persons)
        {
            foreach (Person p in persons)
            {
                Console.WriteLine("Notified person");
            }
        }
    }
}