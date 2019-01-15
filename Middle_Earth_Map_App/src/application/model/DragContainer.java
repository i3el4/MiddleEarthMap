package application.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.util.Pair;

public class DragContainer implements Serializable {

	/**
	 * Container class for the content that will store all the information 
	 * for restoring after the drag event is finished.
	 * 
	 * It inherits Serializable (required for custom Dragboard content)
	 * 
	 * It acts as a data model, storing data and providing get/set accessor methods:
	 * 
	 * An ArrayList is created to store key/value pairs using a Pair <String, Object> object.
	 * It provides a way to get the total list of data using getData() or accessing one 
	 * particular value by passing it’s key to getValue().
	 * 
	 * New key/value pairs can be added by using addData().
	 * 
	 */
	private static final long serialVersionUID = -1890998765646621338L;

	public static final DataFormat AddNode = 
			new DataFormat("application.DragIcon.add");
	
	private final List <Pair<String, Object> > mDataPairs = new ArrayList <Pair<String, Object> > ();
	
	public void addData (String key, Object value) {
		mDataPairs.add(new Pair<String, Object>(key, value));		
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getValue (String key) {
		
		for (Pair<String, Object> data: mDataPairs) {
			
			if (data.getKey().equals(key))
				return (T) data.getValue();
				
		}
		
		return null;
	}
	
	public List <Pair<String, Object> > getData () { return mDataPairs; }	
}
