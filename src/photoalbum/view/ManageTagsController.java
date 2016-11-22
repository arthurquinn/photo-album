package photoalbum.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import photoalbum.app.StateManager;
import photoalbum.models.Photo;
import photoalbum.models.Tag;

public class ManageTagsController implements IController
{
	@FXML private Button btnAdd;
	@FXML private Button btnDelete;
	@FXML private Button btnClose;
	@FXML private ListView<Tag> lstTags;
	
	private Photo activePhoto;
	
	private ObservableList<Tag> tagList;
	
	public void start(Object args)
	{
		Object[] argsArray = (Object[])args;
		
		Runnable r = (Runnable)argsArray[0];
		activePhoto = (Photo)argsArray[1];
		
		btnAdd.setOnAction(e -> add());
		btnDelete.setOnAction(e -> delete());
		btnClose.setOnAction(e -> r.run());
		
		populate();
	}
	
	private void populate()
	{
		tagList = FXCollections.observableArrayList();
		tagList.addAll(activePhoto.getTagList());
		
		lstTags.setItems(tagList);
	}
	
	private void add()
	{
		Stage stage = new Stage(StageStyle.DECORATED);
		stage.setTitle("Add Tag");
		
		Runnable r = () -> { stage.close(); populate(); };
		
		Object[] args = new Object[2];
		args[0] = r;
		args[1] = activePhoto.getTagList();
		
		StateManager.getInstance().createPopupWindow(stage, "/photoalbum/view/AddTagForm.fxml", args);
	}
	
	private void delete()
	{
		Tag tag = lstTags.getSelectionModel().getSelectedItem();
		
		if (tag != null)
		{
			activePhoto.removeTag(tag);
			StateManager.getInstance().save();
			tagList.clear();
			populate();
		}
		else
		{
			// TODO: Error here
		}
	}
	
}
