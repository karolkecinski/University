namespace finance {
	public interface ITaxCalculator {
		public Decimal CalculateTax(Decimal Price);
	}

	public interface IOrder
	{
		public Item[] Order(Item[] items);
	}

	public class TaxCalculator : ITaxCalculator {
		public Decimal tax;
		public TaxCalculator(double tax = 0.23) {
			this.tax = new Decimal(tax);
		}
		public Decimal CalculateTax( Decimal Price ) 
		{ 
			return Price * tax; 
		}
	}

	public class Item {
		public Decimal Price { 
			get; set;
		}

		public string Name { 
			get; set;
		}

		public Item(double price, string name) {
			this.Price = new Decimal(price);
			this.Name = name;
		}
	}

	class OrderByPrice : IOrder
	{
		public Item[] Order(Item[] arr) {
			Array.Sort(
				arr, 
				(x, y) => x.Price.CompareTo(y.Price)
			);
			return arr;
		}
	}

	class OrderByName : IOrder
	{
		public Item[] Order(Item[] arr) {
			Array.Sort(
				arr, 
				(x, y) => x.Name.CompareTo(y.Name)
			);
			return arr;
		}
	}

	public class CashRegister {
		public TaxCalculator taxCalc = new TaxCalculator(0.1);
		public Decimal tax {
			get { return taxCalc.tax; }
			set { taxCalc.tax = value; }
		}
		public Decimal CalculatePrice( Item[] Items ) {
			Decimal _price = 0;
			foreach ( Item item in Items ) {
				_price += item.Price + taxCalc.CalculateTax( item.Price );
			}
			return _price;
		}
		public void PrintBill( Item[] Items , IOrder o) {
			Item[] ordered = o.Order(Items);
			foreach ( var item in ordered )
				Console.WriteLine( "towar {0} : cena {1} + podatek {2}",
					item.Name, item.Price, taxCalc.CalculateTax( item.Price ) );
		}
	}
}