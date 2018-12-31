package application.controller;

import java.io.IOException;

import application.model.DragIconType;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class DragIconController extends AnchorPane{
	
	@FXML AnchorPane icon_root_pane;

	private DragIconType mType = null;
	
	public DragIconController() {
		
		FXMLLoader fxmlLoader = new FXMLLoader(
				getClass().getResource("../view/DragIcon_view.fxml")
				);
		
		fxmlLoader.setRoot(this); 
		fxmlLoader.setController(this);
		
		try { 
			fxmlLoader.load();
        
		} catch (IOException exception) {
		    throw new RuntimeException(exception);
		}
	}
	
	@FXML
	private void initialize() {}
	
	public void relocateToPoint (Point2D p) {

		//relocates the object to a point that has been converted to
		//scene coordinates
		Point2D localCoords = getParent().sceneToLocal(p);
		
		relocate ( 
				(int) (localCoords.getX() - (getBoundsInLocal().getWidth() / 2)),
				(int) (localCoords.getY() - (getBoundsInLocal().getHeight() / 2))
			);
	}
	
	public DragIconType getType () { return mType; }
	
	public void setType (DragIconType type) {
		
		mType = type;
		
		getStyleClass().clear();
		getStyleClass().add("dragicon");
		
		switch (mType) {
		
		case ALL:
			getStyleClass().add("icon-header");
			break;
		
		case PERSON:
			getStyleClass().add("icon-person");			
		break;

		case PLACE:
			getStyleClass().add("icon-place");
		break;
		
		case EVENT:
			getStyleClass().add("icon-event");
		break;

		case ASSORTED:
			getStyleClass().add("icon-assorted");
		break;
		
		default:
		break;
		}
	}
}
