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

public class ImageThumbnailController extends VBox
{
	@FXML private ImageView imgView;
	@FXML private Label lblCaption;
	
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
	
	public void setImage(String imgPath)
	{
		imgView.setImage(new Image(imgPath));
	}
	
	public void setCaption(String text)
	{
		this.lblCaption.setText(text);
	}
	
	public void setHighlight(boolean value)
	{
		if (value)
			this.setBackground(new Background(new BackgroundFill(Color.AQUA, CornerRadii.EMPTY, Insets.EMPTY)));
		else
			this.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
	}
}
