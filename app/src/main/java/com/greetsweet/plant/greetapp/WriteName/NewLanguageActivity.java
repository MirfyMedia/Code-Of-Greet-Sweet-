package com.greetsweet.plant.greetapp.WriteName;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.greetsweet.plant.GridSpacing.EqualSpacingItemDecoration;
import com.greetsweet.plant.GridSpacing.GridSpacingItemDecoration;
import com.greetsweet.plant.Handler.ServiceHandler;
import com.greetsweet.plant.PhotoApp;
import com.greetsweet.plant.R;
import com.greetsweet.plant.greetapp.AdapterClasses.GetLangAdapterClass;
import com.greetsweet.plant.greetapp.AdapterClasses.GridAdapterClass;
import com.greetsweet.plant.greetapp.HomeActivity;
import com.greetsweet.plant.greetapp.Login.SelectLanguge;
import com.greetsweet.plant.greetapp.ModalClasses.GetLangModalClass;
import com.greetsweet.plant.greetapp.ModalClasses.Languages;
import com.greetsweet.plant.greetapp.WriteName.WriteWebsiteNameActivity;
import com.greetsweet.plant.greetapp.WriteName.WriteYourName;
import com.greetsweet.plant.mylibs.utility.Cons;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class NewLanguageActivity extends AppCompatActivity {

    ImageView hindi, english,hinglish, bengali, gujrati, kannada,tamil, marathi,telgu,urdu;

    String language, storelang, getlang;
    Button proceed;

    String url ="http://13.232.178.62/api/user/language/";

    String getlangurl="http://13.232.178.62/api/greeting/language/all/";
    GetLangModalClass val;
    private GetLangAdapterClass adapter;
    RecyclerView langrecycler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_language);

        langrecycler=(RecyclerView)findViewById(R.id.langrecycler);

        proceed=(Button) findViewById(R.id.button13);
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if( PhotoApp.getInstance().getstoreSharedpref().getSharedValue("language").equalsIgnoreCase("NA"))

                {

                    PhotoApp.getInstance().ShowToast(getResources().getString(R.string.inputlang));

                }

                else{

                    new Task_Lang().execute();
                }
            }
        });

new Task_GetLanguage().execute();
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

            progressDialog = new ProgressDialog(NewLanguageActivity.this);
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

                GridLayoutManager linearLayoutManager=new GridLayoutManager(NewLanguageActivity.this,4);
                langrecycler.setLayoutManager(linearLayoutManager);


                int spanCount = 4; // 3 columns
                int spacing = 10; // 50px
                boolean includeEdge = true;
                langrecycler.setHasFixedSize(true);
                adapter=new GetLangAdapterClass(val.lang,NewLanguageActivity.this);
                langrecycler.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));

                langrecycler.setAdapter(adapter);



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

            progressDialog = new ProgressDialog(NewLanguageActivity.this);
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

                jobj.put("language",PhotoApp.getInstance().getstoreSharedpref().getSharedValue("language"));
                ServiceHandler sh = new ServiceHandler();
                PhotoApp.getInstance().createLog("see lang"+ PhotoApp.getInstance().getstoreSharedpref().getSharedValue("language"));


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

                    Toast.makeText(NewLanguageActivity.this, messagelang,
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

            Intent homeIntent = new Intent(NewLanguageActivity.this, WriteYourName.class);
            startActivity(homeIntent);
            finish();


        } else {
            Intent homeIntent = new Intent(NewLanguageActivity.this, HomeActivity.class);
            startActivity(homeIntent);
            finish();

        }
    }

    @Override
    public void onBackPressed() {

            Intent homeIntent = new Intent(NewLanguageActivity.this, HomeActivity.class);
            startActivity(homeIntent);
            finish();



    }

}
