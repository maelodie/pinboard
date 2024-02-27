package pobj.pinboard.editor.tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import pobj.pinboard.document.Clip;
import pobj.pinboard.editor.EditorInterface;

public class ToolSelection implements Tool{
	private double startX, startY;
	
	@Override
	public void press(EditorInterface i, MouseEvent e) {
		startX = e.getX();
		startY = e.getY();
		
		Boolean isShiftPressed = e.isShiftDown();

        if (!isShiftPressed) {
        	// Selection Unique si shift n'est pas appuyé
        	i.getSelection().clear();
            i.getSelection().select(i.getBoard(), e.getX(), e.getY());
        } else {
        	// Selection multiple
            i.getSelection().toogleSelect(i.getBoard(), e.getX(), e.getY());
        }
	}

	@Override
	public void drag(EditorInterface i, MouseEvent e) {
		// Déplacement de tous les éléments
		for (Clip c : i.getSelection().getContents()) {
			double newX = e.getX() - startX;
	        double newY = e.getY() - startY;   
	        c.move(newX, newY);
		}

		// Mise à jour des nouvelles coordonnées de début
		startX = e.getX();
		startY = e.getY();
	}

	@Override
	public void release(EditorInterface i, MouseEvent e) {
		
	}

	@Override
	public void drawFeedback(EditorInterface i, GraphicsContext gc) {
		gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
		i.getSelection().drawFeedback(gc);
		
	}

	@Override
	public String getName(EditorInterface editor) {
		return "Current Tool : Selection";
	}
	
}
