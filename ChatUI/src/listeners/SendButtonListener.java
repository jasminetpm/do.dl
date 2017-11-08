package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import chatUI.MainWindow;
import chatUI.SendTextPanel;
import serverDealer.SendString;
import serverDealer.SendString;

public class SendButtonListener implements ActionListener{
	private MainWindow mw;
	private SendTextPanel stp;
	
	public SendButtonListener(MainWindow mw)
	{
		this.mw = mw;
		this.stp = this.mw.getSendTP();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String message = this.stp.getJTA().getText();
		System.out.println(message);
		new SendString(this.mw, message);
		this.stp.getJTA().setText(null);
		this.stp.getJTA().repaint();
	}

}
