package application;

import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import application.controller.RootLayoutController;
import application.model.MapEntriesWrapper;
import application.model.MapEntry;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


public class MainApp extends Application {
	
	private Stage primaryStage;
	private BorderPane rootLayout;

	/**
	 * The data as an observable list of suggestions.
	 */
	private ObservableList<MapEntry> mapData = FXCollections.observableArrayList();
	private MapEntriesWrapper wrapper;

	/**
	 * Constructor
	 */
	public MainApp() {

		// Add some sample data
		mapData.add(new MapEntry("Olaf", "Rollenspielen"));
		mapData.add(new MapEntry("Beutelsend", "Schlafen"));

	}

	/**
	 * Returns the data as an observable list of suggestions. 
	 * @return
	 */
	public ObservableList<MapEntry> getMapData() {
		return mapData;
	}

	@Override
	public void start(Stage primaryStage) {
		
		this.primaryStage = primaryStage;
		
		// Set the application Title
		this.primaryStage.setTitle("Karte von Mittelerde");
		// Set the application icon.
		this.primaryStage.getIcons().add(new Image("file:resources/images/TolkienIcon.jpg"));
		
		initRootLayout();
	}
	
	/**
	 * Initializes the root layout and tries to load the last opened
	 * suggestion file.
	 */
	public void initRootLayout() {
		
		rootLayout = new BorderPane();

		try {
			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout,1100,900);
			scene.getStylesheets().add(getClass().getResource("view/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch(Exception e) {
			e.printStackTrace();
		}

		rootLayout.setCenter(new RootLayoutController());
		
	
		// Try to load last opened suggestion file.
		File file = getMapDataFilePath();
		if (file != null) {
			loadMapDataFromFile(file);
		}
	}

	/**
	 * Loads suggestion data from the specified file. The current suggestion data will
	 * be replaced.
	 * 
	 * @param file
	 */
	public void loadMapDataFromFile(File file) {
		try {
			JAXBContext context = JAXBContext
					.newInstance(MapEntriesWrapper.class);
			Unmarshaller um = context.createUnmarshaller();

			// Reading XML from the file and unmarshalling.
			MapEntriesWrapper wrapper = (MapEntriesWrapper) um.unmarshal(file);


			mapData.clear();
			mapData.addAll(wrapper.getMapEntries());

			// Save the file path to the registry.
			setMapDataFilePath(file);

		} catch (Exception e) { // catches ANY exception
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Could not load data");
			alert.setContentText("Could not load data from file:\n" + file.getPath());

			alert.showAndWait();
		}
	}

	public void wrapMapData() {
		// Wrapping our suggestion data.
		wrapper = new MapEntriesWrapper();
		wrapper.setMapEntries(mapData);
	}


	/**
	 * Saves the current suggestion data to the specified file.
	 * 
	 * @param file
	 */
	public void saveMapDataToFile(File file) {
		try {
			JAXBContext context = JAXBContext
					.newInstance(MapEntriesWrapper.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			// Wrapping our suggestion data.
			MapEntriesWrapper wrapper = new MapEntriesWrapper();
			wrapper.setMapEntries(mapData);

			// Marshalling and saving XML to the file.
			m.marshal(wrapper, file);

			// Save the file path to the registry.
			setMapDataFilePath(file);

		} catch (Exception e) { // catches ANY exception
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Could not save data");
			alert.setContentText("Could not save data to file:\n" + file.getPath());

			alert.showAndWait();
		}
	}

	/**
	 * Opens a dialog to edit details for the specified suggestion. If the user
	 * clicks OK, the changes are saved into the provided suggestion object and true
	 * is returned.
	 * 
	 * @param mapEntry the suggestion object to be edited
	 * @return true if the user clicked OK, false otherwise.
	 */
	public boolean showMapEntryEditDialog(MapEntry mapEntry) {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/MapEntryEditDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			// Set the application icon.
			dialogStage.getIcons().add(new Image("file:resources/images/TolkienIcon.jpg"));
			dialogStage.setTitle("Karten Eintrag bearbeiten");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set the suggestion into the controller.
//			MapEntryEditDialogController controller = loader.getController();
//			controller.setDialogStage(dialogStage);
//			controller.setMapEntry(mapEntry);

			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();
			return true;

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Returns the suggestion file preference, i.e. the file that was last opened.
	 * The preference is read from the OS specific registry. If no such
	 * preference can be found, null is returned.
	 * 
	 * @return
	 */
	public File getMapDataFilePath() {
		Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
		String filePath = prefs.get("filePath", null);
		System.out.println(filePath);
		if (filePath != null) {
			return new File(filePath);
		} else {
			return null;
		}
	}

	/**
	 * Sets the file path of the currently loaded file. The path is persisted in
	 * the OS specific registry.
	 * 
	 * @param file the file or null to remove the path
	 */
	public void setMapDataFilePath(File file) {
		Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
		if (file != null) {
			prefs.put("filePath", file.getPath());

			// Update the stage title.
			primaryStage.setTitle("Mittelerde Karte - " + file.getName());
		} else {
			prefs.remove("filePath");

			// Update the stage title.
			primaryStage.setTitle("Mittelerde Karte");
		}
	}


	public static void main(String[] args) {
		launch(args);
	}
}
