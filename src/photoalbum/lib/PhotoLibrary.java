package photoalbum.lib;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import photoalbum.app.StateManager;
import photoalbum.models.Photo;
import photoalbum.models.Tag;

/**
 * Static Logic methods for computations involving photos
 * @author Stephen Eisen
 * @author Arthur Quintanilla
 */
public class PhotoLibrary
{
	/**
	 * Removes a photo from an album
	 * @param photoList The desired photo list(album) to delete from
	 * @param p The desired photo to delete
	 */
	public static void removePhoto(List<Photo> photoList, Photo p)
	{
		photoList.remove(p);
		StateManager.getInstance().save();
	}
	
	/**
	 * Changes the caption associated with a photo
	 * @param p The desired photo to change the caption on
	 * @param caption The new desired caption for the photo
	 */
	public static void changeCaption(Photo p, String caption)
	{
		p.setCaption(caption);
		StateManager.getInstance().save();
	}
	
	/**
	 * Copies a photo from one album to another album
	 * @param p The photo object to be copied
	 * @return The new photo object to be placed in the desired album
	 */
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
