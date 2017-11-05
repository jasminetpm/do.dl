package chatPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;



public class MainWindow extends JFrame
{
	private ShowTextPanel stp;
	private TextboxPanel tbp;
	private TitlePanel tp;
	
	
	public MainWindow()
	{
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		this.stp = new ShowTextPanel(this);
		this.tbp = new TextboxPanel(this);
		this.tp = new TitlePanel(this);
		this.stp.setAlignmentX(CENTER_ALIGNMENT);
		this.add(this.tp);
		this.add(this.stp);
		this.add(this.tbp);
		this.setMinimumSize(new Dimension(260,540));
		this.tp.setSize(30, 90);
		this.stp.setSize(260, 360);
		this.tbp.setSize(260, 180);
		this.pack();
		this.setVisible(true);
	}
	
	
	public static void main (String args[])
	{
		MainWindow mw = new MainWindow();
	}
	
}


