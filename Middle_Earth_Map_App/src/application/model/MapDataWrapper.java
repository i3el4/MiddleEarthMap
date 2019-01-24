package application.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import application.controller.DragIconController;

/**
 * Helper class to wrap a list of map icons. This is used for saving the
 * list of map icons to XML.
 * 
 * @author Bela Ackermann, with instructions from code.makery.ch
 */
@XmlRootElement(name = "map_icons")
public class MapDataWrapper {

	private List<DragIconController> mapIcons;

	@XmlElement(name = "map_icon")
	public List<DragIconController> getMapIcons() {
		return mapIcons;
	}

	public void setMapIcons(List<DragIconController> mapIcons) {
		this.mapIcons = mapIcons;
	}

}