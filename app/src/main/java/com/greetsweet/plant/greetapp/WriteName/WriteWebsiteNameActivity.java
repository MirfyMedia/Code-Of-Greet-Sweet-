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
import com.greetsweet.plant.greetapp.Login.SelectLanguge;
import com.greetsweet.plant.greetapp.ModalClasses.GetLangModalClass;
import com.greetsweet.plant.greetapp.ModalClasses.Languages;
import com.greetsweet.plant.mylibs.utility.Cons;

import org.json.JSONArray;
import org.json.JSONObject;


public class WriteWebsiteNameActivity extends AppCompatActivity {


    private String Luci ="Lucida Handwriting Italic.ttf";
    private String blanch ="beyond_wonderland.ttf";
    private String together ="knownbetter.ttf";
    private String roboto ="Roboto-Black.ttf";
    private String aldery ="Adlery-Swash-trial.ttf";

    Button robotobtn,lucidabtn,adlerybtn, togbtn, blanchbtn, proceed;
    EditText Name;
    String url ="http://13.232.178.62/api/greeting/website/add/";
    String weburl ="http://13.232.178.62/api/greeting/website/get/";
    String getname, webwritingtype, gettype;

    String sendwebsite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_website_name);


        new Task_GetWebsite().execute();


        Name =(EditText) findViewById(R.id.editText3);

        if(PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.sendwebsite).equals("NA"))
        {
            Name.setHint("www.yourwebsite.com");
        }

        else {

           if(!PhotoApp.getInstance().getstoreSharedpref().getSharedValue("webwritingtype").equalsIgnoreCase("NA"))
            {
                gettype = PhotoApp.getInstance().getstoreSharedpref().getSharedValue("webwritingtype");
                webwritingtype = gettype;

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


            Name.setText(PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.sendwebsite));
        }


        robotobtn =(Button)findViewById(R.id.button7);
        lucidabtn =(Button)findViewById(R.id.button8);
        adlerybtn =(Button)findViewById(R.id.button9);
        togbtn =(Button)findViewById(R.id.button10);
        blanchbtn =(Button)findViewById(R.id.button11);



        proceed=(Button)findViewById(R.id.button12);
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Name.getText().toString().length() == 0) {

                    Name.setError("Website name not entered");
                    Name.requestFocus();
                }
              else if(webwritingtype==null)

                {


                    PhotoApp.getInstance().ShowToast(getResources().getString(R.string.inputfont));


                }
                else if(!webwritingtype.equalsIgnoreCase(""))

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

                webwritingtype="roboto";


                proceedaction();

            }
        });


        lucidabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Name.setTypeface(Typeface.createFromAsset(getAssets(), Luci));
                getname=Name.getText().toString().trim();

                webwritingtype="lucida";


                proceedaction();

            }
        });
        adlerybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Name.setTypeface(Typeface.createFromAsset(getAssets(), aldery));
                webwritingtype="adlery";



                proceedaction();

            }
        });
        togbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Name.setTypeface(Typeface.createFromAsset(getAssets(), together));
                webwritingtype="better together";


                proceedaction();


            }
        });
        blanchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Name.setTypeface(Typeface.createFromAsset(getAssets(), blanch));
                webwritingtype="blanch";


                proceedaction();


            }
        });









    }

    private void proceedaction() {

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              if(webwritingtype==null)

                {

                    PhotoApp.getInstance().ShowToast(getResources().getString(R.string.inputfont));

                }

                if (Name.getText().toString().length() == 0) {

                    Name.setError("Website name not entered");
                    Name.requestFocus();
                } else {

                    sendwebsite = Name.getText().toString().trim();
                    PhotoApp.getInstance().getstoreSharedpref().storeSharedValue(Cons.sendwebsite, sendwebsite);
                    new Task_Website().execute();





                  /*  if (PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.sendname).equals("NA")) {

                        Intent homeIntent = new Intent(WriteWebsiteNameActivity.this, WriteYourName.class);
                        startActivity(homeIntent);
                        finish();


                    } else {
                        Intent homeIntent = new Intent(WriteWebsiteNameActivity.this, HomeActivity.class);
                        startActivity(homeIntent);
                        finish();

                    }*/
                }
            }
        });

    }


    public class Task_Website extends AsyncTask<Void, Void, Void> {

        String statusCode;
        // private TransparentProgressDialog dialog;
        String responsemsg = "";
        private ProgressDialog progressDialog;
        String sendwebname = "";
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           /* dialog = new TransparentProgressDialog(_mContext, R.drawable.loader);
            dialog.show();*/
            sendwebname  = Name.getText().toString().trim();
            progressDialog = new ProgressDialog(WriteWebsiteNameActivity.this);
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




                jobj.put("website",sendwebname);
                ServiceHandler sh = new ServiceHandler();
                PhotoApp.getInstance().createLog("see website"+ sendwebname);


                PhotoApp.getInstance().createLog("=====jobj+url======"+jobj+"========"+url);

                String jsonStr = sh.postDataHeader(url, jobj);



                if (jsonStr != null) {

                    //{"details":{"status":"post can not be deleted."},"statusCode":"200"}
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    PhotoApp.getInstance().createLog("WEBSITE jsonObj RESPONSE "+jsonObj);

                    statusCode = jsonObj.getString("status");

                    PhotoApp.getInstance().createLog("STATUS CODE  "+statusCode);
                    if (statusCode.equalsIgnoreCase("200")) {
                        PhotoApp.getInstance().getstoreSharedpref().storeSharedValue("webwritingtype",webwritingtype);
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

                    sendwebsite = Name.getText().toString().trim();

                    PhotoApp.getInstance().getstoreSharedpref().storeSharedValue(Cons.sendwebsite, sendwebsite);


                    if (PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.sendname).equals("NA")) {

                        Intent homeIntent = new Intent(WriteWebsiteNameActivity.this, WriteYourName.class);
                        startActivity(homeIntent);
                        finish();


                    } else {
                        Intent homeIntent = new Intent(WriteWebsiteNameActivity.this, HomeActivity.class);
                        startActivity(homeIntent);
                        finish();

                    }

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

    public class Task_GetWebsite extends AsyncTask<Void, Void, Void> {

        String statusCode;
        String getwebname;
        // private TransparentProgressDialog dialog;
        String responsemsg = "";
        private ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           /* dialog = new TransparentProgressDialog(_mContext, R.drawable.loader);
            dialog.show();*/

            progressDialog = new ProgressDialog(WriteWebsiteNameActivity.this);
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

                PhotoApp.getInstance().createLog("=====jobj+url======"+jobj+"========"+weburl);

                String jsonStr = sh.postDataArrayGet(weburl, jobj);



                if (jsonStr != null) {
                    //{"details":{"status":"post can not be deleted."},"statusCode":"200"}
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    PhotoApp.getInstance().createLog("GET WEBSITE jsonObj RESPONSE "+jsonObj);

                    statusCode = jsonObj.getString("status");

                    PhotoApp.getInstance().createLog("WEB STATUS CODE  "+statusCode);
                    if (statusCode.equalsIgnoreCase("200")) {

                        responsemsg = "200";

                        getwebname = jsonObj.getString("message");





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


                Name.setText(getwebname);


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
        if (!PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.sendwebsite).equals("NA")) {

            Intent homeIntent = new Intent(WriteWebsiteNameActivity.this, HomeActivity.class);
            startActivity(homeIntent);
            finish();


        }
        else
        {
            PhotoApp.getInstance().ShowToast(getResources().getString(R.string.inputwebsite));
        }
    }

}
