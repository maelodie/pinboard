package pobj.pinboard.document;

import java.io.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class ClipImage extends AbstractClip {
	private File filename;
	
	public ClipImage(double left, double top, double right, double bottom, File filename) {
		super(left, top, left + right, top + bottom, null);
        this.filename = filename;
    }
	
	//permet de calculer la largeur réelle de l'image
	public static double getImageWidth(File filename) {
        try {
            Image image = new Image(new FileInputStream(filename.getAbsolutePath()));
            return image.getWidth();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }
	
	//permet de calculer la hauteur réelle de l'image
    public static double getImageHeight(File filename) {
        try {
            Image image = new Image(new FileInputStream(filename.getAbsolutePath()));
            return image.getHeight();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public void draw(GraphicsContext ctx) {
        try {
            Image image = new Image(new FileInputStream(filename.getAbsolutePath()));
            ctx.drawImage(image, left, top, right - left , bottom - top);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
	@Override
	public Clip copy() {
		return new ClipImage(left, top, right, bottom, filename);
	
	}
	
	@Override
	public boolean isSelected(double x, double y) {
		return x > left && y > top && x < getImageHeight(filename) && y < getImageWidth(filename);
	}
}
