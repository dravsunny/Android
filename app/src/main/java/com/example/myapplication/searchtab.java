package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class searchtab extends Fragment {

    public User searchuser;
    public Context context;

    public AutoCompleteTextView loc;
    public AutoCompleteTextView per;

    public HashMap<String, List<Photo>> locations;
    public HashMap<String, List<Photo>>  persons;

    public Spinner search;
    public ImageView imageview;

    public final static int LENGTH = 4;

    public searchtab(Intent intent, Context context) {

        searchuser = (User) intent.getSerializableExtra(MainActivity.key);
        this.context = context;

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_searchtab, container, false);

        loc = (AutoCompleteTextView) v.findViewById(R.id.location);
        per = (AutoCompleteTextView) v.findViewById(R.id.person);
        search = (Spinner) v.findViewById(R.id.sq);
        imageview = (ImageView) v.findViewById(R.id.iv);

        locations = new HashMap<String, List<Photo>>();
        persons = new HashMap<String, List<Photo>>();

        populate();

        loc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                List<Photo> list = new ArrayList<Photo>();
                list.addAll(locations.get(adapterView.getItemAtPosition(i)));

                ArrayAdapter<Photo> adapter = new ArrayAdapter<Photo>(getContext(), android.R.layout.simple_spinner_item, list);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                search.setAdapter(adapter);

                search.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        imageview.setImageURI(Uri.parse(adapter.getItem(i).getFilepath()));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

            }
        });
        per.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                List<Photo> list = new ArrayList<Photo>();
                list.addAll(persons.get(adapterView.getItemAtPosition(i)));

                ArrayAdapter<Photo> adapter = new ArrayAdapter<Photo>(getContext(), android.R.layout.simple_spinner_item, list);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                search.setAdapter(adapter);

                search.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        imageview.setImageURI(Uri.parse(adapter.getItem(i).getFilepath()));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });


            }
        });
        return v;
    }
    public void populate()
    {
        for(String i: searchuser.getlistofAlbums().keySet())
        {
            Album album = searchuser.getlistofAlbums().get(i);

            for(String j: album.getlistofPhotos().keySet())
            {
                Photo photo = album.getlistofPhotos().get(j);

                for(String k: photo.getlistofTags().keySet())
                {
                    if(k.equals("Location"))
                    {

                        for(String m: photo.gettagValues(k))
                        {
                            if(locations.get(m) != null)
                            {
                                locations.get(m).add(photo);
                            }
                            else
                            {
                                List<Photo> temp = new ArrayList<Photo>();
                                temp.add(photo);
                                locations.put(m, temp);
                            }


                        }
                    }
                    if(k.equals("Person"))
                    {
                        for(String l: photo.gettagValues(k))
                        {
                            if(persons.get(l) != null)
                            {
                                locations.get(l).add(photo);
                            }
                            else
                            {
                                List<Photo> temp = new ArrayList<Photo>();
                                temp.add(photo);
                                persons.put(l, temp);
                            }
                        }
                    }
                }
            }




        }
        List<String> list = new ArrayList<String>();
        list.addAll(locations.keySet());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        loc.setAdapter(adapter);

        List<String> lis = new ArrayList<String>();
        lis.addAll(persons.keySet());
        //Toast.makeText(context, "Photo: " + persons.keySet().toString(), Toast.LENGTH_LONG).show();
        ArrayAdapter<String> adapters = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, lis);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        per.setAdapter(adapters);
    }
}