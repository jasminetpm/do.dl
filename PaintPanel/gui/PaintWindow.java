package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
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
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.AbstractSpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.colorchooser.AbstractColorChooserPanel;

import chatUI.MainWindow;
import listeners.ColorCancelListener;
import listeners.ColorOKListener;
import listeners.ColorPickerListener;
import listeners.LayerSelectorListener;
import listeners.StrokeSizeListener;
import listeners.ToolSelectorListener;
import model.Instruction;

public class PaintWindow extends JFrame {
	// Panels
	private Dimension WINDOW_SIZE = new Dimension(960, 540);
	private static DoodlePanel paintPanel;
	private MainWindow chatPanel;
	private JPanel toolbar;
	private JColorChooser jcc;
	private JDialog colorDialog;
	// Tool buttons
	private JButton[] toolbarButtons = new JButton[7];
	private JButton colorButton;
	private JSpinner strokeSizeSelector;
	private JSpinner layerSelector;
	// Default values
	private SpinnerNumberModel strokeValues = new SpinnerNumberModel(5,
			                                                         1, // minimum value
			                                                         10, // maximum value
			                                                         1); // step value
	private SpinnerNumberModel layerValues = new SpinnerNumberModel(1,
                                                                    1, // minimum value
                                                                    3, // maximum value
                                                                    1); // step value
	private int strokeSize = 5;
	private int toolType = 0;
	private int currentLayer = 0;
	private Color currentColor = Color.RED;
	// Button icons
	private Icon brush = new ImageIcon("imagesource/ic_brush_black_24dp_1x.png");
	private Icon eraser = new ImageIcon("imagesource/double-sided-eraser.png");
	private Icon text = new ImageIcon("imagesource/ic_text_fields_black_24dp_1x.png");
	private Icon comment = new ImageIcon("imagesource/ic_comment_black_24dp_1x.png");
	private Icon undo = new ImageIcon("imagesource/ic_undo_black_24dp_1x.png");
	private Icon upload = new ImageIcon("imagesource/ic_file_upload_black_24dp_1x.png");
	private Icon download = new ImageIcon("imagesource/ic_file_download_black_24dp_1x.png");
	private final Icon[] TOOL_LIST = { brush, eraser, text, comment, undo, upload, download };
	// Component icons
	private Icon layerIcon = new ImageIcon("imagesource/ic_layers_black_24dp_1x.png");
	private Icon strokeSizeIcon = new ImageIcon("imagesource/ic_line_weight_black_24dp_1x.png");
	private Icon colorIcon = new ImageIcon("imagesource/ic_color_lens_black_24dp_1x.png");
	private JLabel layerLabel = new JLabel(layerIcon);
	private JLabel strokeSizeLabel = new JLabel(strokeSizeIcon);
	private JLabel colorLabel = new JLabel(colorIcon);
	// Listeners
	private ColorPickerListener colorListener;
	
	private PaintClient paintClient;
	private static int clientId;
	
