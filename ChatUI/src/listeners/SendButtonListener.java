package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;

import javax.swing.JTextArea;

import chatUI.MainWindow;
import chatUI.SendTextPanel;
import serverDealer.SendString;
import serverDealer.SendString;

public class SendButtonListener implements ActionListener{
	private MainWindow mw;
	private JTextArea jta;
	
	public SendButtonListener(MainWindow mw, JTextArea jta)
	{
		this.mw = mw;
		this.jta = jta;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String message = this.jta.getText();
		System.out.println(message);
		try {
			new SendString(this.mw, message);
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		this.jta.setText(null);
		this.jta.repaint();
		this.mw.getSTP().textAdded(message);
	}

}
