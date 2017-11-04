package client;

import java.io.IOException;
import java.net.UnknownHostException;

public class ClientMain3 {

	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException
	{
		MyClient mc3 = new MyClient("127.0.0.1", 1679);
		mc3.setName("Client C");
		mc3.sendMessage();
	}
}
