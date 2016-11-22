package photoalbum.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;

/**
 * Describes an Album class that is used to hold user photos
 * @author Stephen Eisen
 * @author Arthur Quintanilla
 */

public class Album implements Serializable
{
	/**
	 * The serial UID associated with album objects
	 */
	private static final long serialVersionUID = 986593781696016892L;
	
	/**
	 * SimpleStringProperty of the name variable
	 */
	private transient SimpleStringProperty nameProp;
	
	/**
	 * SimpleStringProperty of the numPhotos variable
	 */
	private transient SimpleStringProperty numPhotosProp;
	
	/**
	 * SimpleStringProperty of the oldestPhoto variable
	 */
	private transient SimpleStringProperty oldestPhotoProp;
	
	/**
	 * SimpleStringProperty of the rangePhotos variable
	 */
	private transient SimpleStringProperty rangePhotosProp;
	
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
		this.nameProp = new SimpleStringProperty(name);
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
	
	/**
	 * SimpleStringProperty of the name variable
	 * @return The name of the album
	 */
	public SimpleStringProperty getNamePhotoProp()
	{
		nameProp = new SimpleStringProperty(name);
		return nameProp;
	}
	
	/**
	 * SimpleStringProperty of the oldestPhoto variable
	 * @return The number of photos
	 */
	public SimpleStringProperty getOldestPhotoProp()
	{
		oldestPhotoProp = new SimpleStringProperty("0");
		return oldestPhotoProp;
	}
	
	/**
	 * SimpleStringProperty of the rangePhotos variable
	 * @return The range of photos
	 */
	public SimpleStringProperty getRangePhotosProp()
	{
		rangePhotosProp = new SimpleStringProperty("0");
		return rangePhotosProp;
	}
	
	/**
	 * SimpleStringProperty of the numPhotos variable
	 * @return The number of photos
	 */
	public SimpleStringProperty getNumPhotosProp()
	{
		numPhotosProp = new SimpleStringProperty("" + photoList.size());
		return numPhotosProp;
	}
	
	/**
	 * Sets the name of the album
	 * @param name The new desired name for the album
	 */
	public void setName(String name)
	{
		this.name = name;
		this.nameProp = new SimpleStringProperty(name);
	}
}
