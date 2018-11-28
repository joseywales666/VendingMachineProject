import java.util.ArrayList;

/**
   A set of coins.
*/
public class CoinSet
{  
   private ArrayList<Coin> set;

   /**
      Constructs a CoinSet object.
   */
   public CoinSet()
	{  
		set = new ArrayList<Coin>();
	}
   
	public void add(Coin c)
	{
		set.add(c);
	}
   
	public void remove(Coin c)
	{
		set.remove(c);
	}

	public double total()
	{
		double amount = 0;
		for(int i = 0; i < set.size(); i++)
		{
			amount += set.get(i).getValue();
		}
		return amount;
	}
	
	public void empty()
	{
		set.clear();
	}
	
	public boolean isEmpty()
	{
		return set.isEmpty();
	}
	
	public int size()
	{
		return set.size();
	}
	
	public Coin get(int n)
	{
		return set.get(n);
	}
}