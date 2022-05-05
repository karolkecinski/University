using System;
using Atm;

namespace State
{
    public interface IState
    {
        void ProcessCashlessTransaction();
        void ProcessTransactionWithCash();
        void FinalizeTransaction();
    }

    public class IdleState : IState
    {
        private ATM atm;

        public IdleState(ATM atm)
        {
            this.atm = atm;
        }

        public void ProcessCashlessTransaction() { throw new Exception(); }

        public void ProcessTransactionWithCash() { throw new Exception(); }

        public void FinalizeTransaction()
        {
            Console.WriteLine("Transaction successfull!");
        }
    }

    public class CashlessTransactionState : IState
    {
        private ATM atm;

        public CashlessTransactionState(ATM atm)
        {
            this.atm = atm;
        }

        public void ProcessCashlessTransaction()
        {
            Console.WriteLine("Processing cashless transaction...");
            this.atm.state = new IdleState(this.atm);
        }

        public void ProcessTransactionWithCash()
        {
            throw new Exception();
        }

        public void FinalizeTransaction()
        {
            throw new Exception();
        }
    }

    public class CashTransactionState : IState
    {
        private ATM atm;

        public CashTransactionState(ATM atm)
        {
            this.atm = atm;
        }

        public void ProcessCashlessTransaction()
        {
            throw new Exception();
        }

        public void ProcessTransactionWithCash()
        {
            Console.WriteLine("Processing transaction with cash...");
            this.atm.state = new IdleState(this.atm);
        }

        public void FinalizeTransaction()
        {
            throw new Exception();
        }
    }

    public class Program
    {
        public static void Main(string[] args)
        {
            ATM atm1 = new ATM();

            atm1.SetState(new CashlessTransactionState(atm1));
            atm1.ProcessCashlessTransaction();
            atm1.FinalizeTransaction();

            ATM atm2 = new ATM();

            atm2.SetState(new CashTransactionState(atm2));
            atm2.ProcessTransactionWithCash();
            atm2.FinalizeTransaction();

            try
            {
                ATM atm3 = new ATM();

                atm3.SetState(new CashlessTransactionState(atm3));
                atm3.FinalizeTransaction();
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }

            Console.ReadLine();
        }
    }
}