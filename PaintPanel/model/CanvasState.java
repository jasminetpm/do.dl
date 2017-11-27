package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * A serializable class sent by the server which represents the current picture.
 * It is composed of an instruction log, and an array of layers (corresponds to
 * the baseLayers field of the DoodlePanel class).
 * 
 * @author Daniel Lok
 */
public class CanvasState implements Serializable {
	private transient ArrayList<BufferedImage> layers;
	private LinkedList<Instruction> instructionLog;
	private ArrayList<CommentInstruction> comments;
	private int commentIndex;

	/**
	 * Class constructor
	 * 
	 * @param _layers
	 *            the PaintServer's base layers
	 * @param _instructionLog
	 *            the PaintServer's instruction log
	 */
	public CanvasState(ArrayList<BufferedImage> _layers, LinkedList<Instruction> _instructionLog,
			ArrayList<CommentInstruction> _comments, int _index) {
		this.layers = _layers;
		this.instructionLog = _instructionLog;
		this.comments = _comments;
		this.commentIndex = _index;
	}

	/**
	 * Writes the instruction log and sends the BufferedImages
	 * 
	 * @param oos
	 *            the socket's output stream
	 * @throws IOException
	 */
	private void writeObject(ObjectOutputStream oos) throws IOException {
		oos.defaultWriteObject();
		oos.writeInt(this.commentIndex);
		oos.writeObject(this.instructionLog);
		oos.writeObject(this.comments);
		for (BufferedImage layer : this.layers) {
			ImageIO.write(layer, "png", oos);
		}
	}

	/**
	 * Reads the instruction log and BufferedImages. The read is in a while loop
	 * because occasionally there will be a null read.
	 * 
	 * @param ois the socket's input stream
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws InterruptedException
	 */
	private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException, InterruptedException {
		BufferedImage byteImg;
		BufferedImage rgbImg;

		ois.defaultReadObject();
		this.layers = new ArrayList<BufferedImage>();
		this.commentIndex = ois.readInt();
		this.instructionLog = (LinkedList<Instruction>) ois.readObject();
		this.comments = (ArrayList<CommentInstruction>) ois.readObject();
		while (this.layers.size() < 5) {
			byteImg = ImageIO.read(ois);
			if (byteImg != null) {
				// convert the read image to the appropriate type
				rgbImg = new BufferedImage(byteImg.getWidth(), byteImg.getHeight(), BufferedImage.TYPE_INT_ARGB);
				rgbImg.getGraphics().drawImage(byteImg, 0, 0, null);
				this.layers.add(rgbImg);
			}
		}
	}

	/**
	 * Get the CanvasState's layers field
	 * @return the layers field
	 */
	public ArrayList<BufferedImage> getLayers() {
		return this.layers;
	}

	/**
	 * Get the CanvasState's instruction log
	 * @return the instruction log
	 */
	public LinkedList<Instruction> getLog() {
		return this.instructionLog;
	}
	
	/**
	 * Get the CanvasState's current comment index
	 * @return the comment index
	 */
	public int getIndex() {
		return this.commentIndex;
	}
	
	/**
	 * Get the CanvasState's list of comments
	 * @return the list of comments
	 */
	public ArrayList<CommentInstruction> getComments() {
		return this.comments;
	}

	@Override
	public String toString() {
		return ("Canvas State Object: Layers - " + this.layers + " log - " + this.instructionLog);
	}
}
