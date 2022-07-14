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
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class movetab extends Fragment {



    public User moveuser;
    public Context context;

    public Spinner sourcealbum;
    public Button movephoto;
    public Spinner sourcephoto;
    public Spinner destinationalbum;


    public movetab(Intent intent, Context context) {

        moveuser = (User) intent.getSerializableExtra(MainActivity.key);
        this.context = context;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_movetab, container, false);

        sourcealbum = (Spinner) v.findViewById(R.id.source);
        sourcephoto = (Spinner) v.findViewById(R.id.sp);
        destinationalbum  = (Spinner) v.findViewById(R.id.da);
        movephoto  = (Button)v.findViewById(R.id.mp);

        populateAlbums();

        movephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                movePhoto();
            }
        });


        return v;
    }
    public void populateAlbums()
    {
        List<String> list = new ArrayList<String>();
        list.addAll(moveuser.getlistofAlbums().keySet());
        Log.d("dfsfd", moveuser.getlistofAlbums().keySet().toString());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sourcealbum.setAdapter(adapter);
        destinationalbum.setAdapter(adapter);

        sourcealbum.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                Album album = moveuser.getlistofAlbums().get(sourcealbum.getItemAtPosition(position));

                List<String> list = new ArrayList<String>();
                list.addAll(album.getlistofPhotos().keySet());

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sourcephoto.setAdapter(adapter);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }
    public void movePhoto()
    {
        if(moveuser.getlistofAlbums() != null && (moveuser.getlistofAlbums().keySet().size() < 2 || sourcealbum.getSelectedItem().toString().equals(destinationalbum.getSelectedItem().toString())))
        {
            Toast.makeText(context, "You must choose 2 different albums to move a photo", Toast.LENGTH_SHORT).show();
        }
        if(sourcephoto.getSelectedItem() == null)
        {
            Toast.makeText(context, "Choose Photo to move from the dropdown", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Photo p = moveuser.getlistofAlbums().get(sourcealbum.getSelectedItem().toString()).getPhoto(sourcephoto.getSelectedItem().toString());
            moveuser.getlistofAlbums().get(sourcealbum.getSelectedItem().toString()).deletePhoto(sourcephoto.getSelectedItem().toString());
            moveuser.getlistofAlbums().get(destinationalbum.getSelectedItem().toString()).addPhoto(p);


            Toast.makeText(context, "Photo was moved!", Toast.LENGTH_SHORT).show();
        }
    }
}