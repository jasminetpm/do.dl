package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;

import javax.swing.JTextArea;

import chatUI.MainWindow;
import chatUI.SendTextPanel;
import gui.AllCommentPane;
import gui.PaintWindow;
import serverDealer.SendComment;
import serverDealer.SendString;

public class CommentListener implements ActionListener{
	private AllCommentPane pw;
	private JTextArea jta;
	private int clickCount = 0;
	
	public CommentListener (AllCommentPane pw, JTextArea jta)
	{
		this.pw = pw;
		this.jta = jta;
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		
	System.out.println(this.clickCount);
	System.out.println(this.pw.getPW().getCommentCount());
	
		if (this.clickCount <= this.pw.getPW().getCommentCount() && this.clickCount <= this.pw.getPW().getCommentCircleCount() && this.pw.getPW().getPrintFlag() == 1)
		{
			try 
			{
				String message = this.jta.getText();
				
				
				checkMessage(message);
				try 
				{
					new SendComment(this.pw, message);
					
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				this.jta.setText(null);
				this.jta.repaint();
				this.pw.repaint();
				this.pw.getCP().textAdded(message);
				this.pw.incrementMessageCounter();
				this.pw.updateMyLastWords(message);
			} catch (NullPointerException ex)
			{
				System.err.println("Can't send empty message");
			}
			this.clickCount++;
			this.pw.getPW().setCommentCount(this.clickCount);
			this.pw.getPW().setPrintFlag(0);
		}
		else if (this.clickCount > this.pw.getPW().getCommentCount())
		{
			this.pw.getCP().textReceived("You did not select the comment button in the tool bar");
		}
		else 
		{
			this.pw.getCP().textReceived("You have to choose on the canvas where you want to comment on first");
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
