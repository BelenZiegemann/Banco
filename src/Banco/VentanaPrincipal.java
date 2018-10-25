 package Banco;
 
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class VentanaPrincipal extends JFrame {

	private JPanel contentPane;
	private Consulta consultaPass;
	private Cajero cajero;
	private Prestamos prestamos;

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		//Prueba fecha
		/*
		Fechas f = new Fechas();
		String f1="09/10/2018";
		java.util.Date d = f.convertirStringADate(f1);
		System.out.println("String -> java.util.Date: "+d.toString());
		java.sql.Date dsql = f.convertirStringADateSQL(f1);
		System.out.println("String -> java.sql.Date: "+dsql.toString());
		String f2= f.convertirDateAString(d);
		System.out.println("java.util.Date -> String: "+f2);
		String f3= f.convertirDateAStringDB(d);
		System.out.println("java.util.Date -> StringDB: "+f3);
		java.sql.Date dsql2 = f.convertirDateADateSQL(d);
		if(dsql.equals(dsql2)) 
		{
			System.out.println("son iguales.");
		}
		else 
		{
			System.out.println("no son iguales.");
		}
		*/
		//--------
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaPrincipal frame = new VentanaPrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VentanaPrincipal() {
		setTitle("Banco");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JButton btnConsultas = new JButton("Consultas");
		btnConsultas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				consultaPass=new Consulta();
				consultaPass.setVisible(true);
			}
		});
		btnConsultas.setBounds(10, 74, 110, 23);
		contentPane.add(btnConsultas);
		
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		btnSalir.setBounds(169, 210, 89, 23);
		contentPane.add(btnSalir);
		
		JButton btnCajero = new JButton("Cajero");
		btnCajero.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cajero=new Cajero();
				cajero.setVisible(true);
				
			}
		});
		btnCajero.setBounds(155, 74, 89, 23);
		contentPane.add(btnCajero);
		
		JButton btnAdministracion = new JButton("Prestamos ");
		btnAdministracion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				prestamos=new Prestamos();
				prestamos.setVisible(true);
			}
		});
		btnAdministracion.setBounds(290, 74, 110, 23);
		contentPane.add(btnAdministracion);
				
	}
}
