public class Test
{
	public static void main(String args[])
	{
		Coin a = new Coin(0.50, "Fiddy"); Coin b = new Coin(1.0, "Dolla");
		Coin c = new Coin(1.0, "Anotha Dolla");
	
		System.out.println(a.compareTo(b)); System.out.println(c.compareTo(a));
		System.out.println(b.compareTo(c)); System.out.println(a.compareTo(a));
	}
}