package model;

import java.awt.Graphics2D;
import java.io.Serializable;

public abstract class Instruction implements Serializable {

	// the execute function should handle drawing on a BufferedImage
	public abstract void execute(Graphics2D g);
}
