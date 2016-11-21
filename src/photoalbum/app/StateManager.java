package photoalbum.app;

import photoalbum.models.*;
import photoalbum.view.LoginController;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.*;

import java.util.*;

import java.io.Serializable;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;


/**
 * Handles the state of the application between scenes
 * @author Stephen Eisen
 * @author Arthur Quintanilla
 */

public class StateManager implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6760982129067815490L;
	private transient static final String storeDir = "dat";
	private transient static final String storeFile = "state.dat";
	
	
	private transient static StateManager instance;

	/**
	 * The current active user
	 */
	private transient User activeUser;
	
	/**
	 * The current active scene
	 */
	private transient Scene activeScene;
	
	/**
	 * The current active primary stage
	 */
	private transient Stage primaryStage;
	
	/**
	 * A list of the users for the Photo Album
	 */
	private List<User> userList;
	
	/**
	 * Constructs a StateManager object
	 */
	private StateManager()
	{
		// turn off public constructor - only one active instance
		userList = new ArrayList<User>(); // delete this
	}
	
	/**
	 * Gets the current instance of the StateManager
	 * @return The current instance associated with the manager
	 */
	public static StateManager getInstance()
    {    
        if (instance == null)
        {
            try
            {
                ObjectInputStream inStream = new ObjectInputStream(new FileInputStream(String.format("%s/%s", storeDir, storeFile)));
                instance = (StateManager)inStream.readObject();
                
                for (User user : instance.getUsers())
                {
                	user.setProps();
                }
                
                return instance;
            }
            catch (IOException | ClassNotFoundException e)
            {
                e.printStackTrace(System.out);
                return null;
            }
        }
        else
        {
            return instance;
        }
    }
	
	 public static void initialize()
	 {
	     instance = new StateManager();
	     instance.save();
	 }
	
	/**
	 * Gets the currently active user 
	 * @return The user that is currently using the application
	 */
	public User getActiveUser()
	{
		return this.activeUser;
	}
	
	/**
	 * Gets the currently active scene
	 * @return The scene currently on display for the application
	 */
	public Scene getActiveScene()
	{
		return this.activeScene;
	}
	
	/**
	 * Gets the primary Stage for the application
	 * @return The primary stage of this application
	 */
	public Stage getPrimaryStage()
	{
		return this.primaryStage;
	}
	
	/**
	 * Gets a list of the users 
	 * @return The list of the users
	 */
	public List<User> getUsers()
	{
		return this.userList;
	}
	
	/**
	 * Sets the currently active user
	 * @param user The user to be set as active
	 */
	public void setActiveUser(User user)
	{
		this.activeUser = user;
	}
	
	/**
	 * Sets the currently active scene
	 * @param path The new desired scene to be made active
	 */
	public void setActiveScene(String path)
	{
		
	}
	
	/**
	 * Sets the currently active primary stage
	 * @param stage The new desired primary stage 
	 */
	public void setPrimaryStage(Stage stage)
	{
		this.primaryStage = stage;
	}
	
	/**
	 * Adds a user to the user list
	 * @param user The user object to be added to the list
	 */
	public void addUser(User user)
	{
		userList.add(user);
	}
	
	/**
	 * Removes a user from the user list
	 * @param user The user object to be removed from the list
	 */
	public void removeUser(User user)
	{
		userList.remove(user);
	}
	
	public boolean validateUser(String username, String password)
	{
		for (User user : userList)
		{
			if (username.equals(user.getUsername()) && password.equals(user.getPassword()))
				return true;
		}
		return false;
	}
	
	public boolean userExists(String username)
	{
		for (User user : userList)
		{
			if (user.getUsername().equalsIgnoreCase(username))
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Saves the current state of the application
	 */
	public void save()
	{
		ObjectOutputStream outStream;
        try {
            outStream = new ObjectOutputStream(new FileOutputStream(String.format("%s/%s", storeDir, storeFile)));
            outStream.writeObject(instance);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void saveAndExit()
	{
		save();
		System.exit(0);
	}
}
