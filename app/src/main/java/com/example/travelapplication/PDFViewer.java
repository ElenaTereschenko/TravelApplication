package com.example.travelapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.net.Uri;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;

public class PDFViewer extends AppCompatActivity implements OnPageChangeListener, OnLoadCompleteListener {

    private Uri uri;
    private PDFView pdfView;
    private Integer pageNumber = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfviewer);

        Bundle arguments = getIntent().getExtras();

        pdfView = (PDFView) findViewById(R.id.pdfView);

        if (arguments != null){
            uri = arguments.getParcelable("Uri");

            pdfView.fromUri(uri).defaultPage(pageNumber).enableSwipe(true)
                    .swipeHorizontal(false)
                    .onPageChange(this)
                    .enableAnnotationRendering(true)
                    .onLoad(this)
                    .scrollHandle(new DefaultScrollHandle(this))
                    .load();


        }
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;

    }

    @Override
    public void loadComplete(int nbPages) {

    }
}
