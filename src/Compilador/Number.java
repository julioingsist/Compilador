package Compilador;

public class Number extends Operand
{
	private String number;
	
	public Number(String number)
	{
		this.number=number; 
	}
	
	public String getNumber()
	{
		return number;
	}
	

}
