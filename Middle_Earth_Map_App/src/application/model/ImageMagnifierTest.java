package application.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;

public class ImageMagnifierTest {


	private ImageMagnifier imageMan;
	private Node scrollcontent;
	@FXML ScrollPane image_scroll_pane;

	@FXML StackPane image_stack_pane;
	private Point2D scrollOffset;
	private double scaleFactor;
	
	@BeforeEach
	public void setUp() throws Exception {
		imageMan = new ImageMagnifier();
		scrollOffset = new Point2D(2,1);
		image_scroll_pane = new ScrollPane();
		scrollcontent = new Group();
	}
	

	@Test
	public void testRepositionScrollerWhenZero() throws Exception {
		scaleFactor=5;
		image_scroll_pane.setMaxHeight(3);
		image_scroll_pane.setMinHeight(3);
		image_scroll_pane.setMaxWidth(3);
		image_scroll_pane.setMinHeight(3);
		scrollcontent.maxHeight(3);
		scrollcontent.minHeight(3);
		scrollcontent.maxWidth(3);
		scrollcontent.minWidth(3);
		imageMan.repositionScroller(scrollcontent, image_scroll_pane, scaleFactor, scrollOffset);
		
		assertEquals(3, image_scroll_pane.getHvalue());
	}
	
	public void testRepositionScrollerWhenNotZero () throws Exception{
		scaleFactor=5;
		image_scroll_pane.setMaxHeight(4);
		image_scroll_pane.setMinHeight(4);
		image_scroll_pane.setMaxWidth(4);
		image_scroll_pane.setMinHeight(4);
		scrollcontent.maxHeight(6);
		scrollcontent.minHeight(6);
		scrollcontent.maxWidth(6);
		scrollcontent.minWidth(6);
		
		//extraWidth=2
		//halfWidth = 2
		//newScrollXOffset = 4*2 + 10 =18
		//4 + 18 * (4-4)/2 = 4
		
		imageMan.repositionScroller(scrollcontent, image_scroll_pane, scaleFactor, scrollOffset);
		assertEquals(4, image_scroll_pane.getHvalue());
	}
	




}
