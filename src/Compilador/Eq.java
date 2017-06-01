package Compilador;

public class Eq extends S
{
	private Identifier identifier;
	private Expression expression;
	
	public Eq(Identifier identifier,Expression expression)
	{
		this.identifier=identifier;
		this.expression=expression;
	}
	
	public Identifier getIdentifier()
	{
		return identifier;
	}
	
	public Expression getExpression()
	{
		return expression;
	}
}
