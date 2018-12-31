package application.controller;

import java.io.IOException;

import application.model.DragIconType;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class ButtonController extends Button {

	@FXML 
	private Button button_root_pane;
	@FXML
	private DragIconController default_icon;

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

	public void setButtonType (DragIconType type) {

		getStyleClass().clear();
		getStyleClass().add("button-map");

		default_icon.setType(type);
		mType = type;

		switch (type) {
		
		case ALL:
			setText("Mittelerde Karte");
			setId("header_button");
			getStyleClass().clear();
			getStyleClass().add("button-header");
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
	
	public void changeNodeOrientation() {
		
		setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
	}
	
	public Button getMap_button() {
		return button_root_pane;
	}
	
	public DragIconController getDefault_icon() {
		return default_icon;
	}
}