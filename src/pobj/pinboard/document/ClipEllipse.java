package pobj.pinboard.document;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ClipEllipse extends AbstractClip {

	public ClipEllipse(double left, double top, double right, double bottom, Color color) {
		super(left, top, right, bottom, color);
	}
	
	@Override
	public void draw(GraphicsContext ctx) {
		ctx.setFill(color);
		ctx.fillOval(left, top, right-left, bottom-top) ;
		ctx.setStroke(Color.BLACK) ;
		ctx.strokeOval(left, top, right-left, bottom-top) ;
	}
	
	public ClipEllipse copy() {
		return new ClipEllipse(left, top, right, bottom, color);
	}
	
	@Override
	public boolean isSelected(double x, double y) {
		double cx = (left + right) / 2;
		double cy = (top + bottom) / 2;
		double rx = (right - left) / 2;
		double ry = (bottom - top) / 2;
		double expr1 = Math.pow((x - cx)/rx , 2);
		double expr2 = Math.pow((y - cy)/ry , 2);
		return (expr1 + expr2) <= 1;
	}
}
