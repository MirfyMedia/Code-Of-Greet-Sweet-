package com.greetsweet.plant.greetapp.Splash;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.firebase.analytics.FirebaseAnalytics;
import com.greetsweet.plant.GridSpacing.GridSpacingItemDecoration;
import com.greetsweet.plant.Handler.ServiceHandler;
import com.greetsweet.plant.PhotoApp;
import com.greetsweet.plant.R;
import com.greetsweet.plant.greetapp.AdapterClasses.BannerAdapter2;
import com.greetsweet.plant.greetapp.AdapterClasses.UserGreetingAdapterClass;
import com.greetsweet.plant.greetapp.HomeActivity;
import com.greetsweet.plant.greetapp.ModalClasses.Intro_screen;
import com.greetsweet.plant.greetapp.ModalClasses.SplashModalClass;
import com.greetsweet.plant.greetapp.ModalClasses.UserGreetingModalClass;
import com.greetsweet.plant.greetapp.ModalClasses.UserGreetings;
import com.greetsweet.plant.greetapp.StartPage;
import com.greetsweet.plant.greetapp.UserGreets.UserGreetingsActivity;
import com.switcher.AutoSwitchView;
import com.switcher.builder.CarouselStrategyBuilder;
import com.switcher.builder.DirectionMode;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity {
    SplashModalClass val;
    String splashurl ="http://13.232.178.62/api/greeting/intro/";
    public ArrayList<Intro_screen> splash;
    ImageView skip;

    private FirebaseAnalytics mFirebaseAnalytics;
    private AutoSwitchView mAswBanner;
    private BannerAdapter2 mBannerAdapter = new BannerAdapter2();
    Intro_screen val_splash;
    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "id");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "name");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        mFirebaseAnalytics.setCurrentScreen(this,SplashActivity.class.getSimpleName(),SplashActivity.class.getSimpleName());

//        Log.i(TAG, "Setting screen name: " + name);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        PhotoApp application = (PhotoApp) getApplication();

        if(!(PhotoApp.getInstance().getstoreSharedpref().getSharedValue("apitoken").equalsIgnoreCase("NA")))
        {
            Intent i=new Intent(SplashActivity.this,HomeActivity.class) ;
            startActivity(i);
            finish();
        }
        skip=(ImageView) findViewById(R.id.textView73);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent homeIntent = new Intent(SplashActivity.this, StartPage.class);
                startActivity(homeIntent);
                finish();

            }
        });
        mAswBanner = (AutoSwitchView) findViewById(R.id.start_banner);
        mAswBanner.setAdapter(mBannerAdapter);
        mAswBanner.setSwitchStrategy(
                new CarouselStrategyBuilder().
                        setAnimDuration(900).
                        setInterpolator(new AccelerateDecelerateInterpolator()).
                        setMode(DirectionMode.right2Left).
                        build()
        );
        new Task_Splash().execute();
    }
    public class Task_Splash extends AsyncTask<Void, Void, Void> {
        String statusCode;
        // private TransparentProgressDialog dialog;
        String responsemsg = "";
        private ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           /* dialog = new TransparentProgressDialog(_mContext, R.drawable.loader);
            dialog.show();*/
            progressDialog = new ProgressDialog(SplashActivity.this);
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
                PhotoApp.getInstance().createLog("=====jobj+url======"+jobj+"========"+splashurl);
                String jsonStr = sh.postDataArrayGet(splashurl, jobj);
                if (jsonStr != null) {
                    //{"details":{"status":"post can not be deleted."},"statusCode":"200"}
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    PhotoApp.getInstance().createLog("Splash jsonObj RESPONSE "+jsonObj);
                    statusCode = jsonObj.getString("status");
                    PhotoApp.getInstance().createLog("Splash STATUS CODE  "+statusCode);
                    if (statusCode.equalsIgnoreCase("200")) {
                        responsemsg = "200";
                        JSONArray type_j = jsonObj.getJSONArray("intro_screen");
                        val = new SplashModalClass();
                        splash= new ArrayList<>();
                        for(int i=0;i<type_j.length();i++)
                        {
                            JSONObject j_homepage = type_j.getJSONObject(i);
                             val_splash = new Intro_screen();
                            val_splash.setImage(j_homepage.getString("image"));
                            val_splash.setId(j_homepage.getString("id"));


                            val.splash.add(val_splash);
                            splash.add(val_splash);



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




                    mBannerAdapter.updateImageSlider(splash);


                mBannerAdapter.updateItem(mAswBanner,0);





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
    ArrayList<SplashModalClass> getLogoModalClasses = new ArrayList<SplashModalClass>();
    ArrayList<Intro_screen> getIntroscreenModalClasses = new ArrayList<Intro_screen>();

}
