
package Banco;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

@SuppressWarnings("serial")
public class Prestamos extends JFrame {

	private JPanel contentPane;
	
	private JPanel pLogin;
	private JPanel pClienteSelect;
	private JLabel lNumero;
	private JLabel lPassword;
	private JTextField tNumero, tNroDoc, tTipoDoc;
	private JTextField tMonto;
	private JPasswordField pContraseña;
	private JButton bIngresar;
	
	private JPanel pConsulta;
	private JButton bCreacion,bCrearPres;
	private JButton bPago;
	private JButton bCliente;
	private JButton bElegir;
	
	private JPanel pCreacion;
	private JPanel pPago;
	
	private JPanel pCliente;
	private JTable tablaMorosos;
	
	protected Connection conexionBD = null;
	private String consulta;
	private String legajo;
	private String nroDoc, tipoDoc;
	private String password;
	private String periodo_elegido;
	private Integer [] nros;
	private int nroCliente;
	private float monto_tope;
	private LinkedList<Integer> nros_doc;
	private LinkedList<Integer> prestamos_vigentes;
	private LinkedList<Integer> prestamos_actuales;
	private LinkedList<String> tipos_doc;
	
	private JLabel lNroDoc,lTipoDoc,lHeadSelect, lClienteSelect;
	private JLabel lPeriodos, lMonto;
	private JLabel lTitleCreacion, lTitlePago, lTitleCliente, lEstado, lResultado;
	
	private JComboBox<String> comboPeriodo;
	
