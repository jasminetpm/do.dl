package chatUI;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class MainWindow extends JFrame
{
	private SendTextPanel sendp;
	private ShowTextPanel showp;
	private TitlePanel tp;
	
	private String userName;
	private JOptionPane chooseUserName;
	
	public MainWindow()
	{
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
		
		this.setMinimumSize(new Dimension(260,540));
		this.pack();
		this.setVisible(true);
	}
	
	public static void main (String args[])
	{
		MainWindow mw = new MainWindow();
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
	
}