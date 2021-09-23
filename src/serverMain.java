
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;

public class serverMain  extends PApplet{
	
	private int x, y,id,mov;
	private int r,g,b;
	
	
	private BufferedWriter bfw;
	private BufferedReader bfr;
	
	private ArrayList <PVector> pos;

	public static void main(String[] args) {
		PApplet.main("serverMain");
	}
		
	
	@Override
	public void settings() {
		size(500, 500);
	}
	
	@Override
	public void setup() {
		x = 250;
		y = 250;
		id=0;
		mov=0;
		
		r=255;
		g=255;
		b=255;
				
		iniciarConexion();
	
	
	}
	
	@Override
	public void draw() {
		background(0);	
		
		textAlign(CENTER);
		textSize(18);
		text("Servidor", 250, 30);
		
		fill(r,g, b);
		ellipse(x, y, 50, 50);
	}
	
	

	
	public void iniciarConexion() {
		
		new Thread(
				()->{
		//1. Inicializa el servidor 
				try {
					
					System.out.println("Iniciando Servidor");
					ServerSocket ss= new ServerSocket(9000);
					System.out.println("Esperando cliente"); //conexion es el socket del servidor
					Socket conexion =ss.accept(); //detiene la conexion del servidor hasta que se conecte alguno
					System.out.println("Se conecto el cliente");
					
					//conexion y leer mensajes que le lleguen
					//InputStream(entrada) OutputStream(salida)
					
					InputStream is= conexion.getInputStream(); //inputStram
					InputStreamReader isr= new InputStreamReader(is); //inputStreamReader
					bfr= new BufferedReader(isr);
					
					//Escribir mensajes
					OutputStream os= conexion.getOutputStream();
					OutputStreamWriter osw= new OutputStreamWriter(os);
					bfw= new BufferedWriter(osw);
					
					
					
					while(true) {
						System.out.println("esperando mensaje");
						String line= bfr.readLine();
						System.out.println("Mensaje recibido"+ line);
						
		
						
						System.out.println("Recibido: "+ line);
						String[] coord= line.split(":");
						mov= Integer.parseInt(coord[0]);
						id= Integer.parseInt(coord[1]);
						
						
						if (id == 1) {
							y-=mov;

						}
						if (id == 2) {
							y+=mov;

						}
						if (id == 3) {
							x-=mov;
						}
						if (id == 4) {
							x+=mov;
						}
						
						if (id == 5) {     
							x=x;
							y=y;
							r= (int) random(0,255);
							g= (int) random(0,255);
							b= (int) random(0,255);
						}
						
						
					}
					
	
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				}).start();
	}
	
	
	
}
