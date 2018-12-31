package application.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model class for a Decision.
 *
 * @author 	Bela Ackermann 
 * @version 	2018-10-18
 */
public class MapEntry {

    private final StringProperty entryInfo;
    private final StringProperty entryType;

    /**
     * Constructor with some initial data.
     * 
     * @param number
     * @param mapEntry
     */
    public MapEntry(String type, String mapEntry) {
        this.entryType = new SimpleStringProperty(type);
        this.entryInfo = new SimpleStringProperty(mapEntry);
    }
    
    /**
     * Default constructor.
     */
    public MapEntry() {
    	// invoke constructor that does assign the properties
        this(null, null);
    }
    
    public String getEntryInfo() {
        return entryInfo.get();
    }

    public void setEntryInfo(String mapEntry) {
        this.entryInfo.set(mapEntry);
    }
    
    public StringProperty entryInfoProperty() {
        return entryInfo;
    }
    

    public String getEntryType() {
        return entryType.get();
    }

    public void setEntryType(String type) {
        this.entryType.set(type);
    }
    
    public StringProperty entryTypeProperty() {
        return entryType;
    }
}
