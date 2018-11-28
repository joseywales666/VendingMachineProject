public class LineItem
{
	private Product p;
	private int quantity;
	
	public LineItem(Product prod)
	{
		p = prod; quantity = 0;
	}
	
	public LineItem(Product prod, int x)
	{
		p = prod; quantity = x;
	}
	
	public Product getProd()
	{
		return this.p;
	}
	
	public int getQuantity()
	{
		return quantity;
	}
	
	public double getPrice()
	{
		return p.getPrice();
	}
	 
	public String getDescription()
	{
		return p.getDescription();
	}
	
	public boolean add()
	{
		quantity++; return true;
	}
	
	public boolean add(int amount)
	{
		if(amount > 0)
		{
			quantity += amount; 
			return true;
		}
		else
			return false;
	}
	
	public boolean remove()
	{
		if(quantity > 0)
		{
			quantity--; 
			return true;
		}
		else
			return false;
	}
	
	public boolean remove(int amount)
	{
		if(quantity >= amount && amount > 0)
		{
			quantity -= amount; 
			return true;
		}
		else
			return false;
	}
	
	public int compareProducts(Product p)
	{
			return this.p.compareTo(p);
	}
	
	public int compareProducts(double price, String desc)
	{
			return this.p.compareTo(price, desc);
	}
	
	public String toString()
	{
		return (p + "| Quantity: " + quantity);
	}
}