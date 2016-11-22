package photoalbum.lib;

import java.util.List;

import photoalbum.app.StateManager;
import photoalbum.models.Photo;

public class PhotoLibrary
{
	public static void removePhoto(List<Photo> photoList, Photo p)
	{
		photoList.remove(p);
		StateManager.getInstance().save();
	}
	
	public static void changeCaption(Photo p, String caption)
	{
		p.setCaption(caption);
		StateManager.getInstance().save();
	}
}
