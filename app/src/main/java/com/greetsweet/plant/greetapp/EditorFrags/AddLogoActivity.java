package com.greetsweet.plant.greetapp.EditorFrags;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.greetsweet.plant.APIclient.ApiClient;
import com.greetsweet.plant.EditImageActivity;
import com.greetsweet.plant.Handler.ServiceHandler;
import com.greetsweet.plant.Interface.LogoUploadImageInterface;
import com.greetsweet.plant.PhotoApp;
import com.greetsweet.plant.R;
import com.greetsweet.plant.greetapp.HomeActivity;
import com.greetsweet.plant.greetapp.ModalClasses.GetLogoModalClass;
import com.greetsweet.plant.greetapp.ModalClasses.LogoUploadModalClass;
import com.greetsweet.plant.greetapp.ModalClasses.Logos;
import com.greetsweet.plant.greetapp.ModalClasses.UploadImageModalClass;
import com.greetsweet.plant.greetapp.WriteName.WriteYourName;
import com.greetsweet.plant.mylibs.utility.Cons;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddLogoActivity extends AppCompatActivity {
ImageView image1, image2, image3;
    GetLogoModalClass val;

    String getlogourl ="http://13.232.178.62/api/greeting/logo/get/";
    private static final int STORAGE_PERMISSION_CODE =90 ;
    Bitmap bitmap,mBitmap,bitmapfinal;
    public boolean is_gallery= true;
    public Intent data_;
    ImageView imageView;
    private int GALLERY = 1, CAMERA = 2;
    public static String filePath;
    File finalFile,imageFile;

    String pic1, getlogo;
    TextView save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_logo);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        image1=(ImageView)findViewById(R.id.imageView36);
        image2=(ImageView)findViewById(R.id.imageView37);
        image3=(ImageView)findViewById(R.id.imageView39);
        save=(TextView)findViewById(R.id.textView49);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeIntent = new Intent(AddLogoActivity.this, HomeActivity.class);
                startActivity(homeIntent);
                finish();


            }
        });

        if (image1 != null) {
            image1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    pic1 = "1";

                    if (checkPermission()) {
                        selectImage();
                    } else {
                        requestPermission();
                    }
                }
            });
        }
        if (image2 != null) {
            image2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    pic1 = "2";

                    if (checkPermission()) {
                        selectImage();
                    } else {
                        requestPermission();
                    }
                }
            });
        }
        if (image3 != null) {
            image3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    pic1 = "3";

                    if (checkPermission()) {
                        selectImage();
                    } else {
                        requestPermission();
                    }
                }
            });
        }
