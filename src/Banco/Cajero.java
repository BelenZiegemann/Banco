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

public class Cajero extends JFrame {

	private JPanel contentPane;
	private JPasswordField passwordField;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Cajero frame = new Cajero();
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
	public Cajero() {
		setTitle("Banco-Cajero");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNumeroDeTarjeta = new JLabel("Numero de tarjeta");
		lblNumeroDeTarjeta.setBounds(43, 11, 111, 29);
		contentPane.add(lblNumeroDeTarjeta);
		
		JLabel lblNumeroDePin = new JLabel("Numero de pin");
		lblNumeroDePin.setBounds(43, 51, 111, 29);
		contentPane.add(lblNumeroDePin);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(192, 55, 86, 20);
		contentPane.add(passwordField);
		
		textField = new JTextField();
		textField.setBounds(192, 15, 86, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnIngresar = new JButton("Ingresar");
		btnIngresar.setBounds(189, 108, 89, 23);
		contentPane.add(btnIngresar);
	}
}
