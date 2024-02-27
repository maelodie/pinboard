package pobj.pinboard.editor;

import java.util.*;
import pobj.pinboard.document.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Selection {
	private List<Clip> selected;

	public Selection() {
		selected = new ArrayList<Clip>();
	}

	public void select(Board board, double x, double y) {
		selected.clear(); //on vide d'abord la sélection

		//On ajoute les éléments de la planche qui contiennent (x,y)
		for(Clip c : board.getContents()) {
			if(c.isSelected(x, y)) {
				selected.add(c);
			}
		}	
	}

	public void toogleSelect(Board board, double x, double y) {
		/*
		 * On cherche le premier élément de la planche contenant le pixel spécifié
		 * Element ajouté à la séléction s'il n'y est pas et retiré s'il y était
		 */
		for(Clip c : board.getContents()) {
			if(c.isSelected(x, y)) {
				if(selected.contains(c)) {
					selected.remove(c);
				} else {
					selected.add(c);
				}
				break; // le premier élément de la sélection donc on sort de la boucle après
			}
		}	
	}

	public void clear() {
		selected.clear();
	}

	public List<Clip> getContents() {
		return selected;
	}

	public void drawFeedback(GraphicsContext gc) {
		for(Clip c : selected) {
			gc.setStroke(Color.BLUE);
			gc.strokeRect(
				c.getLeft() - 2, 
				c.getTop() - 2, 
				c.getRight() - c.getLeft() + 4, 
				c.getBottom() - c.getTop() + 4
			);	
		}
	}
}
