package com.greetsweet.plant.greetapp.EditorFrags;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.greetsweet.plant.PhotoApp;
import com.greetsweet.plant.R;
import com.greetsweet.plant.greetapp.HomeActivity;
import com.greetsweet.plant.mylibs.utility.Cons;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class ShowImageActivity extends AppCompatActivity {
ImageView showimg, shareimg;
    Bitmap bt;
    Uri fetchuri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ShowImageActivity.this,HomeActivity.class);
                finish();
                startActivity(intent);
            }
        });

        showimg=(ImageView)findViewById(R.id.imageView50);

        Picasso.with(this).load(PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.newfrag_image)).into(showimg);

        bt= BitmapFactory.decodeFile(PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.shareimagepath));
        shareimg=(ImageView)findViewById(R.id.shareimage);
        shareimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File f = new File("full image path");
                Uri uri = Uri.parse("file://" + f.getAbsolutePath());
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject");
         /*   share.putExtra(Intent.EXTRA_TEXT, "Amaging App"
                    + " " + "https://play.google.com/store/apps/details?id=com.virtuosonetsoft.photoframe");*/
                share.putExtra(Intent.EXTRA_TEXT, "Made via Greetsweet");
                fetchuri= getImageUri(getApplication(), bt);

                PhotoApp.getInstance().createLog("URIII"+fetchuri);
                share.putExtra(Intent.EXTRA_STREAM,fetchuri);
                share.setType("image/*");

                share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(Intent.createChooser(share, "Share Via"));

            }
        });

        if(PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.checksharetype).equals("share")){

            File f = new File("full image path");
            Uri uri = Uri.parse("file://" + f.getAbsolutePath());
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("text/plain");
            share.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject");
         /*   share.putExtra(Intent.EXTRA_TEXT, "Amaging App"
                    + " " + "https://play.google.com/store/apps/details?id=com.virtuosonetsoft.photoframe");*/
            share.putExtra(Intent.EXTRA_TEXT, "Made via Greetsweet");
            fetchuri= getImageUri(getApplication(), bt);

            PhotoApp.getInstance().createLog("URIII"+fetchuri);
            share.putExtra(Intent.EXTRA_STREAM,fetchuri);
            share.setType("image/*");

            share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(Intent.createChooser(share, "Share Via"));
        }


        else{
            Picasso.with(this).load(PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.newfrag_image)).into(showimg);


        }


    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 60, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
}
