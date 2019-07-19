package com.greetsweet.plant.greetapp.Profile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;


import com.greetsweet.plant.Handler.ServiceHandler;
import com.greetsweet.plant.PhotoApp;
import com.greetsweet.plant.R;
import com.greetsweet.plant.greetapp.HomeActivity;
import com.greetsweet.plant.greetapp.WriteName.WriteYourName;
import com.greetsweet.plant.mylibs.utility.Cons;

import org.json.JSONArray;
import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity {
 TextView username, usermobile, userplan;
    String profileurl ="http://13.232.178.62/api/user/profile/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
                finish();
                startActivity(intent);
            }
        });

        username=(TextView)findViewById(R.id.name);
        usermobile=(TextView)findViewById(R.id.contact);
        userplan=(TextView)findViewById(R.id.plan);

        new Task_ProfileGet().execute();


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

            progressDialog = new ProgressDialog(ProfileActivity.this);
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

                username.setText(name);
                usermobile.setText(mobile);
                userplan.setText(usertype);


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


            Intent homeIntent = new Intent(ProfileActivity.this, HomeActivity.class);
            startActivity(homeIntent);
            finish();



    }


}
