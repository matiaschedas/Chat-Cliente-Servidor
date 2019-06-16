package gui;

import java.util.ArrayList;

public class ClienteServidor {

	private static ClienteServidor cliente;
	private ArrayList<ClienteSocket> clientes;

	public ClienteServidor() {
		clientes = new ArrayList<ClienteSocket>();
	}
	
	public void agregarCliente(ClienteSocket cliente) {
		this.clientes.add(cliente);
	}
	public void sacarCliente(ClienteSocket cliente) {
		clientes.remove(cliente);
	}
	
	public static ClienteServidor sharedInstance() {
		if(cliente == null){
			cliente= new ClienteServidor();
		}
		return cliente;
	}
	
	public void distribuir(String mensaje) {
		for(int i=0;i<clientes.size();i++) {
			clientes.get(i).writeMessage(mensaje);
		}
	}

	
	public void messageRecived(int client, String message){

		System.out.println("Cliente: " + client + " mando: " + message);
		
	}
	

}

