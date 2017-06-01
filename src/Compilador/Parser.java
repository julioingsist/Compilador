package Compilador;
import java.util.Vector;;

public class Parser 
{
	private Scanner s;
	private int t;
	private String errores="";
	private Vector<S> statements;
	private Vector<Simbol> simbols;
	private Expression exp;
	private Quadruples qu;
	private int pos=0;
	private Comparation comp;
	private String token;
	private Identifier identifier1,identifier2;
	private Number n;
	
	//constructor de la clase
	public Parser(String cadena)
	{
		statements=new Vector<S>();
		simbols=new Vector<Simbol>();
		errores="";
		s=new Scanner(cadena);
		programa();
	}
	
	//inicio del análisis sintáctico
	public void programa()
	{
		advance();
		D();
		S();
		if (t!=0)
			error();
		salida();
	}
	
	//fin del análisis
	public void salida()
	{
		if (errores.length()==0)
			errores="No se encontraron errores, compilación exitosa";
	}
	
	//declaración de variables (D)
	public void D()
	{
		if (t!=Scanner.tipo)
			return;
		eat(Scanner.tipo);
		Type type=new Type(token);
		eat(Scanner.identificador);
		Identifier id=new Identifier(token); 
		
		eat(Scanner.finInstruccion);
		if(lookup(id))
			errores+="Error: El identificador '"+id.getIdentifier()+"' está declarado más de una vez"+"\n";
		simbols.add(new Simbol(pos,id,type));
		pos++;
		D(); 
 	}
	
	//statements (S) 
	public void S()
	{
		switch(t)
		{
			case Scanner.pif: eat(Scanner.pif);E();eat(Scanner.pthen);S();eat(Scanner.pelse);
				S(); 
				break;
			case Scanner.identificador: eat(Scanner.identificador); 
				identifier1=new Identifier(token);
				eat(Scanner.eq); E();
				if(!lookup(identifier1))
					errores+="Error: identificador '"+identifier1.getIdentifier()+"' no declarado"+"\n";
				if(identifier1!=null && identifier2!=null)
				{
					if(!verifyTypes(new Comparation(identifier1,null,identifier2)))
						errores+="Error: Incompatibilidad de tipos en identificadores: "+identifier1.getIdentifier()+","+identifier2.getIdentifier()+"\n";
				}
				else if(n!=null)
				{
					if(!verifyTypes(new Comparation(identifier1,null,n)))
						errores+="Error: Incompatibilidad de tipos en asignación: "+identifier1.getIdentifier()+"="+n.getNumber()+". Se esperaba un int"+"\n";
				}
				break;     
			case Scanner.llaveA: eat(Scanner.llaveA);S();L(); 
				 break;
			case Scanner.print: eat(Scanner.print);E(); statements.add(new Print(exp));
				 break;
			default: error(); advance();	  
			      break;
		}
	}

	//expresion (E)
	private void E() 
	{
		Identifier id1 = null,id2=null;
		Number number = null,number2 = null;
		Operator ope = null;
		switch(t)
		{
			case Scanner.identificador: eat(Scanner.identificador); 
			     id1=new Identifier(token);
			      if(!lookup(id1))
			    	 errores+="Error: identificador '"+id1.getIdentifier()+"' no declarado"+"\n";
				break;
			case Scanner.numero: eat(Scanner.numero);
				 number=new Number(token);
				break;
			case Scanner.ptrue: eat(Scanner.ptrue);	
				break;
			default: error(); advance();	
		} 
			
		if(t==Scanner.op)
		{
			eat(Scanner.op);
			ope=new Operator(token);
			switch(t)
			{
				case Scanner.identificador: eat(Scanner.identificador);
				id2=new Identifier(token);
				if(!lookup(id2))
					errores+="Error: identificador '"+id2.getIdentifier()+"' no declarado"+"\n";
					break;
				case Scanner.numero: eat(Scanner.numero);
				number2=new Number(token);
					break;
				default: error(); advance();	
			}
			if(id1!=null)
			{
				if(id2!=null)
				{
					comp=new Comparation(id1,ope,id2);
					if (!verifyTypes(comp))
						errores+="Error: Incompatibilidad de tipos en identificadores: "+id1.getIdentifier()+","+id2.getIdentifier()+"\n";
				}
				else
				{
					comp=new Comparation(id1,ope,number2);
					if (!verifyTypes(comp))
						errores+="Error: Incompatibilidad de tipos en expresion: "+id1.getIdentifier()+ope.getOperator()+number2.getNumber()+". Se esperaba un int"+"\n";
				}
			}
			else
			{
				if(id2!=null)
				{
					comp=new Comparation(number,ope,id2);
					if (!verifyTypes(comp))
						errores+="Error: Incompatibilidad de tipos en expresion: "+number.getNumber()+ope.getOperator()+id2.getIdentifier()+". Se esperaba un int"+"\n";
				}
				else
					comp=new Comparation(number,ope,number2);
			}
		}
		else
		{
			if (id1!=null)
				identifier2=new Identifier(id1.getIdentifier());
			else if (number!=null)
				n=number;
			else
				exp=new True();
		}
	}	
	
