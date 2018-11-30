/**
   A coin with a monetary value.
*/
public class Coin implements Comparable<Coin>, Writable
{
   private double value;
   private String name;

   /**
      Constructs a coin.
      @param aValue the monetary value of the coin.
      @param aName the name of the coin
   */
   public Coin(double aValue, String aName) 
   { 
      value = aValue; 
      name = aName;
   }

   public double getValue()
   {
	   return value;
   }
   
   public String getName()
   {
	   return name;
   }
   
   public int compareTo(Coin c)
   {
	   if(c.getValue() == this.getValue())
	   {
		   return 0;
	   }
	   else if(c.getValue() < this.getValue())
	   {
		   return 1;
	   }
	   else
	   {
		   return -1;
	   }
   }
   
   public String toCSV()
   {
	   return (name + "," + value);
   }
   
   public String toString()
   {
	   return String.format("$%1.2f", value);
   }
}