package photoalbum.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import photoalbum.models.Photo;
import photoalbum.models.Tag;

/**
 * Displays a photo for the user
 * @author Stephen Eisen
 * @author Arthur Quintanilla
 */
public class DisplayPhotoViewController implements IController
{
	/**
	 * The selected image to be displayed
	 */
	@FXML private ImageView imgView;
	
	/**
	 * The caption associated with the selected photo
	 */
	@FXML private Label lblCaption;
	
	/**
	 * The capture date associated with the selected photo
	 */
	@FXML private Label lblCaptureDateTime;
	
	
	@FXML private Label lblTags;
	
	/**
	 * Button for closing the display
	 */
	@FXML private Button btnClose;
	
	/**
	 * Sets up the display photo controller
	 */
	public void start(Object args)
	{
		Object[] argsArray = (Object[])args;
		
		Runnable r = (Runnable)argsArray[0];
		Photo p = (Photo)argsArray[1];
		
		imgView.setImage(new Image(p.getImgPath()));
		lblCaption.setText(String.format("Caption: %s", p.getCaption()));
		lblCaptureDateTime.setText(String.format("Capture Date/Time: ", p.getDateTaken().getTime().toString()));
		setTagText(p);
		btnClose.setOnAction(e -> r.run());
	}
	
	private void setTagText(Photo p)
	{
		String tagString = "Tags: ";
		for (Tag tag : p.getTagList())
		{
			tagString += String.format("[Type: %s, Value: %s], ", tag.getType(), tag.getValue());
		}
		lblTags.setText(tagString);
	}
}
