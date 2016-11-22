package photoalbum.lib;

import java.util.Calendar;
import java.util.List;

import photoalbum.app.StateManager;
import photoalbum.models.Album;
import photoalbum.models.Photo;

public class AlbumLibrary
{
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
	
	public static void addPhotoToAlbum(Album a, Photo p)
	{
		a.addPhoto(p);
		StateManager.getInstance().save();
	}
}
