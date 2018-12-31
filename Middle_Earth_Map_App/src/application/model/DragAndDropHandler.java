package application.model;

import application.controller.DragIconController;
import application.controller.RootLayoutController;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.control.SplitPane;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class DragAndDropHandler {

//	private RootLayoutController rootCtrl;

//	// variables for drag and drop
//	private DragIconController mDragOverIcon = null;
//
//	private EventHandler<DragEvent> mIconDragOverRoot = null;
//	private EventHandler<DragEvent> mIconDragDropped = null;
//	private EventHandler<DragEvent> mIconDragOverRightPane = null;
//	
//	private StackPane imagePane;
//	private SplitPane basePane;

	// Constructor
	public DragAndDropHandler() {}

//	public void addDragDetection(DragIconController dragIcon) {
//
//		dragIcon.setOnDragDetected (new EventHandler <MouseEvent> () {
//
//			@Override
//			public void handle(MouseEvent event) {
//				
//				
//				basePane = rootCtrl.getBasePane();
//				imagePane = rootCtrl.getImagePane();
//
//				// set drag event handlers on their respective objects
//				basePane.setOnDragOver(mIconDragOverRoot);
//				imagePane.setOnDragOver(mIconDragOverRightPane);
//				imagePane.setOnDragDropped(mIconDragDropped);
//
//				// get a reference to the clicked DragIcon object
//				DragIconController icn = (DragIconController) event.getSource();
//
//				//begin drag ops
//				dragIcon.setType(icn.getType());
//				dragIcon.relocateToPoint(new Point2D (event.getSceneX(), event.getSceneY()));
//
//				ClipboardContent content = new ClipboardContent();
//				DragContainer container = new DragContainer();
//
//				container.addData ("type", dragIcon.getType().toString());
//				content.put(DragContainer.AddNode, container);
//
//				dragIcon.startDragAndDrop (TransferMode.ANY).setContent(content);
//				dragIcon.setVisible(true);
//				dragIcon.setMouseTransparent(true);
//				event.consume();					
//			}
//		});
//	}	

//	public void buildDragHandlers() {
//		
//		basePane = rootCtrl.getBasePane();
//		imagePane = rootCtrl.getImagePane();
//		mDragOverIcon = rootCtrl.getDragOverIcon();
//
//		//drag over transition to move widget form left pane to right pane
//		mIconDragOverRoot = new EventHandler <DragEvent>() {
//
//			@Override
//			public void handle(DragEvent event) {
//				
//				Point2D p = imagePane.sceneToLocal(event.getSceneX(), event.getSceneY());
//
//				//turn on transfer mode and track in the right-pane's context 
//				//if (and only if) the mouse cursor falls within the right pane's bounds.
//				if (!imagePane.boundsInLocalProperty().get().contains(p)) {
//
//					event.acceptTransferModes(TransferMode.ANY);
//					
//					mDragOverIcon.relocateToPoint(new Point2D(event.getSceneX(), event.getSceneY()));
//					return;
//				}
//
//				event.consume();
//			}
//		};
//
//		mIconDragOverRightPane = new EventHandler <DragEvent> () {
//
//			@Override
//			public void handle(DragEvent event) {
//
//				event.acceptTransferModes(TransferMode.ANY);
//
//				//convert the mouse coordinates to scene coordinates,
//				//then convert back to coordinates that are relative to 
//				//the parent of mDragIcon.  Since mDragIcon is a child of the root
//				//pane, coordinates must be in the root pane's coordinate system to work
//				//properly.
//				mDragOverIcon.relocateToPoint(
//						new Point2D(event.getSceneX(), event.getSceneY())
//						);
//				event.consume();
//			}
//		};
//
//		mIconDragDropped = new EventHandler <DragEvent> () {
//
//			@Override
//			public void handle(DragEvent event) {
//
//				DragContainer container = 
//						(DragContainer) event.getDragboard().getContent(DragContainer.AddNode);
//
//				container.addData("scene_coords", 
//						new Point2D(event.getSceneX(), event.getSceneY()));
//
//				ClipboardContent content = new ClipboardContent();
//				content.put(DragContainer.AddNode, container);
//
//				event.getDragboard().setContent(content);
//				event.setDropCompleted(true);
//			}
//		};
//
//		rootCtrl.setOnDragDone (new EventHandler <DragEvent> (){
//
//			@Override
//			public void handle (DragEvent event) {
//
//				imagePane.removeEventHandler(DragEvent.DRAG_OVER, mIconDragOverRightPane);
//				imagePane.removeEventHandler(DragEvent.DRAG_DROPPED, mIconDragDropped);
//				basePane.removeEventHandler(DragEvent.DRAG_OVER, mIconDragOverRoot);
//
//				mDragOverIcon.setVisible(false);
//
//				DragContainer container = 
//						(DragContainer) event.getDragboard().getContent(DragContainer.AddNode);
//
//				if (container != null) {
//					if (container.getValue("scene_coords") != null) {
//
//						DragIconController droppedIcon = new DragIconController();
//
//						droppedIcon.setType(DragIconType.valueOf(container.getValue("type")));
//
//						DragIconType mType = mDragOverIcon.getType();
//						AnchorPane currentPane = rootCtrl.getDroppablePane(mType);
//
//						if(currentPane != null) {
//							currentPane.toFront();
//							currentPane.getChildren().add(droppedIcon);
//
//							Point2D cursorPoint = container.getValue("scene_coords");
//
//							droppedIcon.relocateToPoint(
//									new Point2D(cursorPoint.getX() - 32, cursorPoint.getY() - 32)
//									);
//							addDragDetection(droppedIcon);
//						}
//
//					}
//				}
//
//				event.consume();
//			}
//		});
//	}

}
