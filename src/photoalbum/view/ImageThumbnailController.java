package photoalbum.view;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import photoalbum.models.Photo;

/**
 * Custom fxml control to display an image with its caption
 * @author Stephen Eisen
 * @author Arthur Quintanilla
 */
public class ImageThumbnailController extends VBox
{
	/**
	 * The photo to be displayed
	 */
	@FXML private ImageView imgView;
	
	/**
	 * The caption to be associated with the photo
	 */
	@FXML private Label lblCaption;
	
	/**
	 * Temporary reference to photo object for the display
	 */
	private Photo photoModel;
	
	/**
	 * Constructs an ImageThumbnailController obnject
	 */
	public ImageThumbnailController()
	{
        try 
        {
	        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/photoalbum/view/ImageThumbnail.fxml"));
	        fxmlLoader.setRoot(this);
	        fxmlLoader.setController(this);
	        fxmlLoader.load();
        } 
        catch (IOException e)
        {
        	e.printStackTrace();
        }
	}
	
	/**
	 * Sets the current photo object for this Image controller
	 * @param p The new desired image for this object
	 */
	public void setPhoto(Photo p)
	{
		imgView.setImage(new Image(p.getImgPath()));
		lblCaption.setText(p.getCaption());
		this.photoModel = p;
	}
	
	/**
	 * Gets the currently selected photo for this Image object
	 * @return The photo associated with this image object
	 */
	public Photo getPhoto()
	{
		return this.photoModel;
	}
	
	/**
	 * Sets the highlight boolean to true or false
	 * @param value Highlights the object if true. False will remove the highlight.
	 */
	public void setHighlight(boolean value)
	{
		if (value)
			this.setBackground(new Background(new BackgroundFill(Color.AQUA, CornerRadii.EMPTY, Insets.EMPTY)));
		else
			this.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
	}
}
