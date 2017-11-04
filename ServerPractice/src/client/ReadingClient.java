package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class ReadingClient extends Thread {

	private MyClient client;
	private PrintWriter pw;
	private BufferedReader br;
	
	public ReadingClient(MyClient client)
	{
		this.client = client;
		this.pw = this.client.getPW();
		this.br = this.client.getBR();
		
	}
	
	@Override
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
				}
				
			}
			this.client.clean();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
