package photoalbum.view;

import javafx.fxml.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;

import photoalbum.app.StateManager;
import photoalbum.models.*;
import photoalbum.lib.UserLibrary;

import java.io.Serializable;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.util.*;

/**
 * Controls the actions of the login screen
 * @author Stephen Eisen
 * @author Arthur Quintanilla
 */

public class LoginController implements IController
{
	/**
	 * The username entered
	 */
	@FXML private TextField txtUsername;
	
	/**
	 * The password entered
	 */
	@FXML private PasswordField txtPassword;
	
	/**
	 * Confirmation button for login
	 */
	@FXML private Button btnLogin;
	
	/**
	 * Button for exiting the application
	 */
	@FXML private Button btnExit;
	
	/**
	 * Sets up the Login Scene
	 */
	public void start(Object args)
	{
		StateManager.getInstance().getPrimaryStage().setTitle("Photo Album - Login");
		
		btnLogin.setOnAction(e -> Login());
		btnExit.setOnAction(e -> Exit());
	}
	
	/**
	 * Called when the login button is pressed
	 */
	private void Login()
	{
		StateManager stateManager = StateManager.getInstance();
		
		if (UserLibrary.validateUser(stateManager.getUsers(), txtUsername.getText(), txtPassword.getText()))
		{
			if (txtUsername.getText().equals("admin"))
			{
				stateManager.setActiveUser(UserLibrary.getUser(stateManager.getUsers(), "admin"));
				stateManager.setActiveScene("/photoalbum/view/UserView.fxml", null, 800, 600);
			}
			else
			{
				stateManager.setActiveUser(UserLibrary.getUser(stateManager.getUsers(), txtUsername.getText()));
				stateManager.setActiveScene("/photoalbum/view/HomeScreen.fxml", null, 800, 600);
			}
		}
		else
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(StateManager.getInstance().getPrimaryStage());
            alert.setTitle("Error");
            alert.setHeaderText("Invalid username or password.");
            alert.setContentText("Enter a valid username and password.");
            alert.showAndWait();
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
