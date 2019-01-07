package application.controller;


import application.model.MapItemData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Dialog to edit details of a map item.
 * 
 * @author Bela Ackermann
 */
public class MapItemEditDialogController {

	@FXML private TextField name_field;
	@FXML private TextField entry_field;
	@FXML private TextField details_field;

	@FXML private TableView<MapItemData> item_table;
	@FXML private TableColumn<MapItemData, String> entry_column;
	@FXML private TableColumn<MapItemData, String> details_column;

	@FXML private Button cancle_button;
	@FXML private Button save_button;

	private Stage dialogStage;
	private DragIconController icon;
	private ObservableList<MapItemData> iconData = FXCollections.observableArrayList();
	//private boolean speichernGeklickt = false;

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	protected void initialize() {
		
		name_field.getStyleClass().remove("text-field");
		name_field.getStyleClass().add("text-field-header");
				
		item_table.setItems(iconData);

		// Initialize the item table with the two columns
		entry_column.setCellValueFactory(cellData -> cellData.getValue().getListItemNameProperty());
		details_column.setCellValueFactory(cellData -> cellData.getValue().getListItemDescriptionProperty());

	}


	/**
	 * Sets the stage of this dialog.
	 * 
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	/**
	 * Sets the map item to be edited in the dialog.
	 * 
	 * @param droppedIcon  the icon that has been dropped on the map
	 */
	public void setMapItem(DragIconController droppedIcon) {

		this.icon = droppedIcon;

		if(icon.getIconName() != null) {
			name_field.setText(icon.getIconName());
		}
		
		if(icon.getIconData() != null) {
			iconData = icon.getIconData();
			item_table.setItems(iconData);
		}

	}

	/**
	 * Validates the user input in the text fields.
	 * 
	 * @return true if the input is valid
	 */
	private boolean isInputValid() {
		String errorMessage = "";

		if (name_field.getText() == null || name_field.getText().length() == 0) {
			errorMessage += "Bitte geben Sie etwas ein bevor sie auf Speichern klicken.\n"; 
		}

		if (errorMessage.length() == 0) {
			return true;

		} else {
			// Show the error message.
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(dialogStage);
			alert.setTitle("Leeres Feld");
			alert.setHeaderText("Geben Sie erst etwas ins Textfeld ein bevor Sie weiterfahren.");
			alert.setContentText(errorMessage);

			alert.showAndWait();

			return false;
		}
	}

	/**
	 * Kontrolliert ob Vorschlag bereits in der Liste besteht
	 * 
	 * @param neuerVorschlag  neuer Vorschlag aus dem Textfeld
	 * @return unikat true wenn kein Duplikat vorhanden, false wenn Duplikat vorhanden
	 */
	//	public boolean istNeu(String neuerVorschlag) {
	//
	//		boolean	unikat = true;
	//		int i = 0;
	//		String alterVorschlag;
	//
	//		// Iteriere über vorhandene Vorschläge in der Liste
	//		while( 	unikat && (i < mainApp.getSuggestionData().size())) {
	//
	//			alterVorschlag = mainApp.getSuggestionData().get(i).getEntscheidung();
	//			i = i + 1;
	//			unikat = !alterVorschlag.toLowerCase().equals(neuerVorschlag.toLowerCase());
	//		}
	//		return unikat;
	//	}

	/**
	 * Adds a new entry to the item-list through a click on the button
	 * 
	 */

	public void handleAddButton() {

		// Wurde ein neuer Vorschlag im Textfeld für neue Vorschläge eingegeben?
		if(isInputValid()) {
			// Erzeuge String mit neuem Vorschlag aus dem Textfeld
			String newEntry = entry_field.getText().toString();
			// Erzeuge String mit neuem Vorschlag aus dem Textfeld
			String newDetails = details_field.getText().toString();

			//			if(istNeu(newEntry)) {
			// Füge Vorschlag der Liste hinzu und entferne den Vorschlag aus dem Textfeld
			iconData.add(new MapItemData(newEntry, newDetails));
			System.out.println("Der Vorschlag: '" + newEntry + "' wurde der Liste hinzugefügt.");
			entry_field.clear();
			details_field.clear();

		} else {
			// Gebe Fehlermeldung aus und gebe false zurück
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Vorschlag besteht bereits");
			alert.setHeaderText("Duplikation des Vorschlags");
			alert.setContentText("Der Vorschlag ist in der Liste bereits enthalten\n");

			alert.showAndWait();
		}
	}


	@FXML
	private void handleSave() {

		if (isInputValid()) {
			icon.setIconName(name_field.getText());
			icon.setIconData(item_table.getItems());

			dialogStage.close();
		}
	}

	/**
	 * Called when the user clicks on the delete button.
	 */
	@FXML
	private void handleDelete() {
		
		//TODO: Bessere Lösung finden

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.initOwner(dialogStage);
		alert.setTitle("Eintrag Löschen");
		alert.setHeaderText("Momentaner Workaround:");
		alert.setContentText("Um den Marker zu löschen, klicke auf Abbrechen"
				+ " und ziehe den Marker dann aus dem Bild hinaus.");

		alert.showAndWait();

	}


	@FXML
	private void handleCancle() {
		dialogStage.close();
	}

//	@FXML
//	private void handleEnter(KeyEvent event) {
//
//		if (event.getCode() == KeyCode.ENTER && isInputValid()) {
//			mapIcon.setItemInfo(name_field.getText());
//			dialogStage.close();
//		}
//	}
}