using System;

namespace report 
{
    public class ReportPrinter {
        public ReportPrinter() { }
        public void printReport() {
            DataGetter dg = new DataGetter();
            string data = dg.getData();
            
            DocumentFormatter df = new DocumentFormatter();
            df.formatDocument(data);

            Console.WriteLine(data);
        }
    }

    public class DocumentFormatter {
        public DocumentFormatter() { }
        public void formatDocument(string data) {
            Console.WriteLine("Data successfully formatted!");
        }
    }

    public class DataGetter {

        public DataGetter() { }
        public string getData() {
            return "Data";
        }
    }
}