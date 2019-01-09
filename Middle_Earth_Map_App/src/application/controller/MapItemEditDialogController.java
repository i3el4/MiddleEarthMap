package application.controller;


import application.model.MapItemData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
	 * Validates the user input in the name text field.
	 * 
	 * @return true if the input is valid
	 */
	private boolean isNameInputValid() {

		String errorMessage = "";

		if (name_field.getText() == null || name_field.getText().length() == 0) {
			errorMessage += "Bitte geben Sie erst einen Namen in das Textfeld"
					+ " ein bevor sie auf Speichern klicken.\n"; 
		}

		if (errorMessage.length() == 0) {
			return true;

		} else {
			// Show the error message.
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(dialogStage);
			alert.setTitle("Leeres Feld");
			alert.setHeaderText("Ein Eingabefeld ist leer!");
			alert.setContentText(errorMessage);

			alert.showAndWait();

			return false;
		}
	}

	/**
	 * Validates the user input in the list item text fields.
	 * 
	 * @return true if the input is valid
	 */
	private boolean isListInputValid() {

		String errorMessage = "";

		if ( entry_field.getText() == null || entry_field.getText().length() == 0 ||
				details_field.getText() == null || details_field.getText().length() == 0	) {
			errorMessage += "Bitte geben Sie etwas in die entsprechenden Textfelder"
					+ " ein bevor sie auf Hinzufügen klicken.\n"; 
		}

		if (errorMessage.length() == 0) {
			return true;

		} else {
			// Show the error message.
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(dialogStage);
			alert.setTitle("Leeres Feld");
			alert.setHeaderText("Eins oder beide Eingabefelder sind leer!");
			alert.setContentText(errorMessage);

			alert.showAndWait();

			return false;
		}
	}

	/**
	 * Checks is the data entry is new and does not exist already in the list
	 * 
	 * @param newData  new data entry from the textfield
	 * @return uniqueItem true if there is no duplicate in the existing list
	 */
	public boolean isNew(String newData) {

		boolean	uniqueItem = true;
		int i = 0;
		String existingData;

		// Iteriere über vorhandene Vorschläge in der Liste
		while( 	uniqueItem && (i < iconData.size())) {

			existingData = iconData.get(i).getListItemName();
			i = i + 1;
			uniqueItem = !existingData.toLowerCase().equals(newData.toLowerCase());
		}
		return uniqueItem;
	}

	/**
	 * Adds a new entry to the item-list through a click on the button
	 * 
	 */
	public void handleAddButton() {

		// Is there textinput in the textfield for entry name and details?
		if( isListInputValid() ) {

			addListItem();

		}
	}


	@FXML
	private void handleSave() {

		if ( 	entry_field.getText() != null && entry_field.getText().length() != 0 &&
				details_field.getText() != null && details_field.getText().length() != 0  ) {

			addListItem();

		}

		if ( isNameInputValid() ) {

			icon.setIconName(name_field.getText());

			icon.setIconData(item_table.getItems());

			dialogStage.close();

		}
	}

	@FXML
	private void handleEnterPressed(KeyEvent event) {

		if (event.getCode() == KeyCode.ENTER && isListInputValid()) {

			addListItem();

		}
	}

	private void addListItem() {

		// create new string with data from the textfield
		String newEntry = entry_field.getText().toString();
		String newDetails = details_field.getText().toString();

		if(isNew(newEntry)) {
			// Add new item to the list and clear the textfield
			iconData.add(new MapItemData(newEntry, newDetails));

			System.out.println("Der Eintrag: '" + newEntry + "' mit der Beschreibung: '" 
					+ newDetails + "' wurde dem Icon [" 
					+ name_field.getText().toString() + "] hinzugefügt.");

			entry_field.clear();
			details_field.clear();

		} else {
			// throw error message
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Listeneintrag besteht bereits");
			alert.setHeaderText("Mögliche Duplikation des Eintrages");
			alert.setContentText("Ein Eintrag mit dem selben Namen ist "
					+ "in der Liste bereits enthalten\n");

			alert.showAndWait();
		}
	}

	/**
	 * Called when the user clicks on the list item delete button.
	 */
	@FXML
	private void handleDeleteListItem() {

		int selectedIndex = item_table.getSelectionModel().getSelectedIndex();

		if (selectedIndex >= 0) {
			item_table.getItems().remove(selectedIndex);
		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(dialogStage);
			alert.setTitle("Keine Auswahl");
			alert.setHeaderText("Kein bestehender Listeneintrag ist ausgewählt");
			alert.setContentText("Bitte wähle einen Eintrag aus, denn du aus der Liste löschen möchtest.");

			alert.showAndWait();
		}
	}

	/**
	 * Called when the user clicks on the delete button.
	 */
	@FXML
	private void handleDeleteAll() {

		//TODO: Bessere Lösung finden

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.initOwner(dialogStage);
		alert.setTitle("Markierung Löschen ist so nicht möglich!");
		alert.setHeaderText("Momentaner Workaround:");
		alert.setContentText("Um den Marker zu löschen, klicke auf Abbrechen"
				+ " und ziehe den Marker dann aus dem Bild hinaus.");

		alert.showAndWait();

	}

	/**
	 * Called when the user clicks on the cancle button.
	 */
	@FXML
	private void handleCancle() {
		dialogStage.close();
	}
}