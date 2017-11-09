package serverDealer;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;

import org.omg.CORBA.portable.InputStream;

import chatUI.MainWindow;

public class SendString {
	private MainWindow mw;
	private String message;
	
	public SendString(MainWindow mw, String message) throws UnsupportedEncodingException
	{
		this.mw = mw;
		this.message = message;
		
		ByteArrayInputStream is = new ByteArrayInputStream(message.getBytes("UTF-8"));
		
	}

}
