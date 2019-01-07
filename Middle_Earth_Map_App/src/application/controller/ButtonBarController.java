package application.controller;

import java.io.IOException;

import application.MainApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ButtonBarController extends VBox {

	public ButtonBarController() {
		FXMLLoader fxmlLoader = new FXMLLoader(
				getClass().getResource("../view/ButtonBar_view.fxml")
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
	
	/**
	 * Kontrolliert ob Textfeld leer ist
	 * 
	 * @return  true wenn Textfeld leer ist, false wenn es gefüllt ist
	 */
	public boolean textfieldEmpty() {

		//		// Wenn Textfeld leer ist:
		//		if (vorschlagField.getText() == null || vorschlagField.getText().length() == 0) {
		//			// Gebe Fehlermeldung aus und gebe true zurück
		//			Alert alert = new Alert(AlertType.ERROR);
		//			alert.setTitle("Invalid Fields");
		//			alert.setHeaderText("Achtung!");
		//			alert.setContentText("Gib erst einen Vorschlag ein!\n");
		//
		//			alert.showAndWait();
		//
		//			return true;
		//
		//		} else {
		return false;
		//		}
	}

	/**
	 * Checks if entry already exists in list
	 * 
	 * @param newItem  new map item
	 * @return isUnique true if there is no duplicate of the new entry
	 */
	public boolean isNew(String newItem) {

		boolean	isUnique = true;
		int i = 0;
		String existingItem;

//		// Iterates over the existing entries in the list
//		while( 	isUnique && (i < mainApp.getMapData().size())) {
//
//			existingItem = mainApp.getMapData().get(i).getEntryInfo();
//			i = i + 1;
//			isUnique = !existingItem.toLowerCase().equals(newItem.toLowerCase());
//		}
		return isUnique;
	}

	@FXML
	public void handleEnterPressed(KeyEvent event) {

		if (event.getCode() == KeyCode.ENTER) {
			handleIconDropped();
		}
	}



	/**
	 * Adds a new suggestion to the suggestion-list through a click on the button
	 * 
	 * @param MapEntry
	 */

	public void handleIconDropped() {

		//		// Wurde ein neuer Vorschlag im Textfeld für neue Vorschläge eingegeben?
		//		if(!textfeldLeer()) {
		//			// Erzeuge String mit neuem Vorschlag aus dem Textfeld
		//			String neuerVorschlag = vorschlagField.getText().toString();
		//			// Erzeuge Nummer für neuen Vorschlag aus dem Textfeld
		//			int index = mainApp.getSuggestionData().size() + 1;
		//
		//			if(istNeu(neuerVorschlag)) {
		//				// Füge Vorschlag der Liste hinzu und entferne den Vorschlag aus dem Textfeld
		//				mainApp.getSuggestionData().add(new Suggestion(index, neuerVorschlag));
		//				System.out.println("Der Vorschlag: '" + neuerVorschlag + "' wurde der Liste hinzugefügt.");
		//				vorschlagField.clear();
		//			} else {
		//				// Gebe Fehlermeldung aus und gebe false zurück
		//				Alert alert = new Alert(AlertType.ERROR);
		//				alert.setTitle("Vorschlag besteht bereits");
		//				alert.setHeaderText("Duplikation des Vorschlags");
		//				alert.setContentText("Der Vorschlag ist in der Liste bereits enthalten\n");
		//
		//				alert.showAndWait();
		//			}
		//		}
	}

	
	/**
	 * Called when the user clicks the edit button. Opens a dialog to edit
	 * details for the selected
	 */
	@FXML
	private void handleEditButton() {
		//		Suggestion ausgewaehlterVorschlag = vorschlagTable.getSelectionModel().getSelectedItem();
		//		if (ausgewaehlterVorschlag != null) {
		//			boolean bearbeitenGeklickt = mainApp.showSuggestionEditDialog(ausgewaehlterVorschlag);
		//
		//		} else {
		//			// Nothing selected.
		//			Alert alert = new Alert(AlertType.WARNING);
		//			alert.initOwner(mainApp.getPrimaryStage());
		//			alert.setTitle("Keine Auswahl getroffen");
		//			alert.setHeaderText("Es wurde kein Vorschlag ausgewählt");
		//			alert.setContentText("Bitte wählen Sie erst einen Vorschlag zur Bearbeitung aus der Liste aus.");
		//
		//			alert.showAndWait();
		//		}
	}

	/**
	 * Called when the user clicks on the delete button.
	 */
	@FXML
	private void handleDeleteButton() {
		//
		//		int selectedIndex = vorschlagTable.getSelectionModel().getSelectedIndex();
		//
		//		if (selectedIndex >= 0) {
		//			vorschlagTable.getItems().remove(selectedIndex);
		//		} else {
		//			// Nothing selected.
		//			Alert alert = new Alert(AlertType.WARNING);
		//			alert.initOwner(mainApp.getPrimaryStage());
		//			alert.setTitle("Keine Auswahl");
		//			alert.setHeaderText("Kein bestehender Vorschlag ist ausgewählt");
		//			alert.setContentText("Bitte wähle einen Vorschlag aus, denn du aus der Liste löschen möchtest.");
		//
		//			alert.showAndWait();
		//		}
	}
}
