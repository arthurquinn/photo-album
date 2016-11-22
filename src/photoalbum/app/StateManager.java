package photoalbum.app;

import photoalbum.models.*;
import photoalbum.view.IController;
import photoalbum.view.LoginController;
import photoalbum.view.PhotosViewController;
import photoalbum.view.UserAddFormController;
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
	 * The serial UID associated with the StateManager object
	 */
	private static final long serialVersionUID = 6760982129067815490L;
	
	/**
	 * The directory in which to store the .dat file
	 */
	private transient static final String storeDir = "dat";
	
	/**
	 * The name in which to save the .dat file as
	 */
	private transient static final String storeFile = "state.dat";
	
	
	/**
	 * The instance of StateManager associated with this application
	 */
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
	 * The current active album
	 */
	private transient Album activeAlbum;
	
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
                return null;
            }
        }
        else
        {
            return instance;
        }
    }
	
	/**
	 * Sets up the StateManager object
	 */
	 public static void initialize()
	 {
	     instance = new StateManager();
	     instance.addUser(new User("admin", "admin"));
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
	 * @param fxmlPath The new desired scene to be made active
	 * @param args Any argument to be passed to the next scene
	 * @param x The resolution of the new scene
	 * @param y The resolution of the new scene
	 */
	public void setActiveScene(String fxmlPath, Object args, int x, int y)
	{
		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource(fxmlPath));
			AnchorPane root = (AnchorPane)loader.load();
			
			IController controller = loader.getController();
			controller.start(args);
			
			Scene scene = new Scene(root, x, y);
			StateManager.getInstance().getPrimaryStage().setScene(scene);
			StateManager.getInstance().getPrimaryStage().show();	
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Creates a popup window for input and output
	 * @param stage The new stage to be called
	 * @param fxmlPath The file path of the new stage
	 * @param args Any parameter to be passed to the controller
	 */
	public void createPopupWindow(Stage stage, String fxmlPath, Object args)
	{
		try
		{
			FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));

			stage.setScene(new Scene((AnchorPane)loader.load()));
			stage.setResizable(false);
			
			IController controller = loader.getController();
			controller.start(args);
			
			stage.showAndWait();
			
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
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
	
	/**
	 * Saves the current state of the application and closes
	 * - Safe close -
	 */
	public void saveAndExit()
	{
		save();
		System.exit(0);
	}
	
	/**
	 * Sets the currently active album
	 * @param album The new desired album
	 */
	public void setActiveAlbum(Album album)
	{
		this.activeAlbum = album;
	}
	
	/**
	 * The currently active album
	 * @return The album that is currently active
	 */
	public Album getActiveAlbum()
	{
		return this.activeAlbum;
	}
}
