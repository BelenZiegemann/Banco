package Banco;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class Prestamos extends JFrame {

	private JPanel contentPane;
	private JTextField tLegajo;
	private JPasswordField passEmpleado;
	private String legajo;
	private String password;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Prestamos frame = new Prestamos();
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
	public Prestamos() {
		setTitle("Login Empleado");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 547, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblLegajo = new JLabel("Legajo");
		lblLegajo.setBounds(27, 11, 70, 14);
		contentPane.add(lblLegajo);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(27, 44, 70, 14);
		contentPane.add(lblPassword);
		
		tLegajo = new JTextField();
		tLegajo.setBounds(137, 8, 86, 20);
		contentPane.add(tLegajo);
		tLegajo.setColumns(10);
		
		passEmpleado = new JPasswordField();
		passEmpleado.setBounds(137, 41, 86, 20);
		contentPane.add(passEmpleado);
		
		JButton btnIngresar = new JButton("Ingresar");
		btnIngresar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				legajo=tLegajo.getText();
				password=passEmpleado.getText();
			}
		});
		btnIngresar.setBounds(134, 100, 89, 23);
		contentPane.add(btnIngresar);
	}
}
