package Banco;

import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class Consulta extends JFrame {

	private JPanel contentPane;
	private JPasswordField pField;
	private String password;
	private JButton btnIngresar;
	private ConsultaSQL consul;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Consulta frame = new Consulta();
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

	public Consulta() {
		setTitle("Login administrador");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(96, 72, 76, 24);
		contentPane.add(lblPassword);
		
		pField = new JPasswordField();
		pField.setBounds(203, 74, 92, 20);
		contentPane.add(pField);
		
		btnIngresar = new JButton("Ingresar");
		btnIngresar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				consul=new ConsultaSQL();
				consul.setVisible(true);
				dispose();
			}
		});
		btnIngresar.setBounds(206, 139, 89, 23);
		contentPane.add(btnIngresar);
		
		
	}

	/*
	 *Si el password es incorrecto, mostrar un mensaje de error y cerrar ventana.
	 *Caso contrario, abrir la ventana consultaSQL*/
	public void chequeoPassword(){
		password=pField.getText();
		if(password.equals("hola"))
			JOptionPane.showMessageDialog(null, "anda");
	}
	
}//
