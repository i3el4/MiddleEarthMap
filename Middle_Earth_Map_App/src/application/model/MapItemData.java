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
	private transient final StringProperty listItemName;
    private transient final StringProperty listItemDescription;
    private String nameString;
    private String descriptionString;

    /**
     * Constructor with some initial data.
     * 
     * @param name
     * @param description
     */
    public MapItemData(String name, String description) {
        this.listItemName = new SimpleStringProperty(name);
        this.listItemDescription = new SimpleStringProperty(description);
        this.nameString = name;
        this.descriptionString = description;
    }
    
    /**
     * Default constructor.
     */
    public MapItemData() {
    	// invoke constructor that assigns the properties
        this(null, null);
    }
  
    public String getListItemName() {
        return listItemName.get();
    }

    public void setListItemName() {
        this.listItemName.set(nameString);
    }
    
    public StringProperty getListItemNameProperty() {
        return listItemName;
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

	public String getNameString() {
		return nameString;
	}

	public String getDescriptionString() {
		return descriptionString;
	}
}
