package server;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import gui.PaintWindow;
import model.CanvasState;
import model.CommentInstruction;
import model.Instruction;
import model.RemoveCommentInstruction;
import model.UndoInstruction;

/**
 * The server class. This class stores the current state of the canvas, and
 * relays Instructions to its clients. Additionally, this class instantiates the
 * chat server.
 * 
 * @author daniellok
 */
public class PaintServer {
	private ServerSocket paintServSock;
	private ServerSocket chatServSock;
	private static int clientId = 0;
	private static int commentIndex = 0;
	private static ArrayList<ConnectionHandler> paintClientList = new ArrayList<ConnectionHandler>();
	private static ArrayList<ChatServerClientThread> chatClientList = new ArrayList<ChatServerClientThread>();
	private static ArrayList<BufferedImage> baseLayers;
	private static LinkedList<Instruction> instructionLog;
	private static ArrayList<CommentInstruction> comments = new ArrayList<CommentInstruction>();
	
	/**
	 * Class constructor. The port of the chat server will always be the port of the
	 * paint server + 1.
	 * 
	 * @param port
	 *            user-defined; this is the port which the clients will need to
	 *            connect on.
	 */
	public PaintServer(int port) {
		try {
			this.paintServSock = new ServerSocket(port);
			this.chatServSock = new ServerSocket(port + 1);
			PaintServer.baseLayers = new ArrayList<BufferedImage>();
			for (int i = 0; i < 5; i++) 
			{
				PaintServer.baseLayers.add(new BufferedImage(650, 540, BufferedImage.TYPE_INT_ARGB));
			}
			PaintServer.instructionLog = new LinkedList<Instruction>();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Removes a comment from the comments list
	 * @param index
	 */
	public static void removeComment(int index) {
		Iterator<CommentInstruction> it = PaintServer.comments.iterator();
		while (it.hasNext()) {
		   CommentInstruction nextItem = it.next();
		   if (nextItem.getIndex() == index) {
		      it.remove();
		   }
		}
		System.out.println(PaintServer.comments);
	}
	
	/**
	 * Called when the server receives an Instruction from a client. It adds the
	 * instruction to its local instruction log, and if the log grows too large (>
	 * 20), then the first instruction is popped from the queue and used to modify
	 * the base layers.
	 * 
	 * @param instr
	 *            the Instruction received from a client.
	 */
	public static void executeInstruction(Instruction instr) {
		Instruction poppedInstr;
		
		PaintServer.instructionLog.add(instr);
		// if the queue size > 20, then we start modifying the base layers
		if (PaintServer.instructionLog.size() > 20) {
			poppedInstr = PaintServer.instructionLog.removeFirst();
			poppedInstr.execute(PaintServer.baseLayers);
		}
	}
	
	/**
	 * Called when the server receives an undo instruction from a client. Removes
	 * the last instruction from the instruction log.
	 */
	public static void undo() {
		// only undo if there are instructions in the log
		if (PaintServer.instructionLog.size() > 0) {
			// remove the last stored instruction
			PaintServer.instructionLog.removeLast();
		} else {
			System.out.println("Instruction log is empty!");
		}
	}
	
	/**
	 * Get the list of clients connected to the chat server.
	 * 
	 * @return the list of clients connected to the chat server
	 */
	public ArrayList<ChatServerClientThread> getChatClientList() {
		return PaintServer.chatClientList;
	}
	
	/**
	 * A loop to accept clients. Once a client connects, they are added to the list
	 * of clients.
	 */
	public void acceptClientLoop() {
		while (true) {
			Socket pss;
			Socket css;
			try {
				pss = this.paintServSock.accept();
				css = this.chatServSock.accept();
				ConnectionHandler paintTh = new ConnectionHandler(pss, PaintServer.clientId);
				ChatServerClientThread chatTh = new ChatServerClientThread(css, this);
				PaintServer.paintClientList.add(paintTh);
				PaintServer.chatClientList.add(chatTh);
				PaintServer.clientId++;
				paintTh.start();
				chatTh.start();
				System.out.println("Just accepted a client. Going to the next iteration");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Connection handler class for the paint clients. Continuously listens for
	 * messages from its client, and relays them to the other clients connected to
	 * the server. The connection handler for the chat client can be found in the
	 * ChatServerClientThread class.
	 * 
	 * @author daniellok
	 */
	class ConnectionHandler extends Thread {
		private Socket client;
		public int id;
		private OutputStream os;
		private InputStream is;
		private ObjectOutputStream oos;
		private ObjectInputStream ois;
		
		/**
		 * Class constructor.
		 * 
		 * @param c
		 *            the socket on which the client connected
		 * @param _id
		 *            the client's ID (provided by the PaintServer class)
		 */
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
		
		/**
		 * The listening loop. The ConnectionHandler will continuously read from its
		 * client, and relay any messages which are passed.
		 */
		@Override
		public void run() {
			try {
				this.ois = new ObjectInputStream(this.is);
				while(true) {
					Instruction instr = (Instruction) this.ois.readObject();
					System.out.println("Got message from client #" + instr.getClientId());
					if (instr instanceof UndoInstruction) {
						PaintServer.undo();
						for (ConnectionHandler connection : PaintServer.paintClientList) {
							if (connection.id != instr.getClientId()) {
								connection.sendInstruction(instr);
							}
						}
					} else if (instr instanceof CommentInstruction) {
						PaintServer.comments.add((CommentInstruction) instr);
						PaintServer.commentIndex++;
						for (ConnectionHandler connection : PaintServer.paintClientList) {
							if (connection.id != instr.getClientId()) {
								System.out.println("Sending instruction to client #" + connection.id);
								connection.sendInstruction(instr);
							}
						}
					} else if (instr instanceof RemoveCommentInstruction) {
						RemoveCommentInstruction rci = (RemoveCommentInstruction) instr;
						PaintServer.removeComment(rci.getIndex());
						for (ConnectionHandler connection : PaintServer.paintClientList) {
							if (connection.id != instr.getClientId()) {
								System.out.println("Sending instruction to client #" + connection.id);
								connection.sendInstruction(instr);
							}
						}
					} else {
						PaintServer.executeInstruction(instr);
						for (ConnectionHandler connection : PaintServer.paintClientList) {
							if (connection.id != instr.getClientId()) {
								System.out.println("Sending instruction to client #" + connection.id);
								connection.sendInstruction(instr);
							}
						}
					}
				} 
			} catch (ClassNotFoundException | IOException e) {
				System.out.println("Client disconnected!");
				PaintServer.paintClientList.remove(this);
				System.out.println(PaintServer.paintClientList);
			}
		}
		
		/**
		 * Sends an Instruction to the client
		 * 
		 * @param instr
		 *            the instruction to be sent
		 */
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
		
		/**
		 * Sends the canvas state (the current picture) to the client. This function is
		 * called in the class constructor (i.e. when the client connects to the server)
		 * 
		 * @throws IOException
		 */
		public void sendCanvasState() throws IOException {
			System.out.println("Trying to send canvas state...");
			CanvasState currentState = new CanvasState(PaintServer.baseLayers, PaintServer.instructionLog, PaintServer.comments, PaintServer.commentIndex);
			System.out.println(currentState);
			this.oos.writeObject(currentState);
			this.oos.flush();
			this.oos.reset();
			System.out.println("Done sending!");
		}
		
		/**
		 * Sends the clients its ID.
		 * @param id the ID of the client, provided by the server
		 */
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
		PaintServer paintServ = new PaintServer(9873);
		System.out.println("Accepting clients...");
		paintServ.acceptClientLoop();
		//chatServ.cleanStreams();
	}
}
