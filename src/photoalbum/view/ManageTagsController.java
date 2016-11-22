package photoalbum.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import photoalbum.app.StateManager;
import photoalbum.models.Photo;
import photoalbum.models.Tag;

/**
 * Used for adding and deleting tags associated with a photo
 * @author Stephen Eisen
 * @author Arthur Quintanilla
 */
public class ManageTagsController implements IController
{
	/**
	 * Confirmation button for adding a tag to a photo
	 */
	@FXML private Button btnAdd;
	
	/**
	 * Confirmation button for deleting a tag from a photo
	 */
	@FXML private Button btnDelete;
	
	/**
	 * Button for closing the tag form
	 */
	@FXML private Button btnClose;
	
	/**
	 * List containing all the tags associated with a selected photo
	 */
	@FXML private ListView<Tag> lstTags;
	
	/**
	 * The currently selected photo
	 */
	private Photo activePhoto;
	
	/**
	 * Observable list of the tags associated with a photo
	 */
	private ObservableList<Tag> tagList;
	
	/**
	 * Sets up the Manage tags Controller
	 */
	public void start(Object args)
	{
		Object[] argsArray = (Object[])args;
		
		Runnable r = (Runnable)argsArray[0];
		activePhoto = (Photo)argsArray[1];
		
		btnAdd.setOnAction(e -> add());
		btnDelete.setOnAction(e -> delete());
		btnClose.setOnAction(e -> r.run());
		
		populate();
	}
	
	/**
	 * Updates the album list
	 */
	private void populate()
	{
		tagList = FXCollections.observableArrayList();
		tagList.addAll(activePhoto.getTagList());
		
		lstTags.setItems(tagList);
	}
	
	/**
	 * Adds a tag to the taglist for a photo
	 */
	private void add()
	{
		Stage stage = new Stage(StageStyle.DECORATED);
		stage.setTitle("Add Tag");
		
		Runnable r = () -> { stage.close(); populate(); };
		
		Object[] args = new Object[2];
		args[0] = r;
		args[1] = activePhoto.getTagList();
		
		StateManager.getInstance().createPopupWindow(stage, "/photoalbum/view/AddTagForm.fxml", args);
	}
	
	/**
	 * Deletes a tag from the taglist for a photo
	 */
	private void delete()
	{
		Tag tag = lstTags.getSelectionModel().getSelectedItem();
		
		if (tag != null)
		{
			activePhoto.removeTag(tag);
			StateManager.getInstance().save();
			tagList.clear();
			populate();
		}
		else
		{
			Alert alert = new Alert(AlertType.ERROR);
    		alert.initOwner(StateManager.getInstance().getPrimaryStage());
    		alert.setTitle("Error");
    		alert.setHeaderText("No tag selected");
    		alert.setContentText("Select a tag to delete from this photo.");
    		alert.showAndWait();
		}
	}
	
}
