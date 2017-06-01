package Compilador;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

//clase para generar el codigo intermedio(en este caso cuádruples)
public class Quadruples{

	private Vector<String> quadruples;
	private int pos=1;
	private String ruta="C://";
	
	public Quadruples() 
	{
		quadruples=new Vector<String>(); 
	}
	
	public void saveFile()
	{
		try 
		{
			FileWriter fw = new FileWriter(ruta);
			BufferedWriter bw = new BufferedWriter(fw);
			for(int i=0;i<quadruples.size();i++)
			{
				bw.write(quadruples.elementAt(i));
				bw.newLine();
			}
			bw.flush();	
			bw.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public int getPos()
	{
		return pos;
	}
}
