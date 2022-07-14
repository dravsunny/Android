package com.example.myapplication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class User implements Serializable{

	public HashMap<String, Album> listofAlbums;

	
	public User()
	{

		listofAlbums = new HashMap<String, Album>();

		
	}

	public Album getAlbum(String albumName)
	{
		return listofAlbums.get(albumName);
	}
	
	public void addAlbum(Album album)
	{
		listofAlbums.put(album.getalbumName(), album);
	}
	public void deleteAlbum(String album)
	{
		listofAlbums.remove(album);
	}
	
	public void editAlbumName(String oldAlbum, String newAlbum)
	{
		Album temp = listofAlbums.get(oldAlbum);
		temp.changealbumName(newAlbum);
		listofAlbums.remove(oldAlbum);
		listofAlbums.put(newAlbum, temp);
		
	}
	
	public HashMap<String, Album> getlistofAlbums()
	{
		return listofAlbums;
	}

	
}
