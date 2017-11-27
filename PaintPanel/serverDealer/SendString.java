package serverDealer;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;

import org.omg.CORBA.portable.InputStream;

import chatUI.MainWindow;

/**
 * Transformed the string from chat into Byte-wise information
 * @author Coco
 * @version 1.0 Nov 2017
 */
public class SendString {
	/**
	 * mainWindow it associated with
	 */
	private MainWindow mw;
	/**
	 * string sent
	 */
	private String message;
	
	/**
	 * constructor
	 * @param mw: MainWindow it is associated with
	 * @param message: message needing to be sent
	 * @throws UnsupportedEncodingException
	 */
	public SendString(MainWindow mw, String message) throws UnsupportedEncodingException
	{
		this.mw = mw;
		this.message = message;
		
		ByteArrayInputStream is = new ByteArrayInputStream(message.getBytes("UTF-8"));
		
	}

}
