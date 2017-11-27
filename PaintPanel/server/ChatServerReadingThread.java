package server;

import java.io.*;
import java.util.*;

/**
 * generate a reading thread to deal with information sent to the server
 * @author Coco
 * @version 1.0 Nov 2017
 */
public class ChatServerReadingThread extends Thread
{
	/**
	 * the incomingText read by BufferedReader
	 */
	private BufferedReader incomingText;
	/**
	 * the chatServerClientThread associated
	 */
	private ChatServerClientThread ct;
	/**
	 * the server it uses
	 */
	private PaintServer server;

	/**
	 * constructor
	 * @param br: the incomingText read by BufferedReader
	 * @param ct: the chatServerClientThread associated
	 */
	public ChatServerReadingThread(BufferedReader br, ChatServerClientThread ct)
	{
		this.incomingText = br;
		this.ct = ct;
		this.server = this.ct.getServer();
	}

	@Override
	/**
	 * run the thread
	 */
	public void run()
	{
		String inc = "";
		try {
			
			while((inc = this.incomingText.readLine()) != null)
			{
				int i;
				for (i = 0; i < this.server.getChatClientList().size(); i++)
				{
					if (this.server.getChatClientList().get(i).equals(this.ct))
					{
						
					}
					else
					{
						this.server.getChatClientList().get(i).sendMessage(inc);
						System.out.println("I am sending out the message to client " + i + "\n");
					}
				}
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		System.out.println("Connection was closed!");

	}
}