package application;

import application.controller.RootLayoutController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class MainApp extends Application {
	
	private Stage primaryStage;
	private BorderPane rootLayout;

	/**
	 * Constructor
	 */
	public MainApp() {
	}

	@Override
	public void start(Stage primaryStage) {
		
		this.primaryStage = primaryStage;
		
		// Set the application Title
		this.primaryStage.setTitle("Karte von Mittelerde");
		// Set the application icon.
		this.primaryStage.getIcons().add( new Image( 
				"file:resources/images/TolkienIcon.jpg" ) );
		
		initRootLayout();
	}
	
	/**
	 * Initializes the root layout and sets the CCStylesheet
	 */
	public void initRootLayout() {
		
		rootLayout = new BorderPane();

		try {
			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout,1100,900);
			scene.getStylesheets().add( getClass().getResource( 
					"view/application.css" ).toExternalForm() );
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch(Exception e) {
			e.printStackTrace();
		}

		rootLayout.setCenter(new RootLayoutController());
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
