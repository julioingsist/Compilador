package Compilador;

public class D
{
	private String type;
	private String identifier;
		
	public D(Type type,Identifier identifier)
	{
		this.type=type.getType();
		this.identifier=identifier.getIdentifier();
	}
		
	public String getType()
	{
		return type;
	}
	
	public String getIdentifier()
	{
		return identifier;
	}
}
