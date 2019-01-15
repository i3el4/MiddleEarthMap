package application.model;

import java.io.Serializable;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model class for a MapItem.
 *
 * @author 	Bela Ackermann 
 * @version 	2018-10-18
 */
public class MapItemData implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 6915849518904841746L;
	private transient final StringProperty listItemAttribute;
    private transient final StringProperty listItemDescription;
    private String attributeString;
    private String descriptionString;

    /**
     * Constructor
     * 
     * @param attribute
     * @param description
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
