using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Xml;
using System.Data.SqlClient;

namespace Template
{
    public abstract class DataAccessHandler
    {
        public abstract void Connect();
        public abstract void Download();
        public abstract void DataProcess();
        public abstract void CloseConnection();
        public void Execute()
        {
            this.Connect();
            this.Download();
            this.DataProcess();
            this.CloseConnection();
        }
    }

    public class DatabaseDataAccessHandler : DataAccessHandler
    {
        SqlConnection connection;
        SqlDataReader result;
        string connectionData;
        string attribute;
        string table;

        public DatabaseDataAccessHandler(string server, string name, string user, string password, string attribute, string table)
        {
            this.connectionData = String.Format(@"Data Source={0};Initial Catalog={1};User ID={2};Password={3}",
                server,
                name,
                user,
                password);
            this.attribute = attribute;
            this.table = table;
        }

        public override void Connect()
        {
            try
            {
                this.connection = new SqlConnection(this.connectionData);
                if (this.connection != null)
                {
                    this.connection.Open();
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }
        }
        public override void Download()
        {
            try
            {
                string sql = String.Format("Select {0} from {1};", attribute, table);
                this.result = new SqlCommand(sql, this.connection).ExecuteReader();
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }
        }
        public override void DataProcess()
        {
            int counter = 0;
            try
            {
                while (this.result.Read())
                {
                    counter += 1;
                }
                Console.WriteLine("Returned {0} results.", counter);
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }
        }
        public override void CloseConnection()
        {
            try
            {
                result.Close();
                connection.Close();
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }
        }
    }

    public class XMLDataAccessHandler : DataAccessHandler
    {
        XmlTextReader XMLTR;
        string path;

        public XMLDataAccessHandler(string path)
        {
            this.path = path;
        }
        public override void Connect()
        {
            XMLTR = new XmlTextReader(path);
        }
        public override void Download()
        {
            //pass
        }
        public override void DataProcess()
        {
            try
            {
                int counter = 0;
                while (XMLTR.Read())
                {
                    if (XMLTR.NodeType == XmlNodeType.Element)
                    {
                        counter += 1;
                    }
                }
                Console.WriteLine("XML contains {0} Element Node Types.", counter);
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }
        }
        public override void CloseConnection()
        {
            XMLTR.Close();
        }
    }
}
