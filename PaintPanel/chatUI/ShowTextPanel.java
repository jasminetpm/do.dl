package chatUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.text.DefaultCaret;

import chatUI.MainWindow;

/**
 * the ShowTextPanel where the chat histories are shown
 * @author Coco
 * @version 1.0 Nov 2017
 */
public class ShowTextPanel extends JPanel 
{
	/**
	 * the MainWindow on which it is initialised
	 */
	private MainWindow mw;
	/**
	 * enables scrolling
	 */
	private JScrollPane jsp;
	/**
	 * the JTextArea where the chat history is shown
	 */
	private JTextArea text;

	/**
	 * constructor
	 * @param mw: the MainWindow on which the ShowTextPanel is initialised
	 */
	public ShowTextPanel(MainWindow mw) 
	{
		this.mw = mw;
		this.text = new JTextArea();
		this.text.setFont(new Font("Monaco", Font.PLAIN, 12));
		this.text.setEditable(false);
		// Shifts scroll to bottom of page whenever new message received
		DefaultCaret caret = (DefaultCaret) this.text.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		// Places JTextArea in JScrollPane
		this.jsp = new JScrollPane(text);
		this.jsp.setPreferredSize(new Dimension(260, 470));
		this.text.setLineWrap(true);
		this.jsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.add(this.jsp);
		
	}
	
	/**
	 * show in JTextArea the input in format
	 * @param message: the input needs to be shown
	 */
	public void textAdded(String message)
	{
		message = "<" + this.mw.getUserName() + ">: " + message;
		String originalText = this.text.getText();
		this.text.setText(originalText + '\n' + '\n' + message); 
		this.text.repaint();
	}
	
	/**
	 * called locally. show in TextArea the input in a local-user format
	 * @param message: input message received
	 */
	public void textReceived(String message)
	{
		String originalText = this.text.getText();
		this.text.setText(originalText + '\n' + '\n' + message); 
		this.text.repaint();
	}
	
	/**
	 * getter for the JTextArea
	 * @return JTextArea
	 */
	public JTextArea getJTA()
	{
		return this.text;
	}

}
