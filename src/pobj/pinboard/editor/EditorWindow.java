package pobj.pinboard.editor;
import javafx.event.*;
import javafx.scene.Scene;
import javafx.scene.canvas.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.input.*;
import javafx.stage.*;
import pobj.pinboard.document.*;
import pobj.pinboard.editor.tools.*;

public class EditorWindow implements EditorInterface, ClipboardListener{
	private Board board;
	private Tool currentTool = new ToolRect(); //valeur par défaut
	private Label lb; //label en bas de la fenêtre
	private Selection selected; //ensemble des éléments sélectionnés 
	private MenuItem pasteMenu;
	
	public EditorWindow(Stage stage)  {
		// ----------------------------- Initialization ---------------------------------------
		VBox vbox = new VBox();
		this.board = new Board();
		selected = new Selection();
		Clipboard.getInstance().addListener(this); //subscribes to listen to what happens in the clipboard

		// ----------------------------- Stage Setting  ---------------------------------------
		//Fenêtre
		stage.setScene(new Scene(vbox));

		//Titre
		stage.setTitle("PinBoard - <untitled>");

		//Menu
		MenuBar menu = new MenuBar(new Menu("File"), new Menu("Edit"), new Menu("Tools"));
		vbox.getChildren().add(menu);

		//ToolBar
		Button box = new Button("Box");
		Button ellipse = new Button("Ellipse");
		Button image = new Button("Image");
		Button selection = new Button("Select");
		ToolBar tb = new ToolBar(box, ellipse, image, selection);
		vbox.getChildren().add(tb);

		//Zone de dessin
		Canvas canvas = new Canvas(1000, 800);
		vbox.getChildren().add(canvas);

		//Separateur
		Separator sp = new Separator();
		vbox.getChildren().add(sp);

		//Label
		lb = new Label(getName());
		vbox.getChildren().add(lb);

		// ----------------------------- Button Actions ---------------------------------------
		//new rect object
		box.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				currentTool = new ToolRect();
				updateLabel(); 
			}
		});

		//new Ellipse object
		ellipse.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				currentTool = new ToolEllipse();
				updateLabel();
			}
		});

		//new Image
		image.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				currentTool = new ToolImage();
				updateLabel();
			}
		});

		//select objects
		selection.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				currentTool = new ToolSelection();
				updateLabel();
			}
		});

		// ----------------------------- Menu Actions ---------------------------------------
		//MenuItem "new" dans File
		MenuItem nouveau = new MenuItem("New");
		nouveau.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				//New window's size
				Stage newStage = new Stage();
				newStage.setMinWidth(1000);
				newStage.setMinHeight(916);

				new EditorWindow(newStage);
			}
		});
		menu.getMenus().get(0).getItems().add(nouveau);

		//MenuItem "close" dans File
		MenuItem close = new MenuItem("Close");
		close.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				stage.close();
				Clipboard.getInstance().removeListener(EditorWindow.this);
			}
		});
		menu.getMenus().get(0).getItems().add(close);

		//MenuItem "Copy" dans Edit
		MenuItem copy = new MenuItem("Copy");
		copy.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				Clipboard.getInstance().copyToClipboard(selected.getContents());
			}
		});
		menu.getMenus().get(1).getItems().add(copy);

		//MenuItem "Paste" dans Edit
		MenuItem paste = new MenuItem("Paste");
		pasteMenu = paste;
		clipboardChanged(); //ne fait rien si le presse papier est vide
		
		paste.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				board.addClip(Clipboard.getInstance().copyFromClipboard());
				draw(canvas);
			}
		});
		menu.getMenus().get(1).getItems().add(paste);
		
		//MenuItem "Delete" dans Edit
		MenuItem delete= new MenuItem("Delete");
		delete.setOnAction(
				new EventHandler<ActionEvent>() {
					public void handle(ActionEvent e) { 
						board.removeClip(selected.getContents());
						draw(canvas);
					}
				}					
		);
		menu.getMenus().get(1).getItems().add(delete);
		// ----------------------------- Drawing Actions ---------------------------------------
		//pressing the mouse
		canvas.setOnMousePressed(new EventHandler<MouseEvent>(){
			public void handle(MouseEvent e) {
				EditorWindow.this.press(e);
				draw(canvas);
			}
		});
		
		//dragging the mouse
		canvas.setOnMouseDragged(new EventHandler<MouseEvent>(){
			public void handle(MouseEvent e) {
				EditorWindow.this.drag(e);
				draw(canvas);
			}
		});
		
		//releasing the mouse
		canvas.setOnMouseReleased(new EventHandler<MouseEvent>(){
			public void handle(MouseEvent e) {
				EditorWindow.this.release(e);
				draw(canvas);
			}
		});
		
		stage.show();
	}

	//Mouse actions
	public void press(MouseEvent e) {
		currentTool.press(this, e);
	}

	public void drag(MouseEvent e) {
		currentTool.drag(this, e);
	}

	public void release(MouseEvent e) {
		currentTool.release(this, e);
	}

	public void drawFeedback(GraphicsContext gc) {
		currentTool.drawFeedback(this, gc);
	}

	public String getName(){
		return currentTool.getName(this);
	}

	public void draw(Canvas canvas) {
		drawFeedback(canvas.getGraphicsContext2D());
		for(Clip c : board.getContents()) {
			c.draw(canvas.getGraphicsContext2D());
		}
	}

	//Getters
	@Override
	public Board getBoard() {
		return board;
	}

	@Override
	public Selection getSelection() {
		return selected;
	}

	@Override
	public CommandStack getUndoStack() {
		return null;
	}

	//Setters
	public void updateLabel() {
		lb.setText(getName());
	}

	@Override
	public void clipboardChanged() {
		if (pasteMenu != null) {
			pasteMenu.setDisable(Clipboard.getInstance().isEmpty());
        }
    }
}
