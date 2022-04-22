namespace zadanie2
{
    public class Context
    {
        private Dictionary<string, bool> _variables;

        public Context()
        {
            _variables = new Dictionary<string, bool>();
        }

        public bool GetValue(string VariableName)
        {
            if(_variables.ContainsKey(VariableName))
            {
                return _variables[VariableName];
            } 
            else
            {
                throw new ArgumentException();
            }
             
        }
        public bool SetValue(string VariableName, bool Value)
        {
            if (_variables.ContainsKey(VariableName))
            {
                _variables.Remove(VariableName);
            }
            _variables.Add(VariableName, Value);
            return true;
        }
    }

    public abstract class AbstractExpression
    {
        public abstract bool Interpret(Context context);
    }

    public class BinaryExpression : AbstractExpression
    {
        public AbstractExpression left;
        public AbstractExpression right;

        public string Operator;

        public BinaryExpression(
            AbstractExpression left,
            AbstractExpression right,
            string Operator)
        {
            this.left = left;
            this.right = right;
            this.Operator = Operator;
        }

        public override bool Interpret(Context context)
        {
            switch(Operator)
            {
                case "AND":
                    return 
                        this.left.Interpret(context)
                        &&
                        this.right.Interpret(context);
                case "OR":
                    return
                        this.left.Interpret(context)
                        ||
                        this.right.Interpret(context);
                default:
                    throw new ArgumentException();
            }
        }
    }

    public class ConstExpression : AbstractExpression
    {
        protected string variable;

        public ConstExpression(string variable)
        {
            this.variable = variable;
        }

        public override bool Interpret(Context context)
        {
            return context.GetValue(variable);
        }

    }

    public class UnaryExpression : AbstractExpression
    {
        public AbstractExpression expression;
        protected string Operator;

        public UnaryExpression(
            AbstractExpression expression,
            string Operator)
        {
            this.expression = expression;
            this.Operator = Operator;
        }

        public override bool Interpret(Context context)
        {
            switch(this.Operator)
            {
                case "Neg":
                    return !expression.Interpret(context);
                default:
                    throw new ArgumentException();
            }
        }

    }

    class Program
    {
        public static void Main(string[] args)
        {
            Context ctx = new Context();
            ctx.SetValue("x", false);
            ctx.SetValue("y", true);

            AbstractExpression exp = new BinaryExpression(
                new BinaryExpression(
                    new ConstExpression("x"),
                    new UnaryExpression(
                        new ConstExpression("x"),
                        "Neg"),
                    "OR"),
                new ConstExpression("y"),
                "AND");

            bool Value = exp.Interpret(ctx);

            Console.WriteLine(Value);
        }
    }
}
