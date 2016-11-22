package photoalbum.view;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.FlowPane;
import photoalbum.app.StateManager;
import photoalbum.lib.PhotoLibrary;
import photoalbum.lib.UserLibrary;
import photoalbum.models.Album;
import photoalbum.models.Photo;

public class SearchFormController implements IController
{
	@FXML private Button btnSearchDate;
	@FXML private Button btnSearchTag;
	@FXML private DatePicker dateFrom;
	@FXML private DatePicker dateTo;
	@FXML private TextField txtType;
	@FXML private TextField txtValue;
	@FXML private FlowPane imgPane;
	@FXML private Button btnCreate;
	@FXML private Button btnClose;
	
	private Runnable closeForm;
	
	public void start(Object args)
	{
		closeForm = (Runnable)args;
		
		btnSearchDate.setOnAction(e -> searchDate());
		btnSearchTag.setOnAction(e -> searchTag());
		btnCreate.setOnAction(e -> create());
		btnClose.setOnAction(e -> closeForm.run());
	}
	
	private void populate(List<Photo> photoList)
	{
		imgPane.getChildren().clear();
		for (Photo photo : photoList)
		{
			ImageThumbnailController imgControl = new ImageThumbnailController();
			imgControl.setPhoto(photo);
			imgPane.getChildren().add(imgControl);
		}
	}
	
	private void searchDate()
	{
	    Calendar from = Calendar.getInstance();
	    from.set(dateFrom.getValue().getYear(), dateFrom.getValue().getMonthValue(), dateFrom.getValue().getDayOfMonth());
	    
	    Calendar to = Calendar.getInstance();
	    to.set(dateTo.getValue().getYear(), dateTo.getValue().getMonthValue(), dateTo.getValue().getDayOfMonth());
	    
	    List<Photo> results = UserLibrary.searchByDateRange(StateManager.getInstance().getActiveUser().getAlbumList(), from, to);

	    populate(results);
	}
	
	private void searchTag()
	{
		
	}
	
	private void create()
	{
		if (imgPane.getChildren().size() > 0)
		{
			TextInputDialog dialog = new TextInputDialog();
	        dialog.initOwner(StateManager.getInstance().getPrimaryStage()); 
	        dialog.setTitle("Create Album From Search Results");
	        dialog.setHeaderText("Enter a name for the new album.");
	        dialog.setContentText("Name: ");

	        Optional<String> result = dialog.showAndWait();
	        if (result.isPresent()) 
	        { 
	        	Album album = new Album(result.get());
	        	for (Node n : imgPane.getChildren())
	        	{
	        		Photo p = ((ImageThumbnailController)n).getPhoto();
	        		Photo newPhoto = PhotoLibrary.copyPhoto(p);
	        		album.addPhoto(newPhoto);
	        	}
	        	StateManager.getInstance().getActiveUser().addAlbum(album);
	        	StateManager.getInstance().save();
	        	closeForm.run();
	        }
		}
		else
		{
			// TODO: Error here
		}
	}
}
