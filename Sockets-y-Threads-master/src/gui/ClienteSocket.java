package gui;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClienteSocket extends Thread{
	
	private Socket socket;
	private String nombre;
	private int nroCliente;
	private boolean logueado;
	private boolean flag;

	public ClienteSocket(Socket socket, int nro){
		this.socket = socket;
		this.nroCliente = nro;
		this.logueado = false;
		flag=true;
	}

	/*
	 * Permito asociar el cliente a un nombre
	 * el nombre es post login por lo cual no lo inicializo con el
	 */
	public void setNombre(String nombre){
		this.nombre = nombre;
	}
	
	/*
	 * Permite setear el estado si esta o no logueado
	 */
	public void setLogueado(boolean log){
		this.logueado = log;
	}
	
	/*
	 * Devuelve true si el usuario se ha logueado
	 * y false si no lo esta
	 */
	public boolean estaLogueado(){
		return this.logueado;
	}
	
	/*
	 * Permito acceder al socket cuando se requiera
	 *  una comunicacion particular
	 */
	public Socket getSocket(){
		return this.socket;
	}
	
	/*
	 * Permito acceder al nombre cuando se
	 * lo busque para listarlo por su nombre
	 */
	public String getNombre(){
		return this.nombre;
	}
	
	/*
	 * Permito el acceso a su identificador principal
	 */
	public int getNumeroCliente(){
		return this.nroCliente;
	}
	
	/*
	 * Thread que escucha al cliente
	 * recibe el mensaje y se lo pasa al serviceManager
	 * quien se encargar‡ de determinar que tipo de mensaje es y que hacer con el
	 * 
	 */
	public void run() {
		
		while (true) {
			try {
				DataInputStream f = new DataInputStream(this.socket.getInputStream());
				String message = f.readUTF();
				if(flag==true) {
					this.nombre=message;
					flag=false;
				}
				else {
					ClienteServidor.sharedInstance().distribuir(message,nroCliente,nombre);
					//System.out.println(message);
					ClienteServidor.sharedInstance().messageRecived(this.nroCliente, message);
				}
			} 
			catch (IOException e) {
				if(!this.socket.isBound()){
					//no esta mas conectado
					try {
						Servidor.sharedInstance().decrementarClientes();
						ClienteServidor.sharedInstance().sacarCliente(this);
						
						this.socket.close();
						this.interrupt();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		}
	}
	
	/*
	 * Metodo que me permite comunicarme con el cliente
	 */
	public void writeMessage(String message){
		try{
			DataOutputStream f = new DataOutputStream(this.socket.getOutputStream());
			f.writeUTF(message);
		} catch(Exception e){
			
		}
	}
	
	/*
	 * Permite interrumpir el thread que escucha al cliente
	 * y adem‡s cierra el puerto de comunicacion este.
	 */
	public void interrupt(){
		super.interrupt();
		try{
			this.socket.close();
	
		} catch(Exception e){
			
		}
	}
}