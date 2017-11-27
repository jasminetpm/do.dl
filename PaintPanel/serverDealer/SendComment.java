package serverDealer;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;

import org.omg.CORBA.portable.InputStream;

import chatUI.MainWindow;
import gui.AllCommentPane;

public class SendComment {
	private AllCommentPane acp;
	private String message;
	
	public SendComment(AllCommentPane acp, String message) throws UnsupportedEncodingException
	{
		this.acp = acp;
		this.message = message;
		
		ByteArrayInputStream is = new ByteArrayInputStream(message.getBytes("UTF-8"));
		
	}

}