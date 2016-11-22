package photoalbum.view;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import photoalbum.app.StateManager;
import photoalbum.models.Photo;

/**
 * Creates a slide show for the viewer
 * @author Stephen Eisen
 * @author Arthur Quintanilla
 */

public class SlideshowViewController implements IController
{
	/**
	 * Button for returning to the previous photo
	 */
	@FXML private Button btnPrevious;
	
	/**
	 * Button for going to the next photo
	 */
	@FXML private Button btnNext;
	
	/**
	 * Button for closing the slideshow
	 */
	@FXML private Button btnClose;
	
	/**
	 * The photo being viewed
	 */
	@FXML private ImageView imgView;
	
	/**
	 * The image number in the currently viewed album
	 */
	@FXML private Label lblImageNum;
	
	/**
	 * The list of photos in the album
	 */
	private List<Photo> photoList;
	
	/**
	 * The the current index inside the photo album list
	 */
	private int imgIndex;
	
	/**
	 * Sets up the SlideshowViewController screen
	 */
	public void start(Object args)
	{
		Runnable r = (Runnable)args;
		
		photoList = StateManager.getInstance().getActiveAlbum().getPhotoList();
		imgIndex = 0;
		
		imgView.setImage(new Image(photoList.get(imgIndex).getImgPath()));
		lblImageNum.setText(String.format("Image %d of %d", imgIndex + 1, photoList.size()));
		
		btnClose.setOnAction(e -> r.run());
		btnPrevious.setOnAction(e -> previous());
		btnNext.setOnAction(e -> next());
	}
	
	/**
	 * Returns to the previously viewed photo in the album
	 */
	private void previous()
	{
		if (imgIndex > 0)
		{
			imgView.setImage(new Image(photoList.get(--imgIndex).getImgPath()));
			lblImageNum.setText(String.format("Image %d of %d", imgIndex + 1, photoList.size()));
		}
	}
	
	/**
	 * Moves to the next photo to be viewed in the album
	 */
	private void next()
	{
		if (imgIndex < photoList.size() - 1)
		{
			imgView.setImage(new Image(photoList.get(++imgIndex).getImgPath()));
			lblImageNum.setText(String.format("Image %d of %d", imgIndex + 1, photoList.size()));
		}
	}
}
