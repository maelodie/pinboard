package pobj.pinboard.document;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.*;

public class Board {
	private List<Clip> contents;
	
	public Board() {
		contents = new ArrayList<Clip>();
	}
	
	public List<Clip> getContents() {
		return contents;
	}
	
	public void addClip(Clip clip) {
		contents.add(clip);
	}
	
	public void addClip(List<Clip> clip) {
		contents.addAll(clip);
	}
	
	public void removeClip(Clip clip) {
		contents.remove(clip);
	}
	
	public void removeClip(List<Clip> clip) {
		contents.removeAll(clip);
	}
	
	public void draw(GraphicsContext gc) {
		ClipRect cr = new ClipRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight(), Color.WHITE);
		cr.draw(gc);
		for(Clip c : contents) {
			c.draw(gc);
		}
	}
}