	//fin de statement (L)
	public void L()
	{
		eat(Scanner.llaveC);
	}
	
	//metodo para obtener el siguiente token del scanner 
	public void advance()
	{
		token=s.getElement();
		t=s.getToken();
	}
	
	//metodo para analizar y consumir el token   
	public void eat(int tok)
	{
		if (t==-1)
		{
			errores+="El token '"+token+"' no es un token válido"+"\n";
			advance();
			return;
		}
			
		if (t!=tok)
		{
			errores+="Sintaxis incorrecta cerca de '"+token+"', se esperaba '"+s.tokenEsperado(tok)+"'"+"\n";
			advance();
			return;
		}
			
		if (tok==t)
			advance();
	}
	
	//metodo que define los errores lexicos y sintacticos
	public void error()
	{
		if (t==-1)
		{
			errores+="El token '"+token+"' no es un token válido"+"\n";
			return;
		}
		else
		{
			errores+="Sintaxis incorrecta cerca de '"+token+"', se esperaba un statement"+"\n";
			return;
		}
	}
	
	//metodo para regresar los errores a la aplicacion
	public String getErrores()
	{
		return errores;
	}
	
	//metodo que revisa los tipos de los operandos de una operacion para saber si coinciden o no
	public boolean verifyTypes(Comparation c)
	{
		Operand op1=c.getoperand();
		Operand op2=c.getoperand2();
		Identifier id1 = null;
		Identifier id2 = null;
		Number n1 = null,n2=null;
		String t1="",t2="";
		if (op1 instanceof Identifier)
			id1=(Identifier)op1;	
		else
			n1=(Number)op1;
		if (op2 instanceof Identifier)
			id2=(Identifier)op2;	
		else
			n2 =(Number)op2;
		if (id1!=null && id2!=null)
		{
			t1=getTypeSimbol(id1);
			t2=getTypeSimbol(id2);
			if (t1.equals(t2))
				return true;
			else
				return false;	
		}
		if (id1!=null && n2!=null)
		{
			t1=getTypeSimbol(id1);
			if (t1.equals("int") && !(s.esEntero(n2.getNumber())))
				return false;
			else 
				return true;
		}
		if (n1!=null && id2!=null)
		{
			t2=getTypeSimbol(id2);
			if (t2.equals("int") && !(s.esEntero(n1.getNumber())))
				return false;
			else 
				return true;
		}
		return false;
	}
	
	//metodo que indica si la variable se encuentra declarada 
	public boolean lookup(Identifier id)
	{
		for(int i=0;i<simbols.size();i++)
		{
			if (simbols.elementAt(i).getIdentifier().equals(id.getIdentifier()))
				return true;
		}
		return false;
	}

	//metodo que busca la posicion de una variable en la tabla de simbolos
	public int buscaPos(Identifier id)
	{
		for(int i=0;i<simbols.size();i++)
		{
			if (simbols.elementAt(i).getIdentifier().equals(id.getIdentifier()))
				return i;
		}
		return -1;
	}
	
	//metodo para regresar la tabla de simbolos a la interfaz
	public Vector<Simbol> getSimbolTable()
	{
		return simbols;
	}
	
	//metodo que regresa el tipo de dato del identificador 
	public String getTypeSimbol(Identifier id)
	{
		String type="";
		for(int i=0;i<simbols.size();i++)
		{
			if (simbols.elementAt(i).getIdentifier().equals(id.getIdentifier()))
				type=simbols.elementAt(i).getType();
		}
		return type;
	}
}
