package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import chatUI.MainWindow;
import gui.AllCommentPane;

public class CommentClient extends Thread {
	private Socket connection;
	private BufferedReader br;
	private PrintWriter pw;
	private CommentReadingClient crc;
	private AllCommentPane acp;
	private int sendMessageCounter = 0;
	
	public CommentClient(String ip, int port, AllCommentPane acp) throws UnknownHostException, IOException
	{
		this.acp = acp;
		this.connection = new Socket(ip, port);
		this.br = new BufferedReader (new InputStreamReader(this.connection.getInputStream()));
		this.pw = new PrintWriter(this.connection.getOutputStream());
		this.crc = new CommentReadingClient(this);
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
		while (true)
		{
			while (this.sendMessageCounter < this.acp.getMessageCounter())
			{
				System.out.println("Sent comment is called");
				this.pw.println(this.acp.getMyLastWords());
				System.out.println("Just got the message to send: " + this.acp.getMyLastWords());
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
	
	public AllCommentPane getMainWindow()
	{
		return this.acp;
	}
	

	@Override
	public void run() {
		this.crc.start();
		try {
			this.sendMessage();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}



