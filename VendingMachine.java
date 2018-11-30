import java.util.ArrayList;
/**
   A vending machine.
*/
public class VendingMachine
{  
   private ArrayList<LineItem> stock;
   private ArrayList<Operator> operators;
   private ArrayList<CoinLineItem> coins;
   private ArrayList<CoinLineItem> currentCoins;

   /**
      Constructs a VendingMachine object.
   */
   public VendingMachine()
   { 
      stock = new ArrayList<LineItem>();
      coins = new ArrayList<CoinLineItem> ();
      currentCoins = new ArrayList<CoinLineItem> ();
	  operators = new ArrayList<Operator>();
	  operators.add(new Operator("001", "abc")); operators.add(new Operator("002", "cba"));
   }
   
   public Product[] getProductTypes(boolean isOperator)
   {

	   ArrayList<Product> temp = new ArrayList<Product>();
	  
		for(int i = 0; i < this.stock.size(); i++)
		{
			if(!temp.contains(this.stock.get(i).getProd()))
			{	
				if((this.stock.get(i).getQuantity() > 0) || isOperator)
					temp.add(this.stock.get(i).getProd());
			}
		}
	   
	   Product[] ret = new Product[temp.size()];
	   ret = temp.toArray(ret);
	   return ret;
   }
   
   public void addCoin(Coin money)
   {
	   boolean addNew = true;
	   for(int i = 0; i < currentCoins.size(); i++)
	   {
		   if(currentCoins.get(i).getCoin().compareTo(money) == 0)
		   {
			   currentCoins.get(i).add();
			   addNew = false;
			   i = currentCoins.size();
		   }
	   }
	   if(addNew)
	   {
			currentCoins.add(new CoinLineItem(money, 1));
	   }
	   double sum = 0;
		for(int i = 0; i < currentCoins.size(); i++)
		{
			sum += currentCoins.get(i).total();
		}
	   System.out.println("Total Credit:  $" + String.format("%1.2f", sum) + "\n");
   }
   
   public String removeMoney(boolean isOperator)
   {
	   if(isOperator)
	   {
		   String ret = "Machine Empty: No Coins to Collect";
		   if(!(coins.isEmpty()))
		   {
			   double sum= 0;
				for(int j = 0; j < coins.size(); j++)
				{
					sum += coins.get(j).total(); coins.get(j).empty();
				}
				if(sum > 0)
				{					
					ret = String.format("$%1.2f", sum) + ": All Coins Collected";
				}
		   }
		   return ret;
	   }
	   else
	   {
		   String ret = "No Coins Inserted";
		   if(!(currentCoins.isEmpty()))
		   {
			   double sum = 0;
			   for(int i = 0; i < currentCoins.size(); i++)
			   {
				   sum += currentCoins.get(i).total(); currentCoins.get(i).empty();
			   }
				if(sum > 0)
				{
					ret = String.format("$%1.2f", sum) + " Returned";
				}
				return ret;
			}
			return ret;
	   }
   }
   
   public void transferCoins()
   {
		for(int i = 0; i < currentCoins.size(); i++)
		{
			boolean addNew = true;
			for(int j = 0; j < coins.size(); j++)
			{
				if(currentCoins.get(i).getCoin().compareTo(coins.get(j).getCoin()) == 0);
				{
					coins.get(j).add(currentCoins.get(i).getQuantity());
					currentCoins.get(i).empty();
					addNew = false; j = coins.size();
				}
			}
			if(addNew)
			{
				coins.add(new CoinLineItem(currentCoins.get(i).getCoin(), currentCoins.get(i).getQuantity()));
				currentCoins.get(i).empty();
			}
		}
   }
   
	public void buyProduct(Product prod) throws VendingException
	{
		double sum = 0;
		for(int i = 0; i < currentCoins.size(); i++)
		{
			sum += currentCoins.get(i).total();
		}
		if(prod.getPrice() <= sum)
		{
			for(int j = 0; j < stock.size(); j++)
			{
				if((stock.get(j).getProd().compareTo(prod)) == 0)
				{
					stock.get(j).remove();
					j = stock.size();
				}
			}
			
			transferCoins();
		}
		else
		{
			String str = this.removeMoney(false);
			System.out.println(str);
			throw new VendingException("Not enough money\n");
		}
	}
   
   public void addProduct(Product prod, int quant)
   {
	   boolean go = true; int i = 0;
	   while(go && i < stock.size())
	   {
		   if(stock.get(i).compareProducts(prod) == 0)
		   {
			   if(stock.get(i).add(quant))
				   System.out.println("Successfully added"); 
			   else
					System.out.println("Add Unsuccessful"); 
			   go = false;
		   }
		   i++;
	   }
	   if(go)
	   {   
			stock.add(new LineItem(prod, quant)); 
			System.out.println("Successfully added"); 
	   }
   }
   
   public boolean containsProduct(Product p)
   {
	   for(int i = 0; i < stock.size(); i++)
	   {
		   if(stock.get(i).compareProducts(p) == 0)
			   return true;
	   }
	   return false;
   }
   
   public boolean containsProduct(double price, String desc)
   {
	   for(int i = 0; i < stock.size(); i++)
	   {
		    if(stock.get(i).compareProducts(price, desc) == 0)
			   return true;
	   }
	   return false;
   }
   
   public boolean login(String id, String pass) throws NullPointerException
   {
		for(int i = 0; i < operators.size(); i++)
		{
			if(operators.get(i).assertDetails(id, pass))
			{
				return true;
			}
		}
		return false;
   }
}
