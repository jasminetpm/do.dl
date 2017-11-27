package gui;

import java.awt.BorderLayout;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import client.CommentClient;

public class AllCommentPane extends JPanel{
	private PaintWindow pw;
	private CommentPanel cp;
	private SendCommentPanel scp;
	private CommentClient cc;
	private int messageCounter = 0;
	private String myLastWords = "";
	
	public AllCommentPane(PaintWindow pw, String ip, int port) throws UnknownHostException, IOException
	{
		this.cc = new CommentClient(ip, port, this);
		this.cc.start();
		this.pw = pw;
		this.cp = new CommentPanel(this);
		this.scp = new SendCommentPanel(this);
		
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.add(this.cp, BorderLayout.EAST);
		this.add(this.scp, BorderLayout.WEST);
		
	}

	public void incrementMessageCounter()
	{
		this.messageCounter++;
	}
	
	public int getMessageCounter()
	{
		return this.messageCounter;
	}
	
	public void updateMyLastWords(String message)
	{
		this.myLastWords = "Comment " + this.cp.getCounter() +": " + "<" + this.pw.getMw().getUserName() + ">: " + this.myLastWords;
	}
	
	public String getMyLastWords()
	{
		return this.myLastWords;
	}
	
	public CommentPanel getCP()
	{
		return this.cp;
	}
	
	public PaintWindow getPW()
	{
		return this.pw;
	}
	
	public SendCommentPanel getSCP()
	{
		return this.scp;
	}
}
