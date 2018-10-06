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
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class Prestamos extends JFrame {

	private JPanel contentPane;
	
	private JPanel pLogin;
	private JLabel lNumero;
	private JLabel lPassword;
	private JTextField tNumero;
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
	private String legajo;
	private String password;

	
	public Prestamos(){
		super();
		setTitle("Banco-Administracion de prestamos");
		initGUI();
	}
	
	
	private void  initGUI() {
		try{
			//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 450, 300);
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
					legajo=tNumero.getText();
					password=pContraseña.getText();
					pConsulta.setVisible(true);
					pLogin.setVisible(false);
					//oyenteIngresar();
					
				}
			});
			bIngresar.setBounds(293, 23, 89, 23);
			pLogin.add(bIngresar);
			/*Fin panel pLogin*/
			
			/*Comienzo panel pConsulta*/
			pConsulta = new JPanel();
			pConsulta.setBounds(0, 0, 434, 79);
			contentPane.add(pConsulta);
			pConsulta.setLayout(null);
			pConsulta.setVisible(false);
			
			bCreacion = new JButton("Crear prestamo");
			bCreacion.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					pCreacion.setVisible(true);
					pPago.setVisible(false);
					pCliente.setVisible(false);
					oyenteCreacionPrestamos();
				}
			});
			bCreacion.setBounds(10, 29, 133, 23);
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
			bPago.setBounds(174, 29, 107, 23);
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
			bCliente.setBounds(317, 29, 107, 23);
			pConsulta.add(bCliente);
			/*Fin panel pConsulta*/
			
			/*Comienzo panel pSaldo*/
			pCreacion = new JPanel();
			pCreacion.setBounds(0, 83, 434, 178);
			contentPane.add(pCreacion);
			pCreacion.setVisible(false);
			
			/*fin panel pSaldo*/
			
			pPago = new JPanel();
			pPago.setBounds(0, 83, 434, 178);
			contentPane.add(pPago);
			pPago.setVisible(false);
			
			pCliente = new JPanel();
			pCliente.setBounds(0, 83, 434, 178);
			contentPane.add(pCliente);
			pCliente.setVisible(false);
			
		}//try
		catch(Exception e){
			e.printStackTrace();
		}
		
	}//initgui
	
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
	
	 /*agregar md5*/
	private void oyenteIngresar(){
		 try
	      {
	         Statement stmt = this.conexionBD.createStatement();

	         String sql = "SELECT legajo, password " + 
	                      "FROM empleado " +
	                      "WHERE legajo="+ legajo +"AND password";


	         ResultSet rs = stmt.executeQuery(sql);
	       
	                 
	       
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
	private void oyenteCreacionPrestamos(){
		
	}
	
	private void oyentePagoCuotas(){
		
	}
	
	private void oyenteClientesMorosos(){
		
	}

}//principal
