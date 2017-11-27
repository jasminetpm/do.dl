package model;

import java.awt.Graphics2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.awt.image.BufferedImage;

/**
 * Abstract class which represents a single drawing action on the canvas. All
 * instructions (e.g. brush, bucket, etc), will extend this class.
 * 
 * @author Daniel Lok
 */
public abstract class Instruction implements Serializable {
	public int clientId;

	/**
	 * Get the client's ID (given by the server)
	 * 
	 * @return the client's ID
	 */
	public int getClientId() {
		return this.clientId;
	}
	
	/**
	 * All Instructions should implement this function, which handles drawing on the
	 * canvas.
	 * 
	 * @param layers the displayLayers of the DoodlePanel
	 */
	public abstract void execute(ArrayList<BufferedImage> layers);
}
