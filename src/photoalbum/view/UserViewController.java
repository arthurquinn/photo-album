package photoalbum.view;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import photoalbum.app.StateManager;
import photoalbum.models.User;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;

public class UserViewController
{
	@FXML private TableView<User> userGrid;
	@FXML private TableColumn<User, String> colUsername;
	@FXML private TableColumn<User, String> colPassword;
	
	@FXML private Button btnCreate;
	@FXML private Button btnDelete;
	@FXML private Button btnExit;
	
	ObservableList<User> userList;
	
	public void start()
	{
		initialize();
		
		btnCreate.setOnAction(e -> createUser());
		btnDelete.setOnAction(e -> deleteUser());
		btnExit.setOnAction(e -> exit());
	}
	
	private void initialize()
	{
		for (User user : StateManager.getInstance().getUsers())
		{
			System.out.println(user.getUsername());
		}
		
		userList = FXCollections.observableArrayList();
		userList.addAll(StateManager.getInstance().getUsers());
		
		colUsername.setCellValueFactory(cellData -> cellData.getValue().getUsernameProp());
		colPassword.setCellValueFactory(cellData -> cellData.getValue().getPasswordProp());
		
		userGrid.setItems(userList);
	}
	
	private void createUser()
	{
		
	}
	
	private void deleteUser()
	{
		
	}
	
	private void exit()
	{
		
	}
}
