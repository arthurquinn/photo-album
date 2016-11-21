package photoalbum.view;

import javafx.fxml.*;
import javafx.scene.control.*;
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
	public void Start()
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
		
		List<User> users = stateManager.getUsers();


	}
	
	/**
	 * Exits the Login Scene
	 */
	private void Exit()
	{
		System.exit(0);
	}
	
	

}
