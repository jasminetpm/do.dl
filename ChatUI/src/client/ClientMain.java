package client;

import java.io.IOException;
import java.net.UnknownHostException;

import chatUI.MainWindow;
import controller.SetPort;

public class ClientMain {

	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException
	{
		MyClient mc = new MyClient("127.0.0.1", SetPort.port, new MainWindow());
		mc.sendMessage();
	
		
	}
}
