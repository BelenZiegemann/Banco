
package Banco;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
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
	private JButton bPagarCuotas;
	private JButton bCancelarCuotas;
	
	private JPanel pConsulta;
	private JButton bCreacion,bCrearPres;
	private JButton bPago;
	private JButton bCliente;
	private JButton bElegir;
	
	private JPanel pCreacion;
	private JPanel pPago;
	
	private JPanel pCliente;
	private JTable tablaPago;
	private JTable tablaCliente;
	private JScrollPane scrTablaPago;
	private JScrollPane scrTablaCliente;
	private JScrollPane scrListaPagos;
	private JList<String> listaPagos;
	
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
	private LinkedList<Integer> prestamos_select;
	private LinkedList<Integer> pagos_select;
	private LinkedList<String> lista_select;
	
	private JLabel lNroDoc,lTipoDoc,lHeadSelect, lClienteSelect;
	private JLabel lPeriodos, lMonto;
	private JLabel lTitleCreacion, lTitlePago, lTitleCliente, lEstado, lResultado, lCuotasSelect;
	
	private JComboBox<String> comboPeriodo;
	
	public Prestamos(){
		super();
		setTitle("Banco-Administracion de prestamos");
		initGUI();
	}
	
	
	private void  initGUI() {
		try{
			//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 1150, 600);
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
			pConsulta.setBounds(0, 0, 1100, 500);
			pConsulta.setBorder(new EmptyBorder(5, 5, 5, 5));
			contentPane.add(pConsulta);
			pConsulta.setLayout(null);
			pConsulta.setVisible(false);
			//pConsulta.setBorder(new LineBorder(Color.BLUE,2));
			
			pClienteSelect = new JPanel();
			pClienteSelect.setBounds(10, 0, 334, 90);
			pClienteSelect.setVisible(true);
			pClienteSelect.setLayout(null);
			//pClienteSelect.setBorder(new LineBorder(Color.GREEN,2));
			
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
					establecerEstado();
					refrescarComboPeriodo();
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
					refrescarListaPagos();
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
			pCreacion.setBounds(100, 150, 400, 200);
			pConsulta.add(pCreacion);
			pCreacion.setVisible(false);
			pCreacion.setLayout(null);
			//pCreacion.setBorder(new LineBorder(Color.RED,2));
			
			/*Fin creacion panel pCreacion de prestamos*/
			
			pPago = new JPanel();
			pPago.setBounds(100, 150, 800, 320);
			pConsulta.add(pPago);
			pPago.setVisible(false);
			pPago.setLayout(null);
			//pPago.setBorder(new LineBorder(Color.RED,2));
			
			/*Creacion panel pCliente morosos*/
			pCliente = new JPanel();
			pCliente.setBounds(50, 150, 1020, 300);
			pConsulta.add(pCliente);
			pCliente.setVisible(false);
			pCliente.setLayout(null);
			//pCliente.setBorder(new LineBorder(Color.RED,2));
			
			{   
		        //-----------------Creacion del panel pCreacion-------------------------
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
			{  
				//-------------------Creacion del panel pPago---------------------------
				//Titulo
				lTitlePago = new JLabel("Pago de cuotas");
				lTitlePago.setBounds(180, 10, 250, 40);
				lTitlePago.setFont(new Font("Serif", Font.PLAIN, 24));
				lTitlePago.setForeground(Color.BLACK);
				pPago.add(lTitlePago);
				
				lCuotasSelect = new JLabel("Cuotas selectas");
				lCuotasSelect.setBounds(640, 10, 250, 40);
				lCuotasSelect.setFont(new Font("Serif", Font.PLAIN, 16));
				lCuotasSelect.setForeground(Color.BLACK);
				pPago.add(lCuotasSelect);
				
	            scrTablaPago = new JScrollPane();
	            scrTablaPago.setBounds(10,50,600,200);
	            pPago.add(scrTablaPago, BorderLayout.CENTER);
	            
	            {  
	               TableModel PagoModel = 
	                  new DefaultTableModel  
	                  (
	                     new String[][] {},
	                     new String[] {"Prestamo","Cuota","Monto","Fecha Venc."}
	                  )
	                  { 
	            	     Class[] types = new Class[] {java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class };
	                     boolean[] canEdit = new boolean[] { false, false, false, false };
	                      
	                     public Class getColumnClass(int columnIndex) 
	                     {
	                        return types[columnIndex];
	                     }
	                     public boolean isCellEditable(int rowIndex, int columnIndex) 
	                     {
	                        return canEdit[columnIndex];
	                     }
	                  };
	               tablaPago = new JTable();
	               scrTablaPago.setViewportView(tablaPago);                
	               tablaPago.setModel(PagoModel); 
	               tablaPago.setAutoCreateRowSorter(true);
	               tablaPago.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
	            	    public void valueChanged(ListSelectionEvent event) {
	            	        if (tablaPago.getSelectedRow()>-1) 
	            	        {
	            	        	int e1 = Integer.parseInt(tablaPago.getValueAt(tablaPago.getSelectedRow(), 0).toString());
	            	        	int e2 = Integer.parseInt(tablaPago.getValueAt(tablaPago.getSelectedRow(), 1).toString());
	            	            if(agregar(e1,e2)) 
	            	            {
	            	            	prestamos_select.add(e1);
	            	            	pagos_select.add(e2);
	            	            	refrescarListaPagos();
	            	            }
	            	        }
	            	    }
	            	});
	               
	               listaPagos = new JList<String>();
	               listaPagos.setVisible(true);
	               //listaPagos.setBorder(new LineBorder(Color.BLACK,2));
	               scrListaPagos = new JScrollPane(listaPagos);
	               scrListaPagos.setBounds(620, 50, 170, 200);
	         	   pPago.add(scrListaPagos, BorderLayout.CENTER);
	         	   
	         	   bPagarCuotas = new JButton("Pagar");
				   bPagarCuotas.setBounds(630, 260, 150, 25);
				   pPago.add(bPagarCuotas);
				   bPagarCuotas.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							oyentePagarSeleccionadas();
							oyentePagoCuotas();
						}
					});
				   
				   bCancelarCuotas = new JButton("Cancelar");
				   bCancelarCuotas.setBounds(630, 290, 150, 25);
				   pPago.add(bCancelarCuotas);
				   bCancelarCuotas.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							vaciarListaPagos();
						}
					});
	            }
	         }
			{  
				//-------------------Creacion del panel pPago---------------------------
				//Titulo
				lTitleCliente = new JLabel("Morosos");
				lTitleCliente.setBounds(350, 10, 250, 40);
				lTitleCliente.setFont(new Font("Serif", Font.PLAIN, 24));
				lTitleCliente.setForeground(Color.BLACK);
				pCliente.add(lTitleCliente);
				
	            scrTablaCliente = new JScrollPane();
	            scrTablaCliente.setBounds(10,50,1000,200);
	            //scrTablaCliente.setBorder(new LineBorder(Color.ORANGE,2));
	            pCliente.add(scrTablaCliente, BorderLayout.CENTER);
	            
	            {  
	               TableModel ClienteModel = 
	                  new DefaultTableModel  
	                  (
	                     new String[][] {},
	                     new String[] {"Nro Cliente","Tipo Doc.","Nro Doc","Nombre","Apellido","Prestamo","Monto","Periodo","Cuota","Cuotas Morosas"}
	                  )
	                  { 
	            	     Class[] types = new Class[] 
        	    		 {
	            	    		 java.lang.Integer.class, //1 nro cliente
	            	    		 java.lang.String.class, //2 tipo doc
	            	    		 java.lang.Integer.class, //3 nro doc
	            	    		 java.lang.String.class, //4 nombre
	            	    		 java.lang.String.class, //5 apellido
	            	    		 java.lang.Integer.class, //6 prestamo
	            	    		 java.lang.Integer.class, //7 monto
	            	    		 java.lang.Integer.class, //8 periodo
	            	    		 java.lang.Integer.class, //9 cuotas
	            	    		 java.lang.Integer.class //10 cuotas morosas
        	    		 };
	                     boolean[] canEdit = new boolean[] { false, false, false, false, false, false, false, false, false, false };
	                      
	                     public Class getColumnClass(int columnIndex) 
	                     {
	                        return types[columnIndex];
	                     }
	                     public boolean isCellEditable(int rowIndex, int columnIndex) 
	                     {
	                        return canEdit[columnIndex];
	                     }
	                  };
	               tablaCliente = new JTable();
	               scrTablaCliente.setViewportView(tablaCliente);                
	               tablaCliente.setModel(ClienteModel); 
	               tablaCliente.setAutoCreateRowSorter(true);
	               tablaCliente.setBounds(10,40,900,200);
	               //tablaCliente.setBorder(new LineBorder(Color.CYAN,2));
	            }
	         }
		}
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
	
	private void refrescarListaPagos() 
	{
		int i=0;
		lista_select= new LinkedList<String>();
		
		while(i<prestamos_select.size() && i<pagos_select.size()){
			lista_select.add("prestamo: "+prestamos_select.get(i)+", cuota: "+pagos_select.get(i));
			i++;
		}
		listaPagos.setListData(lista_select.toArray(new String[lista_select.size()]));
	}
	
	private void vaciarListaPagos() {
		prestamos_select= new LinkedList<Integer>();
    	pagos_select= new LinkedList<Integer>();
		refrescarListaPagos();
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
	
	private boolean agregar(int e1, int e2) 
	{
		boolean agregar = true;
		int i=0;
		
		while(i<prestamos_select.size() && i<pagos_select.size() && agregar) 
		{
			agregar = prestamos_select.get(i)!=e1 || pagos_select.get(i)!=e2;
			i++;
		}
		return agregar;
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
	
	private void oyenteCrearUnPrestamo()
	{
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
	
	private void oyentePagarSeleccionadas()
	{
		int flag=0;
		int nro_pres_temp, nro_pago_temp;
		while(flag<prestamos_select.size() && flag<pagos_select.size()) 
		{
			nro_pres_temp = prestamos_select.get(flag);
			nro_pago_temp = pagos_select.get(flag);
			try
		      {
		         Statement stmt = this.conexionBD.createStatement();
		         String sql = "UPDATE pago SET fecha_pago=CURDATE() WHERE nro_prestamo="+nro_pres_temp+" AND nro_pago="+nro_pago_temp+";";
		         stmt.execute(sql);
		         stmt.close();
		      }
		      catch (SQLException ex)
		      {
		         System.out.println("SQLException: " + ex.getMessage());
		         System.out.println("SQLState: " + ex.getSQLState());
		         System.out.println("VendorError: " + ex.getErrorCode());
		      }
			flag++;
		}
		
		//Se vacia todo
		vaciarListaPagos();
	}
	
	private void oyentePagoCuotas()
	{
		try
	      {
			prestamos_select= new LinkedList<Integer>();
			pagos_select= new LinkedList<Integer>();
			
	         Statement stmt = this.conexionBD.createStatement();
	         String sql = "SELECT nro_prestamo,nro_pago,monto,fecha_venc FROM cliente NATURAL JOIN prestamo NATURAL JOIN pago WHERE nro_doc="+nroDoc+" AND tipo_doc='"+tipoDoc+"' AND fecha_pago is NULL;";
	         ResultSet rs = stmt.executeQuery(sql);
	         
	         ((DefaultTableModel) this.tablaPago.getModel()).setRowCount(0);
	         int i = 0;
	         while (rs.next())
	         {
	            ((DefaultTableModel) this.tablaPago.getModel()).setRowCount(i + 1);
	            this.tablaPago.setValueAt(rs.getInt("nro_prestamo"), i, 0);
	            this.tablaPago.setValueAt(rs.getInt("nro_pago"), i, 1);            
	            this.tablaPago.setValueAt(rs.getString("monto"), i, 2);
	            this.tablaPago.setValueAt(rs.getString("fecha_venc"), i, 3);
	            i++;
	         }
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
	
	private void oyenteClientesMorosos(){
		 try
	      {
	         Statement stmt = this.conexionBD.createStatement();
	         String sql = "SELECT nro_cliente, tipo_doc, nro_doc, nombre, apellido, nro_prestamo, monto, cant_meses, valor_cuota, COUNT(fecha_venc) AS cuotas_morosas "
	         		+ "FROM pago NATURAL JOIN prestamo NATURAL JOIN cliente WHERE fecha_pago IS NULL AND DATEDIFF(CURDATE(), fecha_venc)>0 GROUP BY nro_prestamo HAVING COUNT(fecha_venc)>=2;";

	         ResultSet rs = stmt.executeQuery(sql);
	         ((DefaultTableModel) this.tablaCliente.getModel()).setRowCount(0);
	         int i = 0;
	         while (rs.next())
	         {
	            ((DefaultTableModel) this.tablaCliente.getModel()).setRowCount(i + 1);
	            this.tablaCliente.setValueAt(rs.getInt("nro_cliente"), i, 0);
	            this.tablaCliente.setValueAt(rs.getString("tipo_doc"), i, 1);
	            this.tablaCliente.setValueAt(rs.getInt("nro_doc"), i, 2);
	            this.tablaCliente.setValueAt(rs.getString("nombre"), i, 3);
	            this.tablaCliente.setValueAt(rs.getString("apellido"), i, 4);
	            this.tablaCliente.setValueAt(rs.getInt("nro_prestamo"), i, 5);
	            this.tablaCliente.setValueAt(rs.getInt("monto"), i, 6);
	            this.tablaCliente.setValueAt(rs.getInt("cant_meses"), i, 7);
	            this.tablaCliente.setValueAt(rs.getInt("valor_cuota"), i, 8);
	            this.tablaCliente.setValueAt(rs.getInt("cuotas_morosas"), i, 9);
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
