package photoalbum.view;

import java.io.IOException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import photoalbum.app.StateManager;
import photoalbum.models.User;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;

public class UserViewController
{
	@FXML private TableView<User> userGrid;
	@FXML private TableColumn<User, String> colUsername;
	@FXML private TableColumn<User, String> colPassword;
	
	@FXML private Button btnCreate;
	@FXML private Button btnDelete;
	@FXML private Button btnLogout;
	@FXML private Button btnExit;
	
	private ObservableList<User> userList;
	
	public void start()
	{
		populate();
		
		btnCreate.setOnAction(e -> createUser());
		btnDelete.setOnAction(e -> deleteUser());
		btnLogout.setOnAction(e -> logout());
		btnExit.setOnAction(e -> exit());
	}
	
	private void populate()
	{		
		userList = FXCollections.observableArrayList();
		userList.addAll(StateManager.getInstance().getUsers());
		
		colUsername.setCellValueFactory(cellData -> cellData.getValue().getUsernameProp());
		colPassword.setCellValueFactory(cellData -> cellData.getValue().getPasswordProp());
		
		userGrid.setItems(userList);
	}
	
	private void createUser()
	{
		try
		{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/photoalbum/view/UserAddForm.fxml"));
			Stage stage = new Stage(StageStyle.DECORATED);

			
			stage.setScene(new Scene((AnchorPane)loader.load()));
			stage.setResizable(false);
			stage.setTitle("Add User");
			
			UserAddFormController controller = loader.getController();
			controller.start(() -> { stage.close(); populate(); });
			
			stage.showAndWait();
			
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}
	
	private void deleteUser()
	{
		User user = userGrid.getSelectionModel().getSelectedItem();
		
		if (user.getUsername().equals("admin"))
		{
			// TODO: Error here
		}
		else
		{
			StateManager.getInstance().removeUser(user);
			StateManager.getInstance().save();
			
			populate();
		}
	}
	
	private void logout()
	{
		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/photoalbum/view/Login.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			
			LoginController controller = loader.getController();
			controller.start();
			
			Scene scene = new Scene(root,400,600);
			StateManager.getInstance().getPrimaryStage().setScene(scene);
			StateManager.getInstance().getPrimaryStage().show();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private void exit()
	{
		StateManager.getInstance().saveAndExit();
	}
}
