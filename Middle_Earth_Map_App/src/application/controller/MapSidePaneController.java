package application.controller;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

/**
 * Controller class for the MapSidePane_view.fxml.
 * 
 * Root for the SidePane bar of the App, populated with drag icons and pane changer buttons
 * also with the control buttons for saving and loading,
 * 
 * @author Bela Ackermann
 */
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
