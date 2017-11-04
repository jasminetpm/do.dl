package client;

import java.io.IOException;
import java.net.UnknownHostException;

public class ClientMain {

	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException
	{
		MyClient mc = new MyClient("127.0.0.1", 1679);
		mc.setName("Client A");
		mc.sendMessage();
	
		
	}
}
