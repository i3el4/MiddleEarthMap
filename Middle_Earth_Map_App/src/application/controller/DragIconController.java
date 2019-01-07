package application.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import application.model.DragIconType;
import application.model.MapItemData;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;

public class DragIconController extends AnchorPane{

	@FXML AnchorPane icon_root_pane;

	private DragIconType mType = null;
	
	private String name = null;
	
	private Tooltip tooltip;
		
	private boolean isIconDropped;
	
	private ObservableList<MapItemData> iconData = FXCollections.observableArrayList();

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

	@FXML
	private void initialize() {

		tooltip = new Tooltip("Ziehe dieses Icon über die Karte um einen Marker zu setzen");

		Tooltip.install(this, tooltip);

		isIconDropped = false;
		
	}
	
	public void setTooltipText() {
		tooltip.setText(name);
	}

	public String getTooltipText() {
		return tooltip.getText();
	}

	public void relocateToPoint (Point2D p) {

		//relocates the object to a point that has been converted to
		//scene coordinates
		Point2D localCoords = getParent().sceneToLocal(p);

		relocate ( 
				(int) (localCoords.getX() - (getBoundsInLocal().getWidth() / 2)),
				(int) (localCoords.getY() - (getBoundsInLocal().getHeight() / 2))
				);
	}

	public DragIconType getType () { return mType; }

	public void setType (DragIconType type) {

		mType = type;

		getStyleClass().clear();
		getStyleClass().add("dragicon");

		switch (mType) {

		case ALL:
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
	 * Returns the data as an observable list of map item information. 
	 * @return
	 */
	public ObservableList<MapItemData> getIconData() {
		return iconData;
	}

	public void setIconData(ObservableList<MapItemData> itemData) {
		iconData = itemData;

	}

	public void setIconName(String name) {
		this.name = name;
		setTooltipText();

	}

	public String getIconName() {
		return name;
	}
	
	public boolean getIconDropStatus() {
		return isIconDropped;
	}

	public void changeIconDropStatus() {
		isIconDropped = !isIconDropped;
	}

	public void setIconDropStatus(boolean iconDropStatus) {
		isIconDropped = iconDropStatus;		
	}

	public ArrayList<MapItemData> getSerializeableIconData() {
		
		ArrayList<MapItemData> data = new ArrayList<>();
		
		for(MapItemData item : iconData) {
			data.add(item);
		}
				
		return data;
	}

	public void setDeserializedIconData(ArrayList<MapItemData> deserializedData) {
		
		for(MapItemData item : deserializedData) {
			String name = item.getNameString();
			String description = item.getDescriptionString();
			iconData.add(new MapItemData(name, description));
		}
		
	}


}
