package server;

import java.net.*;
import java.io.*;

/**
 * generate a server client thread to deal with information sent to the server
 * @author Coco
 * @version 1.0 Nov 2017
 */
public class ChatServerClientThread extends Thread{

	/**
	 * the client socket
	 */
	private Socket client;
	/**
	 * the reading thread
	 */
	private ChatServerReadingThread reading;
	/**
	 * the printWriter associated
	 */
	private PrintWriter pw;
	/**
	 * the bufferedreader associated
	 */
	private BufferedReader br;
	/**
	 * the server it used
	 */
	private PaintServer server;

	/**
	 * constructor
	 * @param c: the server socket
	 * @param server: the server associated
	 */
	public ChatServerClientThread(Socket c, PaintServer server)
	{
		this.client = c;
		this.server = server;
		try {
			this.pw = new PrintWriter(this.client.getOutputStream());	
			this.br = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
			this.reading = new ChatServerReadingThread(this.br, this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
	
	@Override
	/**
	 * to start the reading thread
	 */
	public void run()
	{
		this.reading.start();
		// We will complete that later
	}
	

	/**
	 * cleans up
	 * caleed after the connection is closed
	 */
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
	
	/**
	 * send messages to the server
	 * @param message
	 */
	public synchronized void sendMessage(String message)
	{
		this.pw.println(message);
		this.pw.flush();
	}
	
	/**
	 * get the server associated 
	 * @return
	 */
	public PaintServer getServer()
	{
		return this.server;
	}
	
	/**
	 * get the client associated
	 * @return
	 */
	public Socket getClient()
	{
		return this.client;
	}
	

}