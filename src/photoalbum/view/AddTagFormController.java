package photoalbum.view;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import photoalbum.app.StateManager;
import photoalbum.models.Tag;

/**
 * Called when the user tries to manage the tags associated with a photo
 * @author Stephen Eisen
 * @author Arthur Quintanilla
 */
public class AddTagFormController implements IController
{
	/**
	 * Confirmation button for adding a tag
	 */
	@FXML private Button btnAdd;
	
	/**
	 * Button for canceling tag management
	 */
	@FXML private Button btnCancel;
	
	/**
	 * Textfield for the type of the new desired tag
	 */
	@FXML private TextField txtType;
	
	/**
	 * Textfield for the value of the new desired tag
	 */
	@FXML private TextField txtValue;
	
	/**
	 * List containing the tags associated with a photo
	 */
	private List<Tag> tagList;
	
	/**
	 * Function for closing the form
	 */
	private Runnable closeForm;
	
	/**
	 * Stage onto which this scene is shown
	 */
	private Stage stage;
	
	/**
	 * Sets up the Add tag form controller
	 */
	public void start(Object args)
	{
		Object[] argsArray = (Object[])args;
		
		closeForm = (Runnable)argsArray[0];
		tagList = (List<Tag>)argsArray[1];
		stage = (Stage)argsArray[2];
		
		btnAdd.setOnAction(e -> add());
		btnCancel.setOnAction(e -> closeForm.run());
	}
	
	/**
	 * Adds a tag to the tag list for a photo
	 */
	private void add()
	{
		String type = txtType.getText();
		String value = txtValue.getText();
		
		if (!type.isEmpty() && !value.isEmpty())
		{
			tagList.add(new Tag(type, value));
			StateManager.getInstance().save();
			closeForm.run();
		}
		else
		{
			Alert alert = new Alert(AlertType.ERROR);
    		alert.initOwner(stage);
    		alert.setTitle("Error");
    		alert.setHeaderText("Missing tag type or value");
    		alert.setContentText("Enter a tag type and value pair.");
    		alert.showAndWait();
		}
	}
}
