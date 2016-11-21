package photoalbum.app;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import photoalbum.view.LoginController;

/**
 * Used for creating the primary stage for the Photo Album application
 * Entry point for the application
 * @author Stephen Eisen
 * @author Arthur Quintanilla
 */

public class PhotoAlbum extends Application 
{
	/**
	 * Creates the primary stage for the Photo Album application
	 */
	@Override
	public void start(Stage primaryStage) 
	{
		try 
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/photoalbum/view/Login.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			
			LoginController controller = loader.getController();
			controller.Start();
			
			Scene scene = new Scene(root,400,600);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
