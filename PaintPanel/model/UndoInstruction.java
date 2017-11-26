package model;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class UndoInstruction extends Instruction {

	public UndoInstruction (int _clientId) {
		super.clientId = _clientId;
	}
	
	@Override
	public void execute(ArrayList<BufferedImage> layers) {
		// actually undo doesn't take layers as an argument...
	}

}
