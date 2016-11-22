package photoalbum.lib;

import photoalbum.app.StateManager;
import photoalbum.models.Album;
import photoalbum.models.Photo;
import photoalbum.models.Tag;
import photoalbum.models.User;

import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;

public class UserLibrary
{
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
