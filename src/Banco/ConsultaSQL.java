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
import javax.swing.table.DefaultTableModel;

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
   
   //Constructo de ConsultaSQL
   public ConsultaSQL() 
   {
      super();
      initGUI();
   }
   
   private void initGUI() 
   {
      try 
      {
         setPreferredSize(new Dimension(1600, 600));
         this.setBounds(150, 0, 1000, 800);
         this.setTitle("Banco-Consultas");
         this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
         this.addComponentListener(new ComponentAdapter() 
         {
            public void componentHidden(ComponentEvent evt) 
            {
               thisComponentHidden(evt);
            }
            public void componentShown(ComponentEvent evt) 
            {
               thisComponentShown(evt);
            }
         });
         
         this.setVisible(false);
    	 this.setVisible(true);
         
         getContentPane().setLayout(null);
         {
        	//Panel de las consultas
            pnlConsulta = new JPanel();
            pnlConsulta.setBounds(0, 0, 950, 280);
            getContentPane().add(pnlConsulta);
            pnlConsulta.setLayout(null);
            {
               //Scroll del area de texto para la consulta
               scrConsulta = new JScrollPane();
               scrConsulta.setBounds(36, 5, 566, 176);
               pnlConsulta.add(scrConsulta);
               {
            	  //Area de texto para la consulta
                  txtConsulta = new JTextArea();
                  scrConsulta.setViewportView(txtConsulta);
                  txtConsulta.setTabSize(3);
                  txtConsulta.setColumns(80);
                  txtConsulta.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
                  txtConsulta.setText("Ingrese aqui una solicitud para la base.");
                  txtConsulta.setSelectedTextColor(Color.GRAY);
                  txtConsulta.setFont(new java.awt.Font("Monospaced",0,12));
                  txtConsulta.setRows(10);
                  
                  txtConsulta.addMouseListener(new MouseAdapter()
                  {
                      public void mouseClicked(MouseEvent e)
                      {
                    	  txtConsulta.setSelectedTextColor(Color.BLACK);
                          txtConsulta.setText("");
                      }
                  });
               }
            }
            {
               //Boton Ejecutar
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
            	//Boton Borrar
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
        	{
        		//Lista con los ctributos
         		listaAtributos = new JList();
         		listaAtributos.setVisible(true);
         		listaAtributos.setBounds(814, 10, 784, 1650);
         		pnlConsulta.add(listaAtributos);
         	}
        	{  
        		//ComboBox para las tablas de la BD
          		combo = new JComboBox<String>();
          		combo.setBounds(657, 99, 150, 23);
          		pnlConsulta.add(combo);
          		combo.addActionListener(new ActionListener() {
         			public void actionPerformed(ActionEvent e) {
         				try 
         				{
	         				tabla_elegida = combo.getSelectedItem().toString();
	         				btnListarActionPerformed(e);
	         			}
         				catch(NullPointerException nexcp){
         					tabla_elegida = "Seleccionar opcion";
         				}
         			}
         		});
          	}
         }
         {
        	// crea la tabla  
        	tabla = new DBTable();
        	tabla.setBounds(0, 286, 684, 375);
        	getContentPane().add(tabla);            
            tabla.setEditable(false);
         }
         
	  } 
      catch (Exception e) 
      {
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

   //El accionar tras apretar el boton ejecutar
   private void btnEjecutarActionPerformed(ActionEvent evt) 
   {
      this.refrescarTabla();
      refrescarComboBox();
   }
   
   //El accionar tras apretar un item del ComboBox
   private void btnListarActionPerformed(ActionEvent evt) 
   {
      this.refrescarTablaPorLista();      
   }
   
   //Conecta con la base de datos con el user admin
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
   
            //establece una conexión con la  B.D. "batallas"  usando directamante una tabla DBTable    
            tabla.connectDatabase(driver, uriConexion, usuario, clave);
            this.conexionBD = DriverManager.getConnection(uriConexion, usuario, clave);
            
            refrescarComboBox();
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

   //Desconecta de la base de datos
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

   //Refresca la tabla con los resultados
   private void refrescarTabla()
   {
      try
      {   
    	  String comando = this.txtConsulta.getText().trim();
    	  Statement stmt = conexionBD.createStatement();
  		  if(stmt.execute(comando)) {
	    	  tabla.setSelectSql(comando);
	
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
    	  
    	  refrescarComboBox();
    	  
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
   
   //Refresca la tabla con los atributos de la tabla elegida
   private void refrescarTablaPorLista()
   {  
		consulta="DESCRIBE "+tabla_elegida;
		try {
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
		}
		catch (SQLException ex){
			// en caso de error, se muestra la causa en la consola
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), ex.getMessage() + "\n","Error al ejecutar la consulta.",JOptionPane.ERROR_MESSAGE);
		}
   }
   
   //Refresca el combobox con las tablas de la base datos
   private void refrescarComboBox() 
   {
	   try
	      {
		    if(combo.getItemCount()>0) 
		    {
		    	combo.removeAllItems();
		    }
		    
	         Statement stmt = this.conexionBD.createStatement();
	         
	         String sql = "SHOW TABLES;";
	         ResultSet rs = stmt.executeQuery(sql);
	         while (rs.next()) {
	        	  String str = rs.getString("Tables_in_banco");
	        	  combo.addItem(str);
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

