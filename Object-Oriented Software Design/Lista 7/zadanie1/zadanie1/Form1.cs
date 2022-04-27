namespace zadanie1
{
    public partial class Form1 : Form
    {
        List<Person.Person> persons = new List<Person.Person>();

        TreeNode studentsNode, lecturersNode;
        EditUser editUser = new EditUser();

        public Form1()
        {
            InitializeComponent();

            persons.Add(new Person.Student("Karol", "ICantWriteYourName", 0, DateTime.Now, "Test"));
            persons.Add(new Person.Lecturer("Hans", "Peter", 1, DateTime.Now, "Test"));

            foreach (var n in treeViewLectures.Nodes)
            {
                var node = n as TreeNode;

                if (node.Name == "NodeStudents")
                    studentsNode = node;
                if (node.Name == "NodeLecturers")
                    lecturersNode = node;
            }

            updateData();
        }

        private void treeViewLectures_AfterSelect(object sender, TreeViewEventArgs e)
        {
            var node = treeViewLectures.SelectedNode;

            if (node == studentsNode)
            {
                loadStudents();
            }
            else if (node == lecturersNode)
            {
                loadLecturers();
            } else
            {
                tableLayoutPanel1.Controls.Remove(listViewDetailed);
                tableLayoutPanel1.Controls.Add(editUser, 1, 0);
                editUser.Dock = DockStyle.Fill;
            }
        }

        void updateData()
        {
            foreach (var p in persons)
            {
                if (p is Person.Student)
                {
                    studentsNode.Nodes.Add(p.Name + " " + p.Surname);
                } else
                {
                    lecturersNode.Nodes.Add(p.Name + " " + p.Surname);
                }
            }

            treeViewLectures.ExpandAll();
        }

        void loadStudents()
        {
            tableLayoutPanel1.Controls.Remove(editUser);
            tableLayoutPanel1.Controls.Add(listViewDetailed, 1, 0);

            listViewDetailed.Items.Clear();

            foreach (var p in persons)
            {
                if (p is Person.Student)
                {
                    listViewDetailed.Items.Add(new ListViewItem(p.Data));
                }
            }
        }

        void loadLecturers()
        {
            tableLayoutPanel1.Controls.Remove(editUser);
            tableLayoutPanel1.Controls.Add(listViewDetailed, 1, 0);

            listViewDetailed.Items.Clear();

            foreach (var p in persons)
            {
                if (p is Person.Lecturer)
                {
                    listViewDetailed.Items.Add(new ListViewItem(p.Data));
                }
            }
        }
    }
}