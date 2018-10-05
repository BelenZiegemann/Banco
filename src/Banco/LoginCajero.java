package Banco;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginCajero extends JFrame {

	private JPanel contentPane;
	private JPasswordField passwordField;
	private JTextField textField;
	private Cajero cajero;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginCajero frame = new LoginCajero();
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
	public LoginCajero() {
		setTitle("Banco-Login Cajero");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNumeroDeTarjeta = new JLabel("Numero de tarjeta");
		lblNumeroDeTarjeta.setBounds(81, 43, 90, 27);
		contentPane.add(lblNumeroDeTarjeta);
		
		JLabel lblPin = new JLabel("PIN");
		lblPin.setBounds(81, 101, 90, 14);
		contentPane.add(lblPin);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(205, 98, 86, 20);
		contentPane.add(passwordField);
		
		textField = new JTextField();
		textField.setBounds(205, 46, 86, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnIngresar = new JButton("Ingresar");
		btnIngresar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cajero=new Cajero();
				cajero.setVisible(true);
				dispose();
			}
		});
		btnIngresar.setBounds(205, 168, 89, 23);
		contentPane.add(btnIngresar);
	}
}
