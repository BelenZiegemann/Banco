 package Banco;
 
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class VentanaPrincipal extends JFrame {

	private JPanel contentPane;
	private JLabel Titulo;
	private Consulta consultaPass;
	private Cajero cajero;
	private Prestamos prestamos;

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
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
		//Creacion del Frame principal
		setTitle("Banco");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(500, 100, 185, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Titulo
		Titulo= new JLabel("Banco");
		Titulo.setFont(new Font("Serif", Font.PLAIN, 48));
		Titulo.setForeground(Color.DARK_GRAY);
		Titulo.setBounds(25, 10, 150, 100);
		contentPane.add(Titulo);
		
		//----------Botones------------------
		//Boton Consulta
		JButton btnConsultas = new JButton("Consultas");
		btnConsultas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				consultaPass=new Consulta();
				consultaPass.setVisible(true);
			}
		});
		btnConsultas.setBounds(10, 100, 150, 25);
		contentPane.add(btnConsultas);
		
		//Boton Cajero
		JButton btnCajero = new JButton("Cajero");
		btnCajero.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cajero=new Cajero();
				cajero.setVisible(true);
				
			}
		});
		btnCajero.setBounds(10, 150, 150, 25);
		contentPane.add(btnCajero);
		
		//Boton Prestamo
		JButton btnAdministracion = new JButton("Prestamos ");
		btnAdministracion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				prestamos=new Prestamos();
				prestamos.setVisible(true);
			}
		});
		btnAdministracion.setBounds(10, 200, 150, 25);
		contentPane.add(btnAdministracion);
		
		//Boton Salir
		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnSalir.setBounds(35, 300, 100, 20);
		contentPane.add(btnSalir);
	}
}
