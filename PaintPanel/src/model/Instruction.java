package model;

import java.awt.Graphics2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.awt.image.BufferedImage;

public abstract class Instruction implements Serializable {
	public int clientId;

	public int getClientId() {
		return this.clientId;
	}
	
	// the execute function should handle drawing on a BufferedImage
	public abstract void execute(ArrayList<BufferedImage> layers);
}
