package application.model;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.util.Pair;

/**
 * Junit Test of DragContainer.class
 * 
 * @author sonja läderach
 *
 */
public class DragContainerTest {
	
	private DragContainer container;
	private Object object1; 
	private Object object2;
	
	@BeforeEach
	public void setUp() throws Exception  {
		container = new DragContainer();
		object1 = new Object();
		object2 = new Object();
		new Object();
	}

	@Test
	/**
     * Test if the object gets added to the list and returned correctly  
     * @throws Exception 
     */
	void testAddData() throws Exception  {
		String key = new String("Tarzan");
		container.addData(key, object1);
		
		assertEquals(object1, container.getValue("Tarzan"));
	}
	
	@Test
	/**
	 * Test if multiple objects get added to the list correctly
	 * @throws Exception
	 */
	void testAddMultipleData() throws Exception {
		String key1 = new String("Tarzan");
		String key2 = new String("Jane");
		
		container.addData(key1, object1);
		container.addData(key2, object2);
		
		assertEquals(2, container.getNumberOfData());
	}
	


	@Test
	/**
     * Test if the pairs get stored and returned correctly.
     * @throws Exception
     */
	void testGetData() throws Exception {
		String key = new String("Tarzan");
		container.addData(key, object1);
		
		Pair <String, Object> expectedPair1 = new Pair<String, Object>(key,object1);
		List <Pair<String, Object> > expectedList = new ArrayList <Pair<String, Object> > ();
		expectedList.add(expectedPair1);
		
		assertEquals(expectedList, container.getData());
	}


}
