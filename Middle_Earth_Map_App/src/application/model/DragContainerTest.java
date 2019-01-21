package application.model;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.util.Pair;

class DragContainerTest {
	
	public DragContainer container;
	public Object object1; 
	public Object object2;
	
	@BeforeEach
	public void setUp() {
		container = new DragContainer();
		object1 = new Object();
		object2 = new Object();
	}

	@Test
	/**
     * Test if the object gets added to the list.   
     */
	void testAddData() {
		String key = new String("Tarzan");
		container.addData(key, object1);
		
		assertEquals(object1, container.getValue("Tarzan"));
		assertEquals(1, container.getNumberOfData());
	}
	


	@Test
	/**
     * Test if the object is initialized correctly.
     */
	void testGetData() {
		String key = new String("Tarzan");
		container.addData(key, object1);
		
		Pair <String, Object> expectedPair1 = new Pair<String, Object>(key,object1);
		List <Pair<String, Object> > expectedList = new ArrayList <Pair<String, Object> > ();
		expectedList.add(expectedPair1);
		
		assertEquals(expectedList, container.getData());
	}
	

}
