package photoalbum.lib;

import java.util.Calendar;
import java.util.List;

import photoalbum.app.StateManager;
import photoalbum.models.Album;
import photoalbum.models.Photo;

/**
 * Static Logic methods for computations involving Albums
 * @author Stephen Eisen
 * @author Arthur Quintanilla
 */
public class AlbumLibrary
{
	/**
	 * Edits the selected albums name
	 * @param albumList The album list to search for
	 * @param name The name of the album to be changed
	 * @param newName The new name for the selected album
	 * @return True if the album existed and completed the edit successfully else false
	 */
	public static boolean editName(List<Album> albumList, String name, String newName)
	{
		for (Album album : albumList)
		{
			if (album.getName().equals(name))
			{
				album.setName(newName);
				StateManager.getInstance().save();
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Deletes the selected album
	 * @param albumList The album list to search for
	 * @param deleteAlbum The album to be deleted
	 * @return True if the album existed and completed the delete successfully else false
	 */
	public static boolean deleteAlbum(List<Album> albumList, Album deleteAlbum)
	{
		for (Album album : albumList)
		{
			if (album.equals(deleteAlbum))
			{
				albumList.remove(deleteAlbum);
				StateManager.getInstance().save();
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Adds a photo to an album
	 * @param a The album to add to
	 * @param p The photo to be added to the album
	 */
	public static void addPhotoToAlbum(Album a, Photo p)
	{
		a.addPhoto(p);
		StateManager.getInstance().save();
	}
}
