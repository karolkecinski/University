using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace zadanie1
{
    public partial class EditUser : UserControl
    {
        private Person.Person person;

        public Person.Person Person
        {
            get { return person; }
            set
            {
                person = value;
                updateUI();
            }
        }

        public EditUser()
        {
            InitializeComponent();
        }

        private void updateUI()
        {
            // load person into controls
        }

        private void textBoxName_TextChanged(object sender, EventArgs e)
        {
            person.Name = textBoxName.Text;
        }
    }
}
