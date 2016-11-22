package photoalbum.view;

import java.io.File;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import photoalbum.app.StateManager;
import photoalbum.models.Album;
import photoalbum.models.Photo;

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
			
		}
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
			
		}
	}
	
	private void remove()
	{
		
	}
	
	private void copy()
	{
		
	}
	
	private void move()
	{
		
	}
	
	private void caption()
	{
		
	}
	
	private void tags()
	{
		
	}
	
	private void display()
	{
		
	}
	
	private void slideshow()
	{
		
	}
}
