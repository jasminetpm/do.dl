package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import chatUI.MainWindow;

public class MyClient extends Thread {
	private Socket connection;
	private BufferedReader br;
	private PrintWriter pw;
	private String name;
	private ReadingClient rc;
	private MainWindow mw;
	private int sendMessageCounter = 0;
	
	public MyClient(String ip, int port, MainWindow mw) throws UnknownHostException, IOException
	{
		this.mw = mw;
		this.connection = new Socket(ip, port);
		this.br = new BufferedReader (new InputStreamReader(this.connection.getInputStream()));
		this.pw = new PrintWriter(this.connection.getOutputStream());
		this.rc = new ReadingClient(this);
		//this.rc.start();
		
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
		while (true)
		{
			while (this.sendMessageCounter < this.mw.getMessageCounter())
			{
				this.pw.println(this.mw.getMyLastWords());
				System.out.println("Just got the message to send: " + this.mw.getMyLastWords());
				this.pw.flush();
				this.sendMessageCounter++;
			}
			TimeUnit.MILLISECONDS.sleep(1);
		}
	}
	
	public void clean() throws IOException
	{
		this.br.close();
		this.pw.close();
		this.connection.close();
	}

	/*
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return this.name;
	}
	*/
	
	public MainWindow getMainWindow()
	{
		return this.mw;
	}
	

	@Override
	public void run() {
		this.rc.start();
		try {
			this.sendMessage();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}



