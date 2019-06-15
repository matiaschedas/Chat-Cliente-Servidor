package gui;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JTextArea;

public class Cliente {

	
	private int puerto;
	private String ip;
	private Socket socketCliente;
	private String nombre;
	
	private DataInputStream entrada;
	private DataOutputStream salida;

	public Cliente(String ip, int puerto, String nombre) {
		this.ip = ip;
		this.puerto = puerto;
		this.nombre = nombre;

		try {
			socketCliente = new Socket(ip, puerto);
			salida = new DataOutputStream(socketCliente.getOutputStream());
			salida.writeUTF(nombre);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	public void enviar(String texto) {
		try {
			salida = new DataOutputStream(socketCliente.getOutputStream());
			salida.writeUTF(texto);
//			entrada = new DataInputStream(cliente.getInputStream());
//			System.out.println(entrada.readUTF());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	public void escuchar(JTextArea area) {
        DataInputStream entrada;
        try {
            entrada = new DataInputStream(socketCliente.getInputStream());
            Escuchar hilo = new Escuchar(entrada, area);
            hilo.start();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
	
	public void cerrar () {
		try {
			socketCliente.close();
			salida.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int getPuerto() {
		return puerto;
	}
	
}