package photoalbum.view;

import javafx.fxml.*;
import javafx.scene.control.*;

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
		
	}
	
	private void Exit()
	{
		System.exit(0);
	}

}
