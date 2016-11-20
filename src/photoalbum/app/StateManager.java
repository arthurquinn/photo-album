package photoalbum.app;

import photoalbum.models.*;
import javafx.scene.*;
import javafx.stage.*;

import java.util.*;

import java.io.Serializable;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class StateManager implements Serializable
{
	private transient static final String storeDir = "dat";
	private transient static final String storeFile = "state.dat";
	
	
	private transient static StateManager instance;

	private transient User activeUser;
	private transient Scene activeScene;
	private transient Stage primaryStage;
	
	private List<User> userList;
	
	private StateManager()
	{
		// turn off public constructor - only one active instance
		userList = new ArrayList<User>(); // delete this
	}
	
	public static StateManager getInstance()
	{	
		if (instance == null)
		{
			try
			{
				ObjectInputStream inStream = new ObjectInputStream(new FileInputStream(String.format("%s/%s", storeDir, storeFile)));
				instance = (StateManager)inStream.readObject();
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
	
	public User getActiveUser()
	{
		return this.activeUser;
	}
	
	public Scene getActiveScene()
	{
		return this.activeScene;
	}
	
	public Stage getPrimaryStage()
	{
		return this.primaryStage;
	}
	
	public List<User> getUsers()
	{
		return this.userList;
	}
	
	public void setActiveUser(User user)
	{
		this.activeUser = user;
	}
	
	public void setActiveScene(Scene scene)
	{
		this.activeScene = scene;
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public void setPrimaryStage(Stage stage)
	{
		this.primaryStage = stage;
	}
	
	public void addUser(User user)
	{
		userList.add(user);
	}
	
	public void removeUser(User user)
	{
		userList.remove(user);
	}
	
	public void save() throws IOException
	{
		ObjectOutputStream outStream = new ObjectOutputStream(new FileOutputStream(String.format("%s/%s", storeDir, storeFile)));
		outStream.writeObject(instance);
	}
}
