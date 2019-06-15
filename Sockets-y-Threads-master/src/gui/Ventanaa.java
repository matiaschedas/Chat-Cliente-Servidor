package gui;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import javax.swing.text.DefaultCaret;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.DropMode;
import java.awt.Color;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;



public class Ventanaa extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JScrollPane scrollPaneIzq;
	private JTextArea areaChat;
	private DefaultCaret caret;
	
	private final JTextArea textArea = new JTextArea();
	private JButton btnEnviar;
	private Cliente cliente;
	private JTextField txtUser;
	private JTextField txtUsuario;
	

	public static void main(String[] args) {
		try {
			javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager
					.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		new Ventanaa();
	}

	public Ventanaa() {
		super();
		iniciarGUI();
	}
	

	private void iniciarGUI() {
		try {
			this.setTitle("Sala de chat");
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			getContentPane().setLayout(null);

			scrollPaneIzq = new JScrollPane();
			getContentPane().add(scrollPaneIzq);
			scrollPaneIzq.setBounds(0, 0, 610, 297);
			areaChat = new JTextArea();
			areaChat.setEditable(false);
			scrollPaneIzq.setViewportView(areaChat);
			
			caret = (DefaultCaret)areaChat.getCaret();
			caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
			
			JTextArea panelUsers = new JTextArea();
			panelUsers.setBounds(610, 0, 108, 375);
			getContentPane().add(panelUsers);
			
			btnEnviar = new JButton("Enviar");
			btnEnviar.setEnabled(false);
			
			btnEnviar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(!textArea.getText().isEmpty()) {	
						cliente.enviar(textArea.getText());
						textArea.setText("");
						
					}
				}
			});
			JButton btnConectarse = new JButton("Conectarse");
			btnEnviar.setBounds(529, 303, 71, 72);
			getContentPane().add(btnEnviar);
			textArea.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent arg0) {
					if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
						arg0.consume();
						if (btnEnviar.isEnabled()) {
							if(!textArea.getText().isEmpty()) {	
								cliente.enviar(textArea.getText());
								textArea.setText("");
							}
						}
					}
				}
			});
			textArea.setBounds(10, 303, 514, 72);
			getContentPane().add(textArea);
			btnConectarse.setEnabled(false);
			
			JButton cerrar = new JButton("Cerrar sesion");
			cerrar.setEnabled(false);
			cerrar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					cliente.cerrar();
					//btnConectarse.setEnabled(true);
					btnEnviar.setEnabled(false);
					cerrar.setEnabled(false);
					txtUser.setEnabled(true);
				}
			});
			cerrar.setBounds(590, 386, 119, 23);
			getContentPane().add(cerrar);
			
			btnConectarse.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					cliente = new Cliente("localhost",10000,txtUser.getText());
					cliente.escuchar(areaChat);
					caret = (DefaultCaret)areaChat.getCaret();
					caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
					btnEnviar.setEnabled(true);
					cerrar.setEnabled(true);
					btnConectarse.setEnabled(false);
					txtUser.setEnabled(false);
				}
			});
			
			btnConectarse.setBounds(484, 386, 89, 23);
			getContentPane().add(btnConectarse);
			
			txtUser = new JTextField();
			txtUser.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent arg0) {
					if(arg0.getKeyCode()==KeyEvent.VK_ENTER) {
						if(!txtUser.getText().isEmpty())
							btnConectarse.setEnabled(true);
					}
				}
			});
		
			txtUser.setBounds(366, 387, 108, 20);
			getContentPane().add(txtUser);
			txtUser.setColumns(10);
			
			txtUsuario = new JTextField();
			txtUsuario.setForeground(new Color(255, 0, 0));
			txtUsuario.setHorizontalAlignment(SwingConstants.CENTER);
			txtUsuario.setEditable(false);
			txtUsuario.setText("Usuario:");
			txtUsuario.setBounds(285, 387, 71, 20);
			getContentPane().add(txtUsuario);
			txtUsuario.setColumns(10);

			this.setResizable(false);
			this.setSize(727, 437);
			this.setLocationRelativeTo(null);
			this.setVisible(true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

