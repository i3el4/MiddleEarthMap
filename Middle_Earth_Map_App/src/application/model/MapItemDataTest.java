package application.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Junit Test of MapItemData.class
 * 
 * @author sonja läderach
 *
 */

class MapItemDataTest {
	private MapItemData mapItem;
	private MapItemData mapItemNull;
	
	@BeforeEach
	public void setUp() throws Exception{
		mapItem = new MapItemData("J.R.R. Tolkien","Autor");
		mapItemNull = new MapItemData();
	}
	
	@Test
	/**
     * Test if the object is initialized correctly by constructor.
     * @throws Exception 
     */
	public void testInit() throws Exception{
		
		assertEquals("J.R.R. Tolkien", mapItem.getAttributeString());
		assertEquals("Autor", mapItem.getDescriptionString());
		assertEquals("J.R.R. Tolkien", mapItem.getListItemAttribute());
		assertEquals("Autor", mapItem.getListItemDescription());
	}
	
	@Test
	/**
     * Test if the object is initialized correctly by default constructor.
     * @throws Exception 
     */
	public void testInitDefault() throws Exception{
		assertEquals(null, mapItemNull.getAttributeString());
		assertEquals(null, mapItemNull.getDescriptionString());
	}
	
	@Test
	/**
     * Test if the listitem attribute gets set correctly.
     * @throws Exception 
     */
	public void testSetListItemAttribute() throws Exception{
		mapItemNull.setListItemAttribute();
		mapItem.setListItemAttribute();
		assertEquals(null, mapItemNull.getListItemAttribute());
		assertEquals("J.R.R. Tolkien", mapItem.getListItemAttribute());
	}
	
	@Test
	/**
     * Test if the listitem Description gets set correctly.
     * @throws Exception 
     */
	public void testSetListItemDescription() throws Exception{
		mapItemNull.setListItemDescription();
		mapItem.setListItemDescription();
		assertEquals(null, mapItemNull.getListItemDescription());
		assertEquals("Autor", mapItem.getListItemDescription());
	}


}