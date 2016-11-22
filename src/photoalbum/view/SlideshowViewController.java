package photoalbum.view;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import photoalbum.app.StateManager;
import photoalbum.models.Photo;

public class SlideshowViewController implements IController
{
	@FXML private Button btnPrevious;
	@FXML private Button btnNext;
	@FXML private Button btnClose;
	
	@FXML private ImageView imgView;
	
	@FXML private Label lblImageNum;
	
	private List<Photo> photoList;
	private int imgIndex;
	
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
	
	private void previous()
	{
		if (imgIndex > 0)
		{
			imgView.setImage(new Image(photoList.get(--imgIndex).getImgPath()));
			lblImageNum.setText(String.format("Image %d of %d", imgIndex + 1, photoList.size()));
		}
		else
		{
			// TODO: Add error here
		}
	}
	
	private void next()
	{
		if (imgIndex < photoList.size() - 1)
		{
			imgView.setImage(new Image(photoList.get(++imgIndex).getImgPath()));
			lblImageNum.setText(String.format("Image %d of %d", imgIndex + 1, photoList.size()));
		}
		else
		{
			// TODO: Add error here
		}
	}
}
