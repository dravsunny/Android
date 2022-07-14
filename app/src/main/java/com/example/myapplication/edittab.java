package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class edittab extends Fragment {

    public User edituser;
    public Context context;

    public Spinner albumlists;
    public EditText newalbumname;
    public Button rename;

    public edittab(Intent intent, Context context) {

        edituser = (User) intent.getSerializableExtra(MainActivity.key);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_edittab, container, false);

        albumlists = (Spinner) v.findViewById(R.id.spinneraa);
        newalbumname = (EditText) v.findViewById(R.id.newalbums);
        rename = (Button) v.findViewById(R.id.rename);

        populateAlbums();

        rename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                renameAlbum();
            }
        });
        return v;
    }
    public void populateAlbums()
    {
        List<String> list = new ArrayList<String>();
        list.addAll(edituser.getlistofAlbums().keySet());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        albumlists.setAdapter(adapter);
    }
    public void renameAlbum()
    {
        if(edituser.getlistofAlbums().containsKey(newalbumname.getText().toString()))
        {
            Toast.makeText(context, "An Album already EXISTS with this Name", Toast.LENGTH_SHORT).show();
        }
        else if(albumlists.getSelectedItem() == null)
        {
            Toast.makeText(context, "Please select a album to rename", Toast.LENGTH_SHORT).show();
        }
        else
        {
            edituser.editAlbumName(albumlists.getSelectedItem().toString(), newalbumname.getText().toString());
            populateAlbums();
            Toast.makeText(context, "Album was renamed", Toast.LENGTH_SHORT).show();
        }
    }
}