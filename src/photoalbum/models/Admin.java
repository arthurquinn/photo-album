package photoalbum.models;

import java.io.Serializable;

/**
 * Admin class that inherits from user that can create new user files.
 * At least one Admin account must always exist
 * @author Stephen Eisen
 * @author Arthur Quintanilla
 */

public class Admin extends User implements Serializable 
{
	/**
	 * Constructs an Admin object
	 * @param username The name of this Admin object
	 * @param password The login password for this Admin object
	 */
	public Admin(String username, String password)
	{
		super(username, password);
	}
}
