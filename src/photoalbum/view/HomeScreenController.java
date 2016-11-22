package photoalbum.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
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

public class HomeScreenController
{
	
	@FXML private MenuItem menuOpen;
	@FXML private MenuItem menuSearch;
	@FXML private MenuItem menuCreate;
	@FXML private MenuItem menuRename;
	@FXML private MenuItem menuDelete;
	@FXML private MenuItem menuLogout;
	@FXML private MenuItem menuExit;
	
	@FXML private TableView<Album> albumGrid;
	@FXML private TableColumn<Album, String> colName;
	@FXML private TableColumn<Album, String> colNumPhotos;
	@FXML private TableColumn<Album, String> colOldest;
	@FXML private TableColumn<Album, String> colRange;
	
	private ObservableList<Album> albumList;
	
	/**
	 * Sets up the Home Scene
	 */
	public void start()
	{
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
		
		System.out.println(albumList.size());
		System.out.println(StateManager.getInstance().getActiveUser().getUsername());
		albumGrid.setItems(albumList);
	}
	
	private void open()
	{
		// new scene
	}
	
	private void search()
	{
		
	}
	
	private void create()
	{
		TextInputDialog dialog = new TextInputDialog();
        dialog.initOwner(StateManager.getInstance().getPrimaryStage()); dialog.setTitle("Create new album");
        dialog.setHeaderText("Enter a name for the new album.");
        dialog.setContentText("Name: ");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) 
        { 
        	StateManager.getInstance().getActiveUser().addAlbum(new Album(result.get()));
        	StateManager.getInstance().save();
        	populate();
        }
	}
	
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
	        	List<Album> userAlbums = StateManager.getInstance().getActiveUser().getAlbumList();
	        	AlbumLibrary.editName(userAlbums, selectedAlbum.getName(), result.get());
	        	albumList.clear();
	        	populate();
	        }
		}
	}
	
	private void delete()
	{
		Album selectedAlbum = albumGrid.getSelectionModel().getSelectedItem();
		
		if (selectedAlbum != null)
		{
			Alert alert = new Alert(AlertType.CONFIRMATION);
    		alert.initOwner(StateManager.getInstance().getPrimaryStage());
    		alert.setTitle("Confirmation");
    		alert.setHeaderText("Are you sure you want to delete this album?");
    		alert.setContentText(String.format("Album Name: %s", selectedAlbum.getName()));
    		Optional<ButtonType> response = alert.showAndWait();
    		
    		if (response.isPresent() && response.get() == ButtonType.OK)
    		{
    			AlbumLibrary.deleteAlbum(StateManager.getInstance().getActiveUser().getAlbumList(), selectedAlbum);
    			albumList.clear();
    			populate();
    		}
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
