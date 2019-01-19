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
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Dialog to edit details of a map item.
 * 
 * @author Bela Ackermann
 */
public class MapItemEditDialogController {

	// TextField for adding map item name
	@FXML private TextField name_field;
	// TextField for adding item attributes
	@FXML private TextField attribute_field;
	// TextField for adding item attribute descriptions
	@FXML private TextField description_field;

	// Table for displaying item attributes with descriptions
	@FXML private TableView<MapItemData> item_table;
	@FXML private TableColumn<MapItemData, String> attributes_column;
	@FXML private TableColumn<MapItemData, String> descriptions_column;

	// dialog stage is opened for the clicked icon
	private Stage dialogStage;
	private DragIconController icon;
	private ObservableList<MapItemData> iconData = FXCollections.observableArrayList();

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
		attributes_column.setCellValueFactory(cellData -> cellData.getValue()
				.getListItemAttributeProperty());
		descriptions_column.setCellValueFactory(cellData -> cellData.getValue()
				.getListItemDescriptionProperty());

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

		if(icon.getAdditionalData() != null) {
			iconData = icon.getAdditionalData();
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
			alert.initModality(Modality.APPLICATION_MODAL);
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

		if ( 	attribute_field.getText() == null 
				|| attribute_field.getText().length() == 0 
				|| description_field.getText() == null 
				|| description_field.getText().length() == 0 	) {

			errorMessage += "Bitte geben Sie etwas in die entsprechenden Textfelder"
					+ " ein bevor sie auf Hinzufügen klicken.\n"; 

		}

		if (errorMessage.length() == 0) {
			return true;

		} else {
			// Show the error message.
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(dialogStage);
			alert.initModality(Modality.APPLICATION_MODAL);
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

		// Iterate over the existing list item
		while( 	uniqueItem && (i < iconData.size())) {

			existingData = iconData.get(i).getListItemAttribute();
			i = i + 1;
			uniqueItem = !existingData.toLowerCase().equals(newData.toLowerCase());
		}
		return uniqueItem;
	}

	/**
	 * Called when the user clicks on the add button
	 * Adds a new entry to the item-list through a the methode call addListItem()
	 * 
	 */
	public void handleAddButton() {

		// Is there textinput in the textfield for entry name and details?
		if( isListInputValid() ) {

			addListItem();

		}
	}

	/**
	 * Called when the user clicks on the save button
	 * Saves the data of the map item and closes the dialog stage
	 */
	@FXML
	private void handleSave() {

		if ( 	attribute_field.getText() != null 
				&& attribute_field.getText().length() != 0 
				&& description_field.getText() != null 
				&& description_field.getText().length() != 0 	) {

			addListItem();

		}

		if ( isNameInputValid() ) {

			icon.setIconName(name_field.getText());

			icon.setAdditionalData(item_table.getItems());

			dialogStage.close();

		}
	}

	/**
	 * Called when the user hits enter during an event.
	 * 
	 * @param event
	 */
	@FXML
	private void handleEnterPressed(KeyEvent event) {

		if (event.getCode() == KeyCode.ENTER && isListInputValid()) {

			addListItem();

		}
	}

	/**
	 * Adds the text in the entry and details textfield to the map data list
	 * after the the text has been added the textfields are cleared
	 */
	private void addListItem() {

		// create new string with data from the textfield
		String newEntry = attribute_field.getText().toString();
		String newDetails = description_field.getText().toString();

		if(isNew(newEntry)) {
			// Add new item to the list and clear the textfield
			iconData.add(new MapItemData(newEntry, newDetails));

			System.out.println("Der Eintrag: '" + newEntry + "' mit der Beschreibung: '" 
					+ newDetails + "' wurde dem Icon [" 
					+ name_field.getText().toString() + "] hinzugefügt.");

			attribute_field.clear();
			description_field.clear();

		} else {
			// throw error message
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(dialogStage);
			alert.initModality(Modality.APPLICATION_MODAL);
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
			alert.initModality(Modality.APPLICATION_MODAL);
			alert.setTitle("Keine Auswahl");
			alert.setHeaderText("Kein bestehender Listeneintrag ist ausgewählt");
			alert.setContentText("Bitte wähle einen Eintrag aus, denn du aus der Liste "
					+ "löschen möchtest.");

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
		alert.initModality(Modality.APPLICATION_MODAL);
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