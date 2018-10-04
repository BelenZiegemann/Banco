package Banco;

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

@SuppressWarnings("serial")
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
		setBounds(100, 100, 547, 416);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel pIngreso = new JPanel();
		pIngreso.setBounds(0, 11, 531, 124);
		contentPane.add(pIngreso);
		pIngreso.setLayout(null);
		
		JLabel lblNumeroDeTarjeta = new JLabel("Numero de tarjeta");
		lblNumeroDeTarjeta.setBounds(74, 11, 111, 29);
		pIngreso.add(lblNumeroDeTarjeta);
		
		textField = new JTextField();
		textField.setBounds(207, 11, 115, 20);
		pIngreso.add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(207, 52, 115, 20);
		pIngreso.add(passwordField);
		
		JLabel lblNumeroDePin = new JLabel("Numero de pin");
		lblNumeroDePin.setBounds(72, 46, 111, 29);
		pIngreso.add(lblNumeroDePin);
		
		JButton btnIngresar = new JButton("Ingresar");
		btnIngresar.setBounds(209, 90, 89, 23);
		pIngreso.add(btnIngresar);
		
		JPanel pConsulta = new JPanel();
		pConsulta.setBounds(0, 134, 531, 243);
		contentPane.add(pConsulta);
		pConsulta.setLayout(null);
		
		JButton bSaldo = new JButton("Consultar saldo");
		bSaldo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		bSaldo.setBounds(0, 11, 149, 23);
		pConsulta.add(bSaldo);
		bSaldo.setEnabled(false);
		
		JButton bMovimiento = new JButton("Ultimos movimientos");
		bMovimiento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		bMovimiento.setBounds(197, 11, 149, 23);
		pConsulta.add(bMovimiento);
		bMovimiento.setEnabled(false);
		
		JButton bPeriodo = new JButton("Movimientos por periodo");
		bPeriodo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		bPeriodo.setBounds(372, 11, 149, 23);
		pConsulta.add(bPeriodo);
		bPeriodo.setEnabled(false);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 57, 531, 175);
		pConsulta.add(panel);
		panel.setLayout(null);
	}
}
