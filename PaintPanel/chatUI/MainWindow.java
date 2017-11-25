package chatUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import client.MyClient;
import controller.SetPort;


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
	
	
	public MainWindow(String ip) throws UnknownHostException, IOException, InterruptedException
	{
		this.ip = ip;
		this.mc = new MyClient(ip, SetPort.port, this);
		this.mc.start();
		
		//this.tp = new TitlePanel(this);
		this.showp = new ShowTextPanel(this);
		this.sendp = new SendTextPanel(this);
		
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		//this.add(this.tp, BorderLayout.PAGE_START);
		this.add(this.showp);
		this.add(this.sendp);
		
		this.chooseUserName = new JOptionPane("User Name");
		this.userName = this.chooseUserName.showInputDialog("Please name yourself:");
		this.name = this.userName;
		
		this.setBorder(BorderFactory.createEmptyBorder(1, 5, 1, 1));
		this.setPreferredSize(new Dimension(260, 540));
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