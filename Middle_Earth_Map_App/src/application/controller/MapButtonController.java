package application.controller;

import java.io.IOException;

import application.model.DragIconType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

/**
 * Controller class for the Button_view.fxml.
 * 
 * Set the Button type according to the type of the DragIcon that is 
 * assigned to the Button.
 * 
 * @author Bela Ackermann
 */

public class ButtonController extends Button {

	@FXML 
	private Button button_root_pane;
	@FXML
	private DragIconController button_icon;

	private DragIconType mType = null;

	public ButtonController() {

		FXMLLoader fxmlLoader = new FXMLLoader(
				getClass().getResource("../view/Button_view.fxml")
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

	public DragIconType getType () { return mType; }
	
	/**
	 * Set the type of the Button according to the type of the
	 * DragIconType the is assigned to the button in the 
	 * RootLayoutController.
	 * 
	 * Also sets the style, text and Id of the button.
	 * 
	 * -> Header button differs to rest, since the set icon is
	 *    disabled for any action.
	 * 
	 * @param type   type of the icon that is assigned to the
	 * 				 button
	 */
	public void setButtonType (DragIconType type) {

		getStyleClass().clear();
		getStyleClass().add("button-map");

		button_icon.setType(type);
		mType = type;

		switch (type) {
		
		case HEADER:
			setText("Mittelerde Karte");
			setId("header_button");
			getStyleClass().clear();
			getStyleClass().add("button-header");
			button_icon.setDisable(true);
			break;
		
		case PERSON:
			setText("Personen");
			setId("person_button");
			getStyleClass().add("button-person");
			break;

		case PLACE:
			setText("Ortschaften");
			setId("place_button");
			getStyleClass().add("button-place");
			break;

		case EVENT:
			setText("Ereignisse");
			setId("event_button");
			getStyleClass().add("button-event");
			break;

		case ASSORTED:
			setText("Sonstiges");
			setId("assorted_button");
			getStyleClass().add("button-assorted");
			break;

		default:
			break;
		}
	}

	/**
	 * getter for the button_icon that is assigned as default
	 * to the button during the initialization and then filled 
	 * with a icon type
	 * 
	 * @return button_icon  
	 */
	public DragIconController getButton_icon() {
		return button_icon;
	}
}