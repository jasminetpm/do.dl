package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.AbstractSpinnerModel;
import javax.swing.SpinnerNumberModel;


import listeners.ColorPickerListener;
import model.Instruction;

public class PaintWindow extends JFrame {
	
	private static DoodlePanel paintPanel;
	private JPanel toolbar;
	private JButton[] toolbarButtons = new JButton[7];
	private Dimension WINDOW_SIZE = new Dimension(700, 540);
	private SpinnerNumberModel strokeValues = new SpinnerNumberModel(5,
			                                                         1, //min
			                                                         10, //max
			                                                         1); //step
	private JSpinner strokeSizeSelector;
	private int strokeSize = 5;
	private JButton colorPicker;
	private Color currentColor = Color.RED;
	//Icons
	private Icon brush = new ImageIcon("imagesource/ic_brush_black_24dp_1x.png");
	private Icon eraser = new ImageIcon("imagesource/double-sided-eraser.png");
	private Icon text = new ImageIcon("imagesource/ic_text_fields_black_24dp_1x.png");
	private Icon comment = new ImageIcon("imagesource/ic_comment_black_24dp_1x.png");
	private Icon undo = new ImageIcon("imagesource/ic_undo_black_24dp_1x.png");
	private Icon upload = new ImageIcon("imagesource/ic_file_upload_black_24dp_1x.png");
	private Icon download = new ImageIcon("imagesource/ic_file_download_black_24dp_1x.png");
	private final Icon[] TOOL_LIST = { brush, text, eraser, comment, undo, upload, download };
	//Listeners
	private ColorPickerListener colorListener;
	private PaintClient paintClient;
	
	public PaintWindow(String ip, int port) throws UnknownHostException, IOException
	{
		// Creating connection to server
		this.paintClient = new PaintClient(ip, port);
		this.paintClient.start();
		
		// Instantiating listeners
		this.colorListener = new ColorPickerListener(this);
		// Instantiating and adding JPanels
		this.setLayout(new BorderLayout());
		this.toolbar = new JPanel();		
		PaintWindow.paintPanel = new DoodlePanel(this);
		this.add(PaintWindow.paintPanel, BorderLayout.CENTER);
		this.populateToolbar();
		this.add(this.toolbar, BorderLayout.LINE_START);
		// Fixing window dimensions
		this.setPreferredSize(WINDOW_SIZE);;
		this.setResizable(false);
		
		// Default JFrame operations
		this.pack();
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	private void populateToolbar()
	{
		this.toolbar.setLayout(new GridLayout(0, 1));
		for (int i = 0; i < toolbarButtons.length; i++)
		{
			toolbarButtons[i] = new JButton(TOOL_LIST[i]);
			this.toolbar.add(toolbarButtons[i]);
		}
		this.strokeSizeSelector = new JSpinner(this.strokeValues);
		this.toolbar.add(this.strokeSizeSelector);
		this.colorPicker = new JButton();
		this.setColorChooserIcon();
		this.colorPicker.addActionListener(this.colorListener);
		this.toolbar.add(this.colorPicker);
	}
	public int getStrokeSize()
	{
		return this.strokeSize;
	}
	
	public void setStrokeSize(int i)
	{
		this.strokeSize = i;
	}
	
	public Color getColor()
	{
		return this.currentColor;
	}
	
	public void setColor(Color C)
	{
		this.currentColor = C;
	}
	
	public void setColorChooserIcon()
	{
		this.colorPicker.setOpaque(true);
		this.colorPicker.setBackground(this.currentColor);
	}
	
	public void sendInstruction(Instruction instr) {
		this.paintClient.sendInstruction(instr);
	}
	
	// I declare the PaintClient class here to provide easy access to the parent fields
	class PaintClient extends Thread {
		private Socket mySocket;
		private OutputStream os;
		private ObjectOutputStream oos;
		private InputStream is;
		private ObjectInputStream ois;

		public PaintClient(String ip, int port) throws UnknownHostException, IOException 
		{
			this.mySocket = new Socket(ip, port);
			this.os = this.mySocket.getOutputStream();
			this.oos = new ObjectOutputStream(this.os);
			this.is = this.mySocket.getInputStream();
		}
		
		public void sendInstruction(Instruction instr) 
		{
			try {
				this.oos.writeObject(instr);
				this.oos.flush();
				this.oos.reset();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		@Override
		public void run() 
		{
			// listen for incoming messages
			Instruction instr;
			try {
				this.ois = new ObjectInputStream(this.is);
				while ((instr = (Instruction) this.ois.readObject()) != null) {
					System.out.println("Received instruction:");
					PaintWindow.paintPanel.executeInstruction(instr);
				}
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void main (String args[]) throws UnknownHostException, IOException
	{
		PaintWindow pw = new PaintWindow("127.0.0.1", 9876);
	}

}
