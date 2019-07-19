package com.greetsweet.plant.greetapp.WriteName;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.greetsweet.plant.Handler.ServiceHandler;
import com.greetsweet.plant.PhotoApp;
import com.greetsweet.plant.R;
import com.greetsweet.plant.greetapp.HomeActivity;
import com.greetsweet.plant.greetapp.Login.LoginActivity;
import com.greetsweet.plant.greetapp.Login.OtpVerification;
import com.greetsweet.plant.mylibs.utility.Cons;

import org.json.JSONArray;
import org.json.JSONObject;


public class WriteYourName extends AppCompatActivity {


    private String Luci ="Lucida Handwriting Italic.ttf";
    private String blanch ="beyond_wonderland.ttf";
    private String together ="knownbetter.ttf";
    private String roboto ="Roboto-Black.ttf";
    private String aldery ="Adlery-Swash-trial.ttf";

 Button robotobtn,lucidabtn,adlerybtn, togbtn, blanchbtn, proceed;
 EditText Name;
    String url ="http://13.232.178.62/api/user/name/font/";
String getname,  gettype;
String writingtype = "";
    String profileurl ="http://13.232.178.62/api/user/profile/";
String sendname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_your_name);

        Name =(EditText) findViewById(R.id.editText3);
        if(PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.p_name).equals("NA"))
        {
           // Name.setHint("Please write your name");
        }

        else {


      //  Name.setText(PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.view_name));


            if(!PhotoApp.getInstance().getstoreSharedpref().getSharedValue("writingtype").equalsIgnoreCase("NA"))
            {
                gettype = PhotoApp.getInstance().getstoreSharedpref().getSharedValue("writingtype");
                writingtype = gettype;

                if(gettype.equalsIgnoreCase(Cons.font_adlery))
                {
                    Name.setTypeface(Typeface.createFromAsset(getAssets(), aldery));
                }
                else  if(gettype.equalsIgnoreCase(Cons.font_blanch))
                {
                    Name.setTypeface(Typeface.createFromAsset(getAssets(), blanch));
                }
                else  if(gettype.equalsIgnoreCase(Cons.font_lucida))
                {
                    Name.setTypeface(Typeface.createFromAsset(getAssets(), Luci));
                }
                else  if(gettype.equalsIgnoreCase(Cons.font_roboto))
                {
                    Name.setTypeface(Typeface.createFromAsset(getAssets(), roboto));
                }
                else  if(gettype.equalsIgnoreCase(Cons.font_together))
                {
                    Name.setTypeface(Typeface.createFromAsset(getAssets(), together));
                }




            }




        }

        new Task_ProfileGet().execute();


        robotobtn =(Button)findViewById(R.id.button7);
        lucidabtn =(Button)findViewById(R.id.button8);
        adlerybtn =(Button)findViewById(R.id.button9);
        togbtn =(Button)findViewById(R.id.button10);
        blanchbtn =(Button)findViewById(R.id.button11);



        proceed=(Button)findViewById(R.id.button12);
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  Intent homeIntent=new Intent(WriteYourName.this,HomeActivity.class);
                startActivity(homeIntent);
                finish();
*/


                if (Name.getText().toString().length() == 0) {

                    Name.setError(" Name not entered");
                    Name.requestFocus();
                }

                else if(writingtype.equalsIgnoreCase(""))

                {

                    PhotoApp.getInstance().ShowToast(getResources().getString(R.string.inputfont));

                }
                else if(!writingtype.equalsIgnoreCase(""))

                {

                    PhotoApp.getInstance().ShowToast(getResources().getString(R.string.inputfont));

                }


            }
        });


        robotobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Name.setTypeface(Typeface.createFromAsset(getAssets(), roboto));

                getname=Name.getText().toString().trim();

                writingtype="roboto";




                proceedaction();

            }
        });


        lucidabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Name.setTypeface(Typeface.createFromAsset(getAssets(), Luci));
                getname=Name.getText().toString().trim();

                writingtype="lucida";


                proceedaction();

            }
        });
        adlerybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Name.setTypeface(Typeface.createFromAsset(getAssets(), aldery));
                writingtype="adlery";



                proceedaction();

            }
        });
        togbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Name.setTypeface(Typeface.createFromAsset(getAssets(), together));
                writingtype="better together";


                proceedaction();


            }
        });
        blanchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Name.setTypeface(Typeface.createFromAsset(getAssets(), blanch));
                writingtype="blanch";


                proceedaction();


            }
        });






    }

    private void proceedaction() {

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  Intent homeIntent=new Intent(WriteYourName.this,HomeActivity.class);
                startActivity(homeIntent);
                finish();
*/

              if(writingtype.equalsIgnoreCase(""))

              {

                  PhotoApp.getInstance().ShowToast(getResources().getString(R.string.inputfont));

              }
              else {
                  new Task_Font().execute();
              }
            }
        });

    }


    public class Task_Font extends AsyncTask<Void, Void, Void> {

        String statusCode;
        // private TransparentProgressDialog dialog;
        String responsemsg = "";
        private ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           /* dialog = new TransparentProgressDialog(_mContext, R.drawable.loader);
            dialog.show();*/
            sendname=Name.getText().toString().trim();
            progressDialog = new ProgressDialog(WriteYourName.this);
            progressDialog.setCancelable(false); // set cancelable to false
            progressDialog.setMessage("Please Wait"); // set message
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0)

        {

            try {
                PhotoApp.getInstance().createLog("API HITT");



                JSONObject jobj = new JSONObject();




                PhotoApp.getInstance().getstoreSharedpref().storeSharedValue(Cons.sendname,sendname);

                jobj.put("name",sendname);
                jobj.put("name_font",writingtype);
                ServiceHandler sh = new ServiceHandler();
                PhotoApp.getInstance().createLog("see font"+ gettype);
                PhotoApp.getInstance().createLog("see NAME"+ sendname);


                PhotoApp.getInstance().createLog("=====jobj+url======"+jobj+"========"+url);

                String jsonStr = sh.postDataHeader(url, jobj);



                if (jsonStr != null) {

                    //{"details":{"status":"post can not be deleted."},"statusCode":"200"}
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    PhotoApp.getInstance().createLog("jsonObj RESPONSE "+jsonObj);

                    statusCode = jsonObj.getString("status");

                    PhotoApp.getInstance().createLog("STATUS CODE  "+statusCode);
                    if (statusCode.equalsIgnoreCase("200")) {
                        PhotoApp.getInstance().getstoreSharedpref().storeSharedValue("writingtype",writingtype);
                        responsemsg = "200";



                    } else
                        responsemsg = "No response";


                }


            } catch (Exception e) {

                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(Void result) {

            super.onPostExecute(result);
            try {
                progressDialog.dismiss();


                if (responsemsg.equals("200")) {

                    Toast.makeText(WriteYourName.this, "Font Selected",
                            Toast.LENGTH_LONG).show();

                    Intent homeIntent=new Intent(WriteYourName.this,HomeActivity.class);
                    startActivity(homeIntent);
                    finish();

                } else {

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

            progressDialog = new ProgressDialog(WriteYourName.this);
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
                if(PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.p_name).equals("NA"))
                {
                    Name.setHint("Please write your name");

                    PhotoApp.getInstance().createLog("name work 1");
                }

                else {


                    Name.setText(PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.p_name));
                    PhotoApp.getInstance().createLog("name work 2");

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
        if (!PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.sendname).equals("NA")) {

            Intent homeIntent = new Intent(WriteYourName.this, HomeActivity.class);
            startActivity(homeIntent);
            finish();


        }
    }

}
