package gui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.imageio.ImageIO;
import javax.swing.AbstractSpinnerModel;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.SpinnerNumberModel;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import chatUI.MainWindow;
import listeners.ColorCancelListener;
import listeners.ColorOKListener;
import listeners.ColorPickerListener;
import listeners.FontSizeListener;
import listeners.LayerSelectorListener;
import listeners.SaveButtonListener;
import listeners.StrokeSizeListener;
import listeners.ToolSelectorListener;
import model.CanvasState;
import model.CommentInstruction;
import model.Instruction;
import model.RemoveCommentInstruction;
import model.UndoInstruction;

public class PaintWindow extends JFrame {
	// Panels
	private Dimension WINDOW_SIZE = new Dimension(960, 640);
	private Dimension TOOLBAR_SIZE = new Dimension(30, 640);
	private static DoodlePanel paintPanel;
	private MainWindow chatPanel;
	private JPanel toolbar;
	private JPanel centerWrapper;
	private JColorChooser jcc;
	private JDialog colorDialog;
	private JFileChooser fileChooser;
	private static CommentDisplayPane commentDisplay;
	private CommentDialog commentEntry;
	// Tool buttons
	private JButton[] toolbarButtons = new JButton[10];
	private JButton colorButton;
	private JSpinner fontSizeSelector;
	private JSpinner strokeSizeSelector;
	private JSpinner layerSelector;
	// Default values
	private SpinnerNumberModel fontValues = new SpinnerNumberModel(12,
			                                                       1,
			                                                       48,
			                                                       1);
	private SpinnerNumberModel strokeValues = new SpinnerNumberModel(5,
			                                                         1, // minimum value
			                                                         20, // maximum value
			                                                         1); // step value
	private SpinnerNumberModel layerValues = new SpinnerNumberModel(1,
                                                                    1, // minimum value
                                                                    4, // maximum value
                                                                    1); // step value
	private int fontSize = 12;
	private int strokeSize = 5;
	private int toolType = 0;
	private int currentLayer = 0;
	private static int commentIndex = 0;
	private Color currentColor = Color.RED;
	// Button icons
	private ImageIcon brush = new ImageIcon("PaintPanel/imagesource/ic_brush_black_24dp_1x.png");
	private ImageIcon eraser = new ImageIcon("PaintPanel/imagesource/double-sided-eraser.png");
	private ImageIcon text = new ImageIcon("PaintPanel/imagesource/ic_format_textdirection_l_to_r_black_24dp_1x.png");
	private ImageIcon comment = new ImageIcon("PaintPanel/imagesource/ic_comment_black_24dp_1x.png");
	private ImageIcon undo = new ImageIcon("PaintPanel/imagesource/ic_undo_black_24dp_1x.png");
	private ImageIcon upload = new ImageIcon("PaintPanel/imagesource/ic_wallpaper_black_24dp_1x.png");
	private ImageIcon download = new ImageIcon("PaintPanel/imagesource/ic_file_download_black_24dp_1x.png");
	private ImageIcon bucket = new ImageIcon("PaintPanel/imagesource/ic_format_color_fill_black_24dp_1x.png");
	private ImageIcon rectangle = new ImageIcon("PaintPanel/imagesource/ic_crop_5_4_black_24dp_1x.png");
	private ImageIcon oval = new ImageIcon("PaintPanel/imagesource/ic_panorama_fish_eye_black_24dp_1x.png");
	private final ImageIcon[] TOOL_LIST = { brush, eraser, oval, rectangle, bucket, 
			                                text, comment, upload, download, undo};
	// Component icons
	private Icon fontSizeIcon = new ImageIcon("PaintPanel/imagesource/ic_text_fields_black_24dp_1x.png");
	private Icon layerIcon = new ImageIcon("PaintPanel/imagesource/ic_layers_black_24dp_1x.png");
	private Icon strokeSizeIcon = new ImageIcon("PaintPanel/imagesource/ic_line_weight_black_24dp_1x.png");
	private Icon colorIcon = new ImageIcon("PaintPanel/imagesource/ic_color_lens_black_24dp_1x.png");
	private JLabel fontSizeLabel = new JLabel(fontSizeIcon);
	private JLabel layerLabel = new JLabel(layerIcon);
	private JLabel strokeSizeLabel = new JLabel(strokeSizeIcon);
	private JLabel colorLabel = new JLabel(colorIcon);
	// Listeners
	private ColorPickerListener colorListener;
	private CommentInstruction currentCommentInstruction;
	private static ArrayList<CommentInstruction> comments = new ArrayList<CommentInstruction>();
	
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
		this.commentDisplay = new CommentDisplayPane(this);
		this.commentEntry = new CommentDialog(this);
		this.centerWrapper = new JPanel();
		this.centerWrapper.setLayout(new BoxLayout(this.centerWrapper, BoxLayout.Y_AXIS));
		this.centerWrapper.add(PaintWindow.paintPanel);
		this.centerWrapper.add(this.commentDisplay);
		this.centerWrapper.add(this.commentEntry);
		this.chatPanel = new MainWindow(ip, port + 1);
		this.populateToolbar(); // Adds various buttons to toolbar
		this.createColorChooser();
		this.createFileChooser();
		// Adding JPanels to JFrame
		this.add(this.centerWrapper, BorderLayout.CENTER);
		this.add(this.toolbar, BorderLayout.LINE_START);
		this.add(this.chatPanel, BorderLayout.LINE_END);
		// Fixing window dimensions
		this.setPreferredSize(WINDOW_SIZE);
		this.setResizable(false);
		// Default JFrame operations
		this.pack();
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	private void populateToolbar()
	{
		// Setting toolbar buttons
		this.toolbar.setLayout(new BoxLayout(this.toolbar, BoxLayout.PAGE_AXIS));
		this.toolbar.setBackground(Color.WHITE);
		JPanel toolButtonSection = new JPanel(new GridLayout(0, 2));
		toolButtonSection.setBackground(Color.WHITE);
		for (int i = 0; i < TOOL_LIST.length; i++)
		{
			toolbarButtons[i] = new JButton(TOOL_LIST[i]);
			if (i < 10) {
				toolbarButtons[i].addActionListener(new ToolSelectorListener(i, this));
			}
			toolButtonSection.add(toolbarButtons[i]);
		}
		this.toolbar.add(toolButtonSection);
		// Setting file chooser
		toolbarButtons[8].addActionListener(new SaveButtonListener(this));
		// Setting color chooser
		JPanel colorSection = new JPanel(new BorderLayout());
		colorSection.setBackground(Color.WHITE);
		this.colorButton = new JButton();
		this.setColorChooserIcon();
		this.colorButton.addActionListener(this.colorListener);
		colorSection.add(this.colorButton, BorderLayout.CENTER);
		//colorSection.add(this.colorLabel, BorderLayout.LINE_START);
		this.toolbar.add(colorSection);
		// Adding filler
		this.toolbar.add(Box.createRigidArea(new Dimension(0, 120))); 
		// Setting stroke size selector
		JPanel strokeSizeSection = new JPanel(new BorderLayout());
		strokeSizeSection.setBackground(Color.WHITE);
		this.strokeSizeSelector = new JSpinner(this.strokeValues);
		this.strokeSizeSelector.addChangeListener(new StrokeSizeListener(this));
		strokeSizeSection.add(this.strokeSizeSelector, BorderLayout.CENTER);
		strokeSizeSection.add(this.strokeSizeLabel, BorderLayout.LINE_START);
		this.toolbar.add(strokeSizeSection);
		// Setting font size selector
		JPanel fontSizeSection = new JPanel(new BorderLayout());
		fontSizeSection.setBackground(Color.WHITE);
		this.fontSizeSelector = new JSpinner(this.fontValues);
		this.fontSizeSelector.addChangeListener(new FontSizeListener(this));
		fontSizeSection.add(this.fontSizeSelector, BorderLayout.CENTER);
		fontSizeSection.add(this.fontSizeLabel, BorderLayout.LINE_START);
		this.toolbar.add(fontSizeSection);
		// Setting layer selector
		JPanel layerToolSection = new JPanel(new BorderLayout());
		layerToolSection.setBackground(Color.WHITE);
		this.layerSelector = new JSpinner(this.layerValues);
		this.layerSelector.addChangeListener(new LayerSelectorListener(this));
		layerToolSection.add(this.layerSelector, BorderLayout.CENTER);
		layerToolSection.add(this.layerLabel, BorderLayout.LINE_START);
		this.toolbar.add(layerToolSection);
	}
	
