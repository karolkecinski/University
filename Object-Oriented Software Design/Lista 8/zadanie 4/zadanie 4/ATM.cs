using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using State;

namespace Atm
{
    public class ATM
    {
        public IState state;

        public ATM()
        {
        }

        public void SetState(IState state)
        {
            this.state = state;
        }

        public void ProcessCashlessTransaction()
        {
            this.state.ProcessCashlessTransaction();
        }
        public void ProcessTransactionWithCash()
        {
            this.state.ProcessTransactionWithCash();
        }

        public void FinalizeTransaction()
        {
            this.state.FinalizeTransaction();
        }
    }
}
