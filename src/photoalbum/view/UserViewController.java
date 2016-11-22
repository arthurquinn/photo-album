package photoalbum.view;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import photoalbum.app.StateManager;
import photoalbum.models.User;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;

/**
 * Controls the actions of the user view screen
 * @author Stephen Eisen
 * @author Arthur Quintanilla
 */
public class UserViewController implements IController
{
	/**
	 * A visible list containing all the users
	 */
	@FXML private TableView<User> userGrid;
	
	/**
	 * A column containing the username of a user
	 */
	@FXML private TableColumn<User, String> colUsername;
	
	/**
	 * A column containing the password of a user
	 */
	@FXML private TableColumn<User, String> colPassword;
	
	/**
	 * A button for creating a user object
	 */
	@FXML private MenuItem mnuCreate;
	
	/**
	 * A button for deleting a user object
	 */
	@FXML private MenuItem mnuDelete;
	
	/**
	 * A button for logging out of the UserViewController
	 */
	@FXML private MenuItem mnuLogout;
	
	/**
	 * Button for safe quitting the application
	 */
	@FXML private MenuItem mnuExit;
	
	/**
	 * A list containing all of the users
	 */
	private ObservableList<User> userList;
	
	/**
	 * Sets up the User View scene
	 */
	public void start(Object args)
	{
		StateManager.getInstance().getPrimaryStage().setTitle("Photo Album - Administrator Tools");
		
		populate();
		
		mnuCreate.setOnAction(e -> createUser());
		mnuDelete.setOnAction(e -> deleteUser());
		mnuLogout.setOnAction(e -> logout());
		mnuExit.setOnAction(e -> exit());
	}
	
	/**
	 * Updates the current state of the list
	 */
	private void populate()
	{		
		userList = FXCollections.observableArrayList();
		userList.addAll(StateManager.getInstance().getUsers());
		
		colUsername.setCellValueFactory(cellData -> cellData.getValue().getUsernameProp());
		colPassword.setCellValueFactory(cellData -> cellData.getValue().getPasswordProp());
		
		userGrid.setItems(userList);
	}
	
	/**
	 * Used to create a user object
	 * Adds the new user to the list if the entered username is unique
	 */
	private void createUser()
	{
		Stage stage = new Stage(StageStyle.DECORATED);
		stage.setTitle("Add User");
		
		Runnable r = () -> { stage.close(); populate(); };
		
		Object[] argsArray = new Object[2];
		argsArray[0] = r;
		argsArray[1] = stage;
		
		StateManager.getInstance().createPopupWindow(stage, "/photoalbum/view/UserAddForm.fxml", argsArray);
	}
	
	/**
	 * Deletes the selected user from the table
	 * Cannot delete the admin account
	 */
	private void deleteUser()
	{
		User user = userGrid.getSelectionModel().getSelectedItem();
		
		if (user != null)
		{
			if (user.getUsername().equals("admin"))
			{
				Alert alert = new Alert(AlertType.ERROR);
				alert.initOwner(StateManager.getInstance().getPrimaryStage());
	            alert.setTitle("Error");
	            alert.setHeaderText("Administrator account cannot be deleted");
	            alert.setContentText("Select a different user.");
	            alert.showAndWait();
			}
			else
			{
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.initOwner(StateManager.getInstance().getPrimaryStage());
	            alert.setTitle("Confirmation");
	            alert.setHeaderText("Are you sure you want to delete this user?");
	            alert.setContentText(String.format("User: %s\nAll albums this user owns will be lost.", user.getUsername()));
	            Optional<ButtonType> response = alert.showAndWait();
				
	            if (response.isPresent() && response.get() == ButtonType.OK)
	            {
					StateManager.getInstance().removeUser(user);
					StateManager.getInstance().save();
					populate();	
	            }
			}
		}
		else
		{
			Alert alert = new Alert(AlertType.ERROR);
    		alert.initOwner(StateManager.getInstance().getPrimaryStage());
    		alert.setTitle("Error");
    		alert.setHeaderText("No user selected");
    		alert.setContentText("Select a user to delete.");
    		alert.showAndWait();
		}
	}
	
	/**
	 * Returns to the login scene when called
	 */
	private void logout()
	{
		StateManager.getInstance().setActiveUser(null);
		StateManager.getInstance().setActiveScene("/photoalbum/view/Login.fxml", null, 257, 338);
	}
	
	/**
	 * Saves and safe quits the application
	 */
	private void exit()
	{
		StateManager.getInstance().saveAndExit();
	}
}
