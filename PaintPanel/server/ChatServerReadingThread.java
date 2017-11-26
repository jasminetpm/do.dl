package server;

import java.io.*;
import java.util.*;

public class ChatServerReadingThread extends Thread
{
	private BufferedReader incomingText;
	private ChatServerClientThread ct;
	private PaintServer server;

	public ChatServerReadingThread(BufferedReader br, ChatServerClientThread ct)
	{
		this.incomingText = br;
		this.ct = ct;
		this.server = this.ct.getServer();
	}

	@Override
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