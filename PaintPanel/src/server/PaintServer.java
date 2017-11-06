package server;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.*;
import java.util.ArrayList;

import model.Instruction;

public class PaintServer {
	private ServerSocket servSock;
	private static ArrayList<ConnectionHandler> clientList = new ArrayList<ConnectionHandler>();
	
	public PaintServer(int port) {
		try {
			this.servSock = new ServerSocket(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void acceptClientLoop() {
		while (true) {
			Socket c;
			try {
				c = this.servSock.accept();
				ConnectionHandler th = new ConnectionHandler(c);
				clientList.add(th);
				th.start();
				System.out.println("Just accepted a client. Going to the next iteration");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	class ConnectionHandler extends Thread {
		private Socket client;
		private OutputStream os;
		private InputStream is;
		private ObjectOutputStream oos;
		private ObjectInputStream ois;
		
		public ConnectionHandler(Socket c) {
			// TODO Auto-generated constructor stub
			try {
				this.client = c;
				this.os = this.client.getOutputStream();
 				this.is = this.client.getInputStream();
 				this.oos = new ObjectOutputStream(this.os);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		@Override
		public void run() {
			try {
				this.ois = new ObjectInputStream(this.is);
				while(true) {
					Instruction instr = (Instruction) this.ois.readObject();
					System.out.println("Executing instruction:");
					System.out.println(instr);
					for (ConnectionHandler connection : PaintServer.clientList) {
						connection.sendInstruction(instr);
					}
				} 
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void sendInstruction(Instruction instr) {
			try {
				System.out.println("Sending Message to Client");
				this.oos.writeObject(instr);
				this.oos.flush();
				this.oos.reset();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String args[]) throws Exception {
		PaintServer serv = new PaintServer(9876);
		
		System.out.println("Accepting clients...");
		serv.acceptClientLoop();
	}
}
