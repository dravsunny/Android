package com.example.myapplication;


import android.view.View;

import java.io.*;

public class readwriteObjects<T> implements Serializable{
	

	private static final long serialVersionUID = 1L;
	public static final String storeDir = "server";


	public static void writeUser(User user, File file)throws IOException
	{

		ObjectOutputStream oos = new ObjectOutputStream(
		new FileOutputStream(new File(file, "user.data")));
		oos.writeObject(user);
		oos.close();
	}
	public static User readUser(File file)throws IOException, ClassNotFoundException
	{
		ObjectInputStream ois;
		User user;
		try {
			ois = new ObjectInputStream(
			new FileInputStream(new File(file,"user.data")));
			user = (User)ois.readObject();
			ois.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return null;
		}
		
		return user;
		
	}


}