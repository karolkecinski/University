using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Net.Mail;
using System.Net.Mime;
using System.Net;

namespace zadanie1
{
    public class SmtpFacade
    {
        private SmtpClient client;
        public SmtpFacade(string Host,
                          int    Port,
                          string Username,
                          string Password) {
            client             = new SmtpClient(Host, Port);
            client.Credentials = new NetworkCredential(Username, Password);
        }

        public void Send(string  From,        string To,
                         string  Subject,     string Body,
                         Stream? Attachment,  string? AttachmentMimeType) {
            SmtpClient client   = new SmtpClient();
            MailMessage message = new MailMessage(From, To, Subject, Body);
            if(Attachment != null && AttachmentMimeType != null) {
                message.Attachments.Add(new Attachment(Attachment, new ContentType(AttachmentMimeType)));
            }
            
            client.Send(message);
        }
    }
}
