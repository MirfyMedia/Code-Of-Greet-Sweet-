package com.greetsweet.plant.greetapp.EditorFrags;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.greetsweet.plant.EditImageActivity;
import com.greetsweet.plant.Handler.ServiceHandler;
import com.greetsweet.plant.PhotoApp;
import com.greetsweet.plant.R;
import com.greetsweet.plant.greetapp.ModalClasses.GetLogoModalClass;
import com.greetsweet.plant.greetapp.ModalClasses.Logos;
import com.greetsweet.plant.greetapp.ModalClasses.greetingspojo;
import com.greetsweet.plant.greetapp.ModalClasses.home_page_pojo;
import com.greetsweet.plant.greetapp.ModalClasses.subcategories_pojo;
import com.greetsweet.plant.mylibs.utility.Cons;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetLogoActivity extends AppCompatActivity {
ImageView image1, image2, image3;
    GetLogoModalClass val;

String getlogourl ="http://13.232.178.62/api/greeting/logo/get/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_logo);

        image1=(ImageView)findViewById(R.id.imageView36);
        image2=(ImageView)findViewById(R.id.imageView37);
        image3=(ImageView)findViewById(R.id.imageView39);

        new Task_GetLogo().execute();

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

            progressDialog = new ProgressDialog(GetLogoActivity.this);
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
                    Picasso.with(GetLogoActivity.this).load(val.logoarray.get(0).getLogo_image()).placeholder(getResources().getDrawable(R.drawable.gallery)).into(image1);
               image1.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       PhotoApp.getInstance().getstoreSharedpref().storeSharedValue("getlogourl",val.logoarray.get(0).getLogo_image());
                       PhotoApp.getInstance().getstoreSharedpref().storeSharedValue(Cons.is_viewed_greetings,"false");
                       Intent intent = new Intent(GetLogoActivity.this, EditImageActivity.class);
                       startActivity(intent);
                       finish();
                   }
               });
                }

                if (val.logoarray.size() > 1) {
                    Picasso.with(GetLogoActivity.this).load(val.logoarray.get(1).getLogo_image()).placeholder(getResources().getDrawable(R.drawable.gallery)).into(image2);

                    image2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            PhotoApp.getInstance().getstoreSharedpref().storeSharedValue("getlogourl",val.logoarray.get(1).getLogo_image());
                            PhotoApp.getInstance().getstoreSharedpref().storeSharedValue(Cons.is_viewed_greetings,"false");
                            Intent intent = new Intent(GetLogoActivity.this, EditImageActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                }

                if (val.logoarray.size() > 2) {
                    Picasso.with(GetLogoActivity.this).load(val.logoarray.get(2).getLogo_image()).placeholder(getResources().getDrawable(R.drawable.gallery)).into(image3);

                    image3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            PhotoApp.getInstance().getstoreSharedpref().storeSharedValue("getlogourl",val.logoarray.get(2).getLogo_image());
                            PhotoApp.getInstance().getstoreSharedpref().storeSharedValue(Cons.is_viewed_greetings,"false");
                            Intent intent = new Intent(GetLogoActivity.this, EditImageActivity.class);
                            startActivity(intent);
                            finish();
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

    @Override
    public void onBackPressed() {
       // super.onBackPressed();
        Intent intent = new Intent(GetLogoActivity.this, EditImageActivity.class);
        startActivity(intent);
        finish();
    }
}
