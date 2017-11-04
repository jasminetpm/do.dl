package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class MyClient {
	private Socket connection;
	private BufferedReader br;
	private PrintWriter pw;
	private String name;
	private ReadingClient rc;
	
	public MyClient(String ip, int port) throws UnknownHostException, IOException
	{
		this.connection = new Socket(ip, port);
		this.br = new BufferedReader (new InputStreamReader(this.connection.getInputStream()));
		this.pw = new PrintWriter(this.connection.getOutputStream());
		this.rc = new ReadingClient(this);
		this.rc.start();
		
	}
	
	public BufferedReader getBR()
	{
		return this.br;
	}
	
	public PrintWriter getPW()
	{
		return this.pw;
	}
	
	public synchronized void sendMessage() throws InterruptedException
	{
		int i;
		for (i = 0; i < 10; i++)
		{
			Thread.sleep(500);
			this.pw.println(this.name + "is sending message");
			this.pw.flush();
		}
	}
	
	public void clean() throws IOException
	{
		this.br.close();
		this.pw.close();
		this.connection.close();
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return this.name;
	}
	


	
}



