package photoalbum.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import photoalbum.models.Photo;

public class DisplayPhotoViewController implements IController
{
	@FXML private ImageView imgView;
	@FXML private Label lblCaption;
	@FXML private Label lblCaptureDateTime;
	@FXML private Label lblTags;
	@FXML private Button btnClose;
	
	
	public void start(Object args)
	{
		Object[] argsArray = (Object[])args;
		
		Runnable r = (Runnable)argsArray[0];
		Photo p = (Photo)argsArray[1];
		
		imgView.setImage(new Image(p.getImgPath()));
		lblCaption.setText(p.getCaption());
		btnClose.setOnAction(e -> r.run());
	}
}
