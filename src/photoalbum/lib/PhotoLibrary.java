package photoalbum.lib;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import photoalbum.app.StateManager;
import photoalbum.models.Photo;
import photoalbum.models.Tag;

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
	
	public static Photo copyPhoto(Photo p)
	{
		// Copy date taken
		Calendar newDateTaken = Calendar.getInstance();
		newDateTaken.setTimeInMillis(p.getDateTaken().getTimeInMillis());
		
		// Copy all tags
		List<Tag> newTagList = new ArrayList<Tag>();
		for (Tag tag : p.getTagList())
		{
			Tag newTag = new Tag(new String(tag.getType()), new String(tag.getValue()));
			newTagList.add(newTag);
		}
		
		Photo newPhoto = new Photo(new String(p.getImgPath()), newDateTaken);
		
		// Copy caption
		if (p.getCaption() != null && !p.getCaption().isEmpty())
			newPhoto.setCaption(new String(p.getCaption()));
		
		// Set new tag list
		if (newTagList.size() > 0)
			newPhoto.setTagList(newTagList);
		
		return newPhoto;
	}
}
