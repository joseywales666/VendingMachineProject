public class CoinLineItem
{  
	private Coin c;
	private int quantity;

	/**
      Constructs a CoinLineItem object.
	*/
	public CoinLineItem(Coin c)
	{  
		this.c = c; quantity = 0;
	}
   
	public CoinLineItem(Coin c, int q)
	{  
		this.c = c; quantity = q;
	}
	
	public void add()
	{
		quantity++;
	}
	
	public void add(int x)
	{
		quantity += x;
	}
   
	public void remove()
	{
		quantity--;
	}
	
	public void remove(int x)
	{
		quantity -= x;
	}

	public double total()
	{
		return quantity * (c.getValue());
	}
	
	public void empty()
	{
		quantity = 0;
	}
	
	public boolean isEmpty()
	{
		return (quantity == 0);
	}

	public int getQuantity()
	{
		return quantity;
	}
	
	public Coin getCoin()
	{
		return c;
	}
	
	 public String toString()
   {
	   return c.getName() + ","  + c.getValue() + "," + quantity;
   }
}