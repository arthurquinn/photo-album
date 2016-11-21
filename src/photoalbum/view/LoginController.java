package photoalbum.view;

import javafx.fxml.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import photoalbum.app.StateManager;
import photoalbum.models.*;

import java.io.Serializable;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.util.*;

/**
 * Controls the Login Scene
 * @author Stephen Eisen
 * @author Arthur Quintanilla
 */

public class LoginController
{
	@FXML private TextField txtUsername;
	@FXML private PasswordField txtPassword;
	@FXML private Button btnLogin;
	@FXML private Button btnExit;
	
	/**
	 * Sets up the Login Scene
	 */
	public void start()
	{
		btnLogin.setOnAction(e -> Login());
		btnExit.setOnAction(e -> Exit());
	}
	
	/**
	 * Called when the login button is pressed
	 */
	private void Login()
	{
		StateManager stateManager = StateManager.getInstance();
		
		if (stateManager.validateUser(txtUsername.getText(), txtPassword.getText()))
		{
			if (txtUsername.getText().equals("admin"))
			{
				try 
				{
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(getClass().getResource("/photoalbum/view/UserView.fxml"));
					AnchorPane root = (AnchorPane)loader.load();
					
					UserViewController controller = loader.getController();
					controller.start();
					
					Scene scene = new Scene(root,600,600);
					stateManager.getPrimaryStage().setScene(scene);
					stateManager.getPrimaryStage().show();
				} catch(Exception e) 
				{
					e.printStackTrace();
				}
			}
			else
			{
				try 
				{
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(getClass().getResource("/photoalbum/view/HomeScreen.fxml"));
					AnchorPane root = (AnchorPane)loader.load();
					
					HomeScreenController controller = loader.getController();
					controller.start();
					
					Scene scene = new Scene(root,400,600);
					stateManager.getPrimaryStage().setScene(scene);
					stateManager.getPrimaryStage().show();
				} catch(Exception e) 
				{
					e.printStackTrace();
				}	
			}
		}
		else
		{
			// TODO: Enter error
		}
	}
	
	
	/**
	 * Exits the Login Scene
	 */
	private void Exit()
	{
		System.exit(0);
	}
	
	

}