new Task_GetLogo().execute();

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
        ActivityCompat.requestPermissions(AddLogoActivity.this, new String[]
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
                        Toast.makeText(AddLogoActivity.this, "Permission Granted", Toast.LENGTH_LONG).show();
                        selectImage();
                    } else {
                        Toast.makeText(AddLogoActivity.this, "Permission Denied", Toast.LENGTH_LONG).show();
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
                AddLogoActivity.this);



        builder.setCustomTitle(title);

        // builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals(getResources().getString(R.string.TakePhoto))) {
                    if (checkPermission()) {
                        String timeStamp = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                        String imageFileName = timeStamp + ".jpg";
                        File storageDir = Environment.getExternalStoragePublicDirectory(
                                Environment.DIRECTORY_PICTURES);
                        filePath = storageDir.getAbsolutePath() + "/" + imageFileName;
                        File file = new File(filePath);
                        Uri outputFileUri = Uri.fromFile(file);
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                        startActivityForResult(cameraIntent, CAMERA);
                    } else {
                        requestPermission();
                    }
                }

                else if (items[item].equals(getResources().getString(R.string.ChoosefromLibrary))) {

                    if (checkPermission()) {
                        Intent intent = new Intent(
                                Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/*");
                        startActivityForResult(
                                Intent.createChooser(intent, getResources().getString(R.string.SelectPicture)),
                                GALLERY);
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
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        data_ = data;
        // getProgressBar().setVisibility(View.VISIBLE);
        if (resultCode == RESULT_CANCELED) {
            //getProgressBar().setVisibility(View.GONE);
            return;
        }
        if (requestCode == GALLERY) {
            is_gallery = true;
            new ImageProcessingAsynTask().execute();
        } else if (requestCode == CAMERA) {
            is_gallery = false;
            new ImageProcessingAsynTask().execute();
        }
    }
    private  class ImageProcessingAsynTask extends AsyncTask<Void, Void, Void> {
        private Context context;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgress_ImageUPloading();
        }


        @Override
        protected Void doInBackground(Void... params) {
            // Constants.bitmapfinal = null;
            if (is_gallery) {
                if (data_ != null) {
                    Uri contentURI = data_.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), contentURI);
                        // String path = saveImage(bitmap);
                        //  Toast.makeText(MainActivitySingle.this, "Image Saved!", Toast.LENGTH_SHORT).show();

                        //  Uri tempUri = getImageUri(getApplicationContext(), bitmap);
                        // Bitmap finalbitmapface = ImageUtils.getBitmap(ImageUtils.getRealPathFromURI(ProfileSettings.this, contentURI), 2048, 1232);
                        //Constants.bitmapfinal = finalbitmapface;
                        // CALL THIS METHOD TO GET THE ACTUAL PATH

                        //Bitmap myBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                        final Uri tempUri = getImageUri(getApplicationContext(), bitmap);
                        finalFile = new File(getRealPathFromURI(tempUri));




                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {

                                if(Integer.parseInt(pic1)==1)
                                {
                                    image1.setImageURI(tempUri);


                                }
                                if(Integer.parseInt(pic1)==2)
                                {
                                    image2.setImageURI(tempUri);


                                }
                                if(Integer.parseInt(pic1)==3)
                                {
                                    image3.setImageURI(tempUri);


                                }
                            }
                        });


                        // Constants.file=finalFile;
                        //Constants.keyvalue="true";

                        //imageview.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(AddLogoActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                int rotate = 0;
                try {
                    imageFile = new File(filePath);
                    ExifInterface exif = new ExifInterface(
                            imageFile.getAbsolutePath());
                    int orientation = exif.getAttributeInt(
                            ExifInterface.TAG_ORIENTATION,
                            ExifInterface.ORIENTATION_NORMAL);

                    switch (orientation) {
                        case ExifInterface.ORIENTATION_ROTATE_270:
                            Log.i("i called 270", "270");
                            rotate = 270;
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_180:
                            Log.i("i called 180", "180");
                            rotate = 180;
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_90:
                            Log.i("i called 90", "90");
                            rotate = 90;
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Matrix matrix = new Matrix();
                matrix.postRotate(rotate);
                //  File imgFile = new File(filePath);
                if (imageFile.exists()) {
                    Bitmap myBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                    bitmap = Bitmap.createBitmap(myBitmap, 0, 0, myBitmap.getWidth(), myBitmap.getHeight(), matrix, true);
                    final Uri tempUri = getImageUri(getApplicationContext(), bitmap);
                    finalFile = new File(getRealPathFromURI(tempUri));


                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            if(Integer.parseInt(pic1)==1)
                            {
                                image1.setImageURI(tempUri);


                            }
                            if(Integer.parseInt(pic1)==2)
                            {
                                image2.setImageURI(tempUri);


                            }
                            if(Integer.parseInt(pic1)==3)
                            {
                                image3.setImageURI(tempUri);


                            }
                        }
                    });


                }

            }
            return null;
        }
        @Override
        protected void onPostExecute(Void o) {
            super.onPostExecute(o);
            if(finalFile!=null) {
                uploadFile(finalFile);


            }
else
            {
                CancelProgress_ImageUPloading();
            }

        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
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



    int uploadid = 0;
    boolean is_update = false;


    ProgressDialog    progressDialog = null;
    public void showProgress_ImageUPloading()
    {
            progressDialog = new ProgressDialog(AddLogoActivity.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait...Logo is Uploading"); // set message
        progressDialog.show();
    }
    public void CancelProgress_ImageUPloading()
    {
        if(progressDialog!=null)
            progressDialog.cancel();
    }


    private void uploadFile(File file) {

//creating a file
// File file = new File(getRealPathFromURI(fileUri));
//creating request body for file


        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("image", file.getName(), requestBody);


      //  RequestBody  is_fav =RequestBody.create(MediaType.parse("text/plain"),PhotoApp.getInstance().getstoreSharedpref().getSharedValue("fav"));


    if(pic1.equalsIgnoreCase("1"))
    {
        if(val!=null) {
            if ( val.logoarray.size() > 0 ) {
                uploadid = Integer.parseInt(val.logoarray.get(0).getId());
                is_update = true;

            } else {
                is_update = false;
            }
        }
    }
    else if(pic1.equalsIgnoreCase("2"))
    {
        if(val!=null) {
            if ( val.logoarray.size() > 1 ) {
                uploadid = Integer.parseInt(val.logoarray.get(1).getId());
                is_update = true;

            } else {
                is_update = false;
            }
        }
    }
    else
    {
        if(val!=null) {
            if ( val.logoarray.size() > 2 ) {
                uploadid = Integer.parseInt(val.logoarray.get(2).getId());
                is_update = true;

            } else {
                is_update = false;
            }
        }
    }


    if(is_update) {
        LogoUploadImageInterface apiService =
                ApiClient.getClient().create(LogoUploadImageInterface.class);
        Call<LogoUploadModalClass> call = apiService.logouploadUpdate(uploadid,fileToUpload, PhotoApp.getInstance().getstoreSharedpref().getSharedValue("apitoken"));
        call.enqueue(new Callback<LogoUploadModalClass>() {
            @Override
            public void onResponse(Call<LogoUploadModalClass> call, Response<LogoUploadModalClass> response) {
                try {
                    PhotoApp.getInstance().createLog("RESPONSEE Image" + response);

                    Toast.makeText(getApplicationContext(), "Logo Updated Successfully...", Toast.LENGTH_LONG).show();
                    PhotoApp.getInstance().createLog("filepath " + response.body().getLogo_url());
                    getlogo = response.body().getLogo_url();
                    PhotoApp.getInstance().getstoreSharedpref().storeSharedValue("logourl", getlogo);

                    PhotoApp.getInstance().createLog("LOGOURL " + getlogo);
                }
                catch (Exception e)
                {
                    PhotoApp.getInstance().createLog(getResources().getString(R.string.not_authorize));
                }
                finally {
                    CancelProgress_ImageUPloading();
                }



            }

            @Override
            public void onFailure(Call<LogoUploadModalClass> call, Throwable t) {
                Log.i("Failuew", t.toString());
                CancelProgress_ImageUPloading();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });





    }
    else {
        LogoUploadImageInterface apiService =
                ApiClient.getClient().create(LogoUploadImageInterface.class);
        Call<LogoUploadModalClass> call = apiService.logoupload(fileToUpload, PhotoApp.getInstance().getstoreSharedpref().getSharedValue("apitoken"));
        call.enqueue(new Callback<LogoUploadModalClass>() {
            @Override
            public void onResponse(Call<LogoUploadModalClass> call, Response<LogoUploadModalClass> response) {
try{
                PhotoApp.getInstance().createLog("RESPONSEE Image" + response);

                Toast.makeText(getApplicationContext(), "Logo Uploaded Successfully...", Toast.LENGTH_LONG).show();
                PhotoApp.getInstance().createLog("filepath " + response.body().getLogo_url());
                getlogo = response.body().getLogo_url();
                PhotoApp.getInstance().getstoreSharedpref().storeSharedValue("logourl", getlogo);

                PhotoApp.getInstance().createLog("LOGOURL " + getlogo);
            }
 catch (Exception e)
                {
                    PhotoApp.getInstance().createLog(getResources().getString(R.string.not_authorize));
                }
                finally {
                    CancelProgress_ImageUPloading();
                }



            }

            @Override
            public void onFailure(Call<LogoUploadModalClass> call, Throwable t) {
                Log.i("Failuew", t.toString());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                CancelProgress_ImageUPloading();
            }
        });
    }


    }
    @Override
    public void onBackPressed() {

            Intent homeIntent = new Intent(AddLogoActivity.this, HomeActivity.class);
            startActivity(homeIntent);
            finish();



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

            progressDialog = new ProgressDialog(AddLogoActivity.this);
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
                // title.setText(PhotoApp.getInstance().getstoreSharedpref().getSharedValue("title"));
                if (val.logoarray.size() > 0) {
                    Picasso.with(AddLogoActivity.this).load(val.logoarray.get(0).getLogo_image()).placeholder(getResources().getDrawable(R.drawable.gallery)).into(image1);
                   /* image1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            PhotoApp.getInstance().getstoreSharedpref().storeSharedValue("getlogourl",val.logoarray.get(0).getLogo_image());

                            Intent intent = new Intent(AddLogoActivity.this, EditImageActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });*/
                }

                if (val.logoarray.size() > 1) {
                    Picasso.with(AddLogoActivity.this).load(val.logoarray.get(1).getLogo_image()).placeholder(getResources().getDrawable(R.drawable.gallery)).into(image2);

                  /*  image2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            PhotoApp.getInstance().getstoreSharedpref().storeSharedValue("getlogourl",val.logoarray.get(1).getLogo_image());

                            Intent intent = new Intent(AddLogoActivity.this, EditImageActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });*/
                }

                if (val.logoarray.size() > 2) {
                    Picasso.with(AddLogoActivity.this).load(val.logoarray.get(2).getLogo_image()).placeholder(getResources().getDrawable(R.drawable.gallery)).into(image3);

                /*    image3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            PhotoApp.getInstance().getstoreSharedpref().storeSharedValue("getlogourl",val.logoarray.get(2).getLogo_image());

                            Intent intent = new Intent(AddLogoActivity.this, EditImageActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });*/
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

}
