package photoalbum.view;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import photoalbum.app.StateManager;
import photoalbum.lib.AlbumLibrary;
import photoalbum.models.Album;
import photoalbum.models.Photo;

public class CopyPhotoFormController implements IController 
{
	@FXML private Button btnCopy;
	@FXML private Button btnCancel;
	@FXML private ListView<Album> lstAlbums;
	
	private ObservableList<Album> albumList;
	
	private Photo photoToCopy;
	
	private Runnable closeForm;
	
	public void start(Object args)
	{
		Object[] argsArray = (Object[])args;
		
		closeForm = (Runnable)argsArray[0];
		photoToCopy = (Photo)argsArray[1];
		
		btnCopy.setOnAction(e -> copy());
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
	
	private void copy()
	{
		Album album = lstAlbums.getSelectionModel().getSelectedItem();
		
		if (album != null)
		{
			AlbumLibrary.addPhotoToAlbum(album, photoToCopy);
			closeForm.run();
		}
		else
		{
			// TODO: add error
		}
	}
}
