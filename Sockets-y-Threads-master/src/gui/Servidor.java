package gui;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

	private int puerto;


	private int cantClientes;
	private ServerSocket serverSocket;
	private static Servidor server;

	public ServerSocket getServerSocket() {
		return serverSocket;
	}

	public static Servidor sharedInstance(){
		return server;
	}

	public Servidor(int puerto) {
		
		this.cantClientes=0;
		this.puerto=puerto;
			System.out.println("SERVER INICIADO - Esperando conexiones de clientes ...");
			server = this;
			this.iniciarServidor();
	}
	

	public void iniciarServidor() {
		try {
			serverSocket = new ServerSocket(this.puerto);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HiloServer hilo = new HiloServer(); 
		hilo.start();
	}
	
	public void incrementarClientes(){
		this.cantClientes++;
	}
	
	public void decrementarClientes(){
		if(this.cantClientes > 0)
			this.cantClientes--;
	}
	
	public int getCantClientes() {
		return cantClientes;
	}
	
	public void setCantClientes(int cantClientes) {
		this.cantClientes = cantClientes;
	}
	
	public int getPuerto() {
		return puerto;
	}
	
	public static void main(String[] args) {
		new Servidor(10000);
	}
}
