package gui;


import java.io.DataInputStream;
import java.io.IOException;

import javax.swing.JTextArea;

import com.google.gson.Gson;

public class Escuchar extends Thread{
	DataInputStream entrada;
	JTextArea area;
	
	public Escuchar(DataInputStream entrada, JTextArea area) {
		// TODO Auto-generated constructor stub
		this.entrada=entrada;		
		this.area=area;
	}
	public void run() {
		boolean flag = false;
		while(flag == false) {
			String mensaje;
			
			try {
				mensaje = (String)entrada.readUTF();
				Gson gson = new Gson();
				Mensaje msj = gson.fromJson(mensaje, Mensaje.class);
				
				System.out.println(mensaje);
				area.append(msj.nombre+": ");
				area.append(msj.msj+"\n");
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				flag=true;
			}
		}
	}
	
}
