package pobj.pinboard.editor.tools;
import java.io.File;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import pobj.pinboard.document.ClipImage;
import pobj.pinboard.editor.EditorInterface;

public class ToolImage implements Tool{
	private double startX, startY;
    private File imageFile;
    private double scale;
    
	@Override
	public void press(EditorInterface i, MouseEvent e) {
		startX = e.getX();
        startY = e.getY();
	}

	@Override
	public void drag(EditorInterface i, MouseEvent e) {
		
	}

	@Override
	public void release(EditorInterface i, MouseEvent e) {
		FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir le fichier image");
        imageFile = fileChooser.showOpenDialog(null);

        if (imageFile != null) {
        	//calcul des hauteurs & largeurs du canva & de l'image pour l'échelle
            double imageWidth = ClipImage.getImageWidth(imageFile);
            double imageHeight = ClipImage.getImageHeight(imageFile);
            scale = 0.2;

            // Nouvelles échelles
            imageWidth *= scale;
            imageHeight *= scale;

            i.getBoard().addClip(new ClipImage(startX, startY, imageWidth, imageHeight, imageFile));
        }
		
	}

	@Override
	public void drawFeedback(EditorInterface i, GraphicsContext gc) {
		
	}

	@Override
	public String getName(EditorInterface editor) {
		return "Current Tool : Image";
	}

}
