package photoalbum.models;

import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *  Describes a Photo class that is used to hold user photos
 * @author Stephen Eisen
 * @author Arthur Quintanilla
 */

public class Photo implements Serializable 
{
	/**
	 *  The serial UID associated with Photo objects
	 */
	private static final long serialVersionUID = -4886797984856578196L;

	/**
	 * The caption associated with this Photo
	 */
	private String caption;
	
	/**
	 * The file path that this photo is located
	 */
	private String imgPath;
	
	/**
	 * A list of tags associated with this Photo
	 */
	private List<Tag> tagList;
	
	/**
	 * Constructs a Photo object
	 */
	public Photo(String imgPath)
	{
		this.imgPath = imgPath;
		this.tagList = new ArrayList<Tag>();
	}
	
	/**
	 * Gets the current image file path for this Photo object
	 * @return The file path for this photo
	 */
	public String getImgPath()
	{
		return this.imgPath;
	}
	
	/**
	 * Gets the current caption for this Photo
	 * @return The String caption associated with this Photo
	 */
	public String getCaption()
	{
		return this.caption;
	}
	
	/**
	 * Sets the caption for this Photo
	 * @param caption The new desired caption for this Photo
	 */
	public void setCaption(String caption)
	{
		this.caption = caption;
	}
	
	/**
	 * Adds a string tag to the tagList for this Photo
	 * @param tag The string tag to be associated with this Photo object
	 */
	public void addTag(Tag tag)
	{
		tagList.add(tag);
	}
	
	/**
	 * Removes a string tag from the tagList for this Photo
	 * @param tag The String tag associated with this Photo object
	 */
	public void removeTag(Tag tag)
	{
		tagList.remove(tag);
	}
	
	/**
	 * Gets the list of tags associated with this Photo object
	 * @return The tagList associated with this photo object
	 */
	public List<Tag> getTagList()
	{
		return this.tagList;
	}
	

}
