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
		assertEquals("J.R.R. Tolkien", mapItem.getNameString());
		assertEquals("Autor", mapItem.getDescriptionString());
		assertEquals("J.R.R. Tolkien", mapItem.getListItemName());
		assertEquals("Autor", mapItem.getListItemDescription());
	}
	
	@Test
	/**
     * Test if the object is initialized correctly.
     */
	public void testInitDefault() throws Exception{
		assertEquals(null, mapItemNull.getNameString());
		assertEquals(null, mapItemNull.getDescriptionString());
	}
	
	@Test
	/**
     * Test if the object is initialized correctly.
     */
	public void testSetListItem() throws Exception{
		mapItemNull.setListItemName();
		mapItemNull.setListItemDescription();
		assertEquals(null, mapItemNull.getListItemName());
		assertEquals(null, mapItemNull.getListItemDescription());
	}

}