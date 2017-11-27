package model;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class RemoveCommentInstruction extends Instruction {
	private CommentInstruction comment;
	
	public RemoveCommentInstruction(CommentInstruction _comment) {
		this.comment = _comment;
	}
	
	public CommentInstruction getComment() {
		return this.comment;
	}
	
	@Override
	public void execute(ArrayList<BufferedImage> layers) {
		// TODO Auto-generated method stub
	}

}
