package com.example.myapplication;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;



public class Album implements Serializable{

	public String albumName;

	public HashMap<String, Photo>listofPhotos;

	public Album()
	{
		//for temporary Album variables that need to be initialized
	}

	public Album(String albumname)
	{
		this.albumName = albumname;
		listofPhotos = new HashMap<String, Photo>();
		
	}

	public String getalbumName()
	{
		return albumName;
	}

	public void changealbumName(String newAlbumname)
	{
		albumName = newAlbumname;
	}

	public void addPhoto(Photo photo)
	{
		listofPhotos.put(photo.getCaption(), photo);

	}

	public void deletePhoto(String photo)
	{

		listofPhotos.remove(photo);
		
	}

	public Photo getPhoto(String photoCaption)
	{
		return listofPhotos.get(photoCaption);
	
	}

	public HashMap<String, Photo> getlistofPhotos()
	{
		return listofPhotos;
	}


	
	

}