	// Currently unimplemented -- to be used to scale icons to standard resolution
	private static Icon resizeIcon(ImageIcon icon, int newWidth, int newHeight)
	{
		Image img = icon.getImage();
		Image resizedImage = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
		return new ImageIcon(resizedImage);
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
	
	private void createFileChooser()
	{
		FileFilter pngFilter = new FileNameExtensionFilter("PNG File", "png");
		FileFilter gifFilter = new FileNameExtensionFilter("GIF File", "gif");
		FileFilter jpgFilter = new FileNameExtensionFilter("JPG File", "jpg", "jpeg");
		this.fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		this.fileChooser.addChoosableFileFilter(pngFilter);
		this.fileChooser.addChoosableFileFilter(gifFilter);
		this.fileChooser.addChoosableFileFilter(jpgFilter);
	}
	
	public void viewFileChooser()
	{
		int returnVal = this.fileChooser.showSaveDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION)
		{
			File selectedFile = this.fileChooser.getSelectedFile();
			System.out.println(selectedFile.getAbsolutePath());
			System.out.println(this.fileChooser.getFileFilter().getDescription());
			if (this.fileChooser.getFileFilter().getDescription().equals("PNG File"))
			{
				File pngFile = new File(selectedFile.getAbsolutePath() + ".png");
				try {
				ImageIO.write(this.getDoodlePanel().mergeARGBLayers(this.getDoodlePanel().getDisplayLayers()), 
						      "PNG", pngFile);
				} catch (IOException e) {
				e.printStackTrace();
				}
			} else if (this.fileChooser.getFileFilter().getDescription().equals("JPG File"))
			{
				File jpgFile = new File(selectedFile.getAbsolutePath() + ".jpg");
				try {
					ImageIO.write(this.getDoodlePanel().mergeRGBLayers(this.getDoodlePanel().getDisplayLayers()), 
							      "JPG", jpgFile);
					} catch (IOException e) {
					e.printStackTrace();
					}
			} else if (this.fileChooser.getFileFilter().getDescription().equals("GIF File"))
			{
				File gifFile = new File(selectedFile.getAbsolutePath() + ".gif");
				try {
					ImageIO.write(this.getDoodlePanel().mergeARGBLayers(this.getDoodlePanel().getDisplayLayers()), 
							      "GIF", gifFile);
					} catch (IOException e) {
					e.printStackTrace();
					}
			}
			
		}
	}
	
