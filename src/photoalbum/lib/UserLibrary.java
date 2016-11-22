package photoalbum.lib;

import photoalbum.models.User;

import java.util.List;

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
}
