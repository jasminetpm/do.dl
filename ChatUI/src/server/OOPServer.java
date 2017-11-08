package server;
import java.io.*;
import java.net.*;
import java.util.LinkedList;

import client.MyClient;
import controller.SetPort;

public class OOPServer
{
	private ServerSocket servSock;
	private Socket client;
	private PrintWriter pw;
	private LinkedList<Socket> clientList;
	private int port;

	public OOPServer(int port)
	{
		try {
			this.port = port;
			this.servSock = new ServerSocket(this.port);
			System.out.println("successfully established the port\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.clientList = new LinkedList<Socket>();
	}

	public void acceptClientLoop()
	{
		while (true)
		{
			Socket c;
			try {
				c = this.servSock.accept();
				this.clientList.add(c);
				ClientThread th = new ClientThread(c, this);
				th.start();
				System.out.println("Just accepted a client. Going to the next iteration");
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}




	public void acceptOneClient() throws Exception

	{

		System.out.println("Waiting for an incoming connection");
		this.client = this.servSock.accept();
		System.out.println("I accepted a connection!");
		this.pw = new PrintWriter(client.getOutputStream());

		pw.println("Hi!");
		pw.flush();
	}

	public void cleanStreams() throws Exception
	{
		System.out.println("Closing streams and cleaning stuff");
		pw.close();
		client.close();
		this.servSock.close();
	}
	
	public LinkedList<Socket> getClientList()
	{
		return this.clientList;
	}
	

	public static void main (String[] args) throws Exception
	{
		OOPServer serv = new OOPServer(SetPort.port);

		serv.acceptClientLoop();
		
		serv.cleanStreams();
		
	}
}