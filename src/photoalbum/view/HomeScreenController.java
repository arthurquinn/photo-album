package photoalbum.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import photoalbum.app.StateManager;
import photoalbum.models.*;
import photoalbum.lib.AlbumLibrary;

import java.io.Serializable;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


import java.util.*;

/**
 * Controls the Login Scene
 * @author Stephen Eisen
 * @author Arthur Quintanilla
 */

public class HomeScreenController implements IController
{
	/**
	 * MenuItem for opening an album
	 */
	@FXML private MenuItem menuOpen;
	
	/**
	 * MenuItem for searching for an album
	 */
	@FXML private MenuItem menuSearch;
	
	/**
	 * MenuItem for creating an album
	 */
	@FXML private MenuItem menuCreate;
	
	/**
	 * MenuItem for renaming an album
	 */
	@FXML private MenuItem menuRename;
	
	/**
	 * MenuItem for deleting an album
	 */
	@FXML private MenuItem menuDelete;
	
	/**
	 * MenuItem for logging out 
	 */
	@FXML private MenuItem menuLogout;
	
	/**
	 * MenuItem for safe quitting the application
	 */
	@FXML private MenuItem menuExit;
	
	/**
	 * A grid containing all the albums
	 */
	@FXML private TableView<Album> albumGrid;
	
	/**
	 * The name of the album in the list
	 */
	@FXML private TableColumn<Album, String> colName;
	
	/**
	 * The number of photos within an album
	 */
	@FXML private TableColumn<Album, String> colNumPhotos;
	
	/**
	 * The oldest photo within an album
	 */
	@FXML private TableColumn<Album, String> colOldest;
	
	/**
	 * The range of dates within an album
	 */
	@FXML private TableColumn<Album, String> colRange;
	
	/**
	 * Observable list of the albums for a user
	 */
	private ObservableList<Album> albumList;
	
	/**
	 * Sets up the Home Scene
	 */
	public void start(Object args)
	{
		StateManager.getInstance().getPrimaryStage().setTitle(String.format("Photo Album - User: %s", StateManager.getInstance().getActiveUser().getUsername()));
		
		populate();
		
		menuOpen.setOnAction(e -> open());
		menuSearch.setOnAction(e -> search());
		menuCreate.setOnAction(e -> create());
		menuRename.setOnAction(e -> rename());
		menuDelete.setOnAction(e -> delete());
		menuLogout.setOnAction(e -> logout());
		menuExit.setOnAction(e -> exit());
	}
	
	/**
	 * Updates the current state of the list
	 */
	private void populate()
	{
		albumList = FXCollections.observableArrayList();
		albumList.addAll(StateManager.getInstance().getActiveUser().getAlbumList());
		
		
		colName.setCellValueFactory(cellData -> cellData.getValue().getNamePhotoProp());
		colNumPhotos.setCellValueFactory(cellData -> cellData.getValue().getNumPhotosProp());
		colOldest.setCellValueFactory(cellData -> cellData.getValue().getOldestPhotoProp());
		colRange.setCellValueFactory(cellData -> cellData.getValue().getRangePhotosProp());
		albumGrid.setItems(albumList);
	}
	
	/**
	 * Opens a selected album
	 */
	private void open()
	{
		Album selectedAlbum = albumGrid.getSelectionModel().getSelectedItem();
		
		if (selectedAlbum != null)
		{
			StateManager.getInstance().setActiveAlbum(selectedAlbum);
			StateManager.getInstance().setActiveScene("/photoalbum/view/PhotosView.fxml", null, 800, 600);	
		}
		else
		{
			Alert alert = new Alert(AlertType.ERROR);
    		alert.initOwner(StateManager.getInstance().getPrimaryStage());
    		alert.setTitle("Error");
    		alert.setHeaderText("No album selected");
    		alert.setContentText("Select an album to open.");
    		alert.showAndWait();
		}
	}
	
	/**
	 * Searches for a photo
	 */
	private void search()
	{
		if (StateManager.getInstance().getActiveUser().getAlbumList().isEmpty())
		{
			Alert alert = new Alert(AlertType.ERROR);
    		alert.initOwner(StateManager.getInstance().getPrimaryStage());
    		alert.setTitle("Error");
    		alert.setHeaderText("No albums for this user");
    		alert.setContentText("Cannot search for photos when there are no albums");
    		alert.showAndWait();
		}
		else
		{			
			Stage stage = new Stage(StageStyle.DECORATED);
			stage.setTitle(String.format("Photo Album - Search in Photos belonging to User: %s", StateManager.getInstance().getActiveUser().getUsername()));
			
			Object[] argsArray = new Object[2];
			Runnable r = () -> { stage.close(); populate(); };
			
			argsArray[0] = r;
			argsArray[1] = stage;
			
			StateManager.getInstance().createPopupWindow(stage, "/photoalbum/view/SearchForm.fxml", argsArray);
		}
	}
	
