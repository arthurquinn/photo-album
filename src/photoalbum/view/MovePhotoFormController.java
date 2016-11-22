package photoalbum.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import photoalbum.app.StateManager;
import photoalbum.lib.AlbumLibrary;
import photoalbum.models.Album;
import photoalbum.models.Photo;

public class MovePhotoFormController implements IController
{
	@FXML private Button btnMove;
	@FXML private Button btnCancel;
	@FXML private ListView<Album> lstAlbums;
	
	private ObservableList<Album> albumList;
	
	private Photo photoToMove;
	
	private Runnable closeForm;
	
	public void start(Object args)
	{
		Object[] argsArray = (Object[])args;
		
		closeForm = (Runnable)argsArray[0];
		photoToMove = (Photo)argsArray[1];
		
		btnMove.setOnAction(e -> move());
		btnCancel.setOnAction(e -> closeForm.run());
		
		populate();
	}
	
	private void populate()
	{
		albumList = FXCollections.observableArrayList();
		albumList.addAll(StateManager.getInstance().getActiveUser().getAlbumList());
		albumList.remove(StateManager.getInstance().getActiveAlbum());
		
		lstAlbums.setItems(albumList);
	}
	
	private void move()
	{
		Album album = lstAlbums.getSelectionModel().getSelectedItem();
		
		if (album != null)
		{
			StateManager.getInstance().getActiveAlbum().getPhotoList().remove(photoToMove);
			AlbumLibrary.addPhotoToAlbum(album, photoToMove);
			closeForm.run();
		}
		else
		{
			// TODO: Error here
		}
	}
	
}
