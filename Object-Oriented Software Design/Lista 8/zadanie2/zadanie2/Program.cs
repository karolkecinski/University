using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Xml;
using System.Data.SqlClient;
using Template;

namespace zadanie2
{
    class Program
    {
        public static void Main(string[] args)
        {
            DataAccessHandler d1 = new DatabaseDataAccessHandler("localhost", "name", "123", "qwerty123", "attr1", "table1");
            DataAccessHandler d2 = new XMLDataAccessHandler("./XMLFile.xml");

            d1.Execute();
            d2.Execute();
        }
    }
    
}