	public PaintWindow(String ip, int port) throws UnknownHostException, IOException, InterruptedException
	{
		// Creating connection to server
		this.paintClient = new PaintClient(ip, port);
		this.paintClient.start();
		// Instantiating listeners
		this.colorListener = new ColorPickerListener(this);	
		// Instantiating JPanels
		this.setLayout(new BorderLayout());
		this.toolbar = new JPanel();		
		PaintWindow.paintPanel = new DoodlePanel(this);
		this.chatPanel = new MainWindow("127.0.0.1");
		this.populateToolbar(); // Adds various buttons to toolbar
		this.createColorChooser();
		// Adding JPanels to JFrame
		this.add(PaintWindow.paintPanel, BorderLayout.CENTER);
		this.add(this.toolbar, BorderLayout.LINE_START);
		this.add(this.chatPanel, BorderLayout.LINE_END);
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
		// Setting toolbar buttons
		this.toolbar.setLayout(new GridLayout(0, 1));
		for (int i = 0; i < toolbarButtons.length; i++)
		{
			toolbarButtons[i] = new JButton(TOOL_LIST[i]);
			if (i == 0 || i == 1) {
				toolbarButtons[i].addActionListener(new ToolSelectorListener(i, this));
			}
			this.toolbar.add(toolbarButtons[i]);
		}
		// Setting layer selector
		JPanel layerToolSection = new JPanel(new BorderLayout());
		this.layerSelector = new JSpinner(this.layerValues);
		this.layerSelector.addChangeListener(new LayerSelectorListener(this));
		layerToolSection.add(this.layerSelector, BorderLayout.CENTER);
		layerToolSection.add(this.layerLabel, BorderLayout.LINE_START);
		this.toolbar.add(layerToolSection);
		// Setting stroke size selector
		JPanel strokeSizeSection = new JPanel(new BorderLayout());
		this.strokeSizeSelector = new JSpinner(this.strokeValues);
		this.strokeSizeSelector.addChangeListener(new StrokeSizeListener(this));
		strokeSizeSection.add(this.strokeSizeSelector, BorderLayout.CENTER);
		strokeSizeSection.add(this.strokeSizeLabel, BorderLayout.LINE_START);
		this.toolbar.add(strokeSizeSection);
		// Setting color chooser
		JPanel colorSection = new JPanel(new BorderLayout());
		this.colorButton = new JButton();
		this.setColorChooserIcon();
		this.colorButton.addActionListener(this.colorListener);
		colorSection.add(this.colorButton, BorderLayout.CENTER);
		colorSection.add(this.colorLabel, BorderLayout.LINE_START);
		this.toolbar.add(colorSection);
	}
	
	private void createColorChooser()
	{
		this.jcc = new JColorChooser();
		AbstractColorChooserPanel[] panels = this.jcc.getChooserPanels();
		for (int i = 0; i < panels.length; i++) // Removing unwanted color chooser panels
		{
			String panelName = panels[i].getClass().getName();
			if (panelName.equals("javax.swing.colorchooser.ColorChooserPanel"))
			{
				this.jcc.removeChooserPanel(panels[i]);
			} 
		}
		this.colorDialog = JColorChooser.createDialog(this, 
                                                      "Pick your colour", 
                                                      true, 
                                                      this.jcc, 
                                                      new ColorOKListener(this, this.jcc), 
                                                      new ColorCancelListener(this));
	}
	
	public void viewColorChooser()
	{
		this.colorDialog.setVisible(true);
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
		BufferedImage colorBox = new BufferedImage(25, // Dimensions hard coded
											  	  25,
											  	  BufferedImage.TYPE_INT_RGB);
		Graphics2D g = colorBox.createGraphics();
		g.setColor(this.currentColor);
		g.fillRect(0, 0, 25, 25);
		this.colorButton.setIcon(new ImageIcon(colorBox));
	}
	
	public void sendInstruction(Instruction instr) {
		this.paintClient.sendInstruction(instr);
	}
	
	public DoodlePanel getDoodlePanel() {
		return PaintWindow.paintPanel;
	}
	
	public void setToolType(int type) {
		this.toolType = type;
	}
	
	public int getToolType() {
		return this.toolType;
	}
	
	public void setCurrentLayer(int layer) {
		this.currentLayer = layer;
	}
	
	public int getCurrentLayer() {
		return this.currentLayer;
	}
	
	public void setClientId(int id) {
		this.clientId = id;
	}
	
	public int getClientId() {
		return this.clientId;
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
			Object message;
			try {
				this.ois = new ObjectInputStream(this.is);
				while ((message = this.ois.readObject()) != null) {
					if (message instanceof Integer) {
						System.out.println("Got ID: " + message);
						PaintWindow.clientId = ((Integer) message).intValue();
					} else {
						Instruction instr = (Instruction) message;
						System.out.println("Received instruction:");
						PaintWindow.paintPanel.executeInstruction(instr);							
					}
				}
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Server has disconnected");
			}
		}
	}
	
	public static void main (String args[]) throws UnknownHostException, IOException, InterruptedException
	{
		PaintWindow pw = new PaintWindow("127.0.0.1", 9876);
	}

}