package photoalbum.models;

import java.io.Serializable;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;

/**
 * Describes a user class that contains the name and password of this object
 * @author Stephen Eisen
 * @author Arthur Quintanilla
 */

public class User implements Serializable
{	
	/**
	 * The serial UID associated with album objects
	 */
	private static final long serialVersionUID = 6306381479040511532L;

	/**
	 * The name of this User object
	 */
	private transient SimpleStringProperty usernameProp;
	
	/**
	 * The password associated with this User object
	 */
	private transient SimpleStringProperty passwordProp;
	
	/**
	 * The username associated with this User object
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
		this.usernameProp = new SimpleStringProperty(username);
		this.passwordProp = new SimpleStringProperty(password);
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
	 * Gets the name of this user object
	 * @return The name of this user object
	 */
	public SimpleStringProperty getUsernameProp()
	{
		return this.usernameProp;
	}
	
	/**
	 * Gets the password associated with this user object
	 * @return The password for this User
	 */
	public SimpleStringProperty getPasswordProp()
	{
		return this.passwordProp;
	}
	
	/**
	 * Sets the name of this User object
	 * @param username The new desired name for this object
	 */
	public void setUsername(String username)
	{
		this.usernameProp = new SimpleStringProperty(username);
		this.username = username;
	}
	
	/**
	 * Sets the password of this User object
	 * @param password The new desired password for this object
	 */
	public void setPassword(String password)
	{
		this.passwordProp = new SimpleStringProperty(password);
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
	
	/**
	 * Sets the prop variables for this object
	 */
	public void setProps()
	{
		this.usernameProp = new SimpleStringProperty(username);
		this.passwordProp = new SimpleStringProperty(password);
	}
	
	/**
	 * Gets the current list of albums associated with this user
	 * @return The current albumList for this object
	 */
	public List<Album> getAlbumList()
	{
		return this.albumList;
	}
	
}
