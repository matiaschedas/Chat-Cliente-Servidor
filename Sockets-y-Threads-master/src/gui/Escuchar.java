package gui;


import java.io.DataInputStream;
import java.io.IOException;

import javax.swing.JTextArea;

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
			String m[];
			try {
				mensaje = (String)entrada.readUTF();
				m=mensaje.split("/");
				System.out.println(mensaje);
				area.append(m[0]+": ");
				if(m.length>=2)
					area.append(m[1]+'\n');
				else
					area.append("\n");
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				flag=true;
			}
		}
	}
	
}
