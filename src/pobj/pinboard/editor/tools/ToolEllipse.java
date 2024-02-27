package pobj.pinboard.editor.tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import pobj.pinboard.document.ClipEllipse;
import pobj.pinboard.document.ClipRect;
import pobj.pinboard.editor.EditorInterface;

public class ToolEllipse implements Tool{
private double startX, startY, endX, endY;
	
	@Override
	public void press(EditorInterface i, MouseEvent e) {
		startX = e.getX();
		startY = e.getY();
		endX = e.getX();
		endY = e.getY();		
	}

	@Override
	public void drag(EditorInterface i, MouseEvent e) {
		endX = e.getX();
		endY = e.getY();		
	}

	@Override
	public void release(EditorInterface i, MouseEvent e) {
		//mise à jour des coordonnées 
		endX = e.getX();
		endY = e.getY();
		
		//coordonnées finales: minimum pour le point à haut à gauche, maximum pour le point en haut à droite
		double left = Math.min(startX, endX); 
	    double top = Math.min(startY, endY);
	    double right = Math.max(endX, startX); //hauteur
	    double bottom = Math.max(endY, startY); //largeur
	    
	    //adding the final drawing
	    i.getBoard().addClip(new ClipEllipse(left, top, right, bottom, Color.web("#AFB3F7")));
	}

	@Override
	public void drawFeedback(EditorInterface i, GraphicsContext gc) {
		//On efface le contenu actuel du canva
		gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
		
		//couleur du contour de l'ellipse
		gc.setStroke(Color.BLACK);
		
		//nouvelles coordonnées
		double left = Math.min(startX, endX); 
	    double top = Math.min(startY, endY);
	    double right = Math.abs(endX - startX); //hauteur
	    double bottom = Math.abs(endY - startY); //largeur
	    i.getBoard().draw(gc);
		gc.strokeOval(left, top, right, bottom);
	}

	@Override
	public String getName(EditorInterface editor) {
		return "Current Tool : Ellipse";
	}
	
}
