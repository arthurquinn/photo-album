package photoalbum.view;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import photoalbum.app.StateManager;
import photoalbum.lib.AlbumLibrary;
import photoalbum.lib.PhotoLibrary;
import photoalbum.models.Album;
import photoalbum.models.Photo;

/**
 * Form for copying a photo from one album to another album
 * @author Stephen Eisen
 * @author Arthur Quintanilla
 */
public class CopyPhotoFormController implements IController 
{
	/**
	 * Confirmation button for copying a photo
	 */
	@FXML private Button btnCopy;
	
	/**
	 * Cancel button for copying a photo
	 */
	@FXML private Button btnCancel;
	
	/**
	 * List of albums for a user
	 */
	@FXML private ListView<Album> lstAlbums;
	
	/**
	 * Viewable list of albums for a user
	 */
	private ObservableList<Album> albumList;
	
	/**
	 * The desired photo to be copied
	 */
	private Photo photoToCopy;
	
	/**
	 * Function for closing the form
	 */
	private Runnable closeForm;
	
	/**
	 * The stage onto which this scene is shown
	 */
	private Stage stage;
	
	/**
	 * Sets up the Copy photo form controller
	 */
	public void start(Object args)
	{
		Object[] argsArray = (Object[])args;
		
		closeForm = (Runnable)argsArray[0];
		photoToCopy = (Photo)argsArray[1];
		stage = (Stage)argsArray[2];
		
		btnCopy.setOnAction(e -> copy());
		btnCancel.setOnAction(e -> closeForm.run());
		
		populate();
	}
	
	/**
	 * Updates the album list
	 */
	private void populate()
	{
		albumList = FXCollections.observableArrayList();
		albumList.addAll(StateManager.getInstance().getActiveUser().getAlbumList());
		albumList.remove(StateManager.getInstance().getActiveAlbum());
		
		lstAlbums.setItems(albumList);
	}
	
	/**
	 * Copies a photo from one album to another album
	 */
	private void copy()
	{
		Album album = lstAlbums.getSelectionModel().getSelectedItem();
		
		if (album != null)
		{
			AlbumLibrary.addPhotoToAlbum(album, PhotoLibrary.copyPhoto(photoToCopy));
			closeForm.run();
		}
		else
		{
			Alert alert = new Alert(AlertType.ERROR);
    		alert.initOwner(stage);
    		alert.setTitle("Error");
    		alert.setHeaderText("No album selected");
    		alert.setContentText("Select an album to copy this photo.");
    		alert.showAndWait();
		}
	}
}
