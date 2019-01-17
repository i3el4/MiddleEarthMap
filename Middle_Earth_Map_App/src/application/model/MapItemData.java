package application.model;

import java.io.Serializable;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Helper class to wrap a list of map items. This is used for saving the
 * list of map items to XML and for the DragContainer.
 * 
 * @author bela.ackermann (with instructions from code.makery.ch)
 */

public class MapItemData implements Serializable{

    /**
	 * Serializable data fields. StringProperty is not serializable thus
	 * it is set on transient.
	 * Not very good practice, but workaround to make MapItemData serializable
	 * for DragContainer -> adding String simultaneously to StringProperty
	 */
	private static final long serialVersionUID = 6915849518904841746L;
	private transient final StringProperty listItemAttribute;
    private transient final StringProperty listItemDescription;
    private String attributeString;
    private String descriptionString;

    /**
     * Constructor
     * 
     * @param attribute    item attribute name
     * @param description  item description
     */
    public MapItemData(String attribute, String description) {
        this.listItemAttribute = new SimpleStringProperty(attribute);
        this.listItemDescription = new SimpleStringProperty(description);
        this.attributeString = attribute;
        this.descriptionString = description;
    }
    
    /**
     * Default constructor.
     */
    public MapItemData() {
    	// invoke constructor that assigns the properties
        this(null, null);
    }
    
    /**
     * Setters and Getters for String and StringProperty of the Items
     * 
     */
    
    public String getListItemAttribute() {
        return listItemAttribute.get();
    }

    public void setListItemAttribute() {
        this.listItemAttribute.set(attributeString);
    }
    
    public StringProperty getListItemAttributeProperty() {
        return listItemAttribute;
    }

    public String getListItemDescription() {
        return listItemDescription.get();
    }

    public void setListItemDescription() {
        this.listItemDescription.set(descriptionString);
    }
    
    public StringProperty getListItemDescriptionProperty() {
        return listItemDescription;
    }

	public String getAttributeString() {
		return attributeString;
	}

	public String getDescriptionString() {
		return descriptionString;
	}
}
