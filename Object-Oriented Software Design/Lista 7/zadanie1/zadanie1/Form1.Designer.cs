namespace zadanie1
{
    partial class Form1
    {
        /// <summary>
        ///  Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        ///  Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        ///  Required method for Designer support - do not modify
        ///  the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            System.Windows.Forms.TreeNode treeNode1 = new System.Windows.Forms.TreeNode("Students");
            System.Windows.Forms.TreeNode treeNode2 = new System.Windows.Forms.TreeNode("Lecturers");
            System.Windows.Forms.ListViewItem listViewItem1 = new System.Windows.Forms.ListViewItem(new string[] {
            "Parent",
            "Sun",
            "2"}, -1);
            this.treeViewLectures = new System.Windows.Forms.TreeView();
            this.listViewDetailed = new System.Windows.Forms.ListView();
            this.columnHeaderGivenName = new System.Windows.Forms.ColumnHeader();
            this.columnHeaderName = new System.Windows.Forms.ColumnHeader();
            this.columnHeaderDate = new System.Windows.Forms.ColumnHeader();
            this.columnHeaderAddress = new System.Windows.Forms.ColumnHeader();
            this.tableLayoutPanel1 = new System.Windows.Forms.TableLayoutPanel();
            this.tableLayoutPanel1.SuspendLayout();
            this.SuspendLayout();
            // 
            // treeViewLectures
            // 
            this.treeViewLectures.Dock = System.Windows.Forms.DockStyle.Fill;
            this.treeViewLectures.Location = new System.Drawing.Point(3, 3);
            this.treeViewLectures.Name = "treeViewLectures";
            treeNode1.Name = "NodeStudents";
            treeNode1.Text = "Students";
            treeNode2.Name = "NodeLecturers";
            treeNode2.Text = "Lecturers";
            this.treeViewLectures.Nodes.AddRange(new System.Windows.Forms.TreeNode[] {
            treeNode1,
            treeNode2});
            this.treeViewLectures.Size = new System.Drawing.Size(194, 566);
            this.treeViewLectures.TabIndex = 0;
            this.treeViewLectures.AfterSelect += new System.Windows.Forms.TreeViewEventHandler(this.treeViewLectures_AfterSelect);
            // 
            // listViewDetailed
            // 
            this.listViewDetailed.Columns.AddRange(new System.Windows.Forms.ColumnHeader[] {
            this.columnHeaderGivenName,
            this.columnHeaderName,
            this.columnHeaderDate,
            this.columnHeaderAddress});
            this.listViewDetailed.Dock = System.Windows.Forms.DockStyle.Fill;
            this.listViewDetailed.Items.AddRange(new System.Windows.Forms.ListViewItem[] {
            listViewItem1});
            this.listViewDetailed.Location = new System.Drawing.Point(203, 3);
            this.listViewDetailed.Name = "listViewDetailed";
            this.listViewDetailed.Size = new System.Drawing.Size(681, 566);
            this.listViewDetailed.TabIndex = 1;
            this.listViewDetailed.UseCompatibleStateImageBehavior = false;
            this.listViewDetailed.View = System.Windows.Forms.View.Details;
            // 
            // columnHeaderGivenName
            // 
            this.columnHeaderGivenName.Text = "Given Name";
            this.columnHeaderGivenName.Width = 100;
            // 
            // columnHeaderName
            // 
            this.columnHeaderName.Text = "Name";
            this.columnHeaderName.Width = 100;
            // 
            // columnHeaderDate
            // 
            this.columnHeaderDate.Text = "Date Of Birth";
            this.columnHeaderDate.Width = 100;
            // 
            // columnHeaderAddress
            // 
            this.columnHeaderAddress.Text = "Address";
            this.columnHeaderAddress.Width = 284;
            // 
            // tableLayoutPanel1
            // 
            this.tableLayoutPanel1.ColumnCount = 2;
            this.tableLayoutPanel1.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 200F));
            this.tableLayoutPanel1.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.tableLayoutPanel1.Controls.Add(this.treeViewLectures, 0, 0);
            this.tableLayoutPanel1.Controls.Add(this.listViewDetailed, 1, 0);
            this.tableLayoutPanel1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tableLayoutPanel1.Location = new System.Drawing.Point(0, 0);
            this.tableLayoutPanel1.Name = "tableLayoutPanel1";
            this.tableLayoutPanel1.RowCount = 1;
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.tableLayoutPanel1.Size = new System.Drawing.Size(887, 572);
            this.tableLayoutPanel1.TabIndex = 2;
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 20F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(887, 572);
            this.Controls.Add(this.tableLayoutPanel1);
            this.Name = "Form1";
            this.Text = "Form1";
            this.tableLayoutPanel1.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private TreeView treeViewLectures;
        private ListView listViewDetailed;
        private ColumnHeader columnHeaderGivenName;
        private ColumnHeader columnHeaderName;
        private ColumnHeader columnHeaderDate;
        private ColumnHeader columnHeaderAddress;
        private TableLayoutPanel tableLayoutPanel1;
    }
}