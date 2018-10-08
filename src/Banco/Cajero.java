package Banco;

import Banco.Fechas;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import quick.dbtable.DBTable;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;

@SuppressWarnings("serial")
public class Cajero extends JFrame {

	private JPanel contentPane;
	
	private JPanel pLogin;
	private JLabel lNumero;
	private JLabel lPassword;
	private JLabel lInicial;
	private JLabel lFinal;
	private JTextField tNumero;
	private JTextField tInicial;
	private JTextField tFinal;
	private JPasswordField pContraseña;
	private JButton bIngresar;
	
	private JPanel pConsulta;
	private JButton bSaldo;
	private JButton bMovimientos;
	private JButton bPeriodo;
	
	private JPanel pSaldo;
	private JPanel pMovimiento;
	private JPanel pMovPeriodo;
	private JPanel pPeriodos;
	
	protected Connection conexionBD = null;
	private String nroTarjeta;
	private String password;
	
	private JScrollPane scrTablaSaldo;
	private JTable tablaSaldo;
	private JScrollPane scrTablaMovimiento;
	private JTable tablaMovimiento;
	private JScrollPane scrTablaMovPeriodo;
	private JTable tablaMovPeriodo;
	private Fechas f = new Fechas();
	private String desde, hasta;
	 
	public Cajero() {
		super();
		initGUI();
	}
		
