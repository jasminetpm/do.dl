package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import model.Instruction;

public class PaintServerReadingThread extends Thread {
	private ObjectInputStream ois;
	private InputStream is;
	
	public PaintServerReadingThread(InputStream _is) {
		this.is = _is;
	}
	
	@Override
	public void run() {
		Instruction instr;
		
		try {
			this.ois = new ObjectInputStream(this.is);
			while ((instr = (Instruction) this.ois.readObject()) != null) {
				System.out.println(instr);
			}
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
