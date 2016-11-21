package photoalbum.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.util.function.Consumer;

import photoalbum.app.StateManager;
import photoalbum.models.User;

public class UserAddFormController
{
	@FXML private Button btnAdd;
	@FXML private Button btnCancel;
	@FXML private TextField txtUsername;
	@FXML private PasswordField txtPassword;
	
	private Runnable close;
	
	public void start(Runnable close)
	{
		btnAdd.setOnAction(e -> AddUser());
		btnCancel.setOnAction(e -> Cancel());
		
		this.close = close;
	}
	
	public void AddUser()
	{
		StateManager stateManager = StateManager.getInstance();
		
		if (!stateManager.userExists(txtUsername.getText()))
		{
			User user = new User(txtUsername.getText(), txtPassword.getText());
			stateManager.addUser(user);
			stateManager.save();
			close.run();
		}
		else
		{
			// TODO: Show error
		}
		
	}
	
	public void Cancel()
	{
		close.run();
	}
}
