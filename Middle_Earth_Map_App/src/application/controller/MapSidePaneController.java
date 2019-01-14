package application.controller;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

public class MapSidePaneController extends VBox {

	@FXML private VBox vbox_root_pane; 
	
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
}
