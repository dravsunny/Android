package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class deletetab extends Fragment {

    public User deleteuser;
    public Context context;

    public Spinner albumslist;
    public Button deletealbums;
    public Spinner photoslist;
    public Button deletephotos;
    public Spinner tagslist;
    public Button deletetag;

    public deletetab(Intent intent, Context context) {

        deleteuser = (User) intent.getSerializableExtra(MainActivity.key);
        this.context = context;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_deletetab, container, false);

        albumslist = (Spinner) v.findViewById(R.id.spinnerc);
        deletealbums = (Button) v.findViewById(R.id.deletea);
        photoslist = (Spinner) v.findViewById(R.id.spinnerc2);
        deletephotos = (Button) v.findViewById(R.id.deletep);
        tagslist = (Spinner) v.findViewById(R.id.spinnerc3);
        deletetag = (Button) v.findViewById(R.id.deletet);

        populateAlbums();

        deletealbums.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAlbum();
            }
        });
        deletephotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletePhoto();
            }
        });
        deletetag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteTag();
            }
        });

        return v;
    }
    public void populateAlbums()
    {
        List<String> list = new ArrayList<String>();
        list.addAll(deleteuser.getlistofAlbums().keySet());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        albumslist.setAdapter(adapter);

        albumslist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                Album album = deleteuser.getlistofAlbums().get(albumslist.getItemAtPosition(position));

                List<String> list = new ArrayList<String>();
                list.addAll(album.getlistofPhotos().keySet());

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                photoslist.setAdapter(adapter);

                photoslist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int positio, long id) {

                        Photo photo = deleteuser.getlistofAlbums().get(albumslist.getItemAtPosition(positio)).getlistofPhotos().get(photoslist.getItemAtPosition(positio));
                        List<String> list = new ArrayList<String>();
                        if(photo.getlistofTags() != null)
                        {
                            if(photo.getlistofTags().get("Location") != null)
                            {
                                list.addAll(photo.getlistofTags().get("Location"));
                            }
                            if(photo.getlistofTags().get("Person") != null)
                            {
                                list.addAll(photo.getlistofTags().get("Person"));
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            tagslist.setAdapter(adapter);

                        }



                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // your code here
                    }

                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }

    public void deleteAlbum()
    {
        if(albumslist.getSelectedItem() != null) {
            deleteuser.deleteAlbum(albumslist.getSelectedItem().toString());
            List<String> list = new ArrayList<String>();
            list.addAll(deleteuser.getlistofAlbums().keySet());

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            albumslist.setAdapter(adapter);

        }
        else
        {
            Toast.makeText(context, "Choose the Album you want to delete", Toast.LENGTH_SHORT).show();
        }
    }
    public void deletePhoto()
    {
        if(!(photoslist.getSelectedItem() == null))
        {
            deleteuser.getlistofAlbums().get(albumslist.getSelectedItem().toString()).deletePhoto(photoslist.getSelectedItem().toString());
            List<String> list = new ArrayList<String>();
            list.addAll(deleteuser.getlistofAlbums().get(albumslist.getSelectedItem().toString()).getlistofPhotos().keySet());

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            photoslist.setAdapter(adapter);

        }
        else{
            Toast.makeText(context, "Choose the Photo you want to delete", Toast.LENGTH_SHORT).show();
        }

    }
    public void deleteTag()
    {
        if(!(tagslist.getSelectedItem() == null))
        {
            deleteuser.getlistofAlbums().get(albumslist.getSelectedItem().toString()).getlistofPhotos().get(photoslist.getSelectedItem().toString()).removeTag(tagslist.getSelectedItem().toString());
            Photo photo = deleteuser.getlistofAlbums().get(albumslist.getSelectedItem().toString()).getlistofPhotos().get(photoslist.getSelectedItem());
            List<String> list = new ArrayList<String>();
            if(photo.getlistofTags() != null)
            {
                if(photo.getlistofTags().get("Location") != null)
                {
                    list.addAll(photo.getlistofTags().get("Location"));
                }
                if(photo.getlistofTags().get("Person") != null)
                {
                    list.addAll(photo.getlistofTags().get("Person"));
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                tagslist.setAdapter(adapter);

            }
        }
        else{
            Toast.makeText(context, "Choose the Tag you want to delete", Toast.LENGTH_SHORT).show();
        }
    }
}