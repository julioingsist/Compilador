package Compilador;

public class If extends S 
{
	private Expression expression;
	private S ifS;
	private S elseS;
		
	public If(Expression expression,S ifS,S elseS)
	{
		this.expression=expression;
		this.ifS=ifS;
		this.elseS=elseS;
	}
	
	public Expression getExpression() 
	{
		return expression;
	}
	
	public S getIfS() 
	{
		return ifS;
	}
	
	public S getElseS()
	{
		return elseS;
	}
}