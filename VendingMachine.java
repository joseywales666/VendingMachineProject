import java.util.ArrayList;
/**
   A vending machine.
*/
public class VendingMachine
{  
   private ArrayList<LineItem> stock;
   private ArrayList<Operator> operators;
   public CoinSet coins;
   public CoinSet currentCoins;

   /**
      Constructs a VendingMachine object.
   */
   public VendingMachine()
   { 
      stock = new ArrayList<LineItem>();
      coins = new CoinSet();
      currentCoins = new CoinSet();
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
	   currentCoins.add(money);
	   System.out.println("Total Credit:  $" + String.format("%1.2f", currentCoins.total()) + "\n");
   }
   
   public String removeMoney(boolean isOperator)
   {
	   if(isOperator)
	   {
		   String ret = "Machine Empty: No Coins to Collect";
		   if(!(coins.isEmpty()))
		   {
				ret = String.format("$%1.2f", coins.total()) + ": All Coins Collected";
				coins.empty();
		   }
		   return ret;
	   }
	   else
	   {
		   String ret = "No Coins Inserted";
		   if(!(currentCoins.isEmpty()))
		   {
				ret = String.format("$%1.2f", currentCoins.total()) + " Returned";
				currentCoins.empty();
		   }
		   return ret;
	   }
   }
   
	public void buyProduct(Product prod) throws VendingException
	{
		if(prod.getPrice() <= currentCoins.total())
		{
			for(int j = 0; j < currentCoins.size(); j++)
			{
				if((stock.get(j).getProd().compareTo(prod)) == 0)
				{
					stock.get(j).remove();
					j = currentCoins.size();
				}
			}
			
			for(int i = 0; i < currentCoins.size(); i++)
			{
				coins.add(currentCoins.get(i));
			}
			
			currentCoins.empty();
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