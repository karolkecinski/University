using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using zadanie1;

public class Program
{
    public static void Main(string[] args)
    {

        SmtpFacade facade = new SmtpFacade("smtp.gmail.com", 587, "Username", "Password");
        facade.Send("from@gmail.com", "to@gmail.com", "Subject", "Body", Attachment: null, AttachmentMimeType: "text/plain");
    }
}
