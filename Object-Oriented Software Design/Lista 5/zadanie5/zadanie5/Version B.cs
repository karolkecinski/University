using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace zadanie5
{
    public interface IPersonsGetter
    {
        public IEnumerable<Person> GetPersons();
    }
    public abstract class PersonRegistry2
    {
        IPersonsGetter _getter;
        public PersonRegistry2(IPersonsGetter getter)
        {
            this._getter = getter;
        }
        public IEnumerable<Person> GetPersons()
        {
            return this._getter.GetPersons();
        }

        public abstract void NotifyPersons(IEnumerable<Person> persons);
    }

    public class MailPersonNotifier2 : PersonRegistry2
    {
        public MailPersonNotifier2(IPersonsGetter getter) : base(getter)
        {

        }

        public override void NotifyPersons(IEnumerable<Person> persons)
        {
            foreach (Person p in persons)
            {
                Console.WriteLine("Notified person");
            }
        }
    }

    public class SMSPersonNotifier2 : PersonRegistry2
    {
        public SMSPersonNotifier2(IPersonsGetter getter) : base(getter)
        {

        }

        public override void NotifyPersons(IEnumerable<Person> persons)
        {
            foreach (Person p in persons)
            {
                Console.WriteLine("Notified person");
            }
        }
    }

    public class XMLPersonGetter : IPersonsGetter
    {

        public IEnumerable<Person> GetPersons()
        {
            return new List<Person>() { new Person() };
        }
    }

    public class SQLPersonGetter : IPersonsGetter
    {

        public IEnumerable<Person> GetPersons()
        {
            return new List<Person>() { new Person() };
        }
    }

    
}
