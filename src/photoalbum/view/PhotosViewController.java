package photoalbum.view;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import photoalbum.app.StateManager;
import photoalbum.models.Album;
import photoalbum.models.Photo;
import photoalbum.lib.AlbumLibrary;
import photoalbum.lib.PhotoLibrary;

/**
 * Controls the actions for photos
 * @author Stephen Eisen
 * @author Arthur Quintanilla
 */
public class PhotosViewController implements IController
{
	/**
	 * Menu for closing album
	 */
	@FXML private MenuItem mnuCloseAlbum;
	
	/**
	 * Menu for logging out
	 */
	@FXML private MenuItem mnuLogout;
	
	/**
	 * Menu for safe quitting the application
	 */
	@FXML private MenuItem mnuExit;
	
	/**
	 * Menu for adding a photo
	 */
	@FXML private MenuItem mnuAdd;
	
	/**
	 * Menu for deleting a photo
	 */
	@FXML private MenuItem mnuRemove;
	
	/**
	 * Menu for copying a photo
	 */
	@FXML private MenuItem mnuCopy;
	
	/**
	 * Menu for moving a photo
	 */
	@FXML private MenuItem mnuMove;
	
	/**
	 * Menu for adding a caption to a photo
	 */
	@FXML private MenuItem mnuCaption;
	
	/**
	 * Menu for adding tags to the photo
	 */
	@FXML private MenuItem mnuTags;
	
	/**
	 * Menu for displaying the photo
	 */
	@FXML private MenuItem mnuDisplay;
	
	/**
	 * Menu for entering slideshow mode for current album
	 */
	@FXML private MenuItem mnuSlideshow;
	
	/**
	 * Panal that holds the photo thumbnails
	 */
	@FXML private FlowPane imgPane;
	
	/**
	 * The currently selected image
	 */
	private ImageThumbnailController selectedImage;
	
	/**
	 * Sets up the Photo view controller
	 */
	public void start(Object args)
	{
		StateManager.getInstance().getPrimaryStage().setTitle(String.format("Photo Library - User: %s - Album: %s", 
				StateManager.getInstance().getActiveUser().getUsername(), 
				StateManager.getInstance().getActiveAlbum().getName()));
		
		mnuCloseAlbum.setOnAction(e -> closeAlbum());
		mnuLogout.setOnAction(e -> logout());
		mnuExit.setOnAction(e -> exit());
		mnuAdd.setOnAction(e -> add());
		mnuRemove.setOnAction(e -> remove());
		mnuCopy.setOnAction(e -> copy());
		mnuMove.setOnAction(e -> move());
		mnuCaption.setOnAction(e -> caption());
		mnuTags.setOnAction(e -> tags());
		mnuDisplay.setOnAction(e -> display());
		mnuSlideshow.setOnAction(e -> slideshow());
		
		populate();
	}
	
	/**
	 * Updates the photo view FlowPane
	 */
	private void populate()
	{
		Album album = StateManager.getInstance().getActiveAlbum();
		
		for (Photo photo : album.getPhotoList())
		{
			ImageThumbnailController imgControl = new ImageThumbnailController();
			imgControl.setPhoto(photo);
			imgControl.setOnMouseClicked(e -> highlightImage(e.getSource()));
			
			imgPane.getChildren().add(imgControl);
		}
	}
	
	/**
	 * Gets the currently selected image and puts a highlight around it
	 * @param source The photo to be highlighted
	 */
	private void highlightImage(Object source)
	{
		ImageThumbnailController imgControl = (ImageThumbnailController)source;
		
		for (Node n : imgPane.getChildren())
		{
			((ImageThumbnailController)n).setHighlight(false);
		}
		
		imgControl.setHighlight(true);
		selectedImage = imgControl;
	}
	
