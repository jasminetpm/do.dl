package listeners;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.UnsupportedEncodingException;

import javax.swing.JTextArea;
import chatUI.MainWindow;
import gui.AllCommentPane;
import serverDealer.SendComment;
import serverDealer.SendString;

public class EnterKeyListenerComment implements KeyListener {
	private AllCommentPane mw;
	private JTextArea jta;
	
	public EnterKeyListenerComment(AllCommentPane mw, JTextArea jta)
	{
		this.mw = mw;
		this.jta = jta;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER)
		{
			try
			{
				String message = this.jta.getText();
				checkMessage(message);
				try 
				{
					new SendComment(this.mw, message);
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				}
				this.mw.getCP().textAdded(message);
				this.mw.incrementMessageCounter();
				this.mw.updateMyLastWords(message);
			} catch (NullPointerException ex)
			{
				System.err.println("Can't send empty message");
			}
			
		}
		
	}
	
	public void checkMessage(String input) throws NullPointerException
	{
		if (input.equals(""))
		{
			throw new NullPointerException();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER)
		{
			this.jta.setText(null);
			this.jta.repaint();
		}
	}

}