package model;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class RemoveCommentInstruction extends Instruction {
	private int index;
	
	public RemoveCommentInstruction(int _index) {
		this.index = _index;
	}
	
	public int getIndex() {
		return this.index;
	}
	
	@Override
	public void execute(ArrayList<BufferedImage> layers) {
		// TODO Auto-generated method stub
	}

}
