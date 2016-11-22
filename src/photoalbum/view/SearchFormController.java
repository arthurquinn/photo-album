package photoalbum.view;

import java.util.Calendar;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import photoalbum.app.StateManager;
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
	@FXML private ListView<Photo> lstPhotos;
	@FXML private Button btnCreate;
	@FXML private Button btnClose;
	
	private ObservableList<Photo> searchResults;
	
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
		searchResults = FXCollections.observableArrayList();
		searchResults.addAll(photoList);
		
		lstPhotos.setItems(searchResults);
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
		
	}
}
