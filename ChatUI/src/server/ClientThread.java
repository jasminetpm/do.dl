package server;

import java.net.*;
import java.io.*;


public class ClientThread extends Thread{

	private Socket client;
	private ReadingThread reading;
	private PrintWriter pw;
	private BufferedReader br;
	private OOPServer server;

	public ClientThread(Socket c, OOPServer server)
	{
		this.client = c;
		this.server = server;
		try {
			this.pw = new PrintWriter(this.client.getOutputStream());	
			this.br = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
			this.reading = new ReadingThread(this.br, this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
	
	


	@Override
	public void run()
	{
		this.reading.start();
		// We will complete that later
	}
	

	public void cleanConnection()
	{
		System.out.println("Client disconnecting, cleaning the data!");
		this.pw.close();
		try {
			this.br.close();
			this.client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	
	public synchronized void sendMessage(String message)
	{
		this.pw.println(message);
		this.pw.flush();
	}
	
	public OOPServer getServer()
	{
		return this.server;
	}
	
	public Socket getClient()
	{
		return this.client;
	}
	

}