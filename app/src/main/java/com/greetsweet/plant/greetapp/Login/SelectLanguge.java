package com.greetsweet.plant.greetapp.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.greetsweet.plant.EditImageActivity;
import com.greetsweet.plant.Handler.ServiceHandler;
import com.greetsweet.plant.PhotoApp;
import com.greetsweet.plant.R;
import com.greetsweet.plant.WebsiteEditorDialogFragment;
import com.greetsweet.plant.greetapp.EditorFrags.GetLogoActivity;
import com.greetsweet.plant.greetapp.HomeActivity;
import com.greetsweet.plant.greetapp.ModalClasses.GetLangModalClass;
import com.greetsweet.plant.greetapp.ModalClasses.GetLogoModalClass;
import com.greetsweet.plant.greetapp.ModalClasses.Languages;
import com.greetsweet.plant.greetapp.ModalClasses.Logos;
import com.greetsweet.plant.greetapp.WriteName.WriteWebsiteNameActivity;
import com.greetsweet.plant.greetapp.WriteName.WriteYourName;
import com.greetsweet.plant.mylibs.utility.Cons;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class SelectLanguge extends AppCompatActivity {

    ImageView hindi, english,hinglish, bengali, gujrati, kannada,tamil, marathi,telgu,urdu,mandrain;
    TextView hinditext, englishtext,hinglishtext, bengalitext, gujratitext, kannadatext,
            tamiltext, marathitext,telgutext,urdutext,mandraintext;

    String language, storelang, getlang;
    Button proceed;
    String url ="http://13.232.178.62/api/user/language/";
    String profileurl ="http://13.232.178.62/api/user/profile/";

    String getlangurl="http://13.232.178.62/api/greeting/language/all/";
    GetLangModalClass val;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_languge);

       new Task_GetLanguage().execute();

        hindi=(ImageView)findViewById(R.id.imageView7);
        english=(ImageView)findViewById(R.id.imageView9);
        hinglish=(ImageView)findViewById(R.id.imageView11);
        bengali=(ImageView)findViewById(R.id.imageView13);
        gujrati=(ImageView)findViewById(R.id.imageView15);
        kannada=(ImageView)findViewById(R.id.imageView17);
        tamil=(ImageView)findViewById(R.id.imageView19);
        marathi=(ImageView)findViewById(R.id.imageView21);
        telgu=(ImageView)findViewById(R.id.imageView23);
        mandrain=(ImageView)findViewById(R.id.imageView25);
        urdu=(ImageView)findViewById(R.id.imageView40);

        hinditext=(TextView) findViewById(R.id.textView20);
        englishtext=(TextView) findViewById(R.id.textView21);
        hinglishtext=(TextView) findViewById(R.id.textView22);
        bengalitext=(TextView) findViewById(R.id.textView23);
        gujratitext=(TextView) findViewById(R.id.textView24);
        kannadatext=(TextView) findViewById(R.id.textView25);
        tamiltext=(TextView) findViewById(R.id.textView26);
        marathitext=(TextView) findViewById(R.id.textView27);
        telgutext=(TextView) findViewById(R.id.textView28);
        mandraintext=(TextView) findViewById(R.id.textView29);
        urdutext=(TextView) findViewById(R.id.textView50);




        proceed=(Button) findViewById(R.id.button13);
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if( PhotoApp.getInstance().getstoreSharedpref().getSharedValue("language").equalsIgnoreCase("NA"))

                {

                    PhotoApp.getInstance().ShowToast(getResources().getString(R.string.inputlang));

                }
            }
        });
        if(PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.p_language).equals("1")){

            hindi.setBackgroundResource(R.drawable.purpleborder);
        }
        else if(PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.p_language).equals("2")){

            english.setBackgroundResource(R.drawable.purpleborder);
        }
        else if(PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.p_language).equals("3")){

            hinglish.setBackgroundResource(R.drawable.purpleborder);
        } else if(PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.p_language).equals("4")){

            bengali.setBackgroundResource(R.drawable.purpleborder);
        } else if(PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.p_language).equals("5")){

            gujrati.setBackgroundResource(R.drawable.purpleborder);
        } else if(PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.p_language).equals("6")){

            kannada.setBackgroundResource(R.drawable.purpleborder);
        } else if(PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.p_language).equals("7")){

            tamil.setBackgroundResource(R.drawable.purpleborder);
        } else if(PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.p_language).equals("8")){

            marathi.setBackgroundResource(R.drawable.purpleborder);
        } else if(PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.p_language).equals("9")){

            telgu.setBackgroundResource(R.drawable.purpleborder);
        } else if(PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.p_language).equals("10")){

            mandrain.setBackgroundResource(R.drawable.purpleborder);
        }
        else{

            urdu.setBackgroundResource(R.drawable.purpleborder);

        }

        hindi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                hindi.setBackgroundResource(R.drawable.purpleborder);
                english.setBackgroundResource(R.drawable.greyborder);
                hinglish.setBackgroundResource(R.drawable.greyborder);
                bengali.setBackgroundResource(R.drawable.greyborder);
                gujrati.setBackgroundResource(R.drawable.greyborder);
                kannada.setBackgroundResource(R.drawable.greyborder);
                tamil.setBackgroundResource(R.drawable.greyborder);
                marathi.setBackgroundResource(R.drawable.greyborder);
                telgu.setBackgroundResource(R.drawable.greyborder);
                mandrain.setBackgroundResource(R.drawable.greyborder);
                urdu.setBackgroundResource(R.drawable.greyborder);


                language="1";

                PhotoApp.getInstance().getstoreSharedpref().storeSharedValue("language",language);
                getlang= PhotoApp.getInstance().getstoreSharedpref().getSharedValue("language");
                PhotoApp.getInstance().createLog("SELECTED LANGUAGE "+ getlang);

                langfn();

            }
        });
        english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                hindi.setBackgroundResource(R.drawable.greyborder);
                english.setBackgroundResource(R.drawable.purpleborder);
                hinglish.setBackgroundResource(R.drawable.greyborder);
                bengali.setBackgroundResource(R.drawable.greyborder);
                gujrati.setBackgroundResource(R.drawable.greyborder);
                kannada.setBackgroundResource(R.drawable.greyborder);
                tamil.setBackgroundResource(R.drawable.greyborder);
                marathi.setBackgroundResource(R.drawable.greyborder);
                telgu.setBackgroundResource(R.drawable.greyborder);
                urdu.setBackgroundResource(R.drawable.greyborder);
                mandrain.setBackgroundResource(R.drawable.greyborder);


                language="2";

                PhotoApp.getInstance().getstoreSharedpref().storeSharedValue("language",language);
                getlang= PhotoApp.getInstance().getstoreSharedpref().getSharedValue("language");
                PhotoApp.getInstance().createLog("SELECTED LANGUAGE "+ getlang);
                langfn();

            }
        });
        hinglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                hindi.setBackgroundResource(R.drawable.greyborder);
                english.setBackgroundResource(R.drawable.greyborder);
                hinglish.setBackgroundResource(R.drawable.purpleborder);
                bengali.setBackgroundResource(R.drawable.greyborder);
                gujrati.setBackgroundResource(R.drawable.greyborder);
                kannada.setBackgroundResource(R.drawable.greyborder);
                tamil.setBackgroundResource(R.drawable.greyborder);
                marathi.setBackgroundResource(R.drawable.greyborder);
                telgu.setBackgroundResource(R.drawable.greyborder);
                urdu.setBackgroundResource(R.drawable.greyborder);
                mandrain.setBackgroundResource(R.drawable.greyborder);


                language="3";

                PhotoApp.getInstance().getstoreSharedpref().storeSharedValue("language",language);
                getlang= PhotoApp.getInstance().getstoreSharedpref().getSharedValue("language");
                PhotoApp.getInstance().createLog("SELECTED LANGUAGE "+ getlang);
                langfn();

            }
        });
        bengali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                hindi.setBackgroundResource(R.drawable.greyborder);
                english.setBackgroundResource(R.drawable.greyborder);
                hinglish.setBackgroundResource(R.drawable.greyborder);
                bengali.setBackgroundResource(R.drawable.purpleborder);
                gujrati.setBackgroundResource(R.drawable.greyborder);
                kannada.setBackgroundResource(R.drawable.greyborder);
                tamil.setBackgroundResource(R.drawable.greyborder);
                marathi.setBackgroundResource(R.drawable.greyborder);
                telgu.setBackgroundResource(R.drawable.greyborder);
                urdu.setBackgroundResource(R.drawable.greyborder);
                mandrain.setBackgroundResource(R.drawable.greyborder);


                language="4";

                PhotoApp.getInstance().getstoreSharedpref().storeSharedValue("language",language);
                getlang= PhotoApp.getInstance().getstoreSharedpref().getSharedValue("language");
                PhotoApp.getInstance().createLog("SELECTED LANGUAGE "+ getlang);
                langfn();

            }
        });
        gujrati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                hindi.setBackgroundResource(R.drawable.greyborder);
                english.setBackgroundResource(R.drawable.greyborder);
                hinglish.setBackgroundResource(R.drawable.greyborder);
                bengali.setBackgroundResource(R.drawable.greyborder);
                gujrati.setBackgroundResource(R.drawable.purpleborder);
                kannada.setBackgroundResource(R.drawable.greyborder);
                tamil.setBackgroundResource(R.drawable.greyborder);
                marathi.setBackgroundResource(R.drawable.greyborder);
                telgu.setBackgroundResource(R.drawable.greyborder);
                urdu.setBackgroundResource(R.drawable.greyborder);
                mandrain.setBackgroundResource(R.drawable.greyborder);


                language="5";

                PhotoApp.getInstance().getstoreSharedpref().storeSharedValue("language",language);
                getlang= PhotoApp.getInstance().getstoreSharedpref().getSharedValue("language");
                PhotoApp.getInstance().createLog("SELECTED LANGUAGE "+ getlang);
                langfn();

            }
        });
        kannada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                hindi.setBackgroundResource(R.drawable.greyborder);
                english.setBackgroundResource(R.drawable.greyborder);
                hinglish.setBackgroundResource(R.drawable.greyborder);
                bengali.setBackgroundResource(R.drawable.greyborder);
                gujrati.setBackgroundResource(R.drawable.greyborder);
                kannada.setBackgroundResource(R.drawable.purpleborder);
                tamil.setBackgroundResource(R.drawable.greyborder);
                marathi.setBackgroundResource(R.drawable.greyborder);
                telgu.setBackgroundResource(R.drawable.greyborder);
                urdu.setBackgroundResource(R.drawable.greyborder);
                mandrain.setBackgroundResource(R.drawable.greyborder);


                language="6";

                PhotoApp.getInstance().getstoreSharedpref().storeSharedValue("language",language);
                getlang= PhotoApp.getInstance().getstoreSharedpref().getSharedValue("language");
                PhotoApp.getInstance().createLog("SELECTED LANGUAGE "+ getlang);
                langfn();

            }
        });
        tamil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                hindi.setBackgroundResource(R.drawable.greyborder);
                english.setBackgroundResource(R.drawable.greyborder);
                hinglish.setBackgroundResource(R.drawable.greyborder);
                bengali.setBackgroundResource(R.drawable.greyborder);
                gujrati.setBackgroundResource(R.drawable.greyborder);
                kannada.setBackgroundResource(R.drawable.greyborder);
                tamil.setBackgroundResource(R.drawable.purpleborder);
                marathi.setBackgroundResource(R.drawable.greyborder);
                telgu.setBackgroundResource(R.drawable.greyborder);
                urdu.setBackgroundResource(R.drawable.greyborder);
                mandrain.setBackgroundResource(R.drawable.greyborder);


                language="7";

                PhotoApp.getInstance().getstoreSharedpref().storeSharedValue("language",language);
                getlang= PhotoApp.getInstance().getstoreSharedpref().getSharedValue("language");
                PhotoApp.getInstance().createLog("SELECTED LANGUAGE "+ getlang);
                langfn();

            }
        });
        marathi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                hindi.setBackgroundResource(R.drawable.greyborder);
                english.setBackgroundResource(R.drawable.greyborder);
                hinglish.setBackgroundResource(R.drawable.greyborder);
                bengali.setBackgroundResource(R.drawable.greyborder);
                gujrati.setBackgroundResource(R.drawable.greyborder);
                kannada.setBackgroundResource(R.drawable.greyborder);
                tamil.setBackgroundResource(R.drawable.greyborder);
                marathi.setBackgroundResource(R.drawable.purpleborder);
                telgu.setBackgroundResource(R.drawable.greyborder);
                urdu.setBackgroundResource(R.drawable.greyborder);
                mandrain.setBackgroundResource(R.drawable.greyborder);

                language="8";

                PhotoApp.getInstance().getstoreSharedpref().storeSharedValue("language",language);
                getlang= PhotoApp.getInstance().getstoreSharedpref().getSharedValue("language");
                PhotoApp.getInstance().createLog("SELECTED LANGUAGE "+ getlang);

                langfn();

            }
        });
        telgu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                hindi.setBackgroundResource(R.drawable.greyborder);
                english.setBackgroundResource(R.drawable.greyborder);
                hinglish.setBackgroundResource(R.drawable.greyborder);
                bengali.setBackgroundResource(R.drawable.greyborder);
                gujrati.setBackgroundResource(R.drawable.greyborder);
                kannada.setBackgroundResource(R.drawable.greyborder);
                tamil.setBackgroundResource(R.drawable.greyborder);
                marathi.setBackgroundResource(R.drawable.greyborder);
                telgu.setBackgroundResource(R.drawable.purpleborder);
                urdu.setBackgroundResource(R.drawable.greyborder);
                mandrain.setBackgroundResource(R.drawable.greyborder);


                language="9";

                PhotoApp.getInstance().getstoreSharedpref().storeSharedValue("language",language);
                getlang= PhotoApp.getInstance().getstoreSharedpref().getSharedValue("language");
                PhotoApp.getInstance().createLog("SELECTED LANGUAGE "+ getlang);
                langfn();

            }
        });
        mandrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                hindi.setBackgroundResource(R.drawable.greyborder);
                english.setBackgroundResource(R.drawable.greyborder);
                hinglish.setBackgroundResource(R.drawable.greyborder);
                bengali.setBackgroundResource(R.drawable.greyborder);
                gujrati.setBackgroundResource(R.drawable.greyborder);
                kannada.setBackgroundResource(R.drawable.greyborder);
                tamil.setBackgroundResource(R.drawable.greyborder);
                marathi.setBackgroundResource(R.drawable.greyborder);
                telgu.setBackgroundResource(R.drawable.greyborder);
                mandrain.setBackgroundResource(R.drawable.purpleborder);
                urdu.setBackgroundResource(R.drawable.greyborder);

                language="10";

                PhotoApp.getInstance().getstoreSharedpref().storeSharedValue("language",language);
                getlang= PhotoApp.getInstance().getstoreSharedpref().getSharedValue("language");
                PhotoApp.getInstance().createLog("SELECTED LANGUAGE "+ getlang);
                langfn();

            }
        });
        urdu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                hindi.setBackgroundResource(R.drawable.greyborder);
                english.setBackgroundResource(R.drawable.greyborder);
                hinglish.setBackgroundResource(R.drawable.greyborder);
                bengali.setBackgroundResource(R.drawable.greyborder);
                gujrati.setBackgroundResource(R.drawable.greyborder);
                kannada.setBackgroundResource(R.drawable.greyborder);
                tamil.setBackgroundResource(R.drawable.greyborder);
                marathi.setBackgroundResource(R.drawable.greyborder);
                telgu.setBackgroundResource(R.drawable.greyborder);
                mandrain.setBackgroundResource(R.drawable.greyborder);
                urdu.setBackgroundResource(R.drawable.purpleborder);

                language="11";

                PhotoApp.getInstance().getstoreSharedpref().storeSharedValue("language",language);
                getlang= PhotoApp.getInstance().getstoreSharedpref().getSharedValue("language");
                PhotoApp.getInstance().createLog("SELECTED LANGUAGE "+ getlang);
                langfn();

            }
        });

    }

    private void langfn() {
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // CallNextActivity();

                new Task_Lang().execute();
            }
        });

    }

    public class Task_Lang extends AsyncTask<Void, Void, Void> {

        String statusCode;
        String messagelang;
        String langid;
        // private TransparentProgressDialog dialog;
        String responsemsg = "";
        private ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           /* dialog = new TransparentProgressDialog(_mContext, R.drawable.loader);
            dialog.show();*/

            progressDialog = new ProgressDialog(SelectLanguge.this);
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

                jobj.put("language",getlang);
                ServiceHandler sh = new ServiceHandler();
                PhotoApp.getInstance().createLog("see lang"+ getlang);


                PhotoApp.getInstance().createLog("=====jobj+url======"+jobj+"========"+url);

                String jsonStr = sh.postDataHeader(url, jobj);



                if (jsonStr != null) {

                    //{"details":{"status":"post can not be deleted."},"statusCode":"200"}
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    PhotoApp.getInstance().createLog("jsonObj RESPONSE "+jsonObj);

                    statusCode = jsonObj.getString("status");

                    PhotoApp.getInstance().createLog("STATUS CODE  "+statusCode);
                    if (statusCode.equalsIgnoreCase("200")) {

                        responsemsg = "200";

                        messagelang = jsonObj.getString("message");
                        PhotoApp.getInstance().createLog("messagelang "+messagelang);

                        langid = jsonObj.getString("language");
                        PhotoApp.getInstance().createLog("langid "+langid);

                        PhotoApp.getInstance().getstoreSharedpref().storeSharedValue(Cons.p_language,langid);



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
                    PhotoApp.getInstance().getstoreSharedpref().storeSharedValue(Cons.p_language,langid);

                    Toast.makeText(SelectLanguge.this, messagelang,
                            Toast.LENGTH_LONG).show();

                    CallNextActivity();

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


    public void CallNextActivity()
    {


        if (PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.sendname).equals("NA")) {

            Intent homeIntent = new Intent(SelectLanguge.this, WriteYourName.class);
            startActivity(homeIntent);
            finish();


        } else {
            Intent homeIntent = new Intent(SelectLanguge.this, HomeActivity.class);
            startActivity(homeIntent);
            finish();

        }
    }


    public class Task_GetLanguage extends AsyncTask<Void, Void, Void> {

        String statusCode;
        // private TransparentProgressDialog dialog;
        String responsemsg = "";
        private ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           /* dialog = new TransparentProgressDialog(_mContext, R.drawable.loader);
            dialog.show();*/

            progressDialog = new ProgressDialog(SelectLanguge.this);
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

                PhotoApp.getInstance().createLog("=====jobj+url======"+jobj+"========"+getlangurl);

                String jsonStr = sh.postDataArrayGet(getlangurl, jobj);



                if (jsonStr != null) {
                    //{"details":{"status":"post can not be deleted."},"statusCode":"200"}
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    PhotoApp.getInstance().createLog("GET LANGUAGE jsonObj RESPONSE "+jsonObj);

                    statusCode = jsonObj.getString("status");

                    PhotoApp.getInstance().createLog("LANG STATUS CODE  "+statusCode);
                    if (statusCode.equalsIgnoreCase("200")) {

                        responsemsg = "200";

                        JSONArray type_j = jsonObj.getJSONArray("languages");
                        val = new GetLangModalClass();

                        for(int i=0;i<type_j.length();i++)
                        {


                            JSONObject j_homepage = type_j.getJSONObject(i);
                            Languages val_lang = new Languages();
                            val_lang.setId(j_homepage.getString("id"));
                            val_lang.setLanguage(j_homepage.getString("language"));
                            val_lang.setImage(j_homepage.getString("image"));


                            val.lang.add(val_lang);




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
             hinditext.setText(val.lang.get(0).getLanguage());
                englishtext.setText(val.lang.get(1).getLanguage());
                hinglishtext.setText(val.lang.get(2).getLanguage());
                bengalitext.setText(val.lang.get(3).getLanguage());
                gujratitext.setText(val.lang.get(4).getLanguage());
                kannadatext .setText(val.lang.get(5).getLanguage());
                tamiltext .setText(val.lang.get(6).getLanguage());
                marathitext.setText(val.lang.get(7).getLanguage());
                telgutext.setText(val.lang.get(8).getLanguage());
                mandraintext.setText(val.lang.get(9).getLanguage());
                urdutext.setText(val.lang.get(10).getLanguage());





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
    ArrayList<GetLangModalClass> getLogoModalClasses = new ArrayList<GetLangModalClass>();

    @Override
    public void onBackPressed() {
        if (!PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.p_language).equals("NA")) {

            Intent homeIntent = new Intent(SelectLanguge.this, HomeActivity.class);
            startActivity(homeIntent);
            finish();


        }
    }
}
