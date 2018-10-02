package Banco;


import java.awt.EventQueue;


import javax.swing.JDesktopPane;

import javax.swing.JFrame;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JInternalFrame;


//import batallas.VentanaAbmBatallas;
//import batallas.VentanaBarcos;
//import batallas.VentanaConsultas;

@SuppressWarnings("serial")
public class VentanaPrincipal extends JFrame {
	

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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mFunciones = new JMenu("Funciones");
		menuBar.add(mFunciones);
		
		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBounds(0, 0, 1, 1);
		getContentPane().add(desktopPane);
		
		JInternalFrame internalFrame = new JInternalFrame("New JInternalFrame");
		internalFrame.setBounds(0, 0, 55, 34);
		desktopPane.add(internalFrame);
		internalFrame.setVisible(true);
	
	}
}
