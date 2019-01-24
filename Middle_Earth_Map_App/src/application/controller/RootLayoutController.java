package application.controller;

import java.io.IOException;

import application.model.DragContainer;
import application.model.DragIconType;
import application.model.ImageMagnifier;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class RootLayoutController extends AnchorPane{
	
	// FXML ANNOTATIONS FOR SETTING UP THE ROOTLAYOUT:
	//
	// SplitPane containing the image_scroll_pane on the right
	// and the controller_pane on the left side
	@FXML SplitPane base_pane;

	// ScrollPane containing the StackPane with the map
	@FXML ScrollPane image_scroll_pane;
	// Stackpane containing the map and panes for dropping the icons
	@FXML StackPane image_stack_pane;

	// image view containing the picture of the map
	@FXML ImageView map_pane;
	// panes for dropping the icons set in the image StackPane
	@FXML AnchorPane person_pane;
	@FXML AnchorPane place_pane;
	@FXML AnchorPane event_pane;
	@FXML AnchorPane assorted_pane;
	
	// pane for the sidebar controller of the map
	@FXML MapSidePaneController controller_pane;


	// VARIABLES FOR DRAG AND DROP:
	//
	// Drag Icon (extends AnchorPane) used for Drag and Drop events
	private DragIconController mDragOverIcon = null;
	
	// DragOver event handler for the root AnchorPane.
	private EventHandler<DragEvent> mIconDragOverRoot = null;
	//  DragOver event handler for the image pane.
	private EventHandler<DragEvent> mIconDragOverImagePane = null;
	// DragDropped event handler
	private EventHandler<DragEvent> mIconDragDropped = null;

	/**
	 * Constructor for the RootLayoutController
	 * 
	 * Sets the dynamic root and the controller of RootLayout_view.fxml
	 * and loads it.
	 */
	public RootLayoutController() {

		FXMLLoader fxmlLoader = new FXMLLoader(
				getClass().getResource("../view/RootLayout_view.fxml")
				);

		fxmlLoader.setRoot(this); 
		fxmlLoader.setController(this);

		try { 
			fxmlLoader.load();

		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}
	
	/**
	 * Initializes the dynamic root before the constructor is called
	 */
	@FXML
	private void initialize() {
		
		// Group of Stack and ScrollPane
		final Group group = new Group();
		image_stack_pane.getChildren().add(group);
		final Group scrollContent = new Group(image_stack_pane);
		image_scroll_pane.setContent(scrollContent);
		
		// sets the image while preserving the ratio
		map_pane.setImage(new Image("file:resources/images/ihypkemxzapuu.jpg"));
		map_pane.setPreserveRatio(true);

		// calls the class for zooming the image and sets the StackPane on zoom
		ImageMagnifier iM = new ImageMagnifier();
		iM.setImageOnScroll(image_stack_pane, image_scroll_pane, scrollContent);
		iM.setCursor(image_stack_pane);
		// set the ScrollPane pannable
		image_scroll_pane.setPannable(true);
		
		// center the scroll contents.
		image_scroll_pane.setHvalue(image_scroll_pane.getHmin()
				+ (image_scroll_pane.getHmax() - image_scroll_pane.getHmin()) / 2);
		image_scroll_pane.setVvalue(image_scroll_pane.getVmin()
				+ (image_scroll_pane.getVmax() - image_scroll_pane.getVmin()) / 2);
	
		// Add one icon that will be used for the drag-drop process
		// This is added as a child to the root anchorpane so it can be visible
		// on both sides of the split pane.
		mDragOverIcon = new DragIconController();

		mDragOverIcon.setVisible(false);
		mDragOverIcon.setOpacity(0.65);
		
		getChildren().add(mDragOverIcon);

		// populate the controller_pane with the header button and 
		// multiple colored buttons for manipulating the visible map panes
		// the buttons contain the drag and drop icon for marking the map
		for (int i = 0; i < 5; i++) {

			MapButtonController btn = new MapButtonController();
			addPaneChanger(btn);
			addDragDetection(btn.getButton_icon());

			// set button type according to the icon type
			btn.setButtonType(DragIconType.values()[i]);
			controller_pane.getChildren().add(btn);
		}

		// Adds the button bar for saving and loading map data
		SaveAndLoadController btnCtrl = new SaveAndLoadController();
		controller_pane.getChildren().add(btnCtrl);

		buildDragHandlers();

	}


	/**
	 * Compare the param "type" with all the enums that are specified after
	 * case and return the target pane as an AnchorPane or null as the 
	 * default case
	 * 
	 * @param   type the drag icon type of the dropepd icon
	 * 
	 * @return  the target pane for the drop event
	 */
	public AnchorPane getDroppablePane(DragIconType type) {
		switch (type) {

		case PERSON:
			return person_pane;	

		case PLACE:
			return place_pane;

		case EVENT:
			return event_pane;

		case ASSORTED:
			return assorted_pane;

		default:
			return null;
		}
	}
	
	/**
	 * addPaneChanger for button action to change the pane, where icons will be
	 * dropped on. Changes the stack order of the imae_stack_pane and sets 
	 * visibility
	 * 
	 * @param button    map controller button in the map controller pane
	 */

	private void addPaneChanger(MapButtonController button) {

		button.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// list of the panes in the image StackPane
				ObservableList<Node> children = image_stack_pane.getChildren();
				
				// gets the type of button that is clicked and gets the
				// according pane for the drop event 
				AnchorPane currentPane = getDroppablePane(button.getType());

				// iterates over the list of panes. calls the pane to the
				// front that is from the same type as the clicked button
				// makes all other panes transparent and sends the 
				// map_pane to the back of the stack
				for( int i = children.size() - 1 ; i >= 0 ; i-- ) {

					if( currentPane == children.get(i) ) {

						// Put the concurrent pane to the front
						currentPane.toFront();
						currentPane.setVisible(true);

					} else if (children.get(i) != map_pane) {

						children.get(i).setVisible(false);
						map_pane.toBack();
					}
				}
			}
		});
	}

	/**
	 * Creates the drag detection. First, all of the relevant event handlers for
	 * the remainder of the drag-drop operation are added to their appropriate 
	 * targets. Second, drag operations are begun using the extra, hidden 
	 * DragIconController.
	 * 
	 * @param dragIcon
	 */
	public void addDragDetection(DragIconController dragIcon) {

		dragIcon.setOnDragDetected (new EventHandler <MouseEvent> () {

			@Override
			public void handle(MouseEvent event) {

				// set drag event handlers on their respective objects
				base_pane.setOnDragOver(mIconDragOverRoot);
				image_stack_pane.setOnDragOver(mIconDragOverImagePane);
				image_stack_pane.setOnDragDropped(mIconDragDropped);

				// get a reference to the clicked DragIcon object
				DragIconController icn = (DragIconController) event.getSource();

				// get the pane from which the icon for dragging is sitting on
				// if icon type is null, the source pane is the controller_pane			
				AnchorPane sourcePane = getDroppablePane(icn.getType());
				
				// if the pane is not the controller_pane remove the source icon
				// after the drag is detected --> cutting 
				// else do nothing with the source icon --> copying
				if( sourcePane != null ) {
					sourcePane.getChildren().remove(icn);
				}

				// begin drag operations by setting the type of mDragOverIcon to 
				// the type of the selected DragIcon
				mDragOverIcon.setType(icn.getType());
				// relocate the mDragOverIcon
				mDragOverIcon.relocateToPoint(new Point2D (	event.getSceneX(), 
															event.getSceneY()));
				// creates a container for the clipboard content that will store
				// all the information that needs to be restored after the drag event 
				// is finished
				ClipboardContent content = new ClipboardContent();
				DragContainer container = new DragContainer();
				// information that needs to be stored during the drag event:
				// icon type, name and data (stored as a serializeable list) 
				// also the status if the picked up icon has been dropped on the map
				// before
				container.addData ("type", 		icn.getType().toString());
				container.addData ("name", 		icn.getIconName());
				container.addData ("data", 		icn.getSerializableIconData());
				container.addData ("status", 	icn.getIconDropStatus());
		
				content.put(DragContainer.AddNode, container);

				mDragOverIcon.startDragAndDrop (TransferMode.ANY).setContent(content);
				
				// making the hidden mDragOverIcon visible for duration of drag event
				mDragOverIcon.setVisible(true);
				mDragOverIcon.setMouseTransparent(true);
				event.consume();		
				

			}
		});
	}	

	/**
	 * Builds the drag event handlers:
	 * 
	 * DragOver event handler for the root AnchorPane. Whenever it’s triggered, 
	 * it tests the mouse cursor’s coordinates against the bounds of the right-hand 
	 * ScrollPane.  If the cursor is within the bounds, mDragOverIcon is relocated to 
	 * follow the cursor.
	 * 
	 * DragOver event handler for the image pane. Technically, this handler could be
	 * avoided if the image pane’s mouse transparency is set to true. That way, the root
	 * pane’s DragOver code would still have control.  However, making the right pane 
	 * transparent to mouse movements would prove problematic.  Thus, both DragOver 
	 * event handlers are needed for smooth and consistent dragging between panes.
	 * 
	 * DragDropped event handler indicates that the drag event completed successfully, 
	 * hides the drag over icon, and removes the event handlers that were installed 
	 * previously. And assigns the data stored in the drag container to the dropped icon. 
	 * 
	 * Finally to remove the drag event handlers that were added in the DragDetected 
	 * event a DragDone event handler is created.
	 */
	
	private void buildDragHandlers() {

		//drag over transition to move widget form left pane to right pane
		mIconDragOverRoot = new EventHandler <DragEvent>() {

			@Override
			public void handle(DragEvent event) {

				Point2D p = image_stack_pane.sceneToLocal(event.getSceneX(), 
						event.getSceneY());

				//turn on transfer mode and track in the right-pane's context 
				//if (and only if) the mouse cursor falls within the right pane's bounds.
				if (!image_stack_pane.boundsInLocalProperty().get().contains(p)) {

					event.acceptTransferModes(TransferMode.ANY);
					mDragOverIcon.relocateToPoint(new Point2D(event.getSceneX(), 
							event.getSceneY()));
					return;
				}

				event.consume();
			}
		};

		mIconDragOverImagePane = new EventHandler <DragEvent> () {

			@Override
			public void handle(DragEvent event) {

				event.acceptTransferModes(TransferMode.ANY);

				// convert the mouse coordinates to scene coordinates,
				// then convert back to coordinates that are relative to 
				// the parent of mDragIcon.  Since mDragIcon is a child of the root
				// pane, coordinates must be in the root pane's coordinate system to 
				// work properly.
				mDragOverIcon.relocateToPoint(
						new Point2D(event.getSceneX(), event.getSceneY())
						);
				event.consume();
			}
		};

		mIconDragDropped = new EventHandler <DragEvent> () {

			@Override
			public void handle(DragEvent event) {

				DragContainer container = 
				(DragContainer) event.getDragboard().getContent(DragContainer.AddNode );

				container.addData ( "scene_coords", 
						new Point2D( event.getSceneX(), event.getSceneY() ) );

				ClipboardContent content = new ClipboardContent();
				content.put(DragContainer.AddNode, container);

				event.getDragboard().setContent(content);
				event.setDropCompleted(true);
			}
		};

		this.setOnDragDone (new EventHandler <DragEvent> (){

			@Override
			public void handle (DragEvent event) {

				image_stack_pane.removeEventHandler(DragEvent.DRAG_OVER, 
						mIconDragOverImagePane);
				image_stack_pane.removeEventHandler(DragEvent.DRAG_DROPPED, 
						mIconDragDropped);
				base_pane.removeEventHandler(DragEvent.DRAG_OVER, 
						mIconDragOverRoot);

				mDragOverIcon.setVisible(false);

				DragContainer container = 
						(DragContainer) event.getDragboard().getContent(DragContainer.AddNode);

				if (container != null) {
					if (container.getValue("scene_coords") != null) {

						DragIconController droppedIcon = new DragIconController();

						// gets the data from the source icon that was stored in the
						// drag container and assigns it to the dropped icon
						droppedIcon.setType(DragIconType.valueOf(
														container.getValue("type")));
						droppedIcon.setIconName(		container.getValue("name"));
						droppedIcon.setDeserializedIconData(
														container.getValue("data"));
						droppedIcon.setIconDropStatus(	container.getValue("status"));


						DragIconType mType = mDragOverIcon.getType();
						AnchorPane currentPane = getDroppablePane(mType);
						currentPane.toFront();
						currentPane.setVisible(true);
						currentPane.getChildren().add(droppedIcon);

						Point2D cursorPoint = container.getValue("scene_coords");

						droppedIcon.relocateToPoint(
								new Point2D( cursorPoint.getX() - 34, 
											 cursorPoint.getY() - 34 ) 
								);
						// sets the coordinates of the dropped icon for storing
						droppedIcon.setXCoordinates(cursorPoint.getX());
						droppedIcon.setYCoordinates(cursorPoint.getY());
						
						// When the Icon is dropped the first time open a dialog stage 
						// to edit the icon information and set the status of the icon 
						// to "dropped"
						if( ! droppedIcon.getIconDropStatus() ) {

						showMapItemEditDialog(droppedIcon);
						droppedIcon.changeIconDropStatus();
						
						}
						
						// add a click event to dropped icon
						addEditDialogOpener(droppedIcon);
						// make the dropped Icon dragable again
						addDragDetection(droppedIcon);
					}
				}
				
				event.consume();
			}
		});
	}

	/**
	 * Opens a dialog to edit details for the specified map items. If the user
	 * clicks save, the changes are saved map data list of the object and true
	 * is returned.
	 * 
	 * @param droppedDragIcon the icon to be edited
	 * @return true if the user clicked save, false otherwise.
	 */
	public boolean showMapItemEditDialog(DragIconController droppedDragIcon) {
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(RootLayoutController.class.getResource( 
					"../view/MapItemEditDialog_view.fxml" ) );
			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			// Set the application icon.
			dialogStage.getIcons().add(new Image("file:resources/images/TolkienIcon.jpg"));
			dialogStage.setTitle("Kartenmarkierung bearbeiten");
			
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(null);
	        dialogStage.setAlwaysOnTop(true);
			
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			
			// Sets the location of the dialog stage to the location of the dropped icon
			dialogStage.setX(droppedDragIcon.getXCoordinates());
			dialogStage.setY(droppedDragIcon.getYCoordinates());

			// Set the icon into the controller.
			MapItemEditDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setMapItem(droppedDragIcon);			

			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();
			return true;

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Called when the icon is dropped for the first time or when the user clicks on the
	 * icon. Opens a dialog to edit details for the clicked icon.
	 * 
	 * @param dragIcon   icon that is dropped
	 */

	public void addEditDialogOpener(DragIconController dragIcon) {

		dragIcon.setOnMouseClicked(new EventHandler <MouseEvent> () {
			@Override
			public void handle(MouseEvent event) {
				
				showMapItemEditDialog(dragIcon);
				
				}
		});
	}
}

