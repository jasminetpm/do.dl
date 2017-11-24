package chatUI;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.DefaultCaret;

import chatUI.MainWindow;

public class ShowTextPanel extends JPanel 
{
	private MainWindow mw;
	private JScrollPane jsp;
	private JTextArea text;

	public ShowTextPanel(MainWindow mw) {
		// TODO Auto-generated constructor stub
		this.mw = mw;
		this.text = new JTextArea();
		this.text.setFont(new Font("Monaco", Font.PLAIN, 12));
		this.text.setEditable(false);
		DefaultCaret caret = (DefaultCaret) this.text.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
//		this.text.setText("test");
		this.jsp = new JScrollPane(text);
		this.jsp.setPreferredSize(new Dimension(260, 470));
		this.text.setLineWrap(true);
		this.jsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.add(this.jsp);
		
	}
	
	public void textAdded(String message)
	{
		message = "<" + this.mw.getUserName() + ">: " + message;
		String originalText = this.text.getText();
		this.text.setText(originalText + '\n' + '\n' + message); 
		this.text.repaint();
	}
	
	public void textReceived(String message)
	{
		String originalText = this.text.getText();
		this.text.setText(originalText + '\n' + '\n' + message); 
		this.text.repaint();
	}
	
	public JTextArea getJTA()
	{
		return this.text;
	}

}
