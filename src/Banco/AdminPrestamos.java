package Banco;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AdminPrestamos extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminPrestamos frame = new AdminPrestamos();
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
	public AdminPrestamos() {
		setTitle("Banco-Administracion de prestamos");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 434, 45);
		contentPane.add(panel);
		
		JButton bCreacion = new JButton("Creacion ");
		panel.add(bCreacion);
		
		JButton bPago = new JButton("Pago");
		bPago.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		panel.add(bPago);
		
		JButton bClientes = new JButton("Clientes morosos");
		panel.add(bClientes);
		
		JPanel pCreacion = new JPanel();
		pCreacion.setBounds(0, 51, 424, 199);
		contentPane.add(pCreacion);
	}

}
