package Compilador;

public class Simbol
{
	private String identifier;
	private String type;
	private int pos;
	
	public Simbol(int pos,Identifier identifier,Type type)
	{
		this.pos=pos;
		this.identifier=identifier.getIdentifier();
		this.type=type.getType();
	}
	
	public String getIdentifier()
	{
		return identifier;
	}
	
	public String getType()
	{
		return type;
	}
	
	public int getPos()
	{
		return pos;
	}
}
