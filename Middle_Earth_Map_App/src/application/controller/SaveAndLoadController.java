package application.controller;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import application.model.MapDataWrapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;

/**
 * Controller class for SaveAndLoad_view.xml. 
 *  
 * The class momentarily only contains the loader for the fxml and sets
 * a dynamic root.
 * 
 * TODO: the buttons initialized in this class should later have added
 * methods to load and save the content of the map. Possible methods were
 * already added to this class but do not work yet, hence they are commented
 * out.
 * 
 * @author Bela Ackermann
 */
public class SaveAndLoadController extends VBox {

	private List<DragIconController> mapIcons;
	private MapDataWrapper wrapper;

	public SaveAndLoadController() {
		FXMLLoader fxmlLoader = new FXMLLoader(
				getClass().getResource("../view/SaveAndLoad_view.fxml")
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
	private void initialize() {

		//TODO: Add methodes to save and load content
		// Try to load last opened map file.
		File file = getMapDataFilePath();
		if (file != null) {
			loadMapDataFromFile(file);
		}

	}



	/**
	 * Loads map data from the specified file. The current map data will
	 * be replaced.
	 * 
	 * @param file
	 */
	public void loadMapDataFromFile(File file) {
		try {
			JAXBContext context = JAXBContext
					.newInstance(MapDataWrapper.class);
			Unmarshaller um = context.createUnmarshaller();

			// Reading XML from the file and unmarshalling.
			MapDataWrapper wrapper = (MapDataWrapper) um.unmarshal(file);

			mapIcons.clear();
			mapIcons.addAll(wrapper.getMapIcons());

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
		// Wrapping our map icon list.
		wrapper = new MapDataWrapper();
		wrapper.setMapIcons(mapIcons);
	}


	/**
	 * Saves the current map data to the specified file.
	 * 
	 * @param file
	 */
	public void saveMapDataToFile(File file) {
		try {
			JAXBContext context = JAXBContext
					.newInstance(MapDataWrapper.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			// Wrapping our suggestion data.
			MapDataWrapper wrapper = new MapDataWrapper();
			wrapper.setMapIcons(mapIcons);

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
	 * Returns the suggestion file preference, i.e. the file that was last opened.
	 * The preference is read from the OS specific registry. If no such
	 * preference can be found, null is returned.
	 * 
	 * @return
	 */
	public File getMapDataFilePath() {
		Preferences prefs = Preferences.userNodeForPackage(SaveAndLoadController.class);
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
		Preferences prefs = Preferences.userNodeForPackage(SaveAndLoadController.class);
		if (file != null) {
			
			prefs.put("filePath", file.getPath());

		} else {
			
			prefs.remove("filePath");

		}
	}
	

}
