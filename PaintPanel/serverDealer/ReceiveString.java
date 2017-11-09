package serverDealer;

import chatUI.MainWindow;

public class ReceiveString extends Thread {
	private String message;
	private MainWindow mw;

	@Override
	public void run()
	{}
	
	public ReceiveString(String message, MainWindow mw)
	{
		this.message = message;
		this.mw = mw;
		this.mw.getSTP().textAdded(this.message);
	}
}
