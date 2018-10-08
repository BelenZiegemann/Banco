package Banco;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Types;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;

import quick.dbtable.*; 


@SuppressWarnings("serial")
public class ConsultaSQL extends javax.swing.JFrame 
{
   private JPanel pnlConsulta;
   private JTextArea txtConsulta;
   private JButton botonBorrar;
   private JButton btnEjecutar;
   private DBTable tabla;    
   private JScrollPane scrConsulta;
   private JList<String> listaAtributos;
   private JComboBox<String> combo;
   private String consulta,tabla_elegida;
   private String [] listAtributos;
   
   protected Connection conexionBD = null;
   
   public ConsultaSQL() 
   {
      super();
      initGUI();
   }
   
   private void initGUI() 
   {
      try {
         setPreferredSize(new Dimension(1600, 600));
         this.setBounds(0, 0, 1000, 800);
         setVisible(true);
         this.setTitle("Banco-Consultas");
         this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
         this.addComponentListener(new ComponentAdapter() {
            public void componentHidden(ComponentEvent evt) {
               thisComponentHidden(evt);
            }
            public void componentShown(ComponentEvent evt) {
               thisComponentShown(evt);
            }
         });
         
         getContentPane().setLayout(null);
         {
            pnlConsulta = new JPanel();
            pnlConsulta.setBounds(0, 0, 950, 280);
            getContentPane().add(pnlConsulta);
            pnlConsulta.setLayout(null);
            {
               scrConsulta = new JScrollPane();
               scrConsulta.setBounds(36, 5, 566, 176);
               pnlConsulta.add(scrConsulta);
               {
                  txtConsulta = new JTextArea();
                  scrConsulta.setViewportView(txtConsulta);
                  txtConsulta.setTabSize(3);
                  txtConsulta.setColumns(80);
                  txtConsulta.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
                  txtConsulta.setText("Ingrese aqui una solicitud para la base.");
                  txtConsulta.setSelectedTextColor(Color.GRAY);
                  txtConsulta.setFont(new java.awt.Font("Monospaced",0,12));
                  txtConsulta.setRows(10);
                  
                  txtConsulta.addMouseListener(new MouseAdapter(){
                      public void mouseClicked(MouseEvent e){
                    	  txtConsulta.setSelectedTextColor(Color.BLACK);
                          txtConsulta.setText("");
                         }
                     });
               	  }
            }
            {
         	   btnEjecutar = new JButton();
         	   btnEjecutar.setBounds(657, 65, 89, 23);
         	   pnlConsulta.add(btnEjecutar);
         	   btnEjecutar.setText("Ejecutar");
         	   btnEjecutar.addActionListener(new ActionListener() {
         	      public void actionPerformed(ActionEvent evt) {
         	         btnEjecutarActionPerformed(evt);
         	         txtConsulta.setText("Ingrese una solicitud para la base.");
         	      }
         	   });
         	 }
             {  
         		combo = new JComboBox<String>();
         		combo.addItem("atm");
         		combo.addItem("caja");
         		combo.addItem("caja_ahorro");
         		combo.addItem("ciudad");
         		combo.addItem("cliente");
         		combo.addItem("cliente_ca");
         		combo.addItem("debito");
         		combo.addItem("deposito");
         		combo.addItem("empleado");
         		combo.addItem("extraccion");
         		combo.addItem("pago");
         		combo.addItem("plazo_cliente");
         		combo.addItem("plazo_fijo");
         		combo.addItem("prestamo");
         		combo.addItem("sucursal");
         		combo.addItem("tarjeta");
         		combo.addItem("tasa_plazo_fijo");
         		combo.addItem("tasa_prestamo");
         		combo.addItem("trans_cajas_ahorro");
         		combo.addItem("transaccion");
         		combo.addItem("transaccion_por_caja");
         		combo.addItem("transferencia");
         		combo.addItem("ventanilla");
         		
         		combo.addActionListener(new ActionListener() {
        			public void actionPerformed(ActionEvent e) {
        				tabla_elegida = combo.getSelectedItem().toString();
        				btnListarActionPerformed(e);
        			}
        		});
         		combo.setBounds(657, 99, 150, 23);
         		pnlConsulta.add(combo);
         	}
            {
            	botonBorrar = new JButton();
            	botonBorrar.setBounds(657, 31, 89, 23);
            	pnlConsulta.add(botonBorrar);
            	botonBorrar.setText("Borrar");            
            	botonBorrar.addActionListener(new ActionListener() {
            		public void actionPerformed(ActionEvent arg0) {
            		  txtConsulta.setText("");            			
            		}
            	});
            }	
         }
         {
        	 listaAtributos = new JList();
        	 listaAtributos.setVisible(true);
        	 listaAtributos.setBounds(814, 10, 784, 1650);
        	 pnlConsulta.add(listaAtributos);
         }
         {
        	// crea la tabla  
        	tabla = new DBTable();
        	tabla.setBounds(0, 286, 684, 375);
        	
        	// Agrega la tabla al frame (no necesita JScrollPane como Jtable)
        	getContentPane().add(tabla);           
                      
            // setea la tabla para s�lo lectura (no se puede editar su contenido)  
            tabla.setEditable(false);
         }
         
	      } catch (Exception e) {
	         e.printStackTrace();
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

   private void btnEjecutarActionPerformed(ActionEvent evt) 
   {
      this.refrescarTabla();      
   }
   
   private void btnListarActionPerformed(ActionEvent evt) 
   {
      this.refrescarTablaPorLista();      
   }
   
   private void conectarBD()
   {
         try
         {
            String driver ="com.mysql.cj.jdbc.Driver";
        	String servidor = "localhost:3306";
            String baseDatos = "banco";
            String usuario = "admin";
            String clave = "admin";
            String uriConexion = "jdbc:mysql://" + servidor + "/" + baseDatos+"?serverTimezone=UTC";
   
            //establece una conexi�n con la  B.D. "batallas"  usando directamante una tabla DBTable    
            tabla.connectDatabase(driver, uriConexion, usuario, clave);
            this.conexionBD = DriverManager.getConnection(uriConexion, usuario, clave);
         }
         catch (SQLException ex)
         {
             JOptionPane.showMessageDialog(this,
                                           "Se produjo un error al intentar conectarse a la base de datos.\n" + ex.getMessage(),
                                           "Error",
                                           JOptionPane.ERROR_MESSAGE);
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
         }
         catch (ClassNotFoundException e)
         {
            e.printStackTrace();
         }
   }

   private void desconectarBD()
   {
         try
         {
            tabla.close();            
         }
         catch (SQLException ex)
         {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
         }      
   }

   private void refrescarTabla()
   {
      try
      {    
    	  tabla.setSelectSql(this.txtConsulta.getText().trim());

    	  tabla.createColumnModelFromQuery();    	    
    	  for (int i = 0; i < tabla.getColumnCount(); i++)
    	  {  		   		  
    		 if	 (tabla.getColumn(i).getType()==Types.TIME)  
    		 {    		 
    		  tabla.getColumn(i).setType(Types.CHAR);  
  	       	 }
    		 if	 (tabla.getColumn(i).getType()==Types.DATE)
    		 {
    		    tabla.getColumn(i).setDateFormat("dd/MM/YYYY");
    		 }
          }  	     	  
    	  tabla.refresh();
          
    	  
    	  
       }
      catch (SQLException ex)
      {
         System.out.println("SQLException: " + ex.getMessage());
         System.out.println("SQLState: " + ex.getSQLState());
         System.out.println("VendorError: " + ex.getErrorCode());
         JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this),
                                       ex.getMessage() + "\n", 
                                       "Error al ejecutar la consulta.",
                                       JOptionPane.ERROR_MESSAGE);
         
      }
      
   }
   
   private void refrescarTablaPorLista()
   {  
	consulta="DESCRIBE "+tabla_elegida;
	try {
		conectarBD();
		Statement stmt = conexionBD.createStatement();
		ResultSet rs= stmt.executeQuery(consulta);
		int i=0;
		listAtributos= new String[15];
		
		while(rs.next()){
			listAtributos[i]=rs.getString(1);
			i++;
		}

		listaAtributos.setListData(listAtributos);
		rs.close();
		desconectarBD();

	}
	catch (SQLException ex){
		// en caso de error, se muestra la causa en la consola
		System.out.println("SQLException: " + ex.getMessage());
		System.out.println("SQLState: " + ex.getSQLState());
		System.out.println("VendorError: " + ex.getErrorCode());
		JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), ex.getMessage() + "\n","Error al ejecutar la consulta.",JOptionPane.ERROR_MESSAGE);
	}
   }
}

