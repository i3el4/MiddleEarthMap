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
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
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

	@FXML SplitPane base_pane;

	@FXML ImageView map_image;
	@FXML ScrollPane image_scroll_pane;
	@FXML StackPane image_pane;

	@FXML AnchorPane person_pane;
	@FXML AnchorPane place_pane;
	@FXML AnchorPane event_pane;
	@FXML AnchorPane assorted_pane;

	@FXML MapSidePaneController controller_pane;


	// variables for drag and drop
	private DragIconController mDragOverIcon = null;

	private EventHandler<DragEvent> mIconDragOverRoot = null;
	private EventHandler<DragEvent> mIconDragDropped = null;
	private EventHandler<DragEvent> mIconDragOverRightPane = null;

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

	@FXML
	private void initialize() {

		//Add one icon that will be used for the drag-drop process
		//This is added as a child to the root anchorpane so it can be visible
		//on both sides of the split pane.
		mDragOverIcon = new DragIconController();

		mDragOverIcon.setVisible(false);
		mDragOverIcon.setOpacity(0.65);
		getChildren().add(mDragOverIcon);

		controller_pane.setPadding(new Insets(20, 8, 8, 8));

		//populate the vbox (controller_pane) with multiple colored buttons
		for (int i = 0; i < 5; i++) {

			ButtonController btn = new ButtonController();
			addDragDetection(btn.getDefault_icon());
			addPaneChanger(btn);

			btn.setButtonType(DragIconType.values()[i]);
			controller_pane.getChildren().add(btn);
			if(i == 0) {
				btn.changeNodeOrientation();
			}
		}

		ButtonBarController ctrl = new ButtonBarController();

		controller_pane.getChildren().add(ctrl);

		buildDragHandlers();

		// resizes the image to have width of 100 while preserving the ratio and using
		// higher quality filtering method; this ImageView is also cached to
		// improve performance
		map_image.setImage(new Image("file:resources/images/ihypkemxzapuu.jpg"));
		map_image.setPreserveRatio(true);

		// sets the image on zoom
		ImageMagnifier iM = new ImageMagnifier();
		iM.setImageOnZoom(image_pane);

		// center the scroll contents.
		image_scroll_pane.setHvalue(image_scroll_pane.getHmin() + (image_scroll_pane.getHmax() - image_scroll_pane.getHmin()) / 2);
		image_scroll_pane.setVvalue(image_scroll_pane.getVmin() + (image_scroll_pane.getVmax() - image_scroll_pane.getVmin()) / 2);
	}


	/**
	 * @return the target pane 	for dropping the icons,
	 * 			default:	the source pane of the icons for dropping
	 */
	public AnchorPane getDroppablePane(DragIconType type) {
		switch (type) {

		case ALL:
			return null;

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


	private void addPaneChanger(ButtonController button) {

		button.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// List of the panes in the image stackpane
				ObservableList<Node> children = image_pane.getChildren();

				DragIconType type = button.getType();

				AnchorPane currentPane = getDroppablePane(type);

				for( int i = children.size() - 1 ; i >= 0 ; i-- ) {

					if( currentPane == children.get(i) ) {

						// Put the concurrent pane to the front
						currentPane.toFront();
						currentPane.setVisible(true);

					} else if (children.get(i) != map_image) {

						children.get(i).setVisible(false);
						map_image.toBack();

					}// close if else //
				} // close for loop	//

			} // close handle //
		} // close Eventhanlder //
				); // close setOnAction	//
	} // close methode //




	public void addDragDetection(DragIconController dragIcon) {

		dragIcon.setOnDragDetected (new EventHandler <MouseEvent> () {

			@Override
			public void handle(MouseEvent event) {

				// set drag event handlers on their respective objects
				base_pane.setOnDragOver(mIconDragOverRoot);
				image_pane.setOnDragOver(mIconDragOverRightPane);
				image_pane.setOnDragDropped(mIconDragDropped);

				// get a reference to the clicked DragIcon object
				DragIconController icn = (DragIconController) event.getSource();

				// get the pane from which the icon for dragging is sitting on
				AnchorPane sourcePane = getDroppablePane(icn.getType());
				// if the pane is not the controller_pane remove the source icon
				// after the drag is started (cutting) 
				// else leave the source icon in the controller pane (copying)
				if( sourcePane != null ) {
					sourcePane.getChildren().remove(icn);
				}

				//begin drag ops
				mDragOverIcon.setType(icn.getType());
				mDragOverIcon.relocateToPoint(new Point2D (event.getSceneX(), event.getSceneY()));

				ClipboardContent content = new ClipboardContent();
				DragContainer container = new DragContainer();

				container.addData ("name", icn.getIconName());
				container.addData ("status", icn.getIconDropStatus());
				container.addData("data", icn.getSerializeableIconData());
				container.addData ("type", mDragOverIcon.getType().toString());
				content.put(DragContainer.AddNode, container);

				mDragOverIcon.startDragAndDrop (TransferMode.ANY).setContent(content);
				mDragOverIcon.setVisible(true);
				mDragOverIcon.setMouseTransparent(true);
				event.consume();					
			}
		});
	}	

	private void buildDragHandlers() {

		//drag over transition to move widget form left pane to right pane
		mIconDragOverRoot = new EventHandler <DragEvent>() {

			@Override
			public void handle(DragEvent event) {

				Point2D p = image_pane.sceneToLocal(event.getSceneX(), event.getSceneY());

				//turn on transfer mode and track in the right-pane's context 
				//if (and only if) the mouse cursor falls within the right pane's bounds.
				if (!image_pane.boundsInLocalProperty().get().contains(p)) {

					event.acceptTransferModes(TransferMode.ANY);
					mDragOverIcon.relocateToPoint(new Point2D(event.getSceneX(), event.getSceneY()));
					return;
				}

				event.consume();
			}
		};

		mIconDragOverRightPane = new EventHandler <DragEvent> () {

			@Override
			public void handle(DragEvent event) {

				event.acceptTransferModes(TransferMode.ANY);

				//convert the mouse coordinates to scene coordinates,
				//then convert back to coordinates that are relative to 
				//the parent of mDragIcon.  Since mDragIcon is a child of the root
				//pane, coordinates must be in the root pane's coordinate system to work
				//properly.
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
						(DragContainer) event.getDragboard().getContent(DragContainer.AddNode);

				container.addData ("scene_coords", 
						new Point2D(event.getSceneX(), event.getSceneY()));


				ClipboardContent content = new ClipboardContent();
				content.put(DragContainer.AddNode, container);

				event.getDragboard().setContent(content);
				event.setDropCompleted(true);
			}
		};

		this.setOnDragDone (new EventHandler <DragEvent> (){

			@Override
			public void handle (DragEvent event) {

				image_pane.removeEventHandler(DragEvent.DRAG_OVER, mIconDragOverRightPane);
				image_pane.removeEventHandler(DragEvent.DRAG_DROPPED, mIconDragDropped);
				base_pane.removeEventHandler(DragEvent.DRAG_OVER, mIconDragOverRoot);

				mDragOverIcon.setVisible(false);

				DragContainer container = 
						(DragContainer) event.getDragboard().getContent(DragContainer.AddNode);

				if (container != null) {
					if (container.getValue("scene_coords") != null) {

						DragIconController droppedIcon = new DragIconController();

						droppedIcon.setIconName(container.getValue("name"));
						droppedIcon.setIconDropStatus(container.getValue("status"));
						droppedIcon.setDeserializedIconData(container.getValue("data"));
						droppedIcon.setType(DragIconType.valueOf(container.getValue("type")));

						DragIconType mType = mDragOverIcon.getType();
						AnchorPane currentPane = getDroppablePane(mType);
						currentPane.toFront();
						currentPane.getChildren().add(droppedIcon);

						Point2D cursorPoint = container.getValue("scene_coords");

						droppedIcon.relocateToPoint(
								new Point2D(cursorPoint.getX() - 32, cursorPoint.getY() - 32)
								);
						
						if( ! droppedIcon.getIconDropStatus() ) {

						showMapItemEditDialog(droppedIcon);
						droppedIcon.getIconName();
						droppedIcon.changeIconDropStatus();
						}
						addEditDialogOpener(droppedIcon);
						addDragDetection(droppedIcon);
					}
				}

				event.consume();
			}
		});
	}

	/**
	 * Opens a dialog to edit details for the specified map items. If the user
	 * clicks OK, the changes are saved into the provided suggestion object and true
	 * is returned.
	 * 
	 * @param entscheidung the suggestion object to be edited
	 * @return true if the user clicked OK, false otherwise.
	 */
	public boolean showMapItemEditDialog(DragIconController droppedDragIcon) {
		try {
			
//			FXMLLoader fxmlLoader = new FXMLLoader(
//					getClass().getResource("../view/DragIcon_view.fxml")
//					);
//			
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(RootLayoutController.class.getResource("../view/MapItemEditDialog_view.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			// Set the application icon.
			dialogStage.getIcons().add(new Image("file:resources/images/TolkienIcon.jpg"));
			dialogStage.setTitle("Kartenmarkierung bearbeiten");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(null);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set the suggestion into the controller.
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
	 * Called when the user clicks the edit button. Opens a dialog to edit
	 * details for the selected
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

