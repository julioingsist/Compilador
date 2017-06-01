package Compilador;
import java.util.*;

public class Scanner 
{
	private Vector <String> tokens;
	private int pos=0,token=-1;
	private StringTokenizer t;
	static final int finArchivo=0,tipo=1,eq=2,pif=3,pthen=4,op=5,finInstruccion=6,ptrue=7,llaveA=8,llaveC=9,
			         identificador=10,numero=11,pelse=12,print=13; 
	
	//constructor de la clase
	public Scanner(String fuente)
	{
		tokens=new Vector<String>();
		fuente=fuente.replace(";", " ; ");
		fuente=fuente.replace("=="," == ");
		fuente=fuente.replace("!="," != ");
		fuente=fuente.replace("{"," { ");
		fuente=fuente.replace("}"," } ");
		t=new StringTokenizer(fuente);
		while(t.hasMoreTokens())
			tokens.add(t.nextToken());
		tokens.add("$");
	}
	
	//metodo para regresar el token actual
	public String getElement()
	{
		if (pos>0)
			return tokens.elementAt(pos-1);
		else
			return tokens.elementAt(pos);
	}
	
	//metodo para regresar el codigo del token actual
	public int getToken()
	{
		if (esIdentificador(tokens.elementAt(pos)))
			token = identificador;
		if (esNumero(tokens.elementAt(pos)))
			token = numero;
	
		switch(tokens.elementAt(pos))
		{
			case "$":
				token=finArchivo;
				break;
			case "int":
				token=tipo;
				break;
			case "float":
				token=tipo;
				break;
			case "=":
				token=eq;
				break;
			case "if":
				token=pif;
				break;
			case "then":
				token=pthen;
				break;
			case "true":
				token=ptrue;
				break;
			case "==":
				token=op;
				break;
			case "!=":
				token=op;
				break;
			case ";":
				token=finInstruccion;
				break;
			case "{":
				token=llaveA;
				break;
			case "}":
				token=llaveC;
				break;
			case "else":
				token=pelse;
				break;
			case "print":
				token=print;
				break;
		}
		if (pos<tokens.size()-1)
			pos++;
		return token;
	}
	
	//metodo que regresa si el token es un identificador o no  
	public boolean esIdentificador(String palabra)
	{
		if (palabra.matches("^[A-Za-z]\\w*"))
			return true;
		return false;
	}
	
	//metodo que regresa si el token es un numero o no
	public boolean esNumero(String palabra)
	{
		if (palabra.matches("^\\d+|^\\d+\\.?\\d+"))
			return true;
		return false;
	}
	
	//metodo que regresa el token esperado por la sintaxis
	public String tokenEsperado(int codigo)
	{
		String esperado="";
		switch(codigo)
		{
			case tipo:
				esperado="tipo de dato";
				break;
			case eq:
				esperado="=";
				break;
			case llaveA:
				esperado="{";
				break;
			case llaveC:
				esperado="}";
				break;
			case pif:
				esperado="if";
				break;
			case pthen:
				esperado="then";
				break;
			case op:
				esperado="operador";
				break;
			case identificador:
				esperado="identificador";
				break;
			case numero:
				esperado="número";
				break;
			case finInstruccion:
				esperado=";";
				break;
				
		}
		return esperado;
	}
	
	public boolean esEntero(String cadena){
		try {
			Integer.parseInt(cadena);
			return true;
		} catch (NumberFormatException nfe){
			return false;
		}
	}
}	
