namespace zadanie3
{
    public abstract class Tree
    {
    }

    public class TreeNode : Tree
    {
        public Tree Left { get; set; }
        public Tree Right { get; set; }
    }

    public class TreeLeaf : Tree
    {
        public int Value { get; set; }
    }

    public class TreeHeightVisitor
    {
        public int maxHeight = 0;
        public int Visit(Tree tree)
        {
            if(tree is TreeNode)
                return this.VisitNode((TreeNode)tree);
            if(tree is TreeLeaf)
                return this.VisitLeaf((TreeLeaf)tree);
            throw new ArgumentException();
        }

        public int VisitNode(TreeNode node)
        {
            if(node != null)
            {
                int leftHeight = this.Visit(node.Left);
                int rightHeight = this.Visit(node.Right);

                //Console.WriteLine("Node: " + leftHeight + ' ' + rightHeight);

                if(leftHeight > rightHeight)
                {
                    if(leftHeight >= maxHeight)
                    {
                        maxHeight = leftHeight + 1;
                    }
                    return leftHeight + 1;
                }
                else
                {
                    if(rightHeight >= maxHeight)
                    {
                        maxHeight = rightHeight + 1;
                    }
                    return rightHeight + 1;
                }
            }
            return 0;
        }

        public int VisitLeaf(TreeLeaf leaf)
        {
            return 0;
        }
    }

    class Program
    {
        static void Main(string[] args)
        {
            // Example 1

            Tree root = new TreeNode()
            {
                Left = new TreeNode()
                {
                    Left = new TreeLeaf() { Value = 1 },
                    Right = new TreeLeaf() { Value = 2 },
                },
                Right = new TreeLeaf() { Value = 3 }
            };
            TreeHeightVisitor visitor = new TreeHeightVisitor();
            visitor.Visit(root);
            Console.WriteLine("Tree 1 height: " + visitor.maxHeight);

            // Example 2

            Tree singleLeaf = new TreeLeaf()
            {
                Value = 2
            };
            TreeHeightVisitor visitor2 = new TreeHeightVisitor();
            visitor2.Visit(singleLeaf);
            Console.WriteLine("Tree 2 height: " + visitor2.maxHeight);

            // Example 3

            Tree singleNode = new TreeNode()
            {
                Left = new TreeLeaf()
                {
                   Value = 2
                },
                Right = new TreeLeaf() { Value = 3 }
            };
            TreeHeightVisitor visitor3 = new TreeHeightVisitor();
            visitor3.Visit(singleNode);
            Console.WriteLine("Tree 3 height: " + visitor3.maxHeight);
        }
    }

}