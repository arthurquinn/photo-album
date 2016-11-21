package photoalbum.models;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

/**
 * Describes a user class that contains the name and password of this object
 * @author Stephen Eisen
 * @author Arthur Quintanilla
 */

public class User implements Serializable
{	
	/**
	 * The name of this User object
	 */
	private String username;
	
	/**
	 * The password associated with this User object
	 */
	private String password;
	
	/**
	 * A list of the albums associated with this User
	 */
	private List<Album> albumList;
	
	/**
	 * Constructs a User object
	 * @param username The name of the object to be created
	 * @param password The password to be associated with this object
	 */
	public User(String username, String password)
	{
		this.username = username;
		this.password = password;
		this.albumList = new ArrayList<Album>();
	}
	
	/**
	 * Gets the name of this user object
	 * @return The name of this user object
	 */
	public String getUsername()
	{
		return this.username;
	}
	
	/**
	 * Gets the password associated with this user object
	 * @return The password for this User
	 */
	public String getPassword()
	{
		return this.password;
	}
	
	/**
	 * Sets the name of this User object
	 * @param username The new desired name for this object
	 */
	public void setUsername(String username)
	{
		this.username = username;
	}
	
	/**
	 * Sets the password of this User object
	 * @param password The new desired password for this object
	 */
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	/**
	 * Adds an album to this users albumList
	 * @param album The album object to be added
	 */
	public void addAlbum(Album album)
	{
		albumList.add(album);
	}
	
}
