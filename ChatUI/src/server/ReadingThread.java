package server;

import java.io.*;
import java.util.*;

public class ReadingThread extends Thread
{
	private BufferedReader incomingText;
	private ClientThread ct;
	private OOPServer server;

	public ReadingThread(BufferedReader br, ClientThread ct)
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
				for (i = 0; i < this.server.getClientList().size(); i++)
				{
					if (this.server.getClientList().get(i).equals(this.ct))
					{
						
					}
					else
					{
						this.server.getClientList().get(i).sendMessage(inc);
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