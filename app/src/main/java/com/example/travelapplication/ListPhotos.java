package com.example.travelapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListPhotos extends AppCompatActivity {

    private FloatingActionButton fab;

    Intent fileIntent;
    ViewPager viewPager;
    ListOfPhotosAdapter adapter;

    List<Uri> photos;

    private static final int READ_REQUEST_CODE = 42;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_photos);

        photos = new ArrayList<>();

        fab = findViewById(R.id.fab_listPhotos);
        viewPager = findViewById(R.id.viewPager_listPhotos);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileIntent = new Intent(Intent.ACTION_GET_CONTENT);
                fileIntent.setType("*/*");
                startActivityForResult(fileIntent,READ_REQUEST_CODE);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK ){




            Uri uri = null;
            if (data != null){
                uri = data.getData();
                Log.i ("uri", uri.toString());
                photos.add(uri);
            }

            adapter = new ListOfPhotosAdapter(photos, getBaseContext());
            viewPager.setAdapter(adapter);
            viewPager.setPadding(130,400,130,0);



            //менять что-то в движении
            /*
            viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });*/
        }



    }



}
