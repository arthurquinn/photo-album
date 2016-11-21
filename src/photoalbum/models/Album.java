package photoalbum.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Describes an Album class that is used to hold user photos
 * @author Stephen Eisen
 * @author Arthur Quintanilla
 */

public class Album 
{
	/**
	 * The name of this album object
	 */
	private String name;
	
	/**
	 * A list of the photos contained in this album
	 */
	private List<Photo> photoList;
	
	/**
	 * Constructs an Album object
	 * @param The name of this album
	 */
	public Album(String name)
	{
		this.name = name;
		photoList = new ArrayList<Photo>();
	}
	
	/**
	 * Gets the current name of this Album
	 * @return The name of the album
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Gets the current list of photos that are inside this album
	 * @return The photo list of this album
	 */
	public List<Photo> getPhotoList()
	{
		return photoList;
	}
	
	/**
	 * Adds a photo object to the photoList
	 * @param photo The object to be added to the List
	 */
	public void addPhoto(Photo photo)
	{
		photoList.add(photo);
	}

}
