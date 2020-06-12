package com.example.travelapplication;

import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListOfPhotosAdapter extends PagerAdapter {

    private List<Uri> photos;
    private LayoutInflater layoutInflater;
    private Context context;
    private boolean isImageScaled = false;


    public ListOfPhotosAdapter(List<Uri> photos, Context context) {
        this.photos = photos;
        this.context = context;
    }

    @Override
    public int getCount() {
        return photos.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position){
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.number_list_photos, container, false);

        ImageView imageView;
        TextView title;

        imageView = view.findViewById(R.id.imageView_numberListPhotos_image);
        title = view.findViewById(R.id.textView_numberListPhotos_title);

        Uri uri = photos.get(position);

        imageView.setImageURI(uri);

        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null, null);

        if(cursor != null && cursor.moveToFirst()){
            String titleString = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));

            title.setText(titleString);
        }
        else{
            title.setText("Изображение");
        }

        ContentResolver cR = context.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        String type = mime.getExtensionFromMimeType(cR.getType(uri));

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.equals("pdf")){
                    Intent pdfIntent = new Intent(context, PDFViewer.class);
                    pdfIntent.putExtra("Uri", uri);
                    context.startActivity(pdfIntent);


                }
                else{
                    if (!isImageScaled){
                        v.animate().scaleX(1.4f).scaleY(1.4f).setDuration(500);
                    }
                    if (isImageScaled){
                        v.animate().scaleX(1f).scaleY(1f).setDuration(500);
                    }
                    isImageScaled = !isImageScaled;
                }

            }
        });

        container.addView(view, 0);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
