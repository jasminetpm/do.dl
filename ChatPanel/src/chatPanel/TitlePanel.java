package chatPanel;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TitlePanel extends JPanel
{
	private MainWindow mw;
	private JTextField titleField;
	
	public TitlePanel(MainWindow mw)
	{
		this.mw = mw;
		this.titleField = new JTextField("Title");
	}
	
}