	public int getFontSize()
	{
		return this.fontSize;
	}
	
	public void setFontSize(int i)
	{
		this.fontSize = i;
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
		BufferedImage colorBox = new BufferedImage(40, // Dimensions hard coded
											  	  40,
											  	  BufferedImage.TYPE_INT_RGB);
		Graphics2D g = colorBox.createGraphics();
		g.setColor(this.currentColor);
		g.fillRect(0, 0, 60, 60);
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
	
	public int getCommentIndex() {
		return this.commentIndex;
	}
	
	public String getCommentText() {
		return this.commentEntry.getText();
	}
	
	public ArrayList<CommentInstruction> getCommentList() {
		return PaintWindow.comments;
	}
	
	public void setComments(ArrayList<CommentInstruction> _comments) {
		PaintWindow.comments = _comments;
		this.commentDisplay.repopulateComments();
	}
	
	public static void removeComment(int index) {
		Iterator<CommentInstruction> it = PaintWindow.comments.iterator();
		while (it.hasNext()) {
		   CommentInstruction nextItem = it.next();
		   if (nextItem.getIndex() == index) {
		      it.remove();
		   }
		}
		System.out.println(PaintWindow.comments);
		PaintWindow.commentDisplay.repopulateComments();
		PaintWindow.paintPanel.repaintComments();
	}
	
	public void setCurrentCommentInstruction(CommentInstruction comment) {
		this.currentCommentInstruction = comment;
	}
	
	public void sendCommentInstruction() {
		if (this.currentCommentInstruction == null) {
			System.out.println("Please drag a circle to indicate where you are commenting on");
		} else {
			this.currentCommentInstruction.setCommentText(this.commentEntry.getText());
			if (this.currentCommentInstruction.getCommentText().equals("")) {
				System.out.println("Please enter your comment");
			} else {
				this.commentIndex++;
				this.getDoodlePanel().clearPreviewLayer();
				PaintWindow.comments.add(this.currentCommentInstruction);
				PaintWindow.commentDisplay.repopulateComments();
				this.getDoodlePanel().executeInstruction(this.currentCommentInstruction);
				this.sendInstruction(this.currentCommentInstruction);
				this.currentCommentInstruction = null;
			}
		}
	}
	
	public CommentDisplayPane getCommentDisplay()
	{
		return this.commentDisplay;
	}
	
	public void clearCommentInstruction() {
		this.currentCommentInstruction = null;
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
					System.out.println("Received message");
					if (message instanceof Integer) {
						System.out.println("Got ID: " + message);
						PaintWindow.clientId = ((Integer) message).intValue();
					} else if (message instanceof CanvasState) {
						System.out.println("Received canvas state!");
						PaintWindow.paintPanel.updateState((CanvasState) message);
					} else {
						Instruction instr = (Instruction) message;
						if (instr instanceof UndoInstruction) {
							System.out.println("Received instruction: undo");
							PaintWindow.paintPanel.undo();
						} else if (instr instanceof CommentInstruction) {
							PaintWindow.commentIndex++;
							CommentInstruction comment = (CommentInstruction) instr;
							System.out.println("Received instruction: comment");
							PaintWindow.comments.add(comment);
							PaintWindow.commentDisplay.repopulateComments();
							PaintWindow.paintPanel.executeInstruction(comment);
						} else if (instr instanceof RemoveCommentInstruction) {
							System.out.println("Received instruction: remove comment");
							RemoveCommentInstruction rmComment = (RemoveCommentInstruction) instr;
							PaintWindow.removeComment(rmComment.getIndex());
						} else {
							PaintWindow.paintPanel.executeInstruction(instr);	
						}			
					}
				}
				System.out.println("Read a null for some reason");
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Server has disconnected");
			}
		}
	}
	
	public static void main (String args[]) throws UnknownHostException, IOException, InterruptedException
	{
		PaintWindow pw = new PaintWindow("127.0.0.1", 9873);
	}

}
