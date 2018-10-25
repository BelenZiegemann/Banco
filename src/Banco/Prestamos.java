package Banco;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
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
	private JPanel pPeriodos,pClienteSelect;
	private JLabel lNumero;
	private JLabel lPassword;
	private JTextField tNumero, tFinal, tInicial;
	private JPasswordField pContraseña;
	private JButton bIngresar;
	
	private JPanel pConsulta;
	private JButton bCreacion;
	private JButton bPago;
	private JButton bCliente;
	
	private JPanel pCreacion;
	private JPanel pPago;
	
	private JPanel pCliente;
	private JTable tablaMorosos;
	
	protected Connection conexionBD = null;
	private String consulta;
	private String legajo;
	private String password;
	private Integer [] nros;
	
	private JLabel lFinal,lInicial;

	
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
			pConsulta.setBounds(0, 0, 800, 180);
			pConsulta.setBorder(new EmptyBorder(5, 5, 5, 5));
			contentPane.add(pConsulta);
			pConsulta.setLayout(null);
			pConsulta.setVisible(false);
			
			pClienteSelect = new JPanel();
			pClienteSelect.setBounds(10, 0, 134, 90);
			pClienteSelect.setVisible(true);
			pClienteSelect.setLayout(null);
			
			lInicial = new JLabel("Tipo de doc");
			lInicial.setBounds(10, 10, 86, 14);
			pClienteSelect.add(lInicial);
			
			lFinal = new JLabel("Nro de doc");
			lFinal.setBounds(10, 49, 86, 14);
			pClienteSelect.add(lFinal);
			
			tInicial = new JTextField();
			tInicial.setBounds(10, 24, 86, 20);
			tInicial.setVisible(true);
			pClienteSelect.add(tInicial);
			tInicial.setColumns(10);
			
			tFinal = new JTextField();
			tFinal.setBounds(10, 64, 86, 20);
			tFinal.setVisible(true);
			pClienteSelect.add(tFinal);
			tFinal.setColumns(10);
			
			pConsulta.add(pClienteSelect);
			
			bCreacion = new JButton("Crear prestamo");
			bCreacion.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					pCreacion.setVisible(true);
					pPago.setVisible(false);
					pCliente.setVisible(false);
					oyenteCreacionPrestamos();
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
			/*Fin panel pConsulta*/
			
			/*Comienzo panel pCreacion de prestamos*/
			pCreacion = new JPanel();
			pCreacion.setBounds(0, 83, 434, 178);
			contentPane.add(pCreacion);
			pCreacion.setVisible(false);
			
			/*Fin creacion panel pCreacion de prestamos*/
			
			pPago = new JPanel();
			pPago.setBounds(0, 83, 434, 178);
			contentPane.add(pPago);
			pPago.setVisible(false);
			
			
			/*Creacion panel pCliente morosos*/
			pCliente = new JPanel();
			pCliente.setBounds(0, 83, 434, 178);
			contentPane.add(pCliente);
			pCliente.setVisible(false);
			
			{  //modelo de la tabla donde se almacenaran las tuplas 
	               TableModel MorososModel =  // se crea un modelo de tabla BarcosModel 
	                  new DefaultTableModel  // extendiendo el modelo DefalutTableModel
	                  (
	                     new String[][] {},
	                     new String[] {"legajo", "Apellido", "Nombre", "Tipo doc", "Numero doc",
	                    		 		"direccion", "telefono", "cargo", "numero suc"}
	                  )
	                  {                      // con una clase anónima 
	            	     // define la clase java asociada a cada columna de la tabla
	            	     Class[] types = new Class[] {java.lang.Integer.class, java.lang.String.class, java.lang.String.class, 
	            	    		 java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.Long.class,
	            	    		 java.lang.String.class, java.lang.Integer.class,  };
	            	    // define si una columna es editable
	                     boolean[] canEdit = new boolean[] { false, false, false,
								                    		 false, false, false,
								                    		 false, false, false};
	                      
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
	               tablaMorosos = new JTable(); // Crea una tabla            
	               tablaMorosos.setModel(MorososModel); // setea el modelo de la tabla  
	               tablaMorosos.setAutoCreateRowSorter(true); // activa el ordenamiento por columnas, para
	                                                   // que se ordene al hacer click en una columna
	            }
			
			/*Fin creacion panel pCliente morosos*/
			
		}//try
		catch(Exception e){
			e.printStackTrace();
		}
		
	}//initgui
	
	/*Metodos privados*/
	//Metodo privado para verificar que el usuario sea correcto.
	private boolean verificacion() {
		boolean valida = false;
		consulta ="SELECT legajo FROM empleado WHERE password=md5('"+password+"');";
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
	
	private void oyenteCreacionPrestamos(){
		
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
