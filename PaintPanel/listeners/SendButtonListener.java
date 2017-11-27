package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;

import javax.swing.JTextArea;

import chatUI.MainWindow;
import chatUI.SendTextPanel;
import serverDealer.SendString;

/**
 * listens to the send button for the chat
 * @author Coco
 * @version 1.0 Nov 2017
 */
public class SendButtonListener implements ActionListener{
	/**
	 * the MainWindow (i.e. the chatPanel) 
	 */
	private MainWindow mw;
	/**
	 * the JTextArea the chat messages are typed in
	 */
	private JTextArea jta;
	
	/**
	 * the constructor 
	 * @param mw: the MainWindow (i.e. the chatPanel)
	 * @param jta: the JTextArea the chat messages are typed in
	 */
	public SendButtonListener(MainWindow mw, JTextArea jta)
	{
		this.mw = mw;
		this.jta = jta;
	}

	@Override
	/**
	 * what to do if the sned button is clicked
	 */
	public void actionPerformed(ActionEvent e) 
	{
		try 
		{
			String message = this.jta.getText();
			checkMessage(message);
			try 
			{
				new SendString(this.mw, message);
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			this.jta.setText(null);
			this.jta.repaint();
			this.mw.getSTP().textAdded(message);
			this.mw.incrementMessageCounter();
			this.mw.updateMyLastWords(message);
		} catch (NullPointerException ex)
		{
			System.err.println("Can't send empty message");
		}
		
	}
	

	public void checkMessage(String input) throws NullPointerException
	{
		if (input.equals(""))
		{
			throw new NullPointerException();
		}
	}

}
