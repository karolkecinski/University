
using report;

class Program
{
    static void Main(string[] args) 
    {
        ReportComposer rc = new ReportComposer(
            new DataGetter(),
            new ReportPrinter(),
            new DocumentFormatter()
        );

        rc.ComposeReport();
    }
}