package photoalbum.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
	@FXML private Button btnAdd;
	@FXML private Button btnCancel;
	@FXML private TextField txtUsername;
	@FXML private PasswordField txtPassword;
	
	/**
	 * Callback function for closing the form and return control to main stage
	 */
	private Runnable close;
	
	/**
	 * Sets up the User Add scene
	 * @param close function for closing the form
	 */
	public void start(Object close)
	{	
		this.close = (Runnable)close;
		btnAdd.setOnAction(e -> AddUser());
		btnCancel.setOnAction(e -> Cancel());
	}
	
	/**
	 * Used for adding a user to the userList
	 * Will not work if the entered name already exists
	 */
	public void AddUser()
	{
		StateManager stateManager = StateManager.getInstance();
		
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
			alert.initOwner(StateManager.getInstance().getPrimaryStage());
            alert.setTitle("Error");
            alert.setHeaderText("Name must be unique (Case insensitive).");
            alert.setContentText("Another user with this name already exists.");
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