	/**
	 * Creates an album 
	 */
	private void create()
	{
		TextInputDialog dialog = new TextInputDialog();
        dialog.initOwner(StateManager.getInstance().getPrimaryStage()); dialog.setTitle("Create new album");
        dialog.setHeaderText("Enter a name for the new album.");
        dialog.setContentText("Name: ");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent() && !result.get().isEmpty()) 
        { 
        	if (AlbumLibrary.albumExistsForUser(StateManager.getInstance().getActiveUser(), result.get()))
        	{
    			Alert alert = new Alert(AlertType.ERROR);
        		alert.initOwner(StateManager.getInstance().getPrimaryStage());
        		alert.setTitle("Error");
        		alert.setHeaderText("An album with this name already exists.");
        		alert.setContentText("A single user cannot have duplicate album names. Enter a unique name for this album.");
        		alert.showAndWait();
        	}
        	else
        	{
            	StateManager.getInstance().getActiveUser().addAlbum(new Album(result.get()));
            	StateManager.getInstance().save();
            	populate();
        	}
        }
        else
        {
			Alert alert = new Alert(AlertType.ERROR);
    		alert.initOwner(StateManager.getInstance().getPrimaryStage());
    		alert.setTitle("Error");
    		alert.setHeaderText("Album name empty.");
    		alert.setContentText("Cannot create and album with an empty name.");
    		alert.showAndWait();
        }
	}
	
	/**
	 * Renames an album
	 */
	private void rename()
	{
		Album selectedAlbum = albumGrid.getSelectionModel().getSelectedItem();
		
		if (selectedAlbum != null)
		{
			TextInputDialog dialog = new TextInputDialog();
	        dialog.initOwner(StateManager.getInstance().getPrimaryStage()); dialog.setTitle("Edit album name");
	        dialog.setHeaderText("Enter a new name for this album");
	        dialog.setContentText("Name: ");

	        Optional<String> result = dialog.showAndWait();
	        if (result.isPresent() && !result.get().isEmpty()) 
	        {
	        	if (!AlbumLibrary.editName(StateManager.getInstance().getActiveUser(), selectedAlbum.getName(), result.get()))
	        	{
	    			Alert alert = new Alert(AlertType.ERROR);
	        		alert.initOwner(StateManager.getInstance().getPrimaryStage());
	        		alert.setTitle("Error");
	        		alert.setHeaderText("Album name already exists");
	        		alert.setContentText(String.format("Another album belonging to user: %s already exists with this name.\nAlbum names must be unique per user.\nEnter a unique album name.",
	        				StateManager.getInstance().getActiveUser().getUsername()));
	        		alert.showAndWait();
	        	}
	        	else
	        	{
		        	albumList.clear();
		        	populate();	
	        	}
	        }
		}
		else
		{
			Alert alert = new Alert(AlertType.ERROR);
    		alert.initOwner(StateManager.getInstance().getPrimaryStage());
    		alert.setTitle("Error");
    		alert.setHeaderText("No album selected");
    		alert.setContentText("Select an album to rename.");
    		alert.showAndWait();
		}
	}
	
	/**
	 * Deletes an album
	 */
	private void delete()
	{
		Album selectedAlbum = albumGrid.getSelectionModel().getSelectedItem();
		
		if (selectedAlbum != null)
		{
			Alert alert = new Alert(AlertType.CONFIRMATION);
    		alert.initOwner(StateManager.getInstance().getPrimaryStage());
    		alert.setTitle("Confirmation");
    		alert.setHeaderText("Are you sure you want to delete this album?");
    		alert.setContentText(String.format("Album Name: %s\nAll photos and photo metadata for this album will be deleted as well.", selectedAlbum.getName()));
    		Optional<ButtonType> response = alert.showAndWait();
    		
    		if (response.isPresent() && response.get() == ButtonType.OK)
    		{
    			AlbumLibrary.deleteAlbum(StateManager.getInstance().getActiveUser().getAlbumList(), selectedAlbum);
    			albumList.clear();
    			populate();
    		}
		}
		else
		{
			Alert alert = new Alert(AlertType.ERROR);
    		alert.initOwner(StateManager.getInstance().getPrimaryStage());
    		alert.setTitle("Error");
    		alert.setHeaderText("No album selected");
    		alert.setContentText("Select an album to delete.");
    		alert.showAndWait();
		}
	}
	
	/**
	 * Returns the user to the login screen
	 */
	private void logout()
	{
		StateManager.getInstance().setActiveUser(null);
		StateManager.getInstance().setActiveScene("/photoalbum/view/Login.fxml", null, 257, 338);
	}
	
	/**
	 * Safe quits the application
	 */
	private void exit()
	{
		StateManager.getInstance().saveAndExit();
	}

}
