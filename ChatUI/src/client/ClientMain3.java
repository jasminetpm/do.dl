package client;

import java.io.IOException;
import java.net.UnknownHostException;

import chatUI.MainWindow;
import controller.SetPort;

public class ClientMain3 {

	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException
	{
		MyClient mc3 = new MyClient("127.0.0.1", SetPort.port, new MainWindow());
		mc3.sendMessage();
	}
}
