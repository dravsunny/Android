package com.example.myapplication;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;

public class Photo implements Serializable{

	public String filepath;

	public String caption;

	public HashMap<String, HashSet<String>> myTags;

	public Photo(String filepath, String caption)
	{
		this.filepath = filepath;
		this.caption = caption;

		
		myTags = new HashMap<String, HashSet<String>>();
	}

	public String getFilepath()
	{
		return filepath;
	}

	public String getCaption()
	{
		return caption;
	}

	public void addnewTag(String tag, String value)
	{
		if(myTags.get(tag) == null)
		{
			HashSet<String> temp = new HashSet<String>();
			temp.add(value);
			
			myTags.put(tag, temp);
		}
		else
		{
			myTags.get(tag).add(value);
		}
	}

	public void removeTag(String value)
	{
		for(String i: myTags.keySet())
		{
			for(String j : myTags.get(i))
			{
				if(value.equals(j))
				{
					myTags.get(i).remove(j);
					if(myTags.get(i).isEmpty())
					{
						myTags.remove(i);
					}
					break;
				}
			}
		}
	}

	public boolean doIhaveTag(String tag, String value)
	{
		if(myTags.containsKey(tag) && myTags.get(tag).contains(value))
		{
			return true;
		}
	
		return false;
		
	}
	public HashMap<String, HashSet<String>> getlistofTags()
	{
		return myTags;
	}

	public HashSet<String> gettagValues(String tagtype)
	{
		return myTags.get(tagtype);
	}

	public String stringofTags()
	{
		String string = "";
		for(String i: myTags.keySet())
		{
			string = string + i + " : " + myTags.get(i).toString() + ", ";
		}
		
		return string;
	}
	public String toString()
	{
		return caption;
	}
	
	
	
}
