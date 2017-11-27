package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;

import javax.swing.JTextArea;

import chatUI.MainWindow;
import chatUI.SendTextPanel;
import serverDealer.SendString;

public class SendButtonListener implements ActionListener{
	private MainWindow mw;
	private JTextArea jta;
	
	/**
	 * Constructs an instance of the listener in the given JPanel and JTextArea.
	 * @param mw the current JPanel
	 * @param jta the JTextArea in which the message is being entered
	 */
	public SendButtonListener(MainWindow mw, JTextArea jta)
	{
		this.mw = mw;
		this.jta = jta;
	}

	@Override
	/**
	 * Sends the String in the text field when the given button is pressed if there is a message typed.
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
	
	/**
	 * Checks the given strings and throws an exception if the string is empty.
	 * @param input the given string to be checked
	 * @throws NullPointerException if the string is empty
	 */
	public void checkMessage(String input) throws NullPointerException
	{
		if (input.equals(""))
		{
			throw new NullPointerException();
		}
	}

}
