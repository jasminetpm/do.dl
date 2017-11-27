package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class CanvasState implements Serializable {
	private transient ArrayList<BufferedImage> layers;
	private LinkedList<Instruction> instructionLog;
	
	public CanvasState(ArrayList<BufferedImage> _layers, LinkedList<Instruction> _instructionLog) {
		this.layers = _layers;
		this.instructionLog = _instructionLog;
	}

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        oos.writeObject(this.instructionLog);
        for (BufferedImage layer : this.layers) {
            ImageIO.write(layer, "png", oos);
        }
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException, InterruptedException {
    		BufferedImage byteImg;
    		BufferedImage rgbImg;
    		
        ois.defaultReadObject();
        this.layers = new ArrayList<BufferedImage>();
        this.instructionLog = (LinkedList<Instruction>) ois.readObject();
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
    
    public ArrayList<BufferedImage> getLayers() {
    		return this.layers;
    }
    
    public LinkedList<Instruction> getLog() {
    		return this.instructionLog;
    }
    
    @Override
	public String toString() {
		return ("Canvas State Object: Layers - " + this.layers + " log - " + this.instructionLog);
	}
}