	private void initGUI(){
		try{
			setTitle("Banco-Cajero");
			setBounds(100, 100, 850, 400);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			contentPane.setBounds(100,100,850,400);
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
			
			/*Creacion panel login*/
			pLogin = new JPanel();
			pLogin.setBounds(0, 0, 434, 79);
			contentPane.add(pLogin);
			pLogin.setLayout(null);
			
			
			lNumero = new JLabel("Numero");
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
					nroTarjeta=tNumero.getText();
					password=pContraseña.getText();
					System.out.println(nroTarjeta);
					System.out.println(password);
					
					pConsulta.setVisible(true);
					pLogin.setVisible(false);
				}
			});
			bIngresar.setBounds(293, 23, 89, 23);
			pLogin.add(bIngresar);
			/*Fin cracion panel pLogin*/
			
			/*Creacion panel pConsulta*/
			pConsulta = new JPanel();
			pConsulta.setBounds(5, 5, 834, 169);
			pConsulta.setBorder(new EmptyBorder(5, 5, 5, 5));
			contentPane.add(pConsulta);
			pConsulta.setLayout(null);
			pConsulta.setVisible(false);
			
			bSaldo = new JButton("Saldo");
			bSaldo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					pSaldo.setVisible(true);
					pMovimiento.setVisible(false);
					pMovPeriodo.setVisible(false);
					oyenteSaldo();
				}
			});
			bSaldo.setBounds(10, 29, 89, 23);
			pConsulta.add(bSaldo);
			
			bMovimientos = new JButton("Movimientos");
			bMovimientos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					pMovimiento.setVisible(true);
					pMovPeriodo.setVisible(false);
					pSaldo.setVisible(false);
					oyenteMovimientos();
				}
			});
			bMovimientos.setBounds(153, 29, 110, 23);
			pConsulta.add(bMovimientos);
			
			bPeriodo = new JButton("Por periodos");
			bPeriodo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					pMovPeriodo.setVisible(true);
					pMovimiento.setVisible(false);
					pSaldo.setVisible(false);
					desde = tInicial.getText();
					hasta = tFinal.getText();
					oyentePeriodos();
				}
			});
			bPeriodo.setBounds(308, 29, 116, 23);
			pConsulta.add(bPeriodo);
			
			pPeriodos = new JPanel();
			pPeriodos.setLayout(null);
			pPeriodos.setBounds(450, 11, 100, 20);
			
			lInicial = new JLabel("Fecha Inicial");
			lInicial.setBounds(500, 11, 71, 14);
			pPeriodos.add(lInicial);
			
			lFinal = new JLabel("Fecha Final");
			lFinal.setBounds(500, 54, 71, 14);
			pPeriodos.add(lFinal);
			
			tInicial = new JTextField();
			tInicial.setBounds(600, 11, 86, 20);
			pPeriodos.add(tInicial);
			tInicial.setColumns(10);
			
			tFinal = new JTextField();
			tFinal.setBounds(600, 54, 86, 20);
			pPeriodos.add(tFinal);
			tFinal.setColumns(10);
			
			pConsulta.add(pPeriodos);
			
			
			/*Fin creacion panel pConsulta*/
			
			//---------------Declaracion de paneles--------------------
			pSaldo = new JPanel();
			pSaldo.setBounds(0, 83, 434, 178);
			contentPane.add(pSaldo);
			pSaldo.setVisible(false);
			
			pMovimiento = new JPanel();
			pMovimiento.setBounds(0, 83, 434, 178);
			contentPane.add(pMovimiento);
			pMovimiento.setVisible(false);
			
			pMovPeriodo = new JPanel();
			pMovPeriodo.setBounds(0, 83, 434, 178);
			contentPane.add(pMovPeriodo);
			pMovPeriodo.setVisible(false);
			
			//-----Panel Saldo------- 
			{  //se crea un JScrollPane para poder mostrar la tabla en su interior 
	            scrTablaSaldo = new JScrollPane();
	            pSaldo.add(scrTablaSaldo, BorderLayout.CENTER);
	            
	            {  //modelo de la tabla donde se almacenaran las tuplas 
	               TableModel SaldoModel =  // se crea un modelo de tabla BarcosModel 
	                  new DefaultTableModel  // extendiendo el modelo DefalutTableModel
	                  (
	                     new String[][] {},
	                     new String[] {"Saldo"}
	                  )
	                  {                      // con una clase anónima 
	            	     // define la clase java asociada a cada columna de la tabla
	            	     Class[] types = new Class[] {java.lang.Integer.class };
	            	    // define si una columna es editable
	                     boolean[] canEdit = new boolean[] { false, false };
	                      
	                    // recupera la clase java de cada columna de la tabla
	                     public Class getColumnClass(int columnIndex) 
	                     {
	                        return types[columnIndex];
	                     }
	                   // determina si una celda es editable
	                     public boolean isCellEditable(int rowIndex, int columnIndex) 
	                     {
	                        return canEdit[columnIndex];
	                     }
	                  };
	               tablaSaldo = new JTable(); // Crea una tabla
	               scrTablaSaldo.setViewportView(tablaSaldo); //setea la tabla dentro del JScrollPane srcTabla               
	               tablaSaldo.setModel(SaldoModel); // setea el modelo de la tabla  
	               tablaSaldo.setAutoCreateRowSorter(true); // activa el ordenamiento por columnas, para
	                                                   // que se ordene al hacer click en una columna
	            }
	         }
			//-----Panel Movimiento------- 
			{  //se crea un JScrollPane para poder mostrar la tabla en su interior 
	            scrTablaMovimiento = new JScrollPane();
	            pMovimiento.add(scrTablaMovimiento, BorderLayout.CENTER);
	            
	            {  //modelo de la tabla donde se almacenaran las tuplas 
	               TableModel MovimientoModel =  // se crea un modelo de tabla BarcosModel 
	                  new DefaultTableModel  // extendiendo el modelo DefalutTableModel
	                  (
	                     new String[][] {},
	                     new String[] {"Fecha","Hora","Tipo","Monto","Codigo Caja","Caja Ahorro"}
	                  )
	                  {                      // con una clase anónima 
	            	     // define la clase java asociada a cada columna de la tabla
	            	     Class[] types = new Class[] {java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class };
	            	    // define si una columna es editable
	                     boolean[] canEdit = new boolean[] { false, false, false, false, false, false };
	                      
	                    // recupera la clase java de cada columna de la tabla
	                     public Class getColumnClass(int columnIndex) 
	                     {
	                        return types[columnIndex];
	                     }
	                   // determina si una celda es editable
	                     public boolean isCellEditable(int rowIndex, int columnIndex) 
	                     {
	                        return canEdit[columnIndex];
	                     }
	                  };
	               tablaMovimiento = new JTable(); // Crea una tabla
	               scrTablaMovimiento.setViewportView(tablaMovimiento); //setea la tabla dentro del JScrollPane srcTabla               
	               tablaMovimiento.setModel(MovimientoModel); // setea el modelo de la tabla  
	               tablaMovimiento.setAutoCreateRowSorter(true); // activa el ordenamiento por columnas, para
	                                                   // que se ordene al hacer click en una columna
	            }
	         }
			//-----Panel Movimiento Periodo------- 
			{  //se crea un JScrollPane para poder mostrar la tabla en su interior 
	            scrTablaMovPeriodo = new JScrollPane();
	            pMovPeriodo.add(scrTablaMovPeriodo, BorderLayout.CENTER);
	            
	            {  //modelo de la tabla donde se almacenaran las tuplas 
	               TableModel MovPeriodoModel =  // se crea un modelo de tabla BarcosModel 
	                  new DefaultTableModel  // extendiendo el modelo DefalutTableModel
	                  (
	                     new String[][] {},
	                     new String[] {"Fecha","Hora","Tipo","Monto","Codigo Caja","Caja Ahorro"}
	                  )
	                  {                      // con una clase anónima 
	            	     // define la clase java asociada a cada columna de la tabla
	            	     Class[] types = new Class[] {java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class };
	            	    // define si una columna es editable
	                     boolean[] canEdit = new boolean[] { false, false, false, false, false, false };
	                      
	                    // recupera la clase java de cada columna de la tabla
	                     public Class getColumnClass(int columnIndex) 
	                     {
	                        return types[columnIndex];
	                     }
	                   // determina si una celda es editable
	                     public boolean isCellEditable(int rowIndex, int columnIndex) 
	                     {
	                        return canEdit[columnIndex];
	                     }
	                  };
	               tablaMovPeriodo = new JTable(); // Crea una tabla
	               scrTablaMovPeriodo.setViewportView(tablaMovPeriodo); //setea la tabla dentro del JScrollPane srcTabla               
	               tablaMovPeriodo.setModel(MovPeriodoModel); // setea el modelo de la tabla  
	               tablaMovPeriodo.setAutoCreateRowSorter(true); // activa el ordenamiento por columnas, para
	                                                   // que se ordene al hacer click en una columna
	            }
	         }
		}
		catch (Exception e) {
	         e.printStackTrace();	
		}
		
	}
	
	
	/*Metodos privados*/
	
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
	         {  
	            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
	         }
	         catch (Exception ex)
	         {  
	            System.out.println(ex.getMessage());
	         }
	     
	         try
	         { 
	            String servidor = "localhost:3306";
	            String baseDatos = "banco";
	            String usuario = "atm";
	            String clave = "atm";
	            String uriConexion = "jdbc:mysql://" + servidor + "/" + baseDatos +"?serverTimezone=UTC";
	           
	            this.conexionBD = DriverManager.getConnection(uriConexion, usuario, clave);
	            System.out.println("Se conecta");
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
	
	
	private void oyenteSaldo(){
		try
	      {
	         // se crea una sentencia o comando jdbc para realizar la consulta 
	    	 // a partir de la coneccion establecida (conexionBD)
	         Statement stmt = this.conexionBD.createStatement();

	         // se prepara el string SQL de la consulta
	         String sql = "SELECT saldo FROM tarjeta NATURAL JOIN trans_cajas_ahorro WHERE '"+ nroTarjeta +"'= nro_tarjeta AND PIN=md5('"+password+"');";

	         // se ejecuta la sentencia y se recibe un resultset
	         ResultSet rs = stmt.executeQuery(sql);
	         // se recorre el resulset y se actualiza la tabla en pantalla
	         ((DefaultTableModel) this.tablaSaldo.getModel()).setRowCount(0);
	         int i = 0;
	         while (rs.next())
	         {
	        	 // agrega una fila al modelo de la tabla
	            ((DefaultTableModel) this.tablaSaldo.getModel()).setRowCount(i + 1);
	            // se agregan a la tabla los datos correspondientes cada celda de la fila recuperada
	            this.tablaSaldo.setValueAt(rs.getString("saldo"), i, 0);       
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
	
	private void oyenteMovimientos(){
		try
	      {
	         // se crea una sentencia o comando jdbc para realizar la consulta 
	    	 // a partir de la coneccion establecida (conexionBD)
	         Statement stmt = this.conexionBD.createStatement();

	         // se prepara el string SQL de la consulta
	         String sql = "SELECT fecha, hora, tipo, IF(tipo<>'deposito', concat('-',monto), monto) AS monto, cod_caja, destino FROM trans_cajas_ahorro NATURAL JOIN tarjeta WHERE '"+ nroTarjeta +"'= nro_tarjeta AND PIN=md5('"+password+"') ORDER BY fecha,hora DESC LIMIT 15;" ;

	         // se ejecuta la sentencia y se recibe un resultset
	         ResultSet rs = stmt.executeQuery(sql);
	         // se recorre el resulset y se actualiza la tabla en pantalla
	         ((DefaultTableModel) this.tablaMovimiento.getModel()).setRowCount(0);
	         int i = 0;
	         while (rs.next())
	         {
	        	 // agrega una fila al modelo de la tabla
	            ((DefaultTableModel) this.tablaMovimiento.getModel()).setRowCount(i + 1);
	            // se agregan a la tabla los datos correspondientes cada celda de la fila recuperada
	            this.tablaMovimiento.setValueAt(f.convertirDateAString(rs.getDate("fecha")), i, 0);
	            this.tablaMovimiento.setValueAt((rs.getTime("hora")).toString(), i, 1);
	            this.tablaMovimiento.setValueAt(rs.getString("tipo"), i, 2);
	            this.tablaMovimiento.setValueAt(rs.getInt("monto"), i, 3);
	            this.tablaMovimiento.setValueAt(rs.getInt("cod_caja"), i, 4);
	            this.tablaMovimiento.setValueAt(rs.getInt("destino"), i, 5);
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
	
	private void oyentePeriodos(){
		try
	      {
	         // se crea una sentencia o comando jdbc para realizar la consulta 
	    	 // a partir de la coneccion establecida (conexionBD)
	         Statement stmt = this.conexionBD.createStatement();

	         // se prepara el string SQL de la consulta
	         hasta = "10/10/2015";
	         desde = "01/10/2015";
	         String sql = "\n" + 
	         		"SELECT fecha, hora, tipo, IF(tipo<>'deposito', concat('-',monto), monto) AS monto, cod_caja, destino FROM tarjeta NATURAL JOIN trans_cajas_ahorro " + 
	         		"WHERE nro_tarjeta='"+nroTarjeta+"' AND PIN=md5('"+password+"') AND fecha BETWEEN '"+Fechas.convertirStringADateSQL(desde)+"' AND '"+Fechas.convertirStringADateSQL(hasta)+"' "+ 
	         		"ORDER BY fecha, hora;";
	         System.out.println(desde);
	         System.out.println(Fechas.convertirStringADateSQL(desde).toString());
	         // se ejecuta la sentencia y se recibe un resultset
	         ResultSet rs = stmt.executeQuery(sql);
	         // se recorre el resulset y se actualiza la tabla en pantalla
	         ((DefaultTableModel) this.tablaMovPeriodo.getModel()).setRowCount(0);
	         int i = 0;
	         while (rs.next())
	         {
	        	 // agrega una fila al modelo de la tabla
	            ((DefaultTableModel) this.tablaMovPeriodo.getModel()).setRowCount(i + 1);
	            // se agregan a la tabla los datos correspondientes cada celda de la fila recuperada
	            this.tablaMovPeriodo.setValueAt(rs.getDate("fecha").toString(), i, 0);
	            this.tablaMovPeriodo.setValueAt((rs.getTime("hora")).toString(), i, 1);
	            this.tablaMovPeriodo.setValueAt(rs.getString("tipo"), i, 2);
	            this.tablaMovPeriodo.setValueAt(rs.getInt("monto"), i, 3);
	            this.tablaMovPeriodo.setValueAt(rs.getInt("cod_caja"), i, 4);
	            this.tablaMovPeriodo.setValueAt(rs.getInt("destino"), i, 5);
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
