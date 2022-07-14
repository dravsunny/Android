package com.example.myapplication;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.documentfile.provider.DocumentFile;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class hometab extends Fragment {

    public User homeuser;
    public Context context;

    public static final int PICK_IMAGE = 1;
    public Uri imageUri;

    public Spinner albumslist;
    public Spinner photolist;
    public EditText albumname;
    public Button addAlbums;
    public Button savesession;
    public Button addPhotos;
    public ImageView imageview;
    public TextView caption;
    public TextView tags;
    public Spinner tagtype;
    public EditText tagvalue;
    public Button addTag;
    public Button left;
    public Button right;


    public hometab(Intent intent, Context context) {

        homeuser = (User) intent.getSerializableExtra(MainActivity.key);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_hometab, container, false);

        albumslist = (Spinner) v.findViewById(R.id.spinnerc);
        albumname = (EditText) v.findViewById(R.id.texta);
        addAlbums = (Button) v.findViewById(R.id.buttona);
        photolist = (Spinner) v.findViewById(R.id.spinnere);
        addPhotos = (Button) v.findViewById(R.id.buttonp);
        caption = (TextView) v.findViewById(R.id.Caption);

        savesession = (Button) v.findViewById(R.id.buttonss);
        imageview = (ImageView) v.findViewById(R.id.imageView) ;

        tags = (TextView) v.findViewById(R.id.tags);
        tagvalue = (EditText) v.findViewById(R.id.textt);
        tagtype = (Spinner) v.findViewById(R.id.spinneret);
        addTag = (Button) v.findViewById(R.id.addtag);

        left = (Button) v.findViewById(R.id.left);
        right = (Button) v.findViewById(R.id.right);

        populateAlbums();
        populateTags();

        addAlbums.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAlbum();
            }
        });
        addPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPhoto();
            }
        });
        addTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTags();
            }
        });
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                panLeft();
            }
        });
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                panRight();
            }
        });
        savesession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    saveSession();
                } catch (IOException e) {
                    Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });

        return v;
    }


    public void populateAlbums()
    {
        List<String> list = new ArrayList<String>();
        list.addAll(homeuser.getlistofAlbums().keySet());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        albumslist.setAdapter(adapter);

        albumslist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                tags.setText("");
                caption.setText("");
                Album album = homeuser.getlistofAlbums().get(albumslist.getItemAtPosition(position));

                List<String> list = new ArrayList<String>();
                list.addAll(album.getlistofPhotos().keySet());

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                photolist.setAdapter(adapter);

                photolist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        Photo photo = album.getlistofPhotos().get(photolist.getItemAtPosition(position).toString());
                        caption.setText(photo.getCaption());
                        tags.setText(photo.getlistofTags().toString());
                        imageview.setImageURI(Uri.parse(photo.getFilepath()));
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

    public void populateTags()
    {
        List<String> list = new ArrayList<String>();
        list.add("Location");
        list.add("Person");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tagtype.setAdapter(adapter);


    }


    public void addAlbum()
    {
        if(!homeuser.getlistofAlbums().containsKey(albumname.getText().toString()))
        {
            Album newalbum = new Album(albumname.getText().toString());
            homeuser.addAlbum(newalbum);
            List<String> list = new ArrayList<String>();
            list.addAll(homeuser.getlistofAlbums().keySet());

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            albumslist.setAdapter(adapter);
            albumslist.setSelection(adapter.getPosition(newalbum.getalbumName()));
            imageview.setImageResource(android.R.color.transparent);
            tags.setText("");
        }
        else{
            Toast.makeText(context, "Album already EXISTS with this Name", Toast.LENGTH_SHORT).show();
        }

    }
    public void addPhoto()
    {
        if(albumslist.getSelectedItem() != null) {
            Intent gallery = new Intent();
            gallery.setType("image/*");
            gallery.setAction(Intent.ACTION_GET_CONTENT);

            startActivityForResult(Intent.createChooser(gallery, "Select Photo"), PICK_IMAGE);
        }
        else
        {
            Toast.makeText(context, "Choose Source Album from The Dropdown", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK){
            imageUri = data.getData();
            DocumentFile documentFile = DocumentFile.fromSingleUri(context, data.getData());

            String fileName = documentFile.getName();

            if(albumslist.getSelectedItem() != null) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), imageUri);
                    File filepath = Environment.getExternalStorageDirectory();
                    Log.d("dsffd", filepath.getAbsolutePath());
                    //File dir = new File(filepath.getAbsolutePath() + "/Img/");

                    File file = new File(filepath, fileName);
                    file.createNewFile();
                    OutputStream outputstream = new FileOutputStream(file);

                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputstream);
                    outputstream.close();

                    Photo newPhoto = new Photo(file.getAbsolutePath(), fileName);
                    homeuser.getlistofAlbums().get(albumslist.getSelectedItem().toString()).addPhoto(newPhoto);

                } catch (IOException e) {
                    e.printStackTrace();
                }


                List<String> list = new ArrayList<String>();
                list.addAll(homeuser.getlistofAlbums().get(albumslist.getSelectedItem().toString()).getlistofPhotos().keySet());

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                photolist.setAdapter(adapter);

                imageview.setImageURI(imageUri);
                photolist.setSelection(adapter.getPosition(fileName));

                imageview.setImageURI(imageUri);
                caption.setText(fileName);
                Toast.makeText(context, "Photo was added!", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(context, "Choose Album to add photo", Toast.LENGTH_SHORT).show();
            }
        }






    }
    public void panLeft()
    {
        if((photolist.getSelectedItemPosition()-1) >= 0){
            photolist.setSelection(photolist.getSelectedItemPosition()-1);

        }
        else
        {
            Toast.makeText(context, "This is the beginning of the Album, can't back", Toast.LENGTH_SHORT).show();
        }

    }
    public void panRight()
    {
        if((photolist.getSelectedItemPosition()+1) < photolist.getAdapter().getCount()){
            photolist.setSelection(photolist.getSelectedItemPosition()+1);
        }
        else
        {
            Toast.makeText(context, "Reached the End of Album", Toast.LENGTH_SHORT).show();
        }

    }



    public void addTags()
    {
        if(photolist.getSelectedItem() == null)
        {
            Toast.makeText(context, "To ADD tag, SELECT Photo from Dropdown", Toast.LENGTH_SHORT).show();
        }
        else
        {
            homeuser.getlistofAlbums().get(albumslist.getSelectedItem()).getlistofPhotos().get(photolist.getSelectedItem()).
                    addnewTag(tagtype.getSelectedItem().toString(), tagvalue.getText().toString());
            tags.setText(homeuser.getlistofAlbums().get(albumslist.getSelectedItem()).getlistofPhotos().
                    get(photolist.getSelectedItem()).getlistofTags().toString());
            Toast.makeText(context, "Tag Added!", Toast.LENGTH_SHORT).show();

        }

    }
    public void saveSession() throws IOException {
        File file = context.getFilesDir();
        readwriteObjects.writeUser(homeuser, file);
        //context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(file.getAbsolutePath()))));
        Toast.makeText(context, "Session Saved", Toast.LENGTH_SHORT).show();
    }

}