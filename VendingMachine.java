import java.util.ArrayList;
import java.io.*;

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
   public VendingMachine() throws IOException
   { 
		stock = Reader.stockReader("Stock.txt");
		coins = Reader.coinReader("Money.txt");
		currentCoins = new ArrayList<CoinLineItem>();
		operators = Reader.operatorReader("Operators.txt");
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
   
   public String addCoin(Coin money)
   {
       String output = ""; //I added this guy to this method to collect our output 
							//and return it, it helps with the GUI, i also changed
							//the statement where this is called from VendingMenu to
							//a println statement so that the needed info is still printed!
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
	   output=("Total Credit:  $" + String.format("%1.2f", sum) + "\n");
	   
	   return output;
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
				if(currentCoins.get(i).getCoin().compareTo(coins.get(j).getCoin()) == 0)
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
   
   public String buyProduct(Product prod) throws VendingException
   {
		String output = ""; //I added this guy to this method to collect our output 
							//and return it, it helps with the GUI, i also changed
							//the statement where this is called from VendingMenu to
							//a println statement so that the needed info is still printed!
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
			output = "Purchased: " + prod.getDescription() + ".";
			transferCoins();
		}
		else
		{
			//output = this.removeMoney(false);
			//I changed this, it wasnt printing the removeMoney message.////////////////////////
			throw new VendingException("Not enough money\n" + this.removeMoney(false));
		}
		return output;
	}
   
   public String addProduct(Product prod, int quant)
   {   
	   String output = ""; //I added this guy to this method to collect our output 
							//and return it, it helps with the GUI, i also changed
							//the statement where this is called from OperatorMenu to
							//a println statement so that the needed info is still printed!
	   
	   boolean go = true; int i = 0;
	   while(go && i < stock.size())
	   {
		   if(stock.get(i).compareProducts(prod) == 0)
		   {
			   if(stock.get(i).add(quant))
				   output = "Successfully added"; 
			   else
					output = "Add Unsuccessful"; 
			   go = false;
		   }
		   i++;
	   }
	   if(go)
	   {   
			stock.add(new LineItem(prod, quant)); 
			output = "Successfully added"; 
	   }
	   return output;
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
   
   public ArrayList<LineItem> getStock()
   {
	   return stock;
   }
   
   public ArrayList<Operator> getOperators()
   {
	   return operators;
   }
   
   public ArrayList<CoinLineItem> getCoins()
   {
	   return coins;
   }
}