package chatUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import client.MyClient;


public class MainWindow extends JPanel
{
	private SendTextPanel sendp;
	private ShowTextPanel showp;
	private TitlePanel tp;
	
	private String userName;
	private JOptionPane chooseUserName;
	
	private int messageCounter = 0;
	private String myLastWords = "";
	
	public static String name;
	private MyClient mc;
	private String ip;
	private static final Dimension CHAT_PANEL_DIMENSIONS = new Dimension(260, 640);
	
	
	public MainWindow(String ip, int port) throws UnknownHostException, IOException, InterruptedException
	{
		this.ip = ip;
		this.mc = new MyClient(ip, port, this);
		this.mc.start();
		
		//this.tp = new TitlePanel(this);
		this.showp = new ShowTextPanel(this);
		this.sendp = new SendTextPanel(this);
		
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		//this.add(this.tp, BorderLayout.PAGE_START);
		this.add(this.showp);
		this.add(Box.createRigidArea(new Dimension(0, 5)));
		this.add(this.sendp);
		
		while(true) // while loop makes program re-run until a username is entered or CANCEL button is hit
		{
			Object returnVal = JOptionPane.showInputDialog("Please name yourself:");
			if (returnVal == null)
			{
				System.exit(0);
			} else if(returnVal.equals(""))
			{
				System.out.println("No username entered.");
			} else
			{
				this.userName = (String) returnVal;
				break;
			}
		}
		
		this.setBorder(BorderFactory.createEmptyBorder(1, 5, 1, 1));
		this.setPreferredSize(CHAT_PANEL_DIMENSIONS);
	}
	
	
	public ShowTextPanel getSTP()
	{
		return this.showp;
	}
	
	public SendTextPanel getSendTP()
	{
		return this.sendp;
	}
	
	public String getUserName()
	{
		return this.userName;
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
		this.myLastWords = "<" + this.userName + ">: " + message;
	}
	
	public String getMyLastWords()
	{
		return this.myLastWords;
	}
	
}