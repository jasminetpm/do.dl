package serverDealer;

import java.io.IOException;
import java.net.UnknownHostException;

import chatUI.MainWindow;

public class Main {
	
	public static void main (String args[]) throws UnknownHostException, IOException, InterruptedException
	{
		MainWindow mw = new MainWindow("127.0.0.1");
	}

}
