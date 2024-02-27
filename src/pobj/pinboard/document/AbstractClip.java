package pobj.pinboard.document;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class AbstractClip implements Clip {

	protected double left, top, right, bottom;
	protected Color color;
	
	public AbstractClip(double left, double top, double right, double bottom, Color color) {
		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
		this.color = color;
	}
	@Override
	public double getTop() {
		return top;
	}

	@Override
	public double getLeft() {
		return left;
	}

	@Override
	public double getBottom() {
		return bottom;
	}

	@Override
	public double getRight() {
		return right;
	}

	@Override
	public void setGeometry(double left, double top, double right, double bottom) {
		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
	}
	
	@Override
	public void setColor(Color c) {
		color = c;
		
	}

	@Override
	public Color getColor() {
		return color;
	}
	
	@Override
	public void move(double x, double y) {
		this.setGeometry(left + x, top + y, right + x, bottom + y);
	}
	
	@Override
	public boolean isSelected(double x, double y) {
		return ((x >left) && (x < right)) && ((y < bottom) && (y > top));
	}
	
	public abstract void draw(GraphicsContext ctx); 
	
}
