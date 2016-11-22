package photoalbum.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import photoalbum.lib.AlbumLibrary;

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
	 * The date of the oldest photo in the album
	 */
	private Calendar oldestPhotoDate;
	
	/**
	 * The date of the newest photo in the album
	 */
	private Calendar newestPhotoDate;

	
	/**
	 * A list of the photos contained in this album
	 */
	private List<Photo> photoList;
	
	/**
	 * Constructs an Album object
	 * @param name the name of this album
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
		setOldestPhotoDate();
		oldestPhotoProp = new SimpleStringProperty(oldestPhotoDate != null ? oldestPhotoDate.getTime().toString() : "Album is empty");
		return oldestPhotoProp;
	}
	
	/**
	 * SimpleStringProperty of the rangePhotos variable
	 * @return The range of photos
	 */
	public SimpleStringProperty getRangePhotosProp()
	{
		setOldestPhotoDate();
		setNewestPhotoDate();
		
		rangePhotosProp = new SimpleStringProperty(newestPhotoDate != null && oldestPhotoDate != null ? 
													String.format("%s to %s", oldestPhotoDate.getTime().toString(), newestPhotoDate.getTime().toString()) :
													"Album is empty");
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
	
	/**
	 * Sets the date of the oldest photo within this album
	 */
	private void setOldestPhotoDate()
	{
		if (photoList.size() < 1)
		{
			oldestPhotoDate = null;
		}
		else
		{
			Calendar oldestDate = photoList.get(0).getDateTaken();
			for (Photo photo : photoList)
			{
				if (photo.getDateTaken().before(oldestDate))
				{
					oldestDate = photo.getDateTaken();
				}
			}
			oldestPhotoDate = oldestDate;
		}
	}
	
	/**
	 * Sets the date of the newest photo in this album
	 */
	private void setNewestPhotoDate()
	{
		if (photoList.size() < 1)
		{
			newestPhotoDate = null;
		}
		else
		{
			Calendar newestDate = photoList.get(0).getDateTaken();
			for (Photo photo : photoList)
			{
				if (photo.getDateTaken().after(newestDate))
				{
					newestDate = photo.getDateTaken();
				}
			}
			newestPhotoDate = newestDate;
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString()
	{
		return this.name;
	}
}
