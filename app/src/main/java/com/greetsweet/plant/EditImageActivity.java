package com.greetsweet.plant;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.transition.ChangeBounds;
import android.support.transition.TransitionManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.greetsweet.plant.APIclient.ApiClient;
import com.greetsweet.plant.Handler.ServiceHandler;
import com.greetsweet.plant.Interface.UploadImageInterface;
import com.greetsweet.plant.greetapp.EditorFrags.AddLogoActivity;
import com.greetsweet.plant.greetapp.EditorFrags.FrameEditorDialogFragment;
import com.greetsweet.plant.greetapp.EditorFrags.GetLogoActivity;
import com.greetsweet.plant.greetapp.EditorFrags.ShowImageActivity;
import com.greetsweet.plant.greetapp.HomeActivity;
import com.greetsweet.plant.greetapp.ModalClasses.GetLogoModalClass;
import com.greetsweet.plant.greetapp.ModalClasses.Logos;
import com.greetsweet.plant.greetapp.ModalClasses.UploadImageModalClass;
import com.greetsweet.plant.greetapp.UserGreets.UserGreetingsActivity;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.greetsweet.plant.base.BaseActivity;
import com.greetsweet.plant.filters.FilterListener;
import com.greetsweet.plant.filters.FilterViewAdapter;
import com.greetsweet.plant.mylibs.utility.Cons;
import com.greetsweet.plant.tools.EditingToolsAdapter;
import com.greetsweet.plant.tools.ToolType;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

