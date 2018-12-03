/**
   A product in a vending machine.
*/
public class Product implements Comparable<Product>, Writable
{  
	private String description;
	private double price;

   /**
      Constructs a Product object
      @param aDescription the description of the product
      @param aPrice the price of the product
   */
	public Product(String aDescription, double aPrice)
	{  
		description = aDescription;
		price = aPrice;
	}
	
	public int compareTo(Product other) 
	{
		if(other.getPrice() == this.price)
		{
			return  (other.getDescription().compareTo(this.description));
		}
		else if(other.getPrice() > this.price)
		{
			return 1;
		}
		else
		{
			return (-1);
		}
	}
	
	public int compareTo(double price, String description) 
	{
		if(price == this.price)
		{
			return  (description.compareTo(this.description));
		}
		else if(price > this.price)
		{
			return 1;
		}
		else
		{
			return (-1);
		}
	}
	
	public double getPrice()
	{
		return price;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public String toCSV()
	{
		return (description + "," + price);
	}
	
	public String toString()
	{
		return "Product Type: " + description + "\nPrice: " + String.format("$%1.2f", price);
	}
}
