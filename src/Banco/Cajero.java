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
import java.sql.Connection;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class Cajero extends JFrame {

	private JPanel contentPane;
	
	private long nroTarjeta;
	
	
	protected Connection conexionBD = null;

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
		
		JPanel pBotones = new JPanel();
		pBotones.setBounds(0, 0, 531, 76);
		contentPane.add(pBotones);
		
		JButton bConsulta = new JButton("Consulta de saldo");
		bConsulta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				oyenteConsultaSaldo();
			}
		});
		pBotones.add(bConsulta);
		
		JButton bMovimientos = new JButton("Ultimos movimientos");
		bMovimientos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				oyenteConsultaMovimiento();
			}
		});
		pBotones.add(bMovimientos);
		
		JButton bPeriodo = new JButton("Movimientos por periodo");
		bPeriodo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				oyenteConsultaPeriodos();
			}
		});
		pBotones.add(bPeriodo);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 87, 531, 290);
		contentPane.add(panel);
		panel.setLayout(null);
	}
	
	
	private void oyenteConsultaSaldo(){
		
	}
	
	private void oyenteConsultaMovimiento(){
		
	}
	
	private void oyenteConsultaPeriodos(){
		
	}
}//principal
