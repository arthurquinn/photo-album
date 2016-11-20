package photoalbum.models;

import java.io.Serializable;

public class Admin extends User implements Serializable {

	public Admin(String username, String password)
	{
		super(username, password);
	}

}
