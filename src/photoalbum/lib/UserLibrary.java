package photoalbum.lib;

import photoalbum.app.StateManager;
import photoalbum.models.Album;
import photoalbum.models.Photo;
import photoalbum.models.Tag;
import photoalbum.models.User;

import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Static Logic methods for computations involving users
 * @author Stephen Eisen
 * @author Arthur Quintanilla
 */
public class UserLibrary
{
	/**
	 * Gets a specified user from a list of users
	 * @param userList The list of users to search from
	 * @param username The specified user object to search for
	 * @return The user object matching the specified name
	 */
	public static User getUser(List<User> userList, String username)
	{
		for (User user : userList)
		{
			if (user.getUsername().equals(username))
			{
				return user;
			}
		}
		return null;
	}
	
	/**
	 * Checks to see if a user exits inside a user list
	 * @param userList The list of users to search from
	 * @param username The specified user object to search for
	 * @return True if the user was found else false
	 */
	public static boolean userExists(List<User> userList, String username)
	{
		for (User user : userList)
		{
			if (user.getUsername().equals(username))
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks to see if the password entered matches the password for the username entered
	 * @param userList The list of users to search from
	 * @param username The specified user object to search for
	 * @param password The password to be checked against the username
	 * @return True if the password matches the usernames password else false
	 */
	public static boolean validateUser(List<User> userList, String username, String password)
	{
		for (User user : userList)
		{
			if (user.getUsername().equals(username) && user.getPassword().equals(password))
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Finds all photos within a specified date range
	 * @param from The beginning of the range to be checked
	 * @param to The end of the range to be checked
	 * @return The list of all photos matching the criteria
	 */
	public static List<Photo> searchByDateRange(Calendar from, Calendar to)
	{
		List<Photo> results = new ArrayList<Photo>();
		for (Album album : StateManager.getInstance().getActiveUser().getAlbumList())
		{
			for (Photo photo : album.getPhotoList())
			{
				if (photo.getDateTaken().before(to) && photo.getDateTaken().after(from))
				{
					results.add(photo);
				}
			}
		}
		return results;
	}
	
	/**
	 * Finds all photos with the specified tag value and tag type
	 * @param tagType The tag type to search for
	 * @param tagValue The tag value to search for
	 * @return The list of all photos matching the criteria
	 */
	public static List<Photo> searchByTagValuePair(String tagType, String tagValue)
	{
		List<Photo> results = new ArrayList<Photo>();
		for (Album album : StateManager.getInstance().getActiveUser().getAlbumList())
		{
			for (Photo photo : album.getPhotoList())
			{
				for (Tag tag : photo.getTagList())
				{
					if (tag.getType().equals(tagType) && tag.getValue().equals(tagValue))
					{
						results.add(photo);
						break; // don't add photo more than once
					}
				}
			}
		}
		return results;
	}
}
