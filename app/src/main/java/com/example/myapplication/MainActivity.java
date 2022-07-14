package com.example.myapplication;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    public TabLayout tablayout;
    public ViewPager viewpager;
    public TabItem home, search, edit, move, delete;
    public PageAdapter pageadapter;

    public User user;
    public final static String key = "USER_KEY";

    public class PageAdapter extends FragmentPagerAdapter
    {
        public int numberoftabs;

        public PageAdapter(@NonNull FragmentManager fm, int numberoftabs) {
            super(fm);
            this.numberoftabs = numberoftabs;

        }
        @NonNull
        @Override
        public Fragment getItem(int position) {

            Intent intent = new Intent();
            intent.putExtra(key, user);

            switch(position)
            {
                case 0:

                    return new hometab(intent, getApplicationContext());
                case 1:
                    return new searchtab(intent, getApplicationContext());
                case 2:
                    return new edittab(intent, getApplicationContext());
                case 3:
                    return new deletetab(intent, getApplicationContext());
                case 4:
                    return new movetab(intent, getApplicationContext());
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return numberoftabs;
        }


        @Override
        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            if(readwriteObjects.readUser(getApplicationContext().getFilesDir()) == null)
            {
                user = new User();
                Log.d("fdfgb", "dfvcxcxfgg");
            }
            else
            {
                user = readwriteObjects.readUser(getApplicationContext().getFilesDir());
                Log.d("fdfgb", "dffgg");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        tablayout = (TabLayout) findViewById(R.id.tablayout);
        home = (TabItem) findViewById(R.id.home);
        search = (TabItem) findViewById(R.id.search);
        edit = (TabItem) findViewById(R.id.edit);
        move = (TabItem) findViewById(R.id.move);
        delete = (TabItem) findViewById(R.id.delete);

        //for (View v: tablayout.getTouchables())
            //v.setClickable(false);

        viewpager = (ViewPager) findViewById(R.id.viewpager);

        pageadapter = new PageAdapter(getSupportFragmentManager(), tablayout.getTabCount());
        viewpager.setAdapter(pageadapter);

        tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewpager.setCurrentItem(tab.getPosition());

                if(tab.getPosition() == 0){
                    pageadapter.notifyDataSetChanged();
                } else if(tab.getPosition() == 1) {
                    pageadapter.notifyDataSetChanged();
                } else if(tab.getPosition() == 2) {
                    pageadapter.notifyDataSetChanged();
                } else if(tab.getPosition() == 3) {
                    pageadapter.notifyDataSetChanged();
                } else if(tab.getPosition() == 4) {
                    pageadapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));



    }

}