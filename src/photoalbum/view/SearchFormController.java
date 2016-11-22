package photoalbum.view;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import photoalbum.app.StateManager;
import photoalbum.lib.PhotoLibrary;
import photoalbum.lib.UserLibrary;
import photoalbum.models.Album;
import photoalbum.models.Photo;

/**
 * Controller for the search form
 * @author Stephen Eisen
 * @author Arthur Quintanilla
 */
public class SearchFormController implements IController
{
	/**
	 * Confirmation Button for the search date
	 */
	@FXML private Button btnSearchDate;
	
	/**
	 * Confirmation Button for the search tag
	 */
	@FXML private Button btnSearchTag;
	
	/**
	 * The beginning date to search from
	 */
	@FXML private DatePicker dateFrom;
	
	/**
	 * The end date to search to
	 */
	@FXML private DatePicker dateTo;
	
	/**
	 * The tag type to search for
	 */
	@FXML private TextField txtType;
	
	/**
	 * The tag value to search for
	 */
	@FXML private TextField txtValue;
	
	/**
	 * Panel that holds the image thumbnails
	 */
	@FXML private FlowPane imgPane;
	
	/**
	 * Confirmation button to create an album from the search results
	 */
	@FXML private Button btnCreate;
	
	/**
	 * Button to return to the album select screen
	 */
	@FXML private Button btnClose;
	
	/**
	 * Function to close the form
	 */
	private Runnable closeForm;
	
	/**
	 * Stage onto which this scene is displayed
	 */
	private Stage stage;
	
	/**
	 * Sets up the Search form controller
	 */
	public void start(Object args)
	{
		Object[] argsArray = (Object[])args;
		closeForm = (Runnable)argsArray[0];
		stage = (Stage)argsArray[1];
		
		stage.setResizable(false);
		
		btnSearchDate.setOnAction(e -> searchDate());
		btnSearchTag.setOnAction(e -> searchTag());
		btnCreate.setOnAction(e -> create());
		btnClose.setOnAction(e -> closeForm.run());
	}
	
	/**
	 * Updates the photoList after a search query is executed
	 * @param photoList The search results
	 */
	private void populate(List<Photo> photoList)
	{
		imgPane.getChildren().clear();
		if (photoList != null)
		{
			for (Photo photo : photoList)
			{
				ImageThumbnailController imgControl = new ImageThumbnailController();
				imgControl.setPhoto(photo);
				imgPane.getChildren().add(imgControl);
			}	
		}
	}
	
	/**
	 * Searches all photos in all albums for a specific user based on a date range
	 */
	private void searchDate()
	{
	    if (dateFrom.getValue() != null && dateTo.getValue() != null)
	    {
		    Calendar from = Calendar.getInstance();
		    from.set(dateFrom.getValue().getYear(), dateFrom.getValue().getMonthValue(), dateFrom.getValue().getDayOfMonth());
		    
		    Calendar to = Calendar.getInstance();
		    to.set(dateTo.getValue().getYear(), dateTo.getValue().getMonthValue(), dateTo.getValue().getDayOfMonth());
		    
		    List<Photo> results = UserLibrary.searchByDateRange(from, to);

		    if (results.size() > 0)
		    {
		    	populate(results);
		    }
		    else
		    {
		    	populate(null);
				Alert alert = new Alert(AlertType.WARNING);
	    		alert.initOwner(stage);
	    		alert.setTitle("Warning");
	    		alert.setHeaderText("No results found");
	    		alert.setContentText("No photos match the constraints of this query.");
	    		alert.showAndWait();
		    }	
	    }
	    else
	    {
			Alert alert = new Alert(AlertType.ERROR);
    		alert.initOwner(stage);
    		alert.setTitle("Error");
    		alert.setHeaderText("Missing start or end date");
    		alert.setContentText("Enter a start date and an end date to search for.");
    		alert.showAndWait();
	    }
	}
	
	/**
	 * Searches all photos in all albums for a specific user based on tag value pairs
	 */
	private void searchTag()
	{
		if (!txtType.getText().isEmpty() && !txtValue.getText().isEmpty())
		{
			List<Photo> results = UserLibrary.searchByTagValuePair(txtType.getText(), txtValue.getText());
			
			if (results.size() > 0)
			{
				populate(results);
			}
			else
			{
				Alert alert = new Alert(AlertType.WARNING);
	    		alert.initOwner(stage);
	    		alert.setTitle("Warning");
	    		alert.setHeaderText("No results found");
	    		alert.setContentText("No photos match the constraints of this query.");
	    		alert.showAndWait();
			}
		}
		else
		{
			Alert alert = new Alert(AlertType.ERROR);
    		alert.initOwner(stage);
    		alert.setTitle("Error");
    		alert.setHeaderText("Missing tag type or value");
    		alert.setContentText("Enter a tag type and value to search for.");
    		alert.showAndWait();
		}
	}
	
	/**
	 * Creates an album from search query results
	 */
	private void create()
	{
		if (imgPane.getChildren().size() > 0)
		{
			TextInputDialog dialog = new TextInputDialog();
	        dialog.initOwner(stage); 
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
			Alert alert = new Alert(AlertType.ERROR);
    		alert.initOwner(stage);
    		alert.setTitle("Error");
    		alert.setHeaderText("No search results");
    		alert.setContentText("Run a successful search query before attempting to create an album.");
    		alert.showAndWait();
		}
	}
}
