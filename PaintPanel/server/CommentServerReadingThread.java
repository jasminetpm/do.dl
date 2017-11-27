package server;

import java.io.BufferedReader;
import java.io.IOException;

public class CommentServerReadingThread extends Thread
{
	private BufferedReader incomingText;
	private CommentServerClientThread ct;
	private PaintServer server;

	public CommentServerReadingThread(BufferedReader br, CommentServerClientThread ct)
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
			System.out.println("comment reading initiated");
				int i;
				for (i = 0; i < this.server.getCommentClientList().size(); i++)
				{
					if (this.server.getCommentClientList().get(i).equals(this.ct))
					{
						
					}
					else
					{
						this.server.getCommentClientList().get(i).sendMessage(inc);
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


