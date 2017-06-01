package Compilador;

public class Comparation extends Expression 
{
	private Operand operand;
	private Operator operator;
	private Operand operand2;
	
	public Comparation(Operand operand1,Operator operator,Operand operand2)
	{
		this.operand=operand1;
		this.operator=operator;
		this.operand2=operand2;
	}
	
	public Operand getoperand()
	{
		return operand;
	}
	
	public Operator getOperator()
	{
		return operator;
	}
	
	public Operand getoperand2()
	{
		return operand2;
	}
}