import com.greetsweet.plant.editor.OnPhotoEditorListener;
import com.greetsweet.plant.editor.PhotoEditor;
import com.greetsweet.plant.editor.PhotoEditorView;
import com.greetsweet.plant.editor.SaveSettings;
import com.greetsweet.plant.editor.ViewType;
import com.greetsweet.plant.editor.PhotoFilter;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class EditImageActivity extends BaseActivity implements OnPhotoEditorListener,
        View.OnClickListener,
        PropertiesBSFragment.Properties,
        EmojiBSFragment.EmojiListener,
        StickerBSFragment.StickerListener, EditingToolsAdapter.OnItemSelected, FilterListener {
    InterstitialAd interstitialAd;
    ProgressDialog    progressDialog;
    int action_type = 0;   // [1-> website, 2-> name]
    private static final String TAG = EditImageActivity.class.getSimpleName();
    public static final String EXTRA_IMAGE_PATHS = "extra_image_paths";
    private static final int CAMERA_REQUEST = 52;
    private static final int PICK_REQUEST = 53;
    private static final int SHARE_REQUEST = 54;
    private PhotoEditor mPhotoEditor;
    private PhotoEditorView mPhotoEditorView;
    private PropertiesBSFragment mPropertiesBSFragment;
    private EmojiBSFragment mEmojiBSFragment;
    private StickerBSFragment mStickerBSFragment;
    private TextView mTxtCurrentTool;
    private Typeface mWonderFont;
    private RecyclerView mRvTools, mRvFilters;
    private EditingToolsAdapter mEditingToolsAdapter = new EditingToolsAdapter(this);
    private FilterViewAdapter mFilterViewAdapter = new FilterViewAdapter(this);
    private ConstraintLayout mRootView;
    private ConstraintSet mConstraintSet = new ConstraintSet();
    private boolean mIsFilterVisible;
    ProgressDialog pd;
    Bitmap bt,uploadbit;
    private static final int STORAGE_PERMISSION_CODE =90 ;
    private Integer[] imageIDs = {
            R.drawable.img};
String url="http://13.232.178.62/api/greeting/upload/";
String imageurl ="http://13.232.178.62/api/greeting/view/count/";
String downloadurl ="http://13.232.178.62/api/greeting/download/count/";
String shareurl ="http://13.232.178.62/api/greeting/shares/count/";
String profileurl ="http://13.232.178.62/api/user/profile/";
ImageView image1, image2, image3;
    GetLogoModalClass val;
    String getlogourl ="http://13.232.178.62/api/greeting/logo/get/";
    AlertDialog dialog;
    String imageid;
    Uri fetchuri,takephotouri;
    String converteduri, sendimagepath;
    File finalFile;
    ImageView staticlogo;
    ImageView getlogoimage, flag,imageView_frame;
    TextView removelogo;
    Bitmap bitmap_logo = null;
    String bitmap_logo_url="";
    private AdView adView;
    AdRequest adRequest;
    AdRequest adRequest1;
    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    } // Author: silentnuke


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        makeFullScreen();
        setContentView(R.layout.activity_edit_image);

        PhotoApp.getInstance().createLog("url"+PhotoApp.getInstance().getstoreSharedpref().getSharedValue("urlimage"));
        imageid=PhotoApp.getInstance().getstoreSharedpref().getSharedValue("imageid");
        PhotoApp.getInstance().createLog("EDIT GET IMAGEID"+ imageid);
        MobileAds.initialize(this,
                "ca-app-pub-3940256099942544~3347511713");
        interstitialAd= new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
         adRequest1 = new AdRequest.Builder().build();
        interstitialAd.loadAd(adRequest1);
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                interstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });
        getlogoimage =(ImageView)findViewById(R.id.getlogo);
        staticlogo =(ImageView)findViewById(R.id.imageView2);
        imageView_frame =(ImageView)findViewById(R.id.imageView_frame);
        adView = (AdView)findViewById(R.id.ad_view);
        adRequest = new AdRequest.Builder().build();

        if(PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.p_user_type).equals("basic")) {
            adView.loadAd(adRequest);
        }


        flag =(ImageView)findViewById(R.id.flag);
        flag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_SEND);
                String[] recipients={""};
                intent.putExtra(Intent.EXTRA_EMAIL, recipients);
                intent.putExtra(Intent.EXTRA_SUBJECT,"GreetSweet Report");
                intent.putExtra(Intent.EXTRA_TEXT,"Greeting ID:"+imageid+"\n"+
                        "Sub-Category: "+PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.greettitle)+"\n"+
                        "Category: "+PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.maintitle)+"\n"+
                        "Language: "+PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.p_language)
                );
              //  intent.putExtra(Intent.EXTRA_CC,"mailcc@gmail.com");
                intent.setType("text/html");
                intent.setPackage("com.google.android.gm");
                startActivity(Intent.createChooser(intent, "Send mail"));



            }
        });


        removelogo =(TextView) findViewById(R.id.textView45);
        removelogo.setText("Remove GS Logo");


        if(!PhotoApp.getInstance().getstoreSharedpref().getSharedValue("getlogourl").equalsIgnoreCase("NA")) {
           // bitmap_logo_url = PhotoApp.getInstance().getstoreSharedpref().getSharedValue("getlogourl");
         //   new Task_GetBitmapFromUrl().execute();

          //  Picasso.with(EditImageActivity.this).load(PhotoApp.getInstance().getstoreSharedpref().getSharedValue("getlogourl")).into(getlogoimage);
        }

        if (!PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.is_viewed_greetings).equalsIgnoreCase("true")) {
            new Task_View().execute();
        }



        removelogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.p_user_type).equalsIgnoreCase("NA"))
                {

                    action_type =3;
                    new Task_ProfileGet().execute();
                }
                else {
                    if ( PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.p_user_type).equals(Cons.u_type_enterprise)||PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.p_user_type).equals(Cons.u_type_smart) ) {

                        PhotoApp.getInstance().createLog("REMOVE logo working ");
                        staticlogo.setBackgroundResource(0);
                        removelogo.setText("GS Logo Removed");



                    } else {
                      PhotoApp.getInstance().ShowToast(getResources().getString(R.string.both_user));
                    }
                }

            }
        });
        initViews();

        mWonderFont = Typeface.createFromAsset(getAssets(), "beyond_wonderland.ttf");

        mPropertiesBSFragment = new PropertiesBSFragment();
        mEmojiBSFragment = new EmojiBSFragment();
        mStickerBSFragment = new StickerBSFragment();
        mStickerBSFragment.setStickerListener(this);
        mEmojiBSFragment.setEmojiListener(this);
        mPropertiesBSFragment.setPropertiesChangeListener(this);

        LinearLayoutManager llmTools = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRvTools.setLayoutManager(llmTools);
        mRvTools.setAdapter(mEditingToolsAdapter);

        LinearLayoutManager llmFilters = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRvFilters.setLayoutManager(llmFilters);
        mRvFilters.setAdapter(mFilterViewAdapter);


        //Typeface mTextRobotoTf = ResourcesCompat.getFont(this, R.font.roboto_medium);
        //Typeface mEmojiTypeFace = Typeface.createFromAsset(getAssets(), "emojione-android.ttf");

        mPhotoEditor = new PhotoEditor.Builder(this, mPhotoEditorView)
                .setPinchTextScalable(true) // set flag to make text scalable when pinch
                //.setDefaultTextTypeface(mTextRobotoTf)
                //.setDefaultEmojiTypeface(mEmojiTypeFace)
                .build(); // build photo editor sdk

        mPhotoEditor.setOnPhotoEditorListener(this);

        //Set Image Dynamically
        // mPhotoEditorView.getSource().setImageResource(Integer.parseInt(PhotoApp.getInstance().getstoreSharedpref().getSharedValue("urlimage")));
    }

    private void initViews() {
        ImageView imgUndo;
        ImageView imgRedo;
        ImageView imgCamera;
        ImageView imgGallery;
        ImageView imgSave;
        ImageView imgShare;
        ImageView imgClose;
        ImageView defaultimg;
        final ImageView heart;



        defaultimg=(ImageView)findViewById(R.id.imageView33);




       // Picasso.with(EditImageActivity.this).load(PhotoApp.getInstance().getstoreSharedpref().getSharedValue("urlimage")).into(defaultimg);

        mPhotoEditorView = findViewById(R.id.photoEditorView);

      //  Picasso.with(EditImageActivity.this).load(PhotoApp.getInstance().getstoreSharedpref().getSharedValue("urlimage")).into((Target) mPhotoEditor);
        mTxtCurrentTool = findViewById(R.id.txtCurrentTool);
        mRvTools = findViewById(R.id.rvConstraintTools);
        mRvFilters = findViewById(R.id.rvFilterView);
        mRootView = findViewById(R.id.rootView);
        heart=(ImageView)findViewById(R.id.heart);

        heart.setOnClickListener(new View.OnClickListener(){
            int check = 1; //When check = 1 ,you have your FIRST background set to the button

            @Override
            public void onClick(View view){
                if(check == 1){
                    heart.setBackgroundResource(R.drawable.ic_favorite_red_24dp);
                    PhotoApp.getInstance().getstoreSharedpref().storeSharedValue("fav","1");
                    PhotoApp.getInstance().createLog("see fav"+PhotoApp.getInstance().getstoreSharedpref().getSharedValue("fav"));

                    check = 0;
                }else{
                    heart.setBackgroundResource(R.drawable.ic_favorite_border_black_24dp);
                    PhotoApp.getInstance().getstoreSharedpref().storeSharedValue("fav","0");

                    PhotoApp.getInstance().createLog("see fav"+PhotoApp.getInstance().getstoreSharedpref().getSharedValue("fav"));

                    check = 1;
                }
            }

        });

        imgUndo = findViewById(R.id.imgUndo);
        imgUndo.setOnClickListener(this);

        imgRedo = findViewById(R.id.imgRedo);
        imgRedo.setOnClickListener(this);

        imgCamera = findViewById(R.id.imgCamera);
        imgCamera.setOnClickListener(this);

        imgGallery = findViewById(R.id.imgGallery);
        imgGallery.setOnClickListener(this);

        imgSave = findViewById(R.id.imgSave);
        imgSave.setOnClickListener(this);

        imgShare = findViewById(R.id.imgShare);
        imgShare.setOnClickListener(this);

        imgClose = findViewById(R.id.imgClose);
        imgClose.setOnClickListener(this);

        try {
            Picasso.with(this)
                    .load(PhotoApp.getInstance().getstoreSharedpref().getSharedValue("urlimage"))
                    .into(new Target() {
                        @Override
                        public void onBitmapLoaded (final Bitmap bitmap, Picasso.LoadedFrom from){
            /* Save the bitmap or do something with it here */
                               PhotoApp.getInstance().createLog("==picasso loading1==");
                            //Set it in the ImageView
                           if(mPhotoEditor!=null) {
                               mPhotoEditor.clearAllViews();
                           }
                            PhotoApp.getInstance().createLog("==picasso loading2==");
                            mPhotoEditorView.getSource().setImageBitmap(bitmap);
                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {
                            PhotoApp.getInstance().createLog("==picasso loading3==");
                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {
                            PhotoApp.getInstance().createLog("==picasso loading4==");
                        }
                    });

        }
        catch (Exception e)
        {
            PhotoApp.getInstance().createLog("==picasso loading4=="+e.getMessage());

        }


    }

    public class Task_View extends AsyncTask<Void, Void, Void> {

        String statusCode;
        // private TransparentProgressDialog dialog;
        String responsemsg = "";
        private ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           /* dialog = new TransparentProgressDialog(_mContext, R.drawable.loader);
            dialog.show();*/

            progressDialog = new ProgressDialog(EditImageActivity.this);
            progressDialog.setCancelable(false); // set cancelable to false
            progressDialog.setMessage("Please Wait"); // set message
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0)

        {

            try {
                PhotoApp.getInstance().createLog("View API HITT");

                JSONArray jobj = new JSONArray();

                ServiceHandler sh = new ServiceHandler();

                PhotoApp.getInstance().createLog("=====jobj+url======"+jobj+"========"+imageurl+imageid+"/");

                String jsonStr = sh.postDataArrayGet(imageurl+imageid+"/", jobj);



                if (jsonStr != null) {
                    //{"details":{"status":"post can not be deleted."},"statusCode":"200"}
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    PhotoApp.getInstance().getstoreSharedpref().storeSharedValue(Cons.is_viewed_greetings,"true");
                    PhotoApp.getInstance().createLog("VIEW jsonObj RESPONSE "+jsonObj);

                    statusCode = jsonObj.getString("status");

                    PhotoApp.getInstance().createLog("STATUS CODE  "+statusCode);
                    if (statusCode.equalsIgnoreCase("200")) {

                        responsemsg = "200";



                    } else {

                        PhotoApp.getInstance().createLog("===============no response =========");
                        responsemsg = "No response";
                    }


                }


            } catch (Exception e) {
                PhotoApp.getInstance().createLog("===============Exception e ========="+e.getMessage());
                e.printStackTrace();
            }

            return null;
        }




        @Override
        protected void onPostExecute(Void result) {

            super.onPostExecute(result);
            try {
                progressDialog.dismiss();



            } catch (Exception e) {
            }
            finally {
                try {
                    if (progressDialog != null) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                }catch (NullPointerException e)
                {

                }

            }

        }


    }


    public class Task_GetBitmapFromUrl extends AsyncTask<Void, Void, Void> {

        String statusCode;
        // private TransparentProgressDialog dialog;
        String responsemsg = "";
        private ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           /* dialog = new TransparentProgressDialog(_mContext, R.drawable.loader);
            dialog.show();*/

            progressDialog = new ProgressDialog(EditImageActivity.this);
            progressDialog.setCancelable(false); // set cancelable to false
            progressDialog.setMessage("Please Wait"); // set message
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0)

        {

            try {
              bitmap_logo = getBitmapFromURL(bitmap_logo_url);
            } catch (Exception e) {
                PhotoApp.getInstance().createLog("===============Exception e ========="+e.getMessage());
                e.printStackTrace();
                bitmap_logo =  null;
            }

            return null;
        }




        @Override
        protected void onPostExecute(Void result) {

            super.onPostExecute(result);
            try {
                progressDialog.dismiss();

                if(bitmap_logo!=null)
                {
                    mPhotoEditor.addImage(bitmap_logo);

                    mTxtCurrentTool.setText(R.string.AddLogo);
                }

            } catch (Exception e) {
            }
            finally {
                try {
                    if (progressDialog != null) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                }catch (NullPointerException e)
                {

                }
            }


        }


    }





    @Override
    public void onEditTextChangeListener(final View rootView, String text, int colorCode) {
        TextEditorDialogFragment textEditorDialogFragment =
                TextEditorDialogFragment.show(this, text, colorCode);
        textEditorDialogFragment.setOnTextEditorListener(new TextEditorDialogFragment.TextEditor() {
            @Override
            public void onDone(String inputText, int colorCode,Typeface typeface) {
                mPhotoEditor.editText(rootView,typeface, inputText, colorCode);
                mTxtCurrentTool.setText(R.string.label_text);
            }
        });
    }

    @Override
    public void onAddViewListener(ViewType viewType, int numberOfAddedViews) {
        Log.d(TAG, "onAddViewListener() called with: viewType = [" + viewType + "], numberOfAddedViews = [" + numberOfAddedViews + "]");
    }

    @Override
    public void onRemoveViewListener(int numberOfAddedViews) {
        Log.d(TAG, "onRemoveViewListener() called with: numberOfAddedViews = [" + numberOfAddedViews + "]");
    }

    @Override
    public void onRemoveViewListener(ViewType viewType, int numberOfAddedViews) {
        Log.d(TAG, "onRemoveViewListener() called with: viewType = [" + viewType + "], numberOfAddedViews = [" + numberOfAddedViews + "]");
    }

    @Override
    public void onStartViewChangeListener(ViewType viewType) {
        Log.d(TAG, "onStartViewChangeListener() called with: viewType = [" + viewType + "]");
    }

    @Override
    public void onStopViewChangeListener(ViewType viewType) {
        Log.d(TAG, "onStopViewChangeListener() called with: viewType = [" + viewType + "]");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.imgUndo:
                mPhotoEditor.undo();
                break;

            case R.id.imgRedo:
                mPhotoEditor.redo();
                break;

            case R.id.imgSave:
                if(PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.p_user_type).equals("basic")) {
                    interstitialAd.setAdListener(new AdListener() {
                        @Override
                        public void onAdClosed() {
                            // Load the next interstitial.
                            interstitialAd.loadAd(new AdRequest.Builder().build());
                        }

                    });
                    if (interstitialAd.isLoaded()) {
                        interstitialAd.show();
                    } else {
                        Log.d("TAG", "The interstitial wasn't loaded yet.");
                    }
                }


                if(PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.p_user_type).equalsIgnoreCase("NA"))
                {
                    action_type = 1;
                    new Task_ProfileGet().execute();
                }
                else {
                    if ( PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.p_user_type).equals(Cons.u_type_enterprise)||PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.p_user_type).equals(Cons.u_type_smart) ) {
                        saveImage();

                        PhotoApp.getInstance().createLog("download image working ");

                    } else {
                        PhotoApp.getInstance().ShowToast(getResources().getString(R.string.both_user));
                    }
                }

                break;

            case R.id.imgShare:
                if(PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.p_user_type).equals("basic")) {
                    interstitialAd.setAdListener(new AdListener() {
                        @Override
                        public void onAdClosed() {
                            // Load the next interstitial.
                            interstitialAd.loadAd(new AdRequest.Builder().build());
                        }

                    });
                    if (interstitialAd.isLoaded()) {
                        interstitialAd.show();
                    } else {
                        Log.d("TAG", "The interstitial wasn't loaded yet.");
                    }
                }
               saveImageSHARE();
                break;


            case R.id.imgClose:
                onBackPressed();
                break;

            case R.id.imgCamera:
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                break;

            case R.id.imgGallery:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_REQUEST);
                break;
        }
    }



    @SuppressLint("MissingPermission")
    private void saveImage() {
        if (requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            showLoading("Saving...");



/*
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                    + File.separator + ""
                    + System.currentTimeMillis() + ".png");*/

            // Find the SD Card path
            File filepath = Environment.getExternalStorageDirectory();

            // Create a new folder in SD Card
            File dir = new File(filepath.getAbsolutePath()
                    + "/Greet Sweet/");
            dir.mkdirs();

            // Create a name for the saved image
            File file = new File(dir, File.separator + ""
                    + System.currentTimeMillis() + ".png");

            PhotoApp.getInstance().createLog("direct "+file);
            try {
                file.createNewFile();

                SaveSettings saveSettings = new SaveSettings.Builder()
                        .setClearViewsEnabled(true)
                        .setTransparencyEnabled(true)
                        .build();

                mPhotoEditor.saveAsFile(file.getAbsolutePath(), saveSettings, new PhotoEditor.OnSaveListener() {
                    @Override
                    public void onSuccess(@NonNull String imagePath) {
                        hideLoading();
                        showSnackbar("Image Saved Successfully");
                        mPhotoEditorView.getSource().setImageURI(Uri.fromFile(new File(imagePath)));

                        String geturi = String.valueOf(Uri.fromFile((new File(imagePath))));
                        PhotoApp.getInstance().getstoreSharedpref().storeSharedValue(Cons.newfrag_image,geturi);

                        PhotoApp.getInstance().createLog("IMAGEPATH"+ imagePath);



                        bt=BitmapFactory.decodeFile(imagePath);
                         PhotoApp.getInstance().getstoreSharedpref().storeSharedValueuri(Cons.bt, bt);
                        PhotoApp.getInstance().getstoreSharedpref().storeSharedValue(Cons.shareimagepath,imagePath);

                        PhotoApp.getInstance().createLog("bitmapfact"+ bt);

                        PhotoApp.getInstance().createLog("GET URII"+geturi);
                        PhotoApp.getInstance().getstoreSharedpref().storeSharedValue(Cons.imageuri,geturi);

new Task_Download().execute();
                        PhotoApp.getInstance().ShowToast(getResources().getString(R.string.savedimage));
////////////////uploadfile

                     //   uploadbit=BitmapFactory.decodeFile(imagePath);

                      //  PhotoApp.getInstance().createLog("upload bitmapfact"+ uploadbit);

                        takephotouri= getImageUri(getApplication(), bt);
                        PhotoApp.getInstance().createLog(" takephotouri"+ takephotouri);

                        converteduri= takephotouri.toString();

                        PhotoApp.getInstance().getstoreSharedpref().storeSharedValue("takephotouri",converteduri);
                        PhotoApp.getInstance().createLog(" converteduri"+ converteduri);

                        sendimagepath= PhotoApp.getInstance().getstoreSharedpref().getSharedValue("takephotouri");
                        finalFile = new File(getRealPathFromURI(takephotouri));

                        uploadFile(finalFile);

  ///////////////////end




                        PhotoApp.getInstance().getstoreSharedpref().storeSharedValue(Cons.checksharetype,"normal");

                    }

                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        hideLoading();
                        showSnackbar("Failed to save Image");
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
                hideLoading();
                showSnackbar(e.getMessage());
            }
        }
    }

    public class Task_Download extends AsyncTask<Void, Void, Void> {

        String statusCode;
        // private TransparentProgressDialog dialog;
        String responsemsg = "";
        private ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           /* dialog = new TransparentProgressDialog(_mContext, R.drawable.loader);
            dialog.show();*/

            progressDialog = new ProgressDialog(EditImageActivity.this);
            progressDialog.setCancelable(false); // set cancelable to false
            progressDialog.setMessage("Please Wait"); // set message
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0)

        {

            try {
                PhotoApp.getInstance().createLog("View API HITT");

                JSONArray jobj = new JSONArray();

                ServiceHandler sh = new ServiceHandler();

                PhotoApp.getInstance().createLog("=====jobj+url======"+jobj+"========"+downloadurl+imageid+"/");

                String jsonStr = sh.postDataArrayGet(downloadurl+imageid+"/", jobj);



                if (jsonStr != null) {
                    //{"details":{"status":"post can not be deleted."},"statusCode":"200"}
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    PhotoApp.getInstance().createLog("DOWNLOAD jsonObj RESPONSE "+jsonObj);

                    statusCode = jsonObj.getString("status");

                    PhotoApp.getInstance().createLog("DOWNLOAD STATUS CODE  "+statusCode);
                    if (statusCode.equalsIgnoreCase("200")) {

                        responsemsg = "200";



                    } else {

                        PhotoApp.getInstance().createLog("===============no response =========");
                        responsemsg = "No response";
                    }


                }


            } catch (Exception e) {
                PhotoApp.getInstance().createLog("===============Exception e ========="+e.getMessage());
                e.printStackTrace();
            }

            return null;
        }




        @Override
        protected void onPostExecute(Void result) {

            super.onPostExecute(result);
            try {
                progressDialog.dismiss();



            } catch (Exception e) {
            }
            finally {
                try {
                    if (progressDialog != null) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                }catch (NullPointerException e)
                {

                }
            }


        }


    }

    @SuppressLint("MissingPermission")
    private void saveImageSHARE() {
        if (requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            showLoading("Sharing...");
            File file = new File(Environment.getExternalStorageDirectory()
                    + File.separator + ""
                    + System.currentTimeMillis() + ".png");
            try {
                file.createNewFile();

                SaveSettings saveSettings = new SaveSettings.Builder()
                        .setClearViewsEnabled(true)
                        .setTransparencyEnabled(true)
                        .build();

                mPhotoEditor.saveAsFile(file.getAbsolutePath(), saveSettings, new PhotoEditor.OnSaveListener() {
                    @Override
                    public void onSuccess(@NonNull String imagePath) {
                        hideLoading();
                     //   showSnackbar("Image Saved Successfully");
                        mPhotoEditorView.getSource().setImageURI(Uri.fromFile(new File(imagePath)));

                        String geturi = String.valueOf(Uri.fromFile((new File(imagePath))));
                        PhotoApp.getInstance().getstoreSharedpref().storeSharedValue(Cons.newfrag_image,geturi);

                        bt=BitmapFactory.decodeFile(imagePath);
                        PhotoApp.getInstance().getstoreSharedpref().storeSharedValue(Cons.shareimagepath,imagePath);

                        PhotoApp.getInstance().getstoreSharedpref().storeSharedValueuri(Cons.bt, bt);

                        PhotoApp.getInstance().createLog("bitmapfact"+ bt);

                        PhotoApp.getInstance().createLog("GET URII"+geturi);
                        PhotoApp.getInstance().getstoreSharedpref().storeSharedValue(Cons.imageuri,geturi);

                        new Task_ShareCount().execute();
                        shareImage();
                    }

                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        hideLoading();
                        showSnackbar("Failed to save Image");
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
                hideLoading();
                showSnackbar(e.getMessage());
            }
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 60, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private void shareImage() {

        PhotoApp.getInstance().createLog("BT "+bt);

PhotoApp.getInstance().getstoreSharedpref().storeSharedValue(Cons.checksharetype,"share");

        Intent intent= new Intent(EditImageActivity.this, ShowImageActivity.class);
        startActivity(intent);
        finish();




        //////share IMAGE

        /*    File f = new File("full image path");
            Uri uri = Uri.parse("file://" + f.getAbsolutePath());
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("text/plain");
            share.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject");
         *//*   share.putExtra(Intent.EXTRA_TEXT, "Amaging App"
                    + " " + "https://play.google.com/store/apps/details?id=com.virtuosonetsoft.photoframe");*//*
            share.putExtra(Intent.EXTRA_TEXT, "Made via Greetsweet");
            fetchuri= getImageUri(getApplication(), bt);

            PhotoApp.getInstance().createLog("URIII"+fetchuri);
            share.putExtra(Intent.EXTRA_STREAM,fetchuri);
            share.setType("image*//*");

            share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(Intent.createChooser(share, "Share Via"));*/





        }
    @SuppressLint("MissingPermission")
    private void saveUploadImage() {
        if (requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            showLoading("Sharing...");
            File file = new File(Environment.getExternalStorageDirectory()
                    + File.separator + ""
                    + System.currentTimeMillis() + ".png");
            try {
                file.createNewFile();

                SaveSettings saveSettings = new SaveSettings.Builder()
                        .setClearViewsEnabled(true)
                        .setTransparencyEnabled(true)
                        .build();

                mPhotoEditor.saveAsFile(file.getAbsolutePath(), saveSettings, new PhotoEditor.OnSaveListener() {
                    @Override
                    public void onSuccess(@NonNull String imagePath) {
                        hideLoading();
                        //   showSnackbar("Image Saved Successfully");
                        mPhotoEditorView.getSource().setImageURI(Uri.fromFile(new File(imagePath)));

                        String geturi = String.valueOf(Uri.fromFile((new File(imagePath))));

                        uploadbit=BitmapFactory.decodeFile(imagePath);

                        PhotoApp.getInstance().createLog("upload bitmapfact"+ uploadbit);

                        takephotouri= getImageUri(getApplication(), uploadbit);
                        PhotoApp.getInstance().createLog(" takephotouri"+ takephotouri);

                        converteduri= takephotouri.toString();

                        PhotoApp.getInstance().getstoreSharedpref().storeSharedValue("takephotouri",converteduri);
                        PhotoApp.getInstance().createLog(" converteduri"+ converteduri);

                        sendimagepath= PhotoApp.getInstance().getstoreSharedpref().getSharedValue("takephotouri");
                        finalFile = new File(getRealPathFromURI(takephotouri));

            uploadFile(finalFile);

                    }

                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        hideLoading();
                        showSnackbar("Failed to save Image");
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
                hideLoading();
                showSnackbar(e.getMessage());
            }
        }
    }

    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (getContentResolver() != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CAMERA_REQUEST:
                   // mPhotoEditor.clearAllViews();
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                  //  mPhotoEditorView.getSource().setImageBitmap(photo);

                    if(PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.p_user_type).equalsIgnoreCase("NA"))
                    {

                        new Task_ProfileGet().execute();
                    }
                    else {
                        if (PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.p_user_type).equalsIgnoreCase(Cons.u_type_enterprise)) {

                            mPhotoEditor.addImage(photo);



                            PhotoApp.getInstance().createLog("cameraenterorise");

                        } else {

                            mPhotoEditorView.getSource().setImageBitmap(photo);
                            PhotoApp.getInstance().createLog("camerasmart");

                        }
                    }


                    break;
                case PICK_REQUEST:
                    try {

                        //mPhotoEditor.clearAllViews();
                        Uri uri = data.getData();
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                      //  mPhotoEditorView.getSource().setImageBitmap(bitmap);


                        if(PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.p_user_type).equalsIgnoreCase("NA"))
                        {

                            new Task_ProfileGet().execute();
                        }
                        else {
                            if (PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.p_user_type).equalsIgnoreCase(Cons.u_type_enterprise)) {

                                mPhotoEditor.addImage(bitmap);


                                PhotoApp.getInstance().createLog("galeryenterorise");

                            } else {

                                mPhotoEditorView.getSource().setImageBitmap(bitmap);
                                PhotoApp.getInstance().createLog("galerysmart");

                            }
                        }


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;


            }
        }
    }

    @Override
    public void onColorChanged(int colorCode) {
        mPhotoEditor.setBrushColor(colorCode);
        mTxtCurrentTool.setText(R.string.label_brush);
    }

    @Override
    public void onOpacityChanged(int opacity) {
        mPhotoEditor.setOpacity(opacity);
        mTxtCurrentTool.setText(R.string.label_brush);
    }

    @Override
    public void onBrushSizeChanged(int brushSize) {
        mPhotoEditor.setBrushSize(brushSize);
        mTxtCurrentTool.setText(R.string.label_brush);
    }

    @Override
    public void onEmojiClick(String emojiUnicode) {
        mPhotoEditor.addEmoji(emojiUnicode);
        mTxtCurrentTool.setText(R.string.label_emoji);

    }

    @Override
    public void onStickerClick(Bitmap bitmap) {
        mPhotoEditor.addImage(bitmap);
        mTxtCurrentTool.setText(R.string.label_sticker);
    }

    @Override
    public void isPermissionGranted(boolean isGranted, String permission) {
        if (isGranted) {
            saveImage();
        }
    }

    private void showSaveDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you want to exit without saving image ?");
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveImage();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setNeutralButton("Discard", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.create().show();

    }

    @Override
    public void onFilterSelected(PhotoFilter photoFilter) {
        mPhotoEditor.setFilterEffect(photoFilter);
    }

    @Override
    public void onToolSelected(ToolType toolType) {
        switch (toolType) {
            case BRUSH:
                mPhotoEditor.setBrushDrawingMode(true);
                mTxtCurrentTool.setText(R.string.label_brush);
                mPropertiesBSFragment.show(getSupportFragmentManager(), mPropertiesBSFragment.getTag());
                break;
            case UPLOADPHOTO:
                //saveUploadImage();

                if(PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.p_user_type).equalsIgnoreCase("NA"))
                {
                    new Task_ProfileGet().execute();
                }
                else {
                    if ( PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.p_user_type).equals(Cons.u_type_enterprise)||PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.p_user_type).equals(Cons.u_type_smart) ) {

                        PhotoApp.getInstance().createLog("UPLOAD PHOTP working ");
                        if (checkPermission()) {
                            selectImage();
                        } else {
                            requestPermission();
                        }

                    } else {
                        PhotoApp.getInstance().ShowToast(getResources().getString(R.string.both_user));
                    }
                }


                break;
            case TEXT:
                PhotoApp.getInstance().getstoreSharedpref().storeSharedValue(Cons.clickedViewType,Cons.view_text);
                TextEditorDialogFragment textEditorDialogFragment = TextEditorDialogFragment.show(this);
                textEditorDialogFragment.setOnTextEditorListener(new TextEditorDialogFragment.TextEditor() {
                    @Override
                    public void onDone(String inputText, int colorCode,Typeface typeface) {
                        mPhotoEditor.addText(inputText, colorCode,typeface);
                        mTxtCurrentTool.setText(R.string.label_text);
                    }
                });
                break;
            case LOGO:

                if(PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.p_user_type).equalsIgnoreCase("NA"))
                {
                    action_type = 2;
                    new Task_ProfileGet().execute();
                }
                else {
                    if ( PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.p_user_type).equalsIgnoreCase(Cons.u_type_enterprise) ) {

                        PhotoApp.getInstance().getstoreSharedpref().storeSharedValue(Cons.clickedViewType, Cons.view_logo);

                           opendialoggetlogo();

                    } else {
                        PhotoApp.getInstance().ShowToast(getResources().getString(R.string.authorize_enterprise_user));
                    }
                }



                break;
            case ERASER:
                mPhotoEditor.brushEraser();
                mTxtCurrentTool.setText(R.string.label_eraser);
                break;
            case WEBSITE:

            if(PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.p_user_type).equalsIgnoreCase("NA"))
            {
                action_type = 1;
                new Task_ProfileGet().execute();
            }
            else {
                if ( PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.p_user_type).equalsIgnoreCase(Cons.u_type_enterprise) ) {
                    PhotoApp.getInstance().getstoreSharedpref().storeSharedValue(Cons.clickedViewType, Cons.view_web);
                    WebsiteEditorDialogFragment textEditorDialogFragmentwebsitename = WebsiteEditorDialogFragment.show(this);
                    textEditorDialogFragmentwebsitename.setOnTextEditorListener(new WebsiteEditorDialogFragment.TextEditor() {
                        @Override
                        public void onDone(String inputText, int colorCode, Typeface typeface) {
                            mPhotoEditor.addText(inputText, colorCode, typeface);
                            mTxtCurrentTool.setText(R.string.label_text);
                        }
                    });
                } else {
                    PhotoApp.getInstance().ShowToast(getResources().getString(R.string.authorize_enterprise_user));
                }
            }

                break;
            case FILTER:
                mTxtCurrentTool.setText(R.string.label_filter);
                showFilter(true);
                break;
            case EMOJI:
                mEmojiBSFragment.show(getSupportFragmentManager(), mEmojiBSFragment.getTag());
                break;
            case STICKER:
                mStickerBSFragment.show(getSupportFragmentManager(), mStickerBSFragment.getTag());
                break;
            case NAME:

                PhotoApp.getInstance().getstoreSharedpref().storeSharedValue(Cons.clickedViewType,Cons.view_name);
                NameEditorDialogFragment textEditorDialogFragmentname = NameEditorDialogFragment.show(this);
                textEditorDialogFragmentname.setOnTextEditorListener(new NameEditorDialogFragment.TextEditor() {
                    @Override
                    public void onDone(String inputText, int colorCode,Typeface typeface) {
                        mPhotoEditor.addText(inputText, colorCode,typeface);
                        mTxtCurrentTool.setText(R.string.label_text);
                    }
                });



                break;
            case FRAME:

                FrameEditorDialogFragment frameDialogFragmentname = FrameEditorDialogFragment.show(this);
                frameDialogFragmentname.setOnTextEditorListener(new FrameEditorDialogFragment.TextEditor() {
                    @Override
                    public void onDone() {
                        Picasso.with(EditImageActivity.this).load(PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.frameimage)).into(imageView_frame);



                    }
                });



                break;
        }
    }



    public boolean checkPermission() {
        int FirstPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA);
        int SecondPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int ThirdPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE);

        return FirstPermissionResult == PackageManager.PERMISSION_GRANTED &&
                SecondPermissionResult == PackageManager.PERMISSION_GRANTED &&
                ThirdPermissionResult == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(EditImageActivity.this, new String[]
                {
                        android.Manifest.permission.CAMERA,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE
                }, STORAGE_PERMISSION_CODE);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case STORAGE_PERMISSION_CODE:
                if (grantResults.length > 0) {
                    boolean CameraPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean ReadContactsPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean ReadPhoneStatePermission = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    if (CameraPermission && ReadContactsPermission && ReadPhoneStatePermission) {
                        Toast.makeText(EditImageActivity.this, "Permission Granted", Toast.LENGTH_LONG).show();
                        selectImage();
                    } else {
                        Toast.makeText(EditImageActivity.this, "Permission Denied", Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }
    private void selectImage() {
//        Constants.iscamera = true;
        final CharSequence[] items = { getResources().getString(R.string.TakePhoto),getResources().getString(R.string.ChoosefromLibrary),
                getResources().getString(R.string.CANCELButton) };

        TextView title = new TextView(getApplicationContext());
        title.setText(getResources().getString(R.string.AddPhoto)+"!");
        title.setBackgroundColor(Color.BLACK);
        title.setPadding(10, 15, 15, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.WHITE);
        title.setTextSize(22);


        AlertDialog.Builder builder = new AlertDialog.Builder(
                EditImageActivity.this);



        builder.setCustomTitle(title);

        // builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals(getResources().getString(R.string.TakePhoto))) {
                    if (checkPermission()) {
                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST);
                    } else {
                        requestPermission();
                    }
                }

                else if (items[item].equals(getResources().getString(R.string.ChoosefromLibrary))) {

                    if (checkPermission()) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_REQUEST);
                    } else {
                        requestPermission();
                    }


