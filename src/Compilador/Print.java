package Compilador;

public class Print extends S
{
	Expression expression;
	
	public Print(Expression expression)
	{
		this.expression=expression;
	}
	
	public Expression getExpression()
	{
		return expression;
	}
}
