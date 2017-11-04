package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class PaintWindow extends JFrame {
	
	private JPanel toolbar;
	private JButton[] toolbarButtons = new JButton[7];
	private Dimension WINDOW_SIZE = new Dimension(700, 540);
	
	//Icons
	private ImageIcon brush = new ImageIcon("/Users/user/Documents/Do.dl_repo/PaintPanel/src/ic_brush_black_24dp_1x.png");
	private ImageIcon eraser = new ImageIcon("/Users/user/Documents/Do.dl_repo/PaintPanel/src/double-sided-eraser.png");
	private ImageIcon text = new ImageIcon("/Users/user/Documents/Do.dl_repo/PaintPanel/src/ic_text_fields_black_24dp_1x.png");
	private ImageIcon comment = new ImageIcon("/Users/user/Documents/Do.dl_repo/PaintPanel/src/ic_comment_black_24dp_1x.png");
	private ImageIcon undo = new ImageIcon("/Users/user/Documents/Do.dl_repo/PaintPanel/src/ic_undo_black_24dp_1x.png");
	private ImageIcon upload = new ImageIcon("/Users/user/Documents/Do.dl_repo/PaintPanel/src/ic_file_upload_black_24dp_1x.png");
	private ImageIcon download = new ImageIcon("/Users/user/Documents/Do.dl_repo/PaintPanel/src/ic_file_download_black_24dp_1x.png");
	private final ImageIcon[] TOOL_LIST = { brush, text, eraser, comment, undo, upload, download };
	
	public PaintWindow()
	{
		this.setLayout(new BorderLayout());
		this.toolbar = new JPanel();
		this.populateToolbar();
		this.add(this.toolbar, BorderLayout.LINE_START);
		// Fixing window dimensions
		this.setPreferredSize(WINDOW_SIZE);;
		this.setResizable(false);
		// Default operations
		this.pack();
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	private void populateToolbar()
	{
		this.toolbar.setLayout(new GridLayout(5, 1));
		for (int i = 0; i < toolbarButtons.length; i++)
		{
			toolbarButtons[i] = new JButton(TOOL_LIST[i]);
			this.toolbar.add(toolbarButtons[i]);
		}
	}
	
	public static void main (String args[])
	{
		PaintWindow pw = new PaintWindow();
	}

}