	/**
	 * Closes the album and reopens the home screen
	 */
	private void closeAlbum()
	{
		StateManager.getInstance().setActiveScene("/photoalbum/view/HomeScreen.fxml", null, 800, 600);
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
	
	/**
	 * Adds a photo to the album
	 */
	private void add()
	{
		FileChooser photoChooser = new FileChooser();
		photoChooser.setTitle("Select a Photo");
		photoChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files (*.jpg, *.jpeg, *.gif, *.png)", "*.jpg", "*.jpeg", "*.gif", "*.png"));
		File imgFile = photoChooser.showOpenDialog(StateManager.getInstance().getPrimaryStage());
		
		if (imgFile != null && imgFile.exists())
		{
			Calendar dateTaken = Calendar.getInstance();
			dateTaken.setTimeInMillis(imgFile.lastModified());
			
			Photo photo = new Photo(imgFile.toURI().toString(), dateTaken);
			
			ImageThumbnailController imgControl = new ImageThumbnailController();
			imgControl.setPhoto(photo);
			imgControl.setOnMouseClicked(e -> highlightImage(e.getSource()));
			
			imgPane.getChildren().add(imgControl);
			
			
			StateManager.getInstance().getActiveAlbum().addPhoto(photo);
			StateManager.getInstance().save();
		}
	}
	
	/**
	 * Removes a photo from the album
	 */
	private void remove()
	{
		if (selectedImage == null)
		{
			Alert alert = new Alert(AlertType.ERROR);
    		alert.initOwner(StateManager.getInstance().getPrimaryStage());
    		alert.setTitle("Error");
    		alert.setHeaderText("No photo selected");
    		alert.setContentText("Select a photo to delete.");
    		alert.showAndWait();
		}
		else
		{
			Alert alert = new Alert(AlertType.CONFIRMATION);
    		alert.initOwner(StateManager.getInstance().getPrimaryStage());
    		alert.setTitle("Confirmation");
    		alert.setHeaderText("Are you sure you want to delete this photo?");
    		alert.setContentText("This photo and all of its metadata will be deleted.");
    		Optional<ButtonType> response = alert.showAndWait();
    		
    		if (response.isPresent() && response.get() == ButtonType.OK)
    		{
    			Photo p = selectedImage.getPhoto();
    			PhotoLibrary.removePhoto(StateManager.getInstance().getActiveAlbum().getPhotoList(), p);
    			imgPane.getChildren().clear();
    			populate();
    		}
		}
	}
	
	/**
	 * Copies a photo from one album to another album
	 */
	private void copy()
	{
		if (selectedImage == null)
		{
			Alert alert = new Alert(AlertType.ERROR);
    		alert.initOwner(StateManager.getInstance().getPrimaryStage());
    		alert.setTitle("Error");
    		alert.setHeaderText("No photo selected");
    		alert.setContentText("Select a photo to copy.");
    		alert.showAndWait();
		}
		else
		{			
			Stage stage = new Stage(StageStyle.DECORATED);
			stage.setTitle("Copy Photo into another Album");
			
			Runnable r = () -> stage.close();
			Photo p = selectedImage.getPhoto();
			
			Object[] args = new Object[3];
			args[0] = r;
			args[1] = p;
			args[2] = stage;
			
			StateManager.getInstance().createPopupWindow(stage, "/photoalbum/view/CopyPhotoForm.fxml", args);
		}
	}
	
	/**
	 * Moves a photo from one album to another album
	 */
	private void move()
	{
		if (selectedImage == null)
		{
			Alert alert = new Alert(AlertType.ERROR);
    		alert.initOwner(StateManager.getInstance().getPrimaryStage());
    		alert.setTitle("Error");
    		alert.setHeaderText("No photo selected");
    		alert.setContentText("Select a photo to move.");
    		alert.showAndWait();
		}
		else
		{			
			Stage stage = new Stage(StageStyle.DECORATED);
			stage.setTitle("Move Photo into another Album");
			
			Runnable r = () -> { stage.close(); imgPane.getChildren().clear(); populate(); };
			Photo p = selectedImage.getPhoto();
			
			Object[] args = new Object[3];
			args[0] = r;
			args[1] = p;
			args[2] = stage;
			
			StateManager.getInstance().createPopupWindow(stage, "/photoalbum/view/MovePhotoForm.fxml", args);
		}
	}
	
	/**
	 * Add or edit the caption of a photo
	 */
	private void caption()
	{
		if (selectedImage == null)
		{
			Alert alert = new Alert(AlertType.ERROR);
    		alert.initOwner(StateManager.getInstance().getPrimaryStage());
    		alert.setTitle("Error");
    		alert.setHeaderText("No photo selected");
    		alert.setContentText("Select a photo to caption.");
    		alert.showAndWait();
		}
		else
		{
			TextInputDialog dialog = new TextInputDialog();
	        dialog.initOwner(StateManager.getInstance().getPrimaryStage()); dialog.setTitle("Add caption to photo");
	        dialog.setHeaderText("Enter a caption for this photo");
	        dialog.setContentText("Caption: ");

	        Optional<String> result = dialog.showAndWait();
	        if (result.isPresent() && !result.get().isEmpty()) 
	        { 
	        	PhotoLibrary.changeCaption(selectedImage.getPhoto(), result.get());
	        	imgPane.getChildren().clear();
	        	populate();
	        }
		}
	}
	
	/**
	 * Manage the tags associated with a photo
	 */
	private void tags()
	{
		if (selectedImage != null)
		{
			Stage stage = new Stage(StageStyle.DECORATED);
			stage.setTitle("Manage Tags");
			
			Runnable r = () -> stage.close();
			Photo p = selectedImage.getPhoto();
			
			Object[] args = new Object[3];
			args[0] = r;
			args[1] = p;
			args[2] = stage;
			
			StateManager.getInstance().createPopupWindow(stage, "/photoalbum/view/ManageTags.fxml", args);
		}
		else
		{
			Alert alert = new Alert(AlertType.ERROR);
    		alert.initOwner(StateManager.getInstance().getPrimaryStage());
    		alert.setTitle("Error");
    		alert.setHeaderText("No photo selected");
    		alert.setContentText("Select a photo to manage its tags.");
    		alert.showAndWait();
		}
	}
	
	/**
	 * Displays a selected photo
	 */
	private void display()
	{
		if (selectedImage != null)
		{
			Stage stage = new Stage(StageStyle.DECORATED);
			stage.setTitle("Photo Display");
			
			Runnable r = () -> stage.close();
			Photo p = selectedImage.getPhoto();
			
			Object[] args = new Object[2];
			args[0] = r;
			args[1] = p;
			
			StateManager.getInstance().createPopupWindow(stage, "/photoalbum/view/DisplayPhotoView.fxml", args);
		}
		else
		{
			Alert alert = new Alert(AlertType.ERROR);
    		alert.initOwner(StateManager.getInstance().getPrimaryStage());
    		alert.setTitle("Error");
    		alert.setHeaderText("No photo selected");
    		alert.setContentText("Select a photo to display.");
    		alert.showAndWait();
		}
	}
	
	/**
	 * Displays a slideshow containing all photos within the currently selected album
	 */
	private void slideshow()
	{
		if (StateManager.getInstance().getActiveAlbum().getPhotoList().size() > 0)
		{
			Stage stage = new Stage(StageStyle.DECORATED);
			stage.setTitle(String.format("Slideshow - Album: %s", StateManager.getInstance().getActiveAlbum().getName()));
			
			Runnable r = () -> stage.close();
			
			StateManager.getInstance().createPopupWindow(stage, "/photoalbum/view/SlideshowView.fxml", (Object)r);
		}
		else
		{
			Alert alert = new Alert(AlertType.ERROR);
    		alert.initOwner(StateManager.getInstance().getPrimaryStage());
    		alert.setTitle("Error");
    		alert.setHeaderText("No photos in album");
    		alert.setContentText("Cannot display a slideshow for empty album.");
    		alert.showAndWait();
		}
	}
}
