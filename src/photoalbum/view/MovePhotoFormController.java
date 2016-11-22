package photoalbum.view;

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
import photoalbum.models.Album;
import photoalbum.models.Photo;

/**
 * Used for moving a photo from one album to another album
 * @author Stephen Eisen
 * @author Arthur Quintanilla
 */
public class MovePhotoFormController implements IController
{
	/**
	 * Confirmation button for moving a photo
	 */
	@FXML private Button btnMove;
	
	/**
	 * Button for canceling the moving of a photo
	 */
	@FXML private Button btnCancel;
	
	/**
	 * A list of all the possible albums that can move an album
	 */
	@FXML private ListView<Album> lstAlbums;
	
	/**
	 * An observable list of all the possible albums that can be moved to
	 */
	private ObservableList<Album> albumList;
	
	/**
	 * Temporary reference to the photo that is to be moved
	 */
	private Photo photoToMove;
	
	/**
	 * Function for closing the form
	 */
	private Runnable closeForm;
	
	/**
	 * The stage onto which this scene is shown
	 */
	private Stage stage;
	
	/**
	 * Sets up the Move photo form controller
	 */
	public void start(Object args)
	{
		Object[] argsArray = (Object[])args;
		
		closeForm = (Runnable)argsArray[0];
		photoToMove = (Photo)argsArray[1];
		stage = (Stage)argsArray[2];
		stage.setResizable(false);
		
		btnMove.setOnAction(e -> move());
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
	 * Moves a selected photo from one album to another album
	 * The photo is deleted from the original album
	 */
	private void move()
	{
		Album album = lstAlbums.getSelectionModel().getSelectedItem();
		
		if (album != null)
		{
			StateManager.getInstance().getActiveAlbum().getPhotoList().remove(photoToMove);
			AlbumLibrary.addPhotoToAlbum(album, photoToMove);
			closeForm.run();
		}
		else
		{
			Alert alert = new Alert(AlertType.ERROR);
    		alert.initOwner(stage);
    		alert.setTitle("Error");
    		alert.setHeaderText("No album selected");
    		alert.setContentText("Select an album to move this photo into.");
    		alert.showAndWait();
		}
	}
	
}
