package ysc3232.server;

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
				System.out.println("I received the following message: " + inc);
				//send a confirmation that a text is received
				this.ct.sendConfirmation();
				//broadcast to everyone but the sender
				int i;
				for (i = 0; i < this.server.getClientList().size(); i++)
				{
					if (this.server.getClientList().get(i).equals(this.ct.getClient()))
					{
						
					}
					else
					{
						this.ct.sendMessage(inc);
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