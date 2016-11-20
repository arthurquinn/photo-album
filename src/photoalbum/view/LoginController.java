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

public class LoginController {

	@FXML private TextField txtUsername;
	@FXML private TextField txtPassword;
	@FXML private Button btnLogin;
	@FXML private Button btnExit;
	
	public void Start()
	{
		btnLogin.setOnAction(e -> Login());
		btnExit.setOnAction(e -> Exit());
	}
	
	private void Login()
	{
		StateManager stateManager = StateManager.getInstance();
		
		List<User> users = stateManager.getUsers();


	}
	
	private void Exit()
	{
		System.exit(0);
	}
	
	

}