//                    Intent intent = new Intent(
//                            Intent.ACTION_PICK,
//                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    intent.setType("image/*");
//                    startActivityForResult(
//                            Intent.createChooser(intent, "Select Picture"),
//                            SELECT_PICTURE);
                } else if (items[item].equals(getResources().getString(R.string.CANCELButton))) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void uploadFile(File file) {

//creating a file
// File file = new File(getRealPathFromURI(fileUri));
//creating request body for file
       progressDialog = new ProgressDialog(EditImageActivity.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait...Image is Uploading"); // set message
        progressDialog.show();

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("image", file.getName(), requestBody);


        RequestBody  is_fav =RequestBody.create(MediaType.parse("text/plain"),PhotoApp.getInstance().getstoreSharedpref().getSharedValue("fav"));

        UploadImageInterface apiService =
                ApiClient.getClient().create(UploadImageInterface.class);
        Call<UploadImageModalClass> call = apiService.updateImage( fileToUpload,is_fav, PhotoApp.getInstance().getstoreSharedpref().getSharedValue("apitoken"));
        call.enqueue(new Callback<UploadImageModalClass>() {
            @Override
            public void onResponse(Call<UploadImageModalClass> call, Response<UploadImageModalClass> response) {

                PhotoApp.getInstance().createLog("RESPONSEE Image"+ response);

                 //   Toast.makeText(getApplicationContext(), "Image Uploaded Successfully...", Toast.LENGTH_LONG).show();
PhotoApp.getInstance().createLog("filepath "+ response.body().getFile());

                Intent intent= new Intent(EditImageActivity.this, ShowImageActivity.class);
                startActivity(intent);
                finish();



//progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<UploadImageModalClass> call, Throwable t) {
                Log.i("Failuew", t.toString());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    void showFilter(boolean isVisible) {
        mIsFilterVisible = isVisible;
        mConstraintSet.clone(mRootView);

        if (isVisible) {
            mConstraintSet.clear(mRvFilters.getId(), ConstraintSet.START);
            mConstraintSet.connect(mRvFilters.getId(), ConstraintSet.START,
                    ConstraintSet.PARENT_ID, ConstraintSet.START);
            mConstraintSet.connect(mRvFilters.getId(), ConstraintSet.END,
                    ConstraintSet.PARENT_ID, ConstraintSet.END);
        } else {
            mConstraintSet.connect(mRvFilters.getId(), ConstraintSet.START,
                    ConstraintSet.PARENT_ID, ConstraintSet.END);
            mConstraintSet.clear(mRvFilters.getId(), ConstraintSet.END);
        }

        ChangeBounds changeBounds = new ChangeBounds();
        changeBounds.setDuration(350);
        changeBounds.setInterpolator(new AnticipateOvershootInterpolator(1.0f));
        TransitionManager.beginDelayedTransition(mRootView, changeBounds);

        mConstraintSet.applyTo(mRootView);
    }
    public class Task_ProfileGet extends AsyncTask<Void, Void, Void> {

        String statusCode;
        String name;
        String mobile;
        String lang;
        String usertype;
        String namefont;
        // private TransparentProgressDialog dialog;
        String responsemsg = "";
        private ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           /* dialog = new TransparentProgressDialog(_mContext, R.drawable.loader);
            dialog.show();*/

            progressDialog = new ProgressDialog(EditImageActivity.this);
            progressDialog.setCancelable(false); // set cancelable to false
            progressDialog.setMessage("Please Wait"); // set message
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0)

        {

            try {
                PhotoApp.getInstance().createLog("API HITT");

                JSONArray jobj = new JSONArray();

                ServiceHandler sh = new ServiceHandler();

                PhotoApp.getInstance().createLog("=====jobj+url======"+jobj+"========"+profileurl);

                String jsonStr = sh.postDataArrayGet(profileurl, jobj);



                if (jsonStr != null) {
                    //{"details":{"status":"post can not be deleted."},"statusCode":"200"}
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    PhotoApp.getInstance().createLog("profile jsonObj RESPONSE "+jsonObj);

                    statusCode = jsonObj.getString("status");

                    PhotoApp.getInstance().createLog("LOGO STATUS CODE  "+statusCode);
                    if (statusCode.equalsIgnoreCase("200")) {

                        responsemsg = "200";

                        lang = jsonObj.getString("language");
                        mobile = jsonObj.getString("user_id");
                        namefont = jsonObj.getString("name_font");
                        usertype = jsonObj.getString("user_type");
                        name = jsonObj.getString("name");

                        PhotoApp.getInstance().createLog("lang "+lang);
                        PhotoApp.getInstance().createLog("mobile "+mobile);
                        PhotoApp.getInstance().createLog("namefont "+namefont);
                        PhotoApp.getInstance().createLog("usertype "+usertype);
                        PhotoApp.getInstance().createLog("name "+name);

                        PhotoApp.getInstance().getstoreSharedpref().storeSharedValue(Cons.p_language,lang);
                        PhotoApp.getInstance().getstoreSharedpref().storeSharedValue(Cons.p_mobile,mobile);
                        PhotoApp.getInstance().getstoreSharedpref().storeSharedValue(Cons.p_name_font,namefont);
                        PhotoApp.getInstance().getstoreSharedpref().storeSharedValue(Cons.p_user_type,usertype);
                        PhotoApp.getInstance().getstoreSharedpref().storeSharedValue(Cons.p_name,name);




                    } else {

                        PhotoApp.getInstance().createLog("===============no response =========");
                        responsemsg = "No response";
                    }


                }


            } catch (Exception e) {
                PhotoApp.getInstance().createLog("===============Exception e ========="+e.getMessage());
                e.printStackTrace();
            }

            return null;
        }




        @Override
        protected void onPostExecute(Void result) {

            super.onPostExecute(result);
            try {
                progressDialog.dismiss();
                // title.setText(PhotoApp.getInstance().getstoreSharedpref().getSharedValue("title"));

               if(action_type==1) {
                   if ( PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.p_user_type).equalsIgnoreCase(Cons.u_type_enterprise) ) {
                       PhotoApp.getInstance().getstoreSharedpref().storeSharedValue(Cons.clickedViewType, Cons.view_web);
                       WebsiteEditorDialogFragment textEditorDialogFragmentwebsitename = WebsiteEditorDialogFragment.show(EditImageActivity.this);
                       textEditorDialogFragmentwebsitename.setOnTextEditorListener(new WebsiteEditorDialogFragment.TextEditor() {
                           @Override
                           public void onDone(String inputText, int colorCode, Typeface typeface) {
                               mPhotoEditor.addText(inputText, colorCode, typeface);
                               mTxtCurrentTool.setText(R.string.label_text);
                           }
                       });
                   } else {
                       PhotoApp.getInstance().ShowToast(getResources().getString(R.string.authorize_enterprise_user));
                   }
               }
               else if(action_type==2)
               {
                   if ( PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.p_user_type).equalsIgnoreCase(Cons.u_type_enterprise) ) {
                       if(!PhotoApp.getInstance().getstoreSharedpref().getSharedValue("imageid").equals("NA")) {
                          /* Intent homeIntent = new Intent(EditImageActivity.this, GetLogoActivity.class);
                           startActivity(homeIntent);
                           finish();*/

                           opendialoggetlogo();

                       }

                       else{

                           Toast.makeText(getApplicationContext(), "First Upload Your Logos", Toast.LENGTH_LONG).show();

                       }
                   } else {
                       PhotoApp.getInstance().ShowToast(getResources().getString(R.string.authorize_enterprise_user));
                   }
               }

               else if(action_type==3){
                   if ( PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.p_user_type).equals(Cons.u_type_enterprise)||PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.p_user_type).equals(Cons.u_type_smart) ) {

                       PhotoApp.getInstance().createLog("REMOVE logo working ");
                       staticlogo.setBackgroundResource(0);
                       removelogo.setText("GS Logo Removed");



                   } else {
                       PhotoApp.getInstance().ShowToast(getResources().getString(R.string.both_user));
                   }
               }


            } catch (Exception e) {
            }
            finally {
                try {
                    if (progressDialog != null) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                }catch (NullPointerException e)
                {

                }
            }


        }


    }

    @Override
    public void onBackPressed() {
        if (interstitialAd.isLoaded()) {
            interstitialAd.show();
            interstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    super.onAdClosed();
                    finish();
                }
            });
        }
            else
            {
                if (mIsFilterVisible) {
                    showFilter(false);
                    mTxtCurrentTool.setText(R.string.app_name);
                } else if (!mPhotoEditor.isCacheEmpty()) {
                    showSaveDialog();
                } else {
                    //super.onBackPressed();
                    Intent homeIntent = new Intent(EditImageActivity.this, HomeActivity.class);
                    finish();
                    startActivity(homeIntent);
                }
            }
    }

    private void opendialoggetlogo() {

        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.activity_get_logo, null);
          image1=(ImageView)alertLayout.findViewById(R.id.imageView36);
 image2=(ImageView)alertLayout.findViewById(R.id.imageView37);
       image3=(ImageView)alertLayout.findViewById(R.id.imageView39);
        new Task_GetLogo().execute();

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Logo");
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

         dialog = alert.create();
        dialog.show();


    }

    public class Task_GetLogo extends AsyncTask<Void, Void, Void> {

        String statusCode;
        // private TransparentProgressDialog dialog;
        String responsemsg = "";
        private ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           /* dialog = new TransparentProgressDialog(_mContext, R.drawable.loader);
            dialog.show();*/

            progressDialog = new ProgressDialog(EditImageActivity.this);
            progressDialog.setCancelable(false); // set cancelable to false
            progressDialog.setMessage("Please Wait"); // set message
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0)

        {

            try {
                PhotoApp.getInstance().createLog("API HITT");

                JSONArray jobj = new JSONArray();

                ServiceHandler sh = new ServiceHandler();

                PhotoApp.getInstance().createLog("=====jobj+url======"+jobj+"========"+getlogourl);

                String jsonStr = sh.postDataArrayGet(getlogourl, jobj);



                if (jsonStr != null) {
                    //{"details":{"status":"post can not be deleted."},"statusCode":"200"}
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    PhotoApp.getInstance().createLog("GET LOGO jsonObj RESPONSE "+jsonObj);

                    statusCode = jsonObj.getString("status");

                    PhotoApp.getInstance().createLog("LOGO STATUS CODE  "+statusCode);
                    if (statusCode.equalsIgnoreCase("200")) {

                        responsemsg = "200";

                        JSONArray type_j = jsonObj.getJSONArray("logos");
                        val = new GetLogoModalClass();

                        for(int i=0;i<type_j.length();i++)
                        {


                            JSONObject j_homepage = type_j.getJSONObject(i);
                            Logos val_logo = new Logos();
                            val_logo.setLogo_image(j_homepage.getString("logo_image"));
                            val_logo.setId(j_homepage.getString("id"));


                            val.logoarray.add(val_logo);




                        }


                        PhotoApp.getInstance().createLog("=======getLogoModalClasses========="+getLogoModalClasses.size());



                    } else {

                        PhotoApp.getInstance().createLog("===============no response =========");
                        responsemsg = "No response";
                    }


                }


            } catch (Exception e) {
                PhotoApp.getInstance().createLog("===============Exception e ========="+e.getMessage());
                e.printStackTrace();
            }

            return null;
        }




        @Override
        protected void onPostExecute(Void result) {

            super.onPostExecute(result);
            try {
                progressDialog.dismiss();
                PhotoApp.getInstance().createLog("=====postexecute =========");

                PhotoApp.getInstance().createLog("=====valaray ========="+val.logoarray.size());
                PhotoApp.getInstance().createLog("=====valaray ps1 ========="+val.logoarray.get(0).getLogo_image());

                // title.setText(PhotoApp.getInstance().getstoreSharedpref().getSharedValue("title"));
                if (val.logoarray.size() > 0) {

                    Picasso.with(EditImageActivity.this).load(val.logoarray.get(0).getLogo_image()).placeholder(getResources().getDrawable(R.drawable.gallery)).into(image1);
                    PhotoApp.getInstance().createLog("=====image1 =========");

                    image1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            PhotoApp.getInstance().getstoreSharedpref().storeSharedValue("getlogourl",val.logoarray.get(0).getLogo_image());
                            PhotoApp.getInstance().getstoreSharedpref().storeSharedValue(Cons.is_viewed_greetings,"false");
                          dialog.dismiss();
                      //      Picasso.with(EditImageActivity.this).load(PhotoApp.getInstance().getstoreSharedpref().getSharedValue("getlogourl")).into(getlogoimage);

                             bitmap_logo_url = PhotoApp.getInstance().getstoreSharedpref().getSharedValue("getlogourl");
                               new Task_GetBitmapFromUrl().execute();

                        }
                    });
                }

                if (val.logoarray.size() > 1) {
                    Picasso.with(EditImageActivity.this).load(val.logoarray.get(1).getLogo_image()).placeholder(getResources().getDrawable(R.drawable.gallery)).into(image2);
                    PhotoApp.getInstance().createLog("=====image2 =========");

                    image2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            PhotoApp.getInstance().getstoreSharedpref().storeSharedValue("getlogourl",val.logoarray.get(1).getLogo_image());
                            PhotoApp.getInstance().getstoreSharedpref().storeSharedValue(Cons.is_viewed_greetings,"false");
                            dialog.dismiss();
                            //      Picasso.with(EditImageActivity.this).load(PhotoApp.getInstance().getstoreSharedpref().getSharedValue("getlogourl")).into(getlogoimage);

                            bitmap_logo_url = PhotoApp.getInstance().getstoreSharedpref().getSharedValue("getlogourl");
                            new Task_GetBitmapFromUrl().execute();
                        }
                    });
                }

                if (val.logoarray.size() > 2) {
                    Picasso.with(EditImageActivity.this).load(val.logoarray.get(2).getLogo_image()).placeholder(getResources().getDrawable(R.drawable.gallery)).into(image3);
                    PhotoApp.getInstance().createLog("=====image3 =========");

                    image3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            PhotoApp.getInstance().getstoreSharedpref().storeSharedValue("getlogourl",val.logoarray.get(2).getLogo_image());
                            PhotoApp.getInstance().getstoreSharedpref().storeSharedValue(Cons.is_viewed_greetings,"false");
                            dialog.dismiss();
                            //      Picasso.with(EditImageActivity.this).load(PhotoApp.getInstance().getstoreSharedpref().getSharedValue("getlogourl")).into(getlogoimage);

                            bitmap_logo_url = PhotoApp.getInstance().getstoreSharedpref().getSharedValue("getlogourl");
                            new Task_GetBitmapFromUrl().execute();
                        }
                    });
                }

            } catch (Exception e) {
            }
            finally {
                try {
                    if (progressDialog != null) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                }catch (NullPointerException e)
                {

                }
            }


        }


    }
    ArrayList<GetLogoModalClass> getLogoModalClasses = new ArrayList<GetLogoModalClass>();


    public class Task_ShareCount extends AsyncTask<Void, Void, Void> {

        String statusCode;
        // private TransparentProgressDialog dialog;
        String responsemsg = "";
        private ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           /* dialog = new TransparentProgressDialog(_mContext, R.drawable.loader);
            dialog.show();*/

            progressDialog = new ProgressDialog(EditImageActivity.this);
            progressDialog.setCancelable(false); // set cancelable to false
            progressDialog.setMessage("Please Wait"); // set message
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0)

        {

            try {
                PhotoApp.getInstance().createLog("View API HITT");

                JSONArray jobj = new JSONArray();

                ServiceHandler sh = new ServiceHandler();

                PhotoApp.getInstance().createLog("=====jobj+url======"+jobj+"========"+shareurl+imageid+"/");

                String jsonStr = sh.postDataArrayGet(shareurl+imageid+"/", jobj);



                if (jsonStr != null) {
                    //{"details":{"status":"post can not be deleted."},"statusCode":"200"}
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    PhotoApp.getInstance().createLog("SHARE COUNT jsonObj RESPONSE "+jsonObj);

                    statusCode = jsonObj.getString("status");

                    PhotoApp.getInstance().createLog("STATUS CODE  "+statusCode);
                    if (statusCode.equalsIgnoreCase("200")) {

                        responsemsg = "200";



                    } else {

                        PhotoApp.getInstance().createLog("===============no response =========");
                        responsemsg = "No response";
                    }


                }


            } catch (Exception e) {
                PhotoApp.getInstance().createLog("===============Exception e ========="+e.getMessage());
                e.printStackTrace();
            }

            return null;
        }




        @Override
        protected void onPostExecute(Void result) {

            super.onPostExecute(result);
            try {
                progressDialog.dismiss();



            } catch (Exception e) {
            }
            finally {
                try {
                    if (progressDialog != null) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                }catch (NullPointerException e)
                {

                }
            }

        }


    }
    @Override
    public void onPause() {
        if (adView != null) {
            adView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }


}
