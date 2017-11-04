package client;

import java.io.IOException;
import java.net.UnknownHostException;

public class ClientMain2 {
	
	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException
	{
		MyClient mc2 = new MyClient("127.0.0.1", 1679);
		mc2.setName("Client B");
		mc2.sendMessage();
	}
	
	

}
