using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Data.SqlClient;
using System.Xml;

namespace Strategy
{
    public interface IDataAccessHandlerImplementer
    {
        void Connect();
        void Download();
        void DataProcess();
        void CloseConnection();
    }

    public class DataAccessHandler
    {
        private IDataAccessHandlerImplementer IDAHI;

        public DataAccessHandler(IDataAccessHandlerImplementer IDAHI)
        {
            this.IDAHI = IDAHI;
        }
        public void Execute()
        {
            IDAHI.Connect();
            IDAHI.Download();
            IDAHI.DataProcess();
            IDAHI.CloseConnection();
        }
    }

    public class DatabaseDataAccessHandler : IDataAccessHandlerImplementer
    {
        SqlConnection connection;
        SqlDataReader result;
        string connectionData;
        string attribute;
        string table;

        public DatabaseDataAccessHandler(string server, string name, string user, string password, string attribute, string table)
        {
            this.connectionData = string.Format(@"Data Source={0};Initial Catalog={1};User ID={2};Password={3}",
                server,
                name,
                user,
                password);
            this.attribute = attribute;
            this.table = table;
        }

        public void Connect()
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
        public void Download()
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
        public void DataProcess()
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
        public void CloseConnection()
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

    public class XMLDataAccessHandler : IDataAccessHandlerImplementer
    {
        XmlTextReader XMLTR;
        string path;

        public XMLDataAccessHandler(string path)
        {
            this.path = path;
        }
        public void Connect()
        {
            XMLTR = new XmlTextReader(path);
        }
        public void Download()
        {
            //pass
        }
        public void DataProcess()
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
        public void CloseConnection()
        {
            XMLTR.Close();
        }
    }
}