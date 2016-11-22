package photoalbum.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.function.Consumer;

import photoalbum.app.StateManager;
import photoalbum.models.User;
import photoalbum.lib.UserLibrary;

/**
 * Controls the actions of the User add screen
 * @author Stephen Eisen
 * @author Arthur Quintanilla
 */

public class UserAddFormController implements IController
{
	/**
	 * Confirmation for creating a user object
	 */
	@FXML private Button btnAdd;
	
	/**
	 * Cancels creating a user object
	 */
	@FXML private Button btnCancel;
	
	/**
	 * The desired name for the new user object
	 */
	@FXML private TextField txtUsername;
	
	/**
	 * The desired password for the new user object
	 */
	@FXML private PasswordField txtPassword;
	
	/**
	 * Callback function for closing the form and return control to main stage
	 */
	private Runnable close;
	
	/**
	 * The stage onto which this window is displayed
	 */
	private Stage stage;
	
	/**
	 * Sets up the User Add scene
	 * @param close function for closing the form
	 */
	public void start(Object args)
	{	
		Object[] argsArray = (Object[])args;
		this.close = (Runnable)argsArray[0];
		this.stage = (Stage)argsArray[1];
		
		this.stage.setResizable(false);
		
		btnAdd.setOnAction(e -> addUser());
		btnCancel.setOnAction(e -> Cancel());
	}
	
	/**
	 * Used for adding a user to the userList
	 * Will not work if the entered name already exists
	 */
	public void addUser()
	{
		StateManager stateManager = StateManager.getInstance();
		
		if (!txtUsername.getText().isEmpty() && !txtPassword.getText().isEmpty())
		{
			if (!UserLibrary.userExists(StateManager.getInstance().getUsers(), txtUsername.getText()))
			{
				User user = new User(txtUsername.getText(), txtPassword.getText());
				stateManager.addUser(user);
				stateManager.save();
				close.run();
			}
			else
			{
				Alert alert = new Alert(AlertType.ERROR);
				alert.initOwner(stage);
	            alert.setTitle("Error");
	            alert.setHeaderText("Name must be unique (Case insensitive).");
	            alert.setContentText("Another user with this name already exists.");
	            alert.showAndWait();
			}	
		}
		else
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(stage);
            alert.setTitle("Error");
            alert.setHeaderText("Missing username or password");
            alert.setContentText("Username and password fields cannot be empty.");
            alert.showAndWait();
		}
	}
	
	/**
	 * Closes the Add form
	 */
	public void Cancel()
	{
		close.run();
	}
}
