package model;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * A class which represents a path made by the brush tool
 * @author Daniel Lok
 */
public class UndoInstruction extends Instruction {

	/**
	 * Class constructor
	 * @param _clientId the client's ID (given by the server)
	 */
	public UndoInstruction (int _clientId) {
		super.clientId = _clientId;
	}
	
	/**
	 * Since UndoInstruction does not actually do any drawing, this method is blank.
	 */
	@Override
	public void execute(ArrayList<BufferedImage> layers) {
		// actually undo doesn't take layers as an argument...
	}

}
