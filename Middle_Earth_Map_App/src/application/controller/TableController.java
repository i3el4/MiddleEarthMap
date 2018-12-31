package application.controller;

import java.io.IOException;

import application.MainApp;
import application.model.MapEntry;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

public class TableController extends AnchorPane {

	@FXML
	private AnchorPane table_root_pane;

	@FXML
	private TableView<MapEntry> mapTable;
	@FXML
	private TableColumn<MapEntry, String> typeColumn;
	@FXML
	private TableColumn<MapEntry, String> infoColumn;

	// Reference to the main application.
	private MainApp mainApp;

	/**
	 * The constructor.
	 * The constructor is called before the initialize() method.
	 */
	public TableController() {
		FXMLLoader fxmlLoader = new FXMLLoader(
				getClass().getResource("../view/Table_view.fxml")
				);

		fxmlLoader.setRoot(this); 
		fxmlLoader.setController(this);

		try { 
			fxmlLoader.load();

		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {

		//
		// Initialize the map table with the two columns
		typeColumn.setCellValueFactory(cellData -> cellData.getValue().entryTypeProperty());
		infoColumn.setCellValueFactory(cellData -> cellData.getValue().entryInfoProperty());
	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		
		System.out.println("hall");

		for(int i = 0; i < 1; i++) {
			String text = mainApp.getMapData().get(i).getEntryType();
			System.out.println(text);
			
		}
			
		
		// Add observable list data to the table
		mapTable.setItems(mainApp.getMapData());
	}
}
