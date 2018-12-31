package application.controller;

import java.io.IOException;

import application.model.DragContainer;
import application.model.DragIconType;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class MapSidePaneController extends VBox {

	@FXML private VBox vbox_root_pane; 
	@FXML private Button button_header;

	
	public MapSidePaneController() {

		FXMLLoader fxmlLoader = new FXMLLoader(
				getClass().getResource("../view/MapSidePane_view.fxml")
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
	
	public Button getButton_header() {
		return button_header;
	}

}
