package photoalbum.app;
	
import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import photoalbum.view.LoginController;
import photoalbum.lib.UserLibrary;
import photoalbum.models.*;

/**
 * Used for creating the primary stage for the Photo Album application
 * Entry point for the application
 * @author Stephen Eisen
 * @author Arthur Quintanilla
 */

public class PhotoAlbum extends Application 
{
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void start(Stage primaryStage) 
	{
		try 
        {
            File file = new File("data/state.dat");
            if (file.exists() && !file.isDirectory())
            {
            	
            }
            else
            {
                file.createNewFile();
                StateManager.initialize();
            }
            
            StateManager.getInstance().setPrimaryStage(primaryStage);
            
            StateManager.getInstance().setActiveScene("/photoalbum/view/Login.fxml", null, 257, 338);

        } catch(Exception e) 
        {
            e.printStackTrace();
        }
	}
	
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
