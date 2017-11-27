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

/**
 * represents the chatPanel with chatClient initiated
 * @author Coco
 * @version 1.0, Nov 2017
 *
 */

public class MainWindow extends JPanel
{
	/**
	 * stores the SendTextPanel which locates the textbox listening to incoming text chat and the sendButton
	 */
	private SendTextPanel sendp;
	/**
	 * stores the ShowTextPanel which locates an uneditable textBox to show chats
	 */
	private ShowTextPanel showp;
	
	//private TitlePanel tp;
	
	/**
	 * Stores a name the user keyed in for herself
	 */
	private String userName;
	/**
	 * Stores the JOptionPane where user puts her name in
	 */
	private JOptionPane chooseUserName;
	
	/**
	 * Stores the messageCounter which helps determine whether to show the text received
	 */
	private int messageCounter = 0;
	/**
	 * Stores the last words of the user before message gets sent
	 */
	private String myLastWords = "";
	
	/**
	 * Stores the name of the MyWindow itself which by default is the same as the user name
	 * only used in testing
	 */
	public static String name;
	/**
	 * Stores the client for the window. It works with chat
	 */
	private MyClient mc;
	/**
	 * Stores the ip address used to initiate the client
	 */
	private String ip;
	private static final Dimension CHAT_PANEL_DIMENSIONS = new Dimension(260, 640);
	
	
	/**
	 * Constructor for MainWindow
	 * @param ip: ip address used to initialise the client
	 * @param port: port used to initialise the client
	 * @throws UnknownHostException
	 * @throws IOException
	 * @throws InterruptedException
	 */
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
	
	
	/**
	 * getter for ShowTextPanel associated with the MainWindow
	 * @return ShowTextPanel associated with the MainWindow
	 */
	public ShowTextPanel getSTP()
	{
		return this.showp;
	}
	
	/**
	 * getter for SendTextPanel associated with the MainWindow
	 * @return SendTextPanel associated with the MainWindow
	 */
	public SendTextPanel getSendTP()
	{
		return this.sendp;
	}
	
	/**
	 * getter for the user name
	 * @return user name
	 */
	public String getUserName()
	{
		return this.userName;
	}
	
	/**
	 * increments the messageCounter by 1
	 */
	public void incrementMessageCounter()
	{
		this.messageCounter++;
	}
	
	/**
	 * getter for the messageCounter
	 * @return messageCounter
	 */
	public int getMessageCounter()
	{
		return this.messageCounter;
	}
	
	/**
	 * update this.myLastWords to be the message 
	 * @param message: my new myLastWords
	 */
	public void updateMyLastWords(String message)
	{
		this.myLastWords = "<" + this.userName + ">: " + message;
	}
	
	/**
	 * getter for myLastWords
	 * @return myLastWords 
	 */
	public String getMyLastWords()
	{
		return this.myLastWords;
	}
	
}