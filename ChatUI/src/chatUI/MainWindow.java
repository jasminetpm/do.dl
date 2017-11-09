package chatUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import client.MyClient;
import controller.SetPort;


public class MainWindow extends JFrame
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
		
		this.tp = new TitlePanel(this);
		this.showp = new ShowTextPanel(this);
		this.sendp = new SendTextPanel(this);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		this.getContentPane().add(this.tp, BorderLayout.PAGE_START);
		this.getContentPane().add(this.showp, BorderLayout.CENTER);
		this.getContentPane().add(this.sendp,BorderLayout.PAGE_END);
		
		this.chooseUserName = new JOptionPane("User Name");
		this.userName = this.chooseUserName.showInputDialog("Please name yourself:");
		this.name = this.userName;
		
		this.setMinimumSize(new Dimension(260,540));
		this.pack();
		this.setVisible(true);
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