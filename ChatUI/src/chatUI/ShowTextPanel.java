package chatUI;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import chatUI.MainWindow;
import javafx.scene.paint.Color;

public class ShowTextPanel extends JPanel 
{
	private MainWindow mw;
	private JScrollPane jsp;
	private JTextArea text;

	public ShowTextPanel(MainWindow mw) {
		// TODO Auto-generated constructor stub
		this.mw = mw;
		this.text = new JTextArea();
		this.text.setPreferredSize(new Dimension(230,350));
		this.text.setEditable(false);
		this.text.setText("test");
		this.jsp = new JScrollPane(text);
		this.jsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.add(this.jsp);
		
	}
	
	public void textAdded(String message)
	{
		String originalText = this.text.getText();
		this.text.setText(originalText + '\n' + message); 
		this.text.repaint();
		
	}

}
