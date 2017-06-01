package Compilador;

public class Num extends Expression
{
	private Number number;
	
	public Num(Number number)
	{
		this.number=number;
	}
	
	public Number getNumber()
	{
		return number;
	}

}
