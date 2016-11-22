package photoalbum.lib;

import java.util.List;

import photoalbum.app.StateManager;
import photoalbum.models.Album;

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
}
