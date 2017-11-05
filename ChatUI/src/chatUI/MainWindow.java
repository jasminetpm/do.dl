package chatUI;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

import chatUI.MainWindow;
import chatUI.ShowTextPanel;
import chatUI.SendTextPanel;
import chatUI.TitlePanel;

public class MainWindow extends JFrame
{
	private ShowTextPanel stp;
	private SendTextPanel tbp;
	private TitlePanel tp;
	
	
	public MainWindow()
	{
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		this.stp = new ShowTextPanel(this);
		this.tbp = new SendTextPanel(this);
		this.tp = new TitlePanel(this);
		this.stp.setAlignmentX(CENTER_ALIGNMENT);
		this.add(this.tp);
		this.add(this.stp);
		this.add(this.tbp);
		this.setMinimumSize(new Dimension(260,540));
		this.pack();
		this.setVisible(true);
	}
	
	
	public static void main (String args[])
	{
		MainWindow mw = new MainWindow();
	}
	
}
