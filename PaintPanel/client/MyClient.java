package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import chatUI.MainWindow;

/**
 * Sets up the client for the chat
 * @author Coco
 * @version 1.0 Nov 2017
 */
public class MyClient extends Thread {
	/**
	 * The socket associated
	 */
	private Socket connection;
	/**
	 * Stores the bufferedReader for ReadingClient
	 */
	private BufferedReader br;
	/**
	 * Stores the printWriter for ReadingClient
	 */
	private PrintWriter pw;
	/**
	 * Stores the reading client which reads the input chat
	 */
	private ReadingClient rc;
	/**
	 * Stores the MainWindow on which the client is initialised
	 */
	private MainWindow mw;
	/**
	 * Stores the sendMessageCounter used to determine whether to register an incoming message
	 */
	private int sendMessageCounter = 0;
	
	/**
	 * constructor
	 * @param ip: the IP address used
	 * @param port: the port number used
	 * @param mw: the MainWindow the client is associated with
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public MyClient(String ip, int port, MainWindow mw) throws UnknownHostException, IOException
	{
		this.mw = mw;
		this.connection = new Socket(ip, port);
		this.br = new BufferedReader (new InputStreamReader(this.connection.getInputStream()));
		this.pw = new PrintWriter(this.connection.getOutputStream());
		this.rc = new ReadingClient(this);
		//this.rc.start();
		
	}
	
	/**
	 * getter for the bufferedReader
	 * @return the bufferedReader
	 */
	public BufferedReader getBR()
	{
		return this.br;
	}
	
	/**
	 * getter for the printWriter
	 * @return printWriter
	 */
	public PrintWriter getPW()
	{
		return this.pw;
	}
	
	/**
	 * Send a message to the sever
	 * @throws InterruptedException
	 */
	public synchronized void sendMessage() throws InterruptedException
	{
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
	
	/**
	 * clean up the client
	 * @throws IOException
	 */
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



