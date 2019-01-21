package application.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import application.model.DragIconType;
import application.model.FixedTooltip;
import application.model.MapItemData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;

public class DragIconController extends AnchorPane implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6741952577473661325L;

	@FXML 
	private transient AnchorPane icon_root_pane;

	private DragIconType mType = null;

	private String name = null;	
	private transient Tooltip tooltip;

	private ObservableList<MapItemData> additionalData = FXCollections.observableArrayList();

	private boolean dropped;
	private double xCoordinates;
	private double yCoordinates;

	public DragIconController() {

		FXMLLoader fxmlLoader = new FXMLLoader(
				getClass().getResource("../view/DragIcon_view.fxml")
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
	 * Initialize the DragIconController:
	 * 
	 *  - Sets the Tooltip text of the source icons in the map side controll pane to a
	 *    default text.
	 *  - Installs the tooltip to the AnchorPane
	 *  - Sets the icon drop status to false
	 */
	@FXML
	private void initialize() {

		tooltip = new FixedTooltip("Ziehe dieses Icon über die Karte um einen Marker zu setzen");

		FixedTooltip.install(this, tooltip);

		dropped = false;

	}

	/**
	 * Set icon name and set tooltip information to name
	 * @param name  icon name and title
	 */
	public void setIconName(String name) {
		this.name = name;
		setTooltipText();
	}

	/**
	 * icon name getter
	 * @return name  name of icon as String
	 */
	public String getIconName() { return name; }

	/**
	 * Set the tooltip text according to the icon name
	 */
	public void setTooltipText() { tooltip.setText(name); }

	/**
	 * @return tooltip  tooltip text as String
	 */
	public String getTooltipText() { return tooltip.getText(); }

	/**
	 * get map drag icon type
	 * @return mType  type of the map icon
	 */
	public DragIconType getType () { return mType; }

	/**
	 * The appropriate StyleClass is chosen and added to the 
	 * drag icon.
	 * @param type	 map icon type
	 */
	public void setType (DragIconType type) {

		mType = type;

		getStyleClass().clear();
		getStyleClass().add("dragicon");

		switch (mType) {

		case HEADER:
			getStyleClass().add("icon-header");
			break;

		case PERSON:
			getStyleClass().add("icon-person");			
			break;

		case PLACE:
			getStyleClass().add("icon-place");
			break;

		case EVENT:
			getStyleClass().add("icon-event");
			break;

		case ASSORTED:
			getStyleClass().add("icon-assorted");
			break;

		default:
			break;
		}
	}

	/**
	 * get additional data of the icon 
	 * @return additionalData   map of Strings
	 */
	public ObservableList<MapItemData> getAdditionalData() {

		return additionalData;

	}

	/**
	 * Setter for additional data of the icon
	 * @param data  List of MapItemData that contains name and 
	 * 				description of the input
	 */
	public void setAdditionalData(ObservableList<MapItemData> data) {

		this.additionalData = data;

	}

	/**
	 * Update the position of the dragged icon. 
	 * 
	 * The method is called in the DragDetected() and DragOver() event handlers 
	 * and it updates the position of the icon based on coordinates transformed 
	 * to the Scene coordinate space
	 * @param p  new location point
	 */
	public void relocateToPoint (Point2D p) {

		//relocates the object to a point that has been converted to
		//scene coordinates
		Point2D localCoords = getParent().sceneToLocal(p);

		double x = localCoords.getX() - (getBoundsInLocal().getWidth() / 2);
		double y = localCoords.getY() - (getBoundsInLocal().getHeight() / 2);

		relocate ( (int) (x), (int) (y) );
	}

	/**
	 * Setter for the map coordinates of the icon
	 * @param newX
	 */
	public void setXCoordinates(double newX) { this.xCoordinates = newX; }
	/**
	 * Setter for the map coordinates of the icon
	 * @param newY  y-coordinate, of the map
	 */
	public void setYCoordinates(double newY) { this.yCoordinates = newY; }
	/**
	 * get map coordinate of the icon as a double
	 * @return xCoordinate
	 */
	public double getXCoordinates() { return xCoordinates; }
	/**
	 * get map coordinate of the icon as a double
	 * @return yCoordinate
	 */
	public double getYCoordinates() { return yCoordinates; }

	/**
	 *	Getter for icon drop status
	 * @return dropped  true, when icon is set on map
	 * 					false when it is the source icon
	 */
	public boolean isDroppedOnMap() { return dropped; }
	/**
	 * Setter for icon drop status
	 * @param newStatus   true, for icon on map
	 * 					  false for source icon 
	 */
	public void setIconDropStatus(boolean newStatus) { 
		dropped = newStatus;
	}

	/**
	 * changes the icon drop status
	 */
	public void changeIconDropStatus() { dropped = !dropped; }


	/**
	 * Make additional data serializable.
	 * ObservableList is not serializable thus it needs to be converted
	 * to ArraList.
	 * Not very good practice, but workaround to make MapItemData serializable
	 * for DragContainer -> adding String simultaneously to StringProperty
	 * @return data  serializable List of additional icon data
	 */
	public List<MapItemData> getSerializableIconData() {

		List<MapItemData> data = new ArrayList<>();

		for(MapItemData item : additionalData) {

			data.add(item);

		}

		return data;
	}

	/**
	 * Setter for the additional icon data of the ObservableList
	 * 
	 * the data has been serialized and deserialized during the drag and drop
	 * event. After the drag and drop event is completed the deserialized List
	 * is converted to an ObserveableList.
	 * 
	 * @param deserializedData  List of MapItemData with only String content
	 */
	public void setDeserializedIconData(List<MapItemData> deserializedData) {

		for(MapItemData item : deserializedData) {

			String name = item.getAttributeString();
			String description = item.getDescriptionString();
			additionalData.add(new MapItemData(name, description));

		}

	}


}
