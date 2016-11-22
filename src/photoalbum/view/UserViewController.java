package photoalbum.view;

import java.io.IOException;
import java.util.List;
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
	@FXML private Button btnCreate;
	
	/**
	 * A button for deleting a user object
	 */
	@FXML private Button btnDelete;
	
	/**
	 * A button for logging out of the UserViewController
	 */
	@FXML private Button btnLogout;
	
	/**
	 * Button for safe quitting the application
	 */
	@FXML private Button btnExit;
	
	/**
	 * A list containing all of the users
	 */
	private ObservableList<User> userList;
	
	/**
	 * Sets up the User View scene
	 */
	public void start(Object args)
	{
		populate();
		
		btnCreate.setOnAction(e -> createUser());
		btnDelete.setOnAction(e -> deleteUser());
		btnLogout.setOnAction(e -> logout());
		btnExit.setOnAction(e -> exit());
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
		
		StateManager.getInstance().createPopupWindow(stage, "/photoalbum/view/UserAddForm.fxml", (Object)r);
		
	}
	
	/**
	 * Deletes the selected user from the table
	 * Cannot delete the admin account
	 */
	private void deleteUser()
	{
		User user = userGrid.getSelectionModel().getSelectedItem();
		
		if (user.getUsername().equals("admin"))
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(StateManager.getInstance().getPrimaryStage());
            alert.setTitle("Error");
            alert.setHeaderText("Cannot delete admin account.");
            alert.showAndWait();
		}
		else
		{
			StateManager.getInstance().removeUser(user);
			StateManager.getInstance().save();
			
			populate();
		}
	}
	
	/**
	 * Returns to the login scene when called
	 */
	private void logout()
	{
		StateManager.getInstance().setActiveUser(null);
		StateManager.getInstance().setActiveScene("/photoalbum/view/Login.fxml", null, 400, 600);
	}
	
	/**
	 * Saves and safe quits the application
	 */
	private void exit()
	{
		StateManager.getInstance().saveAndExit();
	}
}
