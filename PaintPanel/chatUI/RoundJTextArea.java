package chatUI;

import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JTextArea;

/**
 * Sets up the format for the TextArea where users' chat history is shown
 * @author Coco
 * @version 1.0 Nov 2017
 */


public class RoundJTextArea extends JTextArea {
	/**
	 * Stores the shape of the textArea
	 */
	private Shape shape;
	
	/**
	 * constructor for RoundTextArea
	 */
	public RoundJTextArea() {
		super();
		setOpaque(false);
	}
	
	@Override
	/**
	 * set to paint it out
	 */
	protected void paintComponent(Graphics g) {
		g.setColor(getBackground());
		g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
		super.paintComponent(g);
	}
	
	@Override
	/**
	 * sets the border
	 */
	protected void paintBorder(Graphics g) {
		g.setColor(getForeground());
		g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
	}
	
	@Override
	/**
	 * automatic roller
	 */
	public boolean contains(int x, int y) {
		if (shape == null || !shape.getBounds().equals(getBounds()))
		{
			shape = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
		}
		return shape.contains(x, y);
	}

}
