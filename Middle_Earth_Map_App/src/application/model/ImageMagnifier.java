package application.model;

import javafx.event.EventHandler;
import javafx.scene.effect.Lighting;
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
