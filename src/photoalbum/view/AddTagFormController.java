package photoalbum.view;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import photoalbum.app.StateManager;
import photoalbum.models.Tag;

public class AddTagFormController implements IController
{
	@FXML private Button btnAdd;
	@FXML private Button btnCancel;
	@FXML private TextField txtType;
	@FXML private TextField txtValue;
	
	private List<Tag> tagList;
	
	private Runnable closeForm;
	
	public void start(Object args)
	{
		Object[] argsArray = (Object[])args;
		
		closeForm = (Runnable)argsArray[0];
		tagList = (List<Tag>)argsArray[1];
		
		btnAdd.setOnAction(e -> add());
		btnCancel.setOnAction(e -> closeForm.run());
	}
	
	private void add()
	{
		String type = txtType.getText();
		String value = txtValue.getText();
		
		if (!type.isEmpty() && !value.isEmpty())
		{
			tagList.add(new Tag(type, value));
			StateManager.getInstance().save();
			closeForm.run();
		}
		else
		{
			// TODO: error here
		}
	}
}
