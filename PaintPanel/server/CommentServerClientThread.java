package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class CommentServerClientThread extends Thread{

	private Socket client;
	private CommentServerReadingThread reading;
	private PrintWriter pw;
	private BufferedReader br;
	private PaintServer server;

	public CommentServerClientThread(Socket c, PaintServer server)
	{
		this.client = c;
		this.server = server;
		try {
			this.pw = new PrintWriter(this.client.getOutputStream());	
			this.br = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
			this.reading = new CommentServerReadingThread(this.br, this);
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
	
	public PaintServer getServer()
	{
		return this.server;
	}
	
	public Socket getClient()
	{
		return this.client;
	}
	

}