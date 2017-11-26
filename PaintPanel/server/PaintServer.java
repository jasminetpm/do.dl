package server;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.LinkedList;

import model.CanvasState;
import model.Instruction;

public class PaintServer {
	private ServerSocket servSock;
	private static int clientId = 0;
	private static ArrayList<ConnectionHandler> clientList = new ArrayList<ConnectionHandler>();
	private static ArrayList<BufferedImage> baseLayers;
	private static LinkedList<Instruction> instructionLog;
	
	public PaintServer(int port) {
		try {
			this.servSock = new ServerSocket(port);
			PaintServer.baseLayers = new ArrayList<BufferedImage>();
			for (int i = 0; i < 4; i++) 
			{
				PaintServer.baseLayers.add(new BufferedImage(650, 540, BufferedImage.TYPE_INT_ARGB));
			}
			PaintServer.instructionLog = new LinkedList<Instruction>();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void executeInstruction(Instruction instr) {
		Instruction poppedInstr;
		
		PaintServer.instructionLog.add(instr);
		// if the queue size > 20, then we start modifying the base layers
		if (PaintServer.instructionLog.size() > 20) {
			poppedInstr = PaintServer.instructionLog.removeFirst();
			poppedInstr.execute(PaintServer.baseLayers);
		}
	}
	
	public void acceptClientLoop() {
		while (true) {
			Socket c;
			try {
				c = this.servSock.accept();
				ConnectionHandler th = new ConnectionHandler(c, PaintServer.clientId);
				PaintServer.clientList.add(th);
				PaintServer.clientId++;
				th.start();
				System.out.println("Just accepted a client. Going to the next iteration");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	class ConnectionHandler extends Thread {
		private Socket client;
		public int id;
		private OutputStream os;
		private InputStream is;
		private ObjectOutputStream oos;
		private ObjectInputStream ois;
		
		public ConnectionHandler(Socket c, int _id) {
			// TODO Auto-generated constructor stub
			try {
				this.client = c;
				this.id = _id;
				this.os = this.client.getOutputStream();
 				this.is = this.client.getInputStream();
 				this.oos = new ObjectOutputStream(this.os);
 				this.sendId(this.id);
 				this.sendCanvasState();
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
					System.out.println("Got message from client #" + instr.getClientId());
					PaintServer.executeInstruction(instr);
					for (ConnectionHandler connection : PaintServer.clientList) {
						if (connection.id != instr.getClientId()) {
							connection.sendInstruction(instr);
						}
					}
				} 
			} catch (ClassNotFoundException | IOException e) {
				System.out.println("Client disconnected!");
				PaintServer.clientList.remove(this);
				System.out.println(PaintServer.clientList);
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
		
		public void sendCanvasState() throws IOException {
			System.out.println("Trying to send canvas state...");
			CanvasState currentState = new CanvasState(PaintServer.baseLayers, PaintServer.instructionLog);
			System.out.println(currentState);
			this.oos.writeObject(currentState);
			this.oos.flush();
			this.oos.reset();
		}
		
		public void sendId(Integer id) {
			try {
				System.out.println("Issuing client ID...");
				this.oos.writeObject(new Integer(this.id));
				this.oos.flush();
				this.oos.reset();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String args[]) throws Exception {
		PaintServer serv = new PaintServer(9873);
		System.out.println("Accepting clients...");
		serv.acceptClientLoop();
	}
}
