package Banco;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;

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
	private JButton bSalirConsulta;
	
	private JPanel pSaldo;
	private JPanel pMovimiento;
	private JPanel pMovPeriodo;
	private JPanel pPeriodos;
	
	private JPanel pAtm;
	private JButton bConsultas;
	private JButton bExtraccion;
	private JButton bTransferencia;
	private JButton bSalir;
	
	private JPanel pExtraccion;
	private JLabel lblMonto;
	private JButton bConfirmar;
	private JButton bCancelar;
	private JTextField tMonto;
	
	private JPanel pTransferencia;
	private JLabel lblCajaDestino;
	private JLabel lblMontoTransferencia;
	private JButton bConfirmarTransferencia;
	private JButton bCancelarTransferencia;
	private JTextField tNroDestino;
	private JTextField tMontoTransferencia;
	
	
	protected Connection conexionBD = null;
	private String nroTarjeta;
	private String password;
	private Integer[] nros;
	
	private JScrollPane scrTablaSaldo;
	private JTable tablaSaldo;
	private JScrollPane scrTablaMovimiento;
	private JTable tablaMovimiento;
	private JScrollPane scrTablaMovPeriodo;
	private JTable tablaMovPeriodo;
	private Fechas f = new Fechas();
	private String desde, hasta, consulta;
	 
	public Cajero() {
		super();
		initGUI();
	}
		
	private void initGUI(){
		try{
			setTitle("Banco-Cajero");
			setBounds(300, 100, 850, 400);
			setSize(600,600);
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
			
	        
	        /*Creacion panel atm*/
	        
	        pAtm = new JPanel();
			contentPane.add(pAtm);
			pAtm.setBounds(5, 5, 574, 545);
			pAtm.setLayout(null);
			pAtm.setVisible(false);
			
			bConsultas = new JButton("Consultas");
			bConsultas.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					pConsulta.setVisible(true);
					pAtm.setVisible(false);
				}
			});
			bConsultas.setBounds(218, 42, 130, 37);
			pAtm.add(bConsultas);
			
			bExtraccion = new JButton("Extraccion");
			bExtraccion.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					pExtraccion.setVisible(true);
					pAtm.setVisible(false);
				}
			});
			bExtraccion.setBounds(218, 124, 130, 37);
			pAtm.add(bExtraccion);
			
			bTransferencia = new JButton("Tranferencias");
			bTransferencia.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					pTransferencia.setVisible(true);
					pAtm.setVisible(false);
				}
			});
			bTransferencia.setBounds(218, 206, 130, 37);
			pAtm.add(bTransferencia);
			
			bSalir = new JButton("Salir");
			bSalir.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
			bSalir.setBounds(218, 344, 130, 29);
			pAtm.add(bSalir);
	        
	        /*Fin creacion panel atm*/
	        
	        /*Creacion panel Extraccion*/
			pExtraccion = new JPanel();
			contentPane.add(pExtraccion);
			pExtraccion.setBounds(5, 5, 574, 545);
			pExtraccion.setLayout(null);
			pExtraccion.setVisible(false);
			
			lblMonto = new JLabel("Monto");
			lblMonto.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblMonto.setBounds(72, 48, 79, 26);
			pExtraccion.add(lblMonto);
			
			bConfirmar = new JButton("Confirmar");
			bConfirmar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				}
			});
			bConfirmar.setBounds(203, 135, 112, 26);
			pExtraccion.add(bConfirmar);
			
			bCancelar = new JButton("Cancelar");
			bCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					pAtm.setVisible(true);
					pExtraccion.setVisible(false);
				}
			});
			bCancelar.setBounds(203, 187, 112, 26);
			pExtraccion.add(bCancelar);
			
			tMonto = new JTextField();
			tMonto.setBounds(203, 52, 112, 20);
			pExtraccion.add(tMonto);
			tMonto.setColumns(10);
		
			/*Fin creacion panel Extraccion*/
			
			/*Creacion panel transferencia*/
			pTransferencia = new JPanel();
			contentPane.add(pTransferencia);
			pTransferencia.setBounds(5, 5, 574, 545);
			pTransferencia.setLayout(null);
			pTransferencia.setVisible(false);
			
			lblCajaDestino = new JLabel("Caja de ahorro destino");
			lblCajaDestino.setBounds(22, 68, 152, 29);
			pTransferencia.add(lblCajaDestino);
			
			lblMontoTransferencia = new JLabel("Monto");
			lblMontoTransferencia.setBounds(54, 120, 120, 23);
			pTransferencia.add(lblMontoTransferencia);
			
			bConfirmarTransferencia = new JButton("Confirmar");
			bConfirmarTransferencia.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				}
			});
			bConfirmarTransferencia.setBounds(212, 189, 112, 23);
			pTransferencia.add(bConfirmarTransferencia);
			
			bCancelarTransferencia = new JButton("Cancelar");
			bCancelarTransferencia.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					pAtm.setVisible(true);
					pTransferencia.setVisible(false);
				}
			});
			bCancelarTransferencia.setBounds(212, 242, 112, 23);
			pTransferencia.add(bCancelarTransferencia);
			
			tNroDestino = new JTextField();
			tNroDestino.setBounds(212, 72, 112, 20);
			pTransferencia.add(tNroDestino);
			tNroDestino.setColumns(10);
			
			tMontoTransferencia = new JTextField();
			tMontoTransferencia.setColumns(10);
			tMontoTransferencia.setBounds(212, 121, 112, 20);
			pTransferencia.add(tMontoTransferencia);
			
			/*Fin creacion panel transferencia*/
			
			/*Creacion panel login*/
			pLogin = new JPanel();
			pLogin.setBounds(0, 0, 434, 79);
			contentPane.add(pLogin);
			pLogin.setLayout(null);
			
			
			lNumero = new JLabel("Tarjeta");
			lNumero.setBounds(10, 11, 71, 14);
			pLogin.add(lNumero);
			
			lPassword = new JLabel("PIN");
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
					
					if(verificacion()) {
							pAtm.setVisible(true);
							pLogin.setVisible(false);
						}
						else{
							JOptionPane.showMessageDialog(null, "Tarjeta o Contraseña Incorrecta");
						}
					}
			});
			bIngresar.setBounds(293, 23, 89, 23);
			pLogin.add(bIngresar);
			/*Fin cracion panel pLogin*/
			
			/*Creacion panel pConsulta*/
			pConsulta = new JPanel();
			pConsulta.setBounds(0, 0, 574, 79);
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
			bSaldo.setBounds(33, 11, 110, 23);
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
			bMovimientos.setBounds(33, 45, 110, 23);
			pConsulta.add(bMovimientos);
			
			bPeriodo = new JButton("Por periodos");
			bPeriodo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					pMovPeriodo.setVisible(true);
					pPeriodos.setVisible(true);
					pMovimiento.setVisible(false);
					pSaldo.setVisible(false);
					//Adelanto la fecha en un dia por una cuestion de que sql toma valores del dia anterior al pasado.
					desde = tInicial.getText();
					hasta = tFinal.getText();
					try 
					{
						desde = f.adelantarDia(desde);
						hasta = f.adelantarDia(hasta);
					}
					catch(StringIndexOutOfBoundsException strexcp) 
					{
						JOptionPane.showMessageDialog(null, "Ingrese valores de fechas correctas dd/mm/aaaa o dd-mm-aaaa");
						desde="";
						hasta="";
					}
					oyentePeriodos();
				}
			});
			bPeriodo.setBounds(227, 11, 116, 23);
			pConsulta.add(bPeriodo);
			
			bSalirConsulta = new JButton("Salir");
			bSalirConsulta.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					pAtm.setVisible(true);
					pConsulta.setVisible(false);
				}
			});
			bSalirConsulta.setBounds(227, 45, 116, 23);
			pConsulta.add(bSalirConsulta);
			
			pPeriodos = new JPanel();
			pPeriodos.setBounds(440, 0, 134, 79);
			pPeriodos.setVisible(true);
			pPeriodos.setLayout(null);
			
			lInicial = new JLabel("Fecha Inicial");
			lInicial.setBounds(10, 0, 86, 14);
			pPeriodos.add(lInicial);
			
			lFinal = new JLabel("Fecha Final");
			lFinal.setBounds(10, 39, 86, 14);
			pPeriodos.add(lFinal);
			
			tInicial = new JTextField();
			tInicial.setBounds(10, 14, 86, 20);
			tInicial.setVisible(true);
			pPeriodos.add(tInicial);
			tInicial.setColumns(10);
			
			tFinal = new JTextField();
			tFinal.setBounds(10, 54, 86, 20);
			tFinal.setVisible(true);
			pPeriodos.add(tFinal);
			tFinal.setColumns(10);
			
			pConsulta.add(pPeriodos);
			
			
			/*Fin creacion panel pConsulta*/
			
			//---------------Declaracion de paneles--------------------
			pSaldo = new JPanel();
			pSaldo.setBounds(10, 83, 564, 467);
			contentPane.add(pSaldo);
			pSaldo.setVisible(false);
			
			pMovimiento = new JPanel();
			pMovimiento.setBounds(0, 83, 574, 467);
			contentPane.add(pMovimiento);
			pMovimiento.setVisible(false);
			
			pMovPeriodo = new JPanel();
			pMovPeriodo.setBounds(0, 83, 574, 467);
			contentPane.add(pMovPeriodo);
			pMovPeriodo.setVisible(false);
			
			//-----Panel Saldo------- 
			{  
	            scrTablaSaldo = new JScrollPane();
	            pSaldo.add(scrTablaSaldo, BorderLayout.CENTER);
	            
	            {  
	               TableModel SaldoModel = 
	                  new DefaultTableModel  
	                  (
	                     new String[][] {},
	                     new String[] {"Saldo"}
	                  )
	                  { 
	            	     Class[] types = new Class[] {java.lang.Integer.class };
	                     boolean[] canEdit = new boolean[] { false, false };
	                      
	                     public Class getColumnClass(int columnIndex) 
	                     {
	                        return types[columnIndex];
	                     }
	                     public boolean isCellEditable(int rowIndex, int columnIndex) 
	                     {
	                        return canEdit[columnIndex];
	                     }
	                  };
	               tablaSaldo = new JTable();
	               scrTablaSaldo.setViewportView(tablaSaldo);                
	               tablaSaldo.setModel(SaldoModel); 
	               tablaSaldo.setAutoCreateRowSorter(true); 
	            }
	         }
			//-----Panel Movimiento------- 
			{  
	            scrTablaMovimiento = new JScrollPane();
	            pMovimiento.add(scrTablaMovimiento, BorderLayout.CENTER);
	            
	            { 
	               TableModel MovimientoModel = 
	                  new DefaultTableModel 
	                  (
	                     new String[][] {},
	                     new String[] {"Fecha","Hora","Tipo","Monto","Codigo Caja","Caja Ahorro"}
	                  )
	                  {            
	            	     Class[] types = new Class[] {java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class };
	            	
	                     boolean[] canEdit = new boolean[] { false, false, false, false, false, false };
	                      
	         
	                     public Class getColumnClass(int columnIndex) 
	                     {
	                        return types[columnIndex];
	                     }
	           
	                     public boolean isCellEditable(int rowIndex, int columnIndex) 
	                     {
	                        return canEdit[columnIndex];
	                     }
	                  };
	               tablaMovimiento = new JTable(); 
	               scrTablaMovimiento.setViewportView(tablaMovimiento);           
	               tablaMovimiento.setModel(MovimientoModel); 
	               tablaMovimiento.setAutoCreateRowSorter(true);
	            }
	         }
			//-----Panel Movimiento Periodo------- 
			{  
	            scrTablaMovPeriodo = new JScrollPane();
	            pMovPeriodo.add(scrTablaMovPeriodo, BorderLayout.CENTER);
	            
	            {  
	               TableModel MovPeriodoModel =  
	                  new DefaultTableModel  
	                  (
	                     new String[][] {},
	                     new String[] {"Fecha","Hora","Tipo","Monto","Codigo Caja","Caja Ahorro"}
	                  )
	                  {                      
	            	     Class[] types = new Class[] {java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class };
	            
	                     boolean[] canEdit = new boolean[] { false, false, false, false, false, false };
	           
	                     public Class getColumnClass(int columnIndex) 
	                     {
	                        return types[columnIndex];
	                     }
	                
	                     public boolean isCellEditable(int rowIndex, int columnIndex) 
	                     {
	                        return canEdit[columnIndex];
	                     }
	                  };
	               tablaMovPeriodo = new JTable(); 
	               scrTablaMovPeriodo.setViewportView(tablaMovPeriodo);          
	               tablaMovPeriodo.setModel(MovPeriodoModel);
	               tablaMovPeriodo.setAutoCreateRowSorter(true); 
	            }
			}
		}
		catch (Exception e) {
	         e.printStackTrace();	
		}
		
	}
	
	
	//Metodo privado para verificar que el usuario sea correcto.
	private boolean verificacion() {
		boolean valida = false;
		consulta="SELECT nro_tarjeta FROM tarjeta WHERE PIN=md5('"+password+"');";
		try {
			conectarBD();
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
				valida = nros[i].equals(Integer.parseInt(nroTarjeta));
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
	 
	 //Oyente del boton saldo
	private void oyenteSaldo(){
		try
	      {
	         // se crea una sentencia o comando jdbc para realizar la consulta 
	    	 // a partir de la coneccion establecida (conexionBD)
	         Statement stmt = this.conexionBD.createStatement();

	         // se prepara el string SQL de la consulta
	         String sql = "SELECT DISTINCT saldo FROM tarjeta NATURAL JOIN trans_cajas_ahorro WHERE '"+ nroTarjeta +"'= nro_tarjeta AND PIN=md5('"+password+"');";

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
	
	//Oyente del boton movimientos
	private void oyenteMovimientos(){
		try
	      {
			 // se crea una sentencia o comando jdbc para realizar la consulta 
	    	 // a partir de la coneccion establecida (conexionBD)
			 Statement stmt = this.conexionBD.createStatement();
			 String sql_temp = " SELECT nro_ca FROM tarjeta WHERE nro_tarjeta='"+nroTarjeta+"';" ;	 
			 ResultSet rs_temp = stmt.executeQuery(sql_temp);
			 int nro_ca=0;
			 while(rs_temp.next()) 
			 {
				 nro_ca = rs_temp.getInt("nro_ca");
			 }

	         // se prepara el string SQL de la consulta
	         String sql = "SELECT fecha, hora, tipo, IF(tipo<>'deposito', concat('-',monto), monto) AS monto, cod_caja, destino FROM trans_cajas_ahorro WHERE nro_ca='"+ nro_ca +"' ORDER BY fecha DESC, hora LIMIT 15;" ;

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
	            this.tablaMovimiento.setValueAt(f.adelantarDia( f.convertirDateAString(rs.getDate("fecha")) ), i, 0);
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
	
	//Oyente del boton periodo
	private void oyentePeriodos(){
		try
	      {
	         // se crea una sentencia o comando jdbc para realizar la consulta 
	    	 // a partir de la coneccion establecida (conexionBD)
			Statement stmt = this.conexionBD.createStatement();
			 String sql_temp = " SELECT nro_ca FROM tarjeta WHERE nro_tarjeta='"+nroTarjeta+"';" ;	 
			 ResultSet rs_temp = stmt.executeQuery(sql_temp);
			 int nro_ca=0;
			 while(rs_temp.next()) 
			 {
				 nro_ca = rs_temp.getInt("nro_ca");
			 }
			 
			 //Prueba de la conversio de fechas
			 /*
			 String desde_convertido=Fechas.convertirStringADateSQL(desde).toString();
			 String hasta_convertido=Fechas.convertirStringADateSQL(hasta).toString();
			 System.out.println("desde: "+desde);
			 System.out.println("desde_convertido: "+desde_convertido);
			 System.out.println("hasta: "+hasta);
			 System.out.println("hasta_convertido: "+hasta_convertido);
			 */
			 
	         // se prepara el string SQL de la consulta
	         String sql = "\n" + 
	         		"SELECT fecha, hora, tipo, IF(tipo<>'deposito', concat('-',monto), monto) AS monto, cod_caja, destino FROM trans_cajas_ahorro " + 
	         		"WHERE nro_ca='"+ nro_ca +"' AND fecha BETWEEN '"+Fechas.convertirStringADateSQL(desde)+"' AND '"+Fechas.convertirStringADateSQL(hasta)+"' "+ 
	         		"ORDER BY fecha, hora;";
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
		
}
