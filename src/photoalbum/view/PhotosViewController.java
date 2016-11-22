package photoalbum.view;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextInputDialog;
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


public class PhotosViewController implements IController
{
	@FXML private MenuItem mnuLogout;
	@FXML private MenuItem mnuExit;
	@FXML private MenuItem mnuAdd;
	@FXML private MenuItem mnuRemove;
	@FXML private MenuItem mnuCopy;
	@FXML private MenuItem mnuMove;
	@FXML private MenuItem mnuCaption;
	@FXML private MenuItem mnuTags;
	@FXML private MenuItem mnuDisplay;
	@FXML private MenuItem mnuSlideshow;
	
	@FXML private Label lblAlbumTitle;
	@FXML private FlowPane imgPane;
	
	private ImageThumbnailController selectedImage;
	
	public void start(Object args)
	{
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
	
	private void populate()
	{
		Album album = StateManager.getInstance().getActiveAlbum();
		
		lblAlbumTitle.setText(String.format("Active Album: %s", album.getName()));
		
		for (Photo photo : album.getPhotoList())
		{
			ImageThumbnailController imgControl = new ImageThumbnailController();
			imgControl.setPhoto(photo);
			imgControl.setOnMouseClicked(e -> highlightImage(e.getSource()));
			
			imgPane.getChildren().add(imgControl);
		}
	}
	
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
	
	private void logout()
	{
		StateManager.getInstance().setActiveUser(null);
		StateManager.getInstance().setActiveScene("/photoalbum/view/Login.fxml", null, 400, 600);
	}
	
	private void exit()
	{
		StateManager.getInstance().saveAndExit();
	}
	
	private void add()
	{
		FileChooser photoChooser = new FileChooser();
		photoChooser.setTitle("Select a Photo");
		photoChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files (*.jpg, *.jpeg, *.gif, *.png)", "*.jpg", "*.jpeg", "*.gif", "*.png"));
		File imgFile = photoChooser.showOpenDialog(StateManager.getInstance().getPrimaryStage());
		
		if (imgFile != null && imgFile.exists())
		{
			Photo photo = new Photo(imgFile.toURI().toString());
			
			ImageThumbnailController imgControl = new ImageThumbnailController();
			imgControl.setPhoto(photo);
			imgControl.setOnMouseClicked(e -> highlightImage(e.getSource()));
			
			imgPane.getChildren().add(imgControl);
			
			
			StateManager.getInstance().getActiveAlbum().addPhoto(photo);
			StateManager.getInstance().save();
		}
	}
	
	private void remove()
	{
		if (selectedImage == null)
		{
			// TODO: Add error message here
		}
		else
		{
			Photo p = selectedImage.getPhoto();
			PhotoLibrary.removePhoto(StateManager.getInstance().getActiveAlbum().getPhotoList(), p);
			imgPane.getChildren().clear();
			populate();
		}
	}
	
	private void copy()
	{
		
	}
	
	private void move()
	{
		
	}
	
	private void caption()
	{
		if (selectedImage == null)
		{
			// TODO: Add error message
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
	
	private void tags()
	{
		
	}
	
	private void display()
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
	
	private void slideshow()
	{
		Stage stage = new Stage(StageStyle.DECORATED);
		stage.setTitle(String.format("Slideshow - Album: %s", StateManager.getInstance().getActiveAlbum().getName()));
		
		Runnable r = () -> stage.close();
		
		StateManager.getInstance().createPopupWindow(stage, "/photoalbum/view/SlideshowView.fxml", (Object)r);
	}
}
