package application.model;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.Lighting;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.StackPane;

public class ImageMagnifier {
	
	private int gestureCount;

	/**
	 * make the image zoomeable
	 * 
	 * @param image
	 */
	public void setImageOnZoom(StackPane image) {

		image.setOnZoom(new EventHandler<ZoomEvent>() {

			@Override
			public void handle(ZoomEvent event) {
				image.setScaleX(image.getScaleX() * event.getZoomFactor());
				image.setScaleY(image.getScaleY() * event.getZoomFactor());

				event.consume();
			}
		});

		image.setOnZoomStarted(new EventHandler<ZoomEvent>() {

			@Override
			public void handle(ZoomEvent event) {
				inc(image);
				event.consume();
			}
		});

		image.setOnZoomFinished(new EventHandler<ZoomEvent>() {

			@Override
			public void handle(ZoomEvent event) {
				dec(image);
				event.consume();
			}
		});
	}
	
	public void setZoomOnScroll(StackPane image, ScrollPane scroll, Group scrollContent) {
		// Zoom on Scroll
		image.setOnScroll(new EventHandler<ScrollEvent>() {
		            @Override
		            public void handle(ScrollEvent event) {
		                double zoomFactor = 1.05;
		                double deltaY = event.getDeltaY();
		                double layoutY = scrollContent.getLayoutY();
		                double layoutX = scrollContent.getLayoutX();
		                Point2D scrollOffset = figureScrollOffset(scrollContent, scroll);
		                
		                // if zooming out
		                if (deltaY < 0 && (layoutX*zoomFactor > -2600 || layoutY*zoomFactor > -2200)){
		                    zoomFactor = 0.95;
		                }
		                
		                else if (deltaY < 0 && (layoutX*zoomFactor < -2600 || layoutY*zoomFactor < -2200)){
		                	zoomFactor = 1;
		                }
		                
		                	
		                image.setScaleX(image.getScaleX() * zoomFactor);
		                image.setScaleY(image.getScaleY() * zoomFactor);
		                // move viewport so that old center remains in the center after the
		                // scaling
		                repositionScroller(scrollContent, scroll, zoomFactor, scrollOffset);
		                event.consume();
		            }
		        });
	}
	
	/**
	 * calculation of the scrolloffset in local coordinates of the scroll content
	 */
	
	private Point2D figureScrollOffset(Node scrollContent, ScrollPane scroller) {
		    double extraWidth = scrollContent.getLayoutBounds().getWidth() - scroller.getViewportBounds().getWidth();
		    double hScrollProportion = (scroller.getHvalue() - scroller.getHmin()) / (scroller.getHmax() - scroller.getHmin());
		    double scrollXOffset = hScrollProportion * Math.max(0, extraWidth);
		    double extraHeight = scrollContent.getLayoutBounds().getHeight() - scroller.getViewportBounds().getHeight();
		    double vScrollProportion = (scroller.getVvalue() - scroller.getVmin()) / (scroller.getVmax() - scroller.getVmin());
		    double scrollYOffset = vScrollProportion * Math.max(0, extraHeight);
		    return new Point2D(scrollXOffset, scrollYOffset);
	}
	
	/**
	 * calculate the new center of the viewport after zooming
	 * 
	 * @param scrollContent, scroller, scaleFactor, scrollOffset
	 */
	protected void repositionScroller(Node scrollContent, ScrollPane scroller, double scaleFactor, Point2D scrollOffset) {
	    double scrollXOffset = scrollOffset.getX();
	    double scrollYOffset = scrollOffset.getY();
	    double extraWidth = scrollContent.getLayoutBounds().getWidth() - scroller.getViewportBounds().getWidth();
	    if (extraWidth > 0) {
	      double halfWidth = scroller.getViewportBounds().getWidth() / 2 ;
	      double newScrollXOffset = (scaleFactor - 1) *  halfWidth + scaleFactor * scrollXOffset;
	      scroller.setHvalue(scroller.getHmin() + newScrollXOffset * (scroller.getHmax() - scroller.getHmin()) / extraWidth);
	    } else {
	      scroller.setHvalue(scroller.getHmin());
	    }
	    double extraHeight = scrollContent.getLayoutBounds().getHeight() - scroller.getViewportBounds().getHeight();
	    if (extraHeight > 0) {
	      double halfHeight = scroller.getViewportBounds().getHeight() / 2 ;
	      double newScrollYOffset = (scaleFactor - 1) * halfHeight + scaleFactor * scrollYOffset;
	      scroller.setVvalue(scroller.getVmin() + newScrollYOffset * (scroller.getVmax() - scroller.getVmin()) / extraHeight);
	    } else {
	      scroller.setHvalue(scroller.getHmin());
	    }
	  }
	
	public void setCursor(StackPane image) {
		
		// Set the cursor to Open and Closed Hand
		image.setCursor(Cursor.OPEN_HAND);
		image.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle (MouseEvent event) {
				image.setCursor(Cursor.CLOSED_HAND);
			}
		});
		image.setOnMouseReleased(e->{
            image.setCursor(Cursor.OPEN_HAND);
        });
	}


	/**
	 * Uses lighting to visually change the object for the duration of 
	 * the gesture.
	 * 
	 * @param shape Target of the gesture
	 */    
	private void inc(StackPane image) {

		if (gestureCount == 0) {
			image.setEffect(new Lighting());
		}
		gestureCount++;
	}

	/**
	 * Restores the object to its original state when the gesture completes.
	 * 
	 * @param shape Target of the gesture
	 */    
	private void dec(StackPane image) {

		gestureCount--;
		if (gestureCount == 0) {
			image.setEffect(null);
		}
	}

}
