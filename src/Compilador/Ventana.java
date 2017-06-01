package Compilador;
import java.io.*;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class Ventana extends JFrame implements ActionListener
{
	private FileWriter fw;
	private FileReader fr;
	private File arch;
	private JFileChooser fc1;
	private JMenuBar barra;
	private JMenu archivo;
	private JMenuItem items[];
	private String menus[]={"Nuevo","Abrir","Guardar","Limpiar","Salir"};
	private Character accesos[]={'N','A','G','L','S'};
	private JTextArea area,area2;
	private String texto;
	private JScrollPane sp1,sp2,sp3;
	private JPanel panel,p1,p2,p3,p4;
	private JPopupMenu menucont;
	private boolean modificado=false;
	private int opcion;
	private Parser p;
	private JLabel lbCodigo,lbErrores,lblTabla;
	private JButton btnCompilar;
	private DefaultTableModel mod;
	private JTable tabla;
	private Vector<Simbol> tablaSimbolos;
	
	public Ventana()
	{	
		super("Compilador hecho en java");
		hazInterfaz();
		hazEscuchadores();
	}
	
	public void hazInterfaz()
	{
		setLayout(new GridLayout(2,2));
		mod=new DefaultTableModel();
		mod.addColumn("Identificador");
		mod.addColumn("Tipo");
		tabla=new JTable(mod);
		barra=new JMenuBar();
		archivo=new JMenu("Menú");
		items=new JMenuItem[menus.length];
		menucont=new JPopupMenu();
		fc1=new JFileChooser();
		area=new JTextArea();
		area2=new JTextArea();
		panel=new JPanel();
		panel.setLayout(new GridLayout(0,1));
		p1=new JPanel();
		p2=new JPanel();
		p3=new JPanel();
		p4=new JPanel();
		p1.setLayout(new BorderLayout());
		p3.setLayout(new BorderLayout());
		p4.setLayout(new BorderLayout());
		sp1=new JScrollPane(area);
		sp2=new JScrollPane(area2);
		sp3=new JScrollPane(tabla);
		for(int i=0;i<items.length;i++)
			items[i]=new JMenuItem(menus[i]);
		for(int i=0;i<5;i++)
			archivo.add(items[i]);		
		for(int i=0;i<items.length;i++)
			items[i].setMnemonic(accesos[i]);
		for(int i=5;i<items.length;i++)
			menucont.add(items[i]);
		barra.add(archivo);
		setJMenuBar(barra);
		lbCodigo=new JLabel("Código");
		lbErrores=new JLabel("Errores");
		lblTabla=new JLabel("Tabla de Símbolos");
		btnCompilar=new JButton("Compilar");
		p2.add(btnCompilar);
		p1.add(lbCodigo,BorderLayout.NORTH);
		p1.add(sp1,BorderLayout.CENTER);
		p3.add(lbErrores,BorderLayout.NORTH);
		p3.add(sp2,BorderLayout.CENTER);
		p4.add(lblTabla,BorderLayout.NORTH);
		p4.add(sp3,BorderLayout.CENTER);
		add(p1);
		add(p2);
		add(p3);
		add(p4);
		setSize(800,500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(3);
		setVisible(true);
	}
	
	public void hazEscuchadores()
	{
		for(int i=0;i<items.length;i++)
			items[i].addActionListener(this);
		btnCompilar.addActionListener(this);
	}
			
	public void nuevo()
	{
		if(area.getText().length()==0)
			return;
		if(modificado)
			opcion=JOptionPane.showConfirmDialog(this, "¿Desea guardar los cambios?");
		if(opcion==0)
			guardar();
		if(opcion==2)
			return;
		limpiar();
	}
	
	public void abrir()
	{
		String aux="";
		opcion=fc1.showOpenDialog(this);
		String texto="";
		
		if(opcion==0) 
		{
			arch=fc1.getSelectedFile();
			
				try {
					fr=new FileReader(arch);
				} catch (FileNotFoundException e1) 
				{
					e1.printStackTrace();
				}
				BufferedReader br=new BufferedReader(fr);
				try 
				{
					while((aux=br.readLine())!=null)
					{
						texto+= aux+"\n";
					}
					br.close();
				} catch (IOException e) 
				{
					e.printStackTrace();
				}
			area.setText(texto);
		}
	}
	
	public void guardar()
	{
		if(area.getText().length()==0)
			return;
		opcion=fc1.showSaveDialog(this);
		
		if(opcion==0)
		{
			arch=fc1.getSelectedFile();
			texto=area.getText();
			
			while(arch.exists())
			{
				JOptionPane.showMessageDialog(fc1, "El nombre de archivo ya existe");
				opcion=fc1.showSaveDialog(this);
				arch=fc1.getSelectedFile();
			}
				try 
				{
					fw=new FileWriter(arch+".txt");
					fw.write(texto);
					fw.close();
				} catch (IOException e) 
				{
					e.printStackTrace();
				}
				modificado=false;
		}
	}
	
	public void limpiar()
	{
		area.setText("");
		area2.setText("");
	}
	
	public void salir()
	{
		if(area.getText().length()==0)
			System.exit(0);
		if(modificado)
			opcion=JOptionPane.showConfirmDialog(this, "¿Desea guardar los cambios?");
		if(opcion==0)
		{	
			guardar();
			System.exit(0);
		}	
		if(opcion==1)
			System.exit(0);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource()==btnCompilar)
		{
			if (area.getText().length()>0)
			{
				p=new Parser(area.getText());
				area2.setText(p.getErrores());
				muestraTabla();
			}
			else
			{
				area2.setText("");
				mod=new DefaultTableModel();
				mod.addColumn("Identificador");
				mod.addColumn("Tipo");
				tabla.setModel(mod);
			}
		}
		
		int index=-1;
				
		for(int i=0;i<items.length;i++)
		{
			if(e.getSource()==items[i])
			{
				index=i;
				break;
			}
		}
		switch(index)
		{
			case 0:
				nuevo();
				break;
			case 1:
				abrir();
				break;
			case 2:
				guardar();
				break;
			case 3:
				limpiar();
				break;
			case 4:
				salir();
				break;
		}
	}
	
	private void muestraTabla()
	{
		tablaSimbolos=p.getSimbolTable();
		mod=new DefaultTableModel();
		mod.addColumn("Identificador");
		mod.addColumn("Tipo");
		for(int i=0;i<tablaSimbolos.size();i++)
		{
			mod.addRow(new Object[]{tablaSimbolos.elementAt(i).getIdentifier(),tablaSimbolos.elementAt(i).getType()});
		}
		tabla.setModel(mod);
	}
	
	public static void main(String[] args) 
	{
		new Ventana();
	}
}
