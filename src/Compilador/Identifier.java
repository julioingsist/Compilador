package Compilador;

public class Identifier extends Operand 
{
	private String identifier;
	
	public Identifier (String identifier)
	{
		this.identifier=identifier;
	}
	
	public String getIdentifier()
	{
		return identifier;
	}
}