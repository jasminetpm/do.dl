package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * the reading client that keeps listens to the incoming streams
 * @author Coco
 * @version 1.0 Nov 2017
 */
public class ReadingClient extends Thread {

	/**
	 * the client it is associated with
	 */
	private MyClient client;
	/**
	 * the printWriter which writes to the server
	 */
	private PrintWriter pw;
	/**
	 * the bufferedReader which listens to incoming messages
	 */
	private BufferedReader br;
	
	/**
	 * constructor for ReadingClient
	 * @param client: the MyClient it is associated with
	 */
	public ReadingClient(MyClient client)
	{
		this.client = client;
		this.pw = this.client.getPW();
		this.br = this.client.getBR();
		
	}
	
	@Override
	/**
	 * initialise the thread
	 */
	public void run()
	{
		int nConfirmation = 0;
		
		String str = "";
		try {
			while ((str = this.br.readLine()) != null)
			{
				if (str.equals("confirmation!"))
				{
					nConfirmation++;
					System.out.println("Times the messages sent are confirmed: " + nConfirmation);
				}
				else
				{
					System.out.println("message received from the server: " + str);
					this.client.getMainWindow().getSTP().textReceived(str);
					System.out.println("sending message to the window " + this.client.getMainWindow().name + " \n");
				}
				
			}
			this.client.clean();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
