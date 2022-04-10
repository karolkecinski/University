using System;

namespace report 
{
    public interface IReportPrinter
    {
        public void printReport(string data);
    }

    public interface IDataGetter
    {
        public string getData();
    }

    public interface IDocumentFormatter
    {
        public string formatDocument(string data);
    }
    public class ReportComposer {
        private IDataGetter _dataGetter;
        private IReportPrinter _reportPrinter;
        private IDocumentFormatter _documentFormatter;

        public ReportComposer(IDataGetter dg, IReportPrinter rp, IDocumentFormatter df) {
            _dataGetter = dg;
            _reportPrinter = rp;
            _documentFormatter = df;
        }

        public void ComposeReport() {
            string data = _documentFormatter.formatDocument(_dataGetter.getData());
            _reportPrinter.printReport(data);
        }
    }
    public class ReportPrinter : IReportPrinter {
        
        public ReportPrinter() { }
        public void printReport(string data) {
            Console.WriteLine(data);
        }
    }

    public class DocumentFormatter : IDocumentFormatter {
        public DocumentFormatter() { }
        public string formatDocument(string data) {
            return "**" + data + "**";
        }
    }

    public class DataGetter : IDataGetter {

        public DataGetter() { }
        public string getData() {
            return "Data";
        }
    }
}