package application.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MapItemDataTest {
	private MapItemData mapItem;
	private MapItemData mapItemNull;
	
	@BeforeEach
	public void setUp() {
		mapItem = new MapItemData("J.R.R. Tolkien","Autor");
		mapItemNull = new MapItemData();
	}
	
	@Test
	/**
     * Test if the object is initialized correctly.
     */
	public void testInit() throws Exception{
		assertEquals("J.R.R. Tolkien", mapItem.getAttributeString());
		assertEquals("Autor", mapItem.getDescriptionString());
		assertEquals("J.R.R. Tolkien", mapItem.getListItemAttribute());
		assertEquals("Autor", mapItem.getListItemDescription());
	}
	
	@Test
	/**
     * Test if the object is initialized correctly.
     */
	public void testInitDefault() throws Exception{
		assertEquals(null, mapItemNull.getAttributeString());
		assertEquals(null, mapItemNull.getDescriptionString());
	}
	
	@Test
	/**
     * Test if the object is initialized correctly.
     */
	public void testSetListItem() throws Exception{
		mapItemNull.setListItemAttribute();
		mapItemNull.setListItemDescription();
		assertEquals(null, mapItemNull.getListItemAttribute());
		assertEquals(null, mapItemNull.getListItemDescription());
	}

}