	public Prestamos(){
		super();
		setTitle("Banco-Administracion de prestamos");
		initGUI();
	}
	
	
	private void  initGUI() {
		try{
			//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 850, 600);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			contentPane.setLayout(null);
			this.addComponentListener(new ComponentAdapter() {
	            public void componentHidden(ComponentEvent evt) {
	               thisComponentHidden(evt);
	            }
	            public void componentShown(ComponentEvent evt) {
	               thisComponentShown(evt);
	            }
	         });
			
			
			/*Comienzo panel login*/
			pLogin = new JPanel();
			pLogin.setBounds(0, 0, 434, 79);
			contentPane.add(pLogin);
			pLogin.setLayout(null);
			
			lNumero = new JLabel("Legajo");
			lNumero.setBounds(10, 11, 71, 14);
			pLogin.add(lNumero);
			
			lPassword = new JLabel("Password");
			lPassword.setBounds(10, 54, 71, 14);
			pLogin.add(lPassword);
			
			tNumero = new JTextField();
			tNumero.setBounds(103, 8, 86, 20);
			pLogin.add(tNumero);
			tNumero.setColumns(10);
			
			pContraseña = new JPasswordField();
			pContraseña.setBounds(103, 51, 86, 20);
			pLogin.add(pContraseña);
			
			bIngresar = new JButton("Ingresar");
			bIngresar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					legajo=tNumero.getText();
					password=pContraseña.getText();
					
					if(verificacion()) 
					{
						pConsulta.setVisible(true);
						pLogin.setVisible(false);
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Tarjeta o Contraseña Incorrecta");
					}
					
				}
			});
			bIngresar.setBounds(293, 23, 89, 23);
			pLogin.add(bIngresar);
			/*Fin panel pLogin*/
			
			/*Comienzo panel pConsulta*/
			pConsulta = new JPanel();
			pConsulta.setBounds(0, 0, 800, 680);
			pConsulta.setBorder(new EmptyBorder(5, 5, 5, 5));
			contentPane.add(pConsulta);
			pConsulta.setLayout(null);
			pConsulta.setVisible(false);
			
			pClienteSelect = new JPanel();
			pClienteSelect.setBounds(10, 0, 334, 90);
			pClienteSelect.setVisible(true);
			pClienteSelect.setLayout(null);
			
			//Zona de seleccion de cliente
			lTipoDoc = new JLabel("Tipo de doc");
			lTipoDoc.setBounds(10, 10, 86, 14);
			pClienteSelect.add(lTipoDoc);
			
			lNroDoc = new JLabel("Nro de doc");
			lNroDoc.setBounds(10, 49, 86, 14);
			pClienteSelect.add(lNroDoc);
			
			tTipoDoc = new JTextField();
			tTipoDoc.setBounds(10, 24, 86, 20);
			tTipoDoc.setVisible(true);
			pClienteSelect.add(tTipoDoc);
			tTipoDoc.setColumns(10);
			
			tNroDoc = new JTextField();
			tNroDoc.setBounds(10, 64, 86, 20);
			tNroDoc.setVisible(true);
			pClienteSelect.add(tNroDoc);
			tNroDoc.setColumns(10);
			
			bElegir = new JButton("Elegir");
			bElegir.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				    desconectarBD();
				    conectarBD();
					nroDoc = tNroDoc.getText();
					tipoDoc = tTipoDoc.getText();
					if(nroDoc.length()>0 && tipoDoc.length()>0 && verificarCliente()) 
					{
						pCliente.setVisible(false);
						pPago.setVisible(false);
						pCreacion.setVisible(false);
						lClienteSelect.setForeground(Color.DARK_GRAY);
						lClienteSelect.setText(nroDoc+" ("+tipoDoc+")");
						habilitarBotones();
						establecerEstado();
						establecerNroCliente();
					}
					else 
					{
						deshabilitarBotones();
						lClienteSelect.setText("Vacio");
						lClienteSelect.setForeground(Color.GRAY);
						JOptionPane.showMessageDialog(null, "Ingrese un numero y tipo de documento valido.", "Invalido", JOptionPane.ERROR_MESSAGE);
					}
				}
			});
			bElegir.setBounds(150, 20, 133, 23);
			pClienteSelect.add(bElegir);
			
			//Zona de cliente selecccionado
			lHeadSelect = new JLabel("Cliente seleccionado:");
			lHeadSelect.setBounds(150, 50, 133, 23);
			pClienteSelect.add(lHeadSelect);
			
			lClienteSelect = new JLabel("Vacio");
			lClienteSelect.setForeground(Color.GRAY);
			lClienteSelect.setBounds(150, 65, 133, 23);
			pClienteSelect.add(lClienteSelect);
			
			pConsulta.add(pClienteSelect);
			
			//Zona de los botones
			bCreacion = new JButton("Crear prestamo");
			bCreacion.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					pCreacion.setVisible(true);
					pPago.setVisible(false);
					pCliente.setVisible(false);
				}
			});
			bCreacion.setBounds(10, 100, 133, 23);
			pConsulta.add(bCreacion);
			
			bPago = new JButton("Cuotas");
			bPago.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					pPago.setVisible(true);
					pCreacion.setVisible(false);
					pCliente.setVisible(false);
					oyentePagoCuotas();
				}
			});
			bPago.setBounds(310, 100, 107, 23);
			pConsulta.add(bPago);
			
			bCliente = new JButton("Morosos");
			bCliente.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					pCliente.setVisible(true);
					pPago.setVisible(false);
					pCreacion.setVisible(false);
					oyenteClientesMorosos();
				}
			});
			bCliente.setBounds(610, 100, 107, 23);
			pConsulta.add(bCliente);
			
			deshabilitarBotones();
			/*Fin panel pConsulta*/
			
			/*Comienzo panel pCreacion de prestamos*/
			pCreacion = new JPanel();
			pCreacion.setBounds(10, 150, 400, 400);
			pConsulta.add(pCreacion);
			pCreacion.setVisible(false);
			pCreacion.setLayout(null);
			
			/*Fin creacion panel pCreacion de prestamos*/
			
			pPago = new JPanel();
			pPago.setBounds(10, 150, 334, 178);
			pConsulta.add(pPago);
			pPago.setVisible(false);
			pPago.setLayout(null);
			
			/*Creacion panel pCliente morosos*/
			pCliente = new JPanel();
			pCliente.setBounds(10, 150, 334, 178);
			pConsulta.add(pCliente);
			pCliente.setVisible(false);
			pCliente.setLayout(null);
			
			{   
		        //Creacion del panel pCreacion 
				//Titulo
				lTitleCreacion = new JLabel("Creacion de Prestamo");
				lTitleCreacion.setBounds(150, 10, 250, 20);
				lTitleCreacion.setFont(new Font("Serif", Font.PLAIN, 24));
				lTitleCreacion.setForeground(Color.BLACK);
				pCreacion.add(lTitleCreacion);
				
				//Estado
				lEstado = new JLabel("");
				lEstado.setBounds(10, 40, 350, 20);
				pCreacion.add(lEstado);
				
				//Seleccion de periodo
				lPeriodos = new JLabel("Periodo: ");
				lPeriodos.setBounds(10, 80, 50, 25);
				pCreacion.add(lPeriodos);
				
	    		//ComboBox de periodos
	      		comboPeriodo = new JComboBox<String>();
	      		comboPeriodo.setBounds(60, 80, 50, 25);
	      		pCreacion.add(comboPeriodo);
	      		comboPeriodo.addActionListener(new ActionListener() {
	     			public void actionPerformed(ActionEvent e) {
	     				try 
	     				{
	         				periodo_elegido = comboPeriodo.getSelectedItem().toString();
	         			}
	     				catch(NullPointerException nexcp){
	     					periodo_elegido = "Seleccionar opcion";
	     				}
	     			}
	     		});
				
	      		//Seleccion de Monto
	      		lMonto = new JLabel("Monto: ");
				lMonto.setBounds(140, 80, 50, 25);
				pCreacion.add(lMonto);
				
				tMonto = new JTextField();
				tMonto.setBounds(180, 80, 100, 25);
				pCreacion.add(tMonto);
				
				//Boton creacion de crear
				bCrearPres = new JButton("Crear");
				bCrearPres.setBounds(300, 80, 80, 25);
				pCreacion.add(bCrearPres);
				bCrearPres.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						oyenteCrearUnPrestamo();
					}
				});
				
				lResultado = new JLabel("");
				lResultado.setBounds(110, 110, 250, 25);
				pCreacion.add(lResultado);
			}
		}//try
		catch(Exception e){
			e.printStackTrace();
		}
		
	}//initgui
	
	/*Metodos privados*/
	//Metodo privado para verificar que el usuario sea correcto.
	private boolean verificacion() 
	{
		boolean valida = false;
		consulta ="SELECT legajo FROM empleado WHERE password=md5('"+password+"');";
		try {
			Statement stmt = conexionBD.createStatement();
			ResultSet rs= stmt.executeQuery(consulta);
			int i=0;
			nros= new Integer[1];
			
			while(rs.next()){
				nros[i]=rs.getInt(1);
				i++;
			}

			rs.close();
			
			i=0;
			while(!valida && i<nros.length) {
				valida = nros[i].equals(Integer.parseInt(legajo));
				i++;
			}
		}
		catch (SQLException ex){
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), ex.getMessage() + "\n","Error al ejecutar la consulta.",JOptionPane.ERROR_MESSAGE);
		}
		catch (Exception e) {
			System.out.println("No se encontro");
		}
		
		return valida;
	}
	
	private boolean verificarCliente()
	{
		boolean valida = false;
		consulta="SELECT nro_doc, tipo_doc FROM cliente;";
		try {
			Statement stmt = conexionBD.createStatement();
			ResultSet rs= stmt.executeQuery(consulta);
			
			nros_doc= new LinkedList<Integer>();
			tipos_doc= new LinkedList<String>();
			
			while(rs.next()){
				Integer i=rs.getInt(1);
				String s=rs.getString(2);
				nros_doc.add(i);
				tipos_doc.add(s);
			}

			rs.close();
			
			Iterator<Integer> i_nros = nros_doc.iterator();
			Iterator<String> i_tipos = tipos_doc.iterator();
			while(i_nros.hasNext() && i_tipos.hasNext() && !valida) {
				valida = i_nros.next().equals(Integer.parseInt(nroDoc)) && i_tipos.next().equals(tipoDoc); 
			}
		}
		catch (SQLException ex){
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), ex.getMessage() + "\n","Error al ejecutar la consulta.",JOptionPane.ERROR_MESSAGE);
		}
		catch (Exception e) {
			System.out.println("No se encontro");
		}
		
		return valida;
	}
	
	private boolean verificarMonto(double monto) 
	{
		boolean valida = false;
		try
	    {
			valida = monto > 0.00;
			Statement stmt = this.conexionBD.createStatement();
			String sql = "SELECT MAX(monto_sup) FROM tasa_prestamo WHERE periodo="+periodo_elegido+";";
			ResultSet rs = stmt.executeQuery(sql);
	         while (rs.next())
	         {
	            monto_tope=rs.getFloat(1);
	         }
	         valida = monto <= monto_tope;
	         
	         rs.close();
	         stmt.close();
		}
		catch (SQLException ex)
	     {
	         // en caso de error, se muestra la causa en la consola
	         System.out.println("SQLException: " + ex.getMessage());
	         System.out.println("SQLState: " + ex.getSQLState());
	         System.out.println("VendorError: " + ex.getErrorCode());
	     }
		if(!valida) 
		{
			JOptionPane.showMessageDialog(null, "Ingrese un monto valido.", "Invalido",JOptionPane.ERROR_MESSAGE);
		}
		return valida;
	}
	
	private void habilitarBotones() 
	{
		bCreacion.setEnabled(true);
		bPago.setEnabled(true);
		bCliente.setEnabled(true);
	}
	
	private void deshabilitarBotones() 
	{
		bCreacion.setEnabled(false);
		bPago.setEnabled(false);
		bCliente.setEnabled(false);
	}
	
	private void refrescarComboPeriodo() 
	{
		try
	      {
		    if(comboPeriodo.getItemCount()>0) 
		    {
		    	comboPeriodo.removeAllItems();
		    }
		    
	         Statement stmt = this.conexionBD.createStatement();
	         
	         String sql = "SELECT DISTINCT periodo FROM tasa_prestamo;";
	         ResultSet rs = stmt.executeQuery(sql);
	         while (rs.next()) {
	        	  String str = rs.getString("periodo");
	        	  comboPeriodo.addItem(str);
	         }
	         
	         // se cierran los recursos utilizados 
	         rs.close();
	         stmt.close();
	      }
	      catch (SQLException ex)
	      {
	         // en caso de error, se muestra la causa en la consola
	         System.out.println("SQLException: " + ex.getMessage());
	         System.out.println("SQLState: " + ex.getSQLState());
	         System.out.println("VendorError: " + ex.getErrorCode());
	      }
	}
	
	private void establecerEstado() 
	{
		boolean habilitado = true;
		String consulta_vigentes="SELECT DISTINCT nro_prestamo FROM pago WHERE fecha_pago IS NULL;";
		String consulta_pres_actual="SELECT nro_prestamo FROM prestamo NATURAL JOIN cliente WHERE nro_doc="+nroDoc+" AND tipo_doc='"+tipoDoc+"';";
		Integer i;
		try 
		{
			Statement stmt = conexionBD.createStatement();
			prestamos_vigentes= new LinkedList<Integer>();
			prestamos_actuales= new LinkedList<Integer>();
			
			//Lista de prestamos vigentes
			ResultSet rs1= stmt.executeQuery(consulta_vigentes);
			while(rs1.next())
			{
				i=rs1.getInt(1);
				prestamos_vigentes.add(i);
			}
			//Lista de prestamos del cliente actual
			ResultSet rs2= stmt.executeQuery(consulta_pres_actual);
			while(rs2.next()) 
			{
				i=rs2.getInt(1);
				prestamos_actuales.add(i);
			}
			//Busqueda de elementos en comun
			Iterator<Integer> i_vigentes = prestamos_vigentes.iterator();
			while(i_vigentes.hasNext() && habilitado) 
			{
				habilitado = !prestamos_actuales.contains(i_vigentes.next()); 
			}
			if(habilitado) 
			{
				lEstado.setText("Estado del cliente: Habilitado para solicitar prestamo.");
				bCrearPres.setEnabled(true);
			}
			else 
			{
				lEstado.setText("Estado del cliente: Inhabilitado para solicitar prestamo.");
				bCrearPres.setEnabled(false);
			}
			
			rs1.close();
			rs2.close();
			stmt.close();
		}
		catch (SQLException ex)
		{
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), ex.getMessage() + "\n","Error al ejecutar la consulta.",JOptionPane.ERROR_MESSAGE);
		}	
	}
	
	private void establecerNroCliente() 
	{
		String consulta="SELECT nro_cliente FROM cliente WHERE nro_doc="+nroDoc+" AND tipo_doc='"+tipoDoc+"';";
		try 
		{
			Statement stmt = conexionBD.createStatement();
			ResultSet rs1= stmt.executeQuery(consulta);
			while(rs1.next())
			{
				nroCliente = rs1.getInt(1);
			}
			
			rs1.close();
			stmt.close();
		}
		catch (SQLException ex)
		{
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), ex.getMessage() + "\n","Error al ejecutar la consulta.",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void thisComponentShown(ComponentEvent evt) 
	{
	      this.conectarBD();
	}
	   
	private void thisComponentHidden(ComponentEvent evt) 
	{
	      this.desconectarBD();
	}
	
	private void conectarBD()
	{
	      if (this.conexionBD == null)
	      { 
	         try
	         {  // Se carga y registra el driver JDBC de MySQL  
	        // no es necesario para versiones de jdbc posteriores a 4.0 
	            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
	         }
	         catch (Exception ex)
	         {  
	            System.out.println(ex.getMessage());
	         }
	     
	         try
	         {  //se genera el string que define los datos de la conección 
	            String servidor = "localhost:3306";
	            String baseDatos = "banco";
	            String usuario = "empleado";
	            String clave = "empleado";
	            String uriConexion = "jdbc:mysql://" + servidor + "/" + baseDatos +"?serverTimezone=UTC";
	            //se intenta establecer la conección
	            this.conexionBD = DriverManager.getConnection(uriConexion, usuario, clave);
	            
	            refrescarComboPeriodo();
	         }
	         catch (SQLException ex)
	         {
	            JOptionPane.showMessageDialog(this,
	                                          "Se produjo un error al intentar conectarse a la base de datos.\n" + 
	                                           ex.getMessage(),
	                                          "Error",
	                                          JOptionPane.ERROR_MESSAGE);
	            System.out.println("SQLException: " + ex.getMessage());
	            System.out.println("SQLState: " + ex.getSQLState());
	            System.out.println("VendorError: " + ex.getErrorCode());
	         }
	      }
	 }

	 private void desconectarBD()
	 {
	      if (this.conexionBD != null)
	      {
	         try
	         {
	            this.conexionBD.close();
	            this.conexionBD = null;
	         }
	         catch (SQLException ex)
	         {
	            System.out.println("SQLException: " + ex.getMessage());
	            System.out.println("SQLState: " + ex.getSQLState());
	            System.out.println("VendorError: " + ex.getErrorCode());
	         }
	      }
	 }
	
	private void oyenteCrearUnPrestamo(){
		 boolean puede_crear=false;
		 double monto = Double.parseDouble(tMonto.getText());
		 puede_crear = verificarMonto(monto);
		 if(puede_crear) 
		 {
			 try
		      {
		         Statement stmt = this.conexionBD.createStatement();
		         String sql = "SELECT tasa FROM tasa_prestamo WHERE periodo="+periodo_elegido+" AND "+monto+" BETWEEN monto_inf AND monto_sup;";
		         ResultSet rs = stmt.executeQuery(sql);
		         double tasa = 0.00;
		         while (rs.next())
		         {
		            tasa = rs.getFloat(1);
		         }
		         double interes = (monto*tasa*Double.parseDouble(periodo_elegido))/1200;
		         double valor_cuota = (monto+interes)/Double.parseDouble(periodo_elegido);
		         
		         sql = "INSERT INTO prestamo(fecha, cant_meses, monto, tasa_interes, interes, valor_cuota, legajo, nro_cliente) "
		         		+ "VALUES (CURDATE(), "+periodo_elegido+", "+monto+", "+tasa+", "+interes+", "+valor_cuota+", "+legajo+", "+nroCliente+");";
		         stmt.execute(sql);
		         
		         lResultado.setText("La transacción finalizo exitosamente.");
		         bCrearPres.setEnabled(false);
		         
		         rs.close();
		         stmt.close();
		      }
		      catch (SQLException ex)
		      {
		         System.out.println("SQLException: " + ex.getMessage());
		         System.out.println("SQLState: " + ex.getSQLState());
		         System.out.println("VendorError: " + ex.getErrorCode());
		      }
	     }
		 else {
			 JOptionPane.showMessageDialog(null, "Ingrese un monto valido.", "Invalido", JOptionPane.ERROR_MESSAGE);
		 }
	}
	
	private void oyentePagoCuotas(){
		
	}
	
	private void oyenteClientesMorosos(){
		 try
	      {
	         // se crea una sentencia o comando jdbc para realizar la consulta 
	    	 // a partir de la coneccion establecida (conexionBD)
	         Statement stmt = this.conexionBD.createStatement();

	         // se prepara el string SQL de la consulta
	         String sql = "SELECT legajo, apellido,nombre, tipo_doc, nro_doc, direccion, telefono, cargo, nro_suc" + 
	                      "FROM empleado";

	         // se ejecuta la sentencia y se recibe un resultset
	         ResultSet rs = stmt.executeQuery(sql);
	         // se recorre el resulset y se actualiza la tabla en pantalla
	         ((DefaultTableModel) this.tablaMorosos.getModel()).setRowCount(0);
	         int i = 0;
	         while (rs.next())
	         {
	        	// agrega una fila al modelo de la tabla
	            ((DefaultTableModel) this.tablaMorosos.getModel()).setRowCount(i + 1);
	            // se agregan a la tabla los datos correspondientes cada celda de la fila recuperada
	            this.tablaMorosos.setValueAt(rs.getInt("legajo"), i, 0);
	            this.tablaMorosos.setValueAt(rs.getString("apellido"), i, 1);            
	            this.tablaMorosos.setValueAt(rs.getString("nombre"), i, 2);
	            this.tablaMorosos.setValueAt(rs.getString("tipo_doc"), i, 3);
	            this.tablaMorosos.setValueAt(rs.getString("nro_doc"), i, 4);            
	            this.tablaMorosos.setValueAt(rs.getString("direccion"), i, 5);
	            this.tablaMorosos.setValueAt(rs.getLong("telefono"), i, 6);
	            this.tablaMorosos.setValueAt(rs.getString("cargo"), i, 7);            
	            this.tablaMorosos.setValueAt(rs.getInt("nro_suc"), i, 8);
	            i++;
	         }
	         // se cierran los recursos utilizados 
	         rs.close();
	         stmt.close();
	      }
	      catch (SQLException ex)
	      {
	         // en caso de error, se muestra la causa en la consola
	         System.out.println("SQLException: " + ex.getMessage());
	         System.out.println("SQLState: " + ex.getSQLState());
	         System.out.println("VendorError: " + ex.getErrorCode());
	      }
	}
	
}//principal
