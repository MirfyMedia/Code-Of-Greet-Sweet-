package com.greetsweet.plant.greetapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.JsonObject;
import com.greetsweet.plant.APIclient.ApiClient;
import com.greetsweet.plant.Handler.ServiceHandler;
import com.greetsweet.plant.Interface.ApiInterface;
import com.greetsweet.plant.Interface.OrderCreatePojo;
import com.greetsweet.plant.PhotoApp;
import com.greetsweet.plant.R;
import com.greetsweet.plant.greetapp.AdapterClasses.NewAdapterClass;
import com.greetsweet.plant.greetapp.EditorFrags.AddLogoActivity;
import com.greetsweet.plant.greetapp.Fragments.CustomFragment;
import com.greetsweet.plant.greetapp.Fragments.GalleryFragment;
import com.greetsweet.plant.greetapp.Fragments.GiftsFragment;
import com.greetsweet.plant.greetapp.Fragments.GoproFragment;
import com.greetsweet.plant.greetapp.Fragments.HomeFragment1;
import com.greetsweet.plant.greetapp.Fragments.More.PlanFragment;
import com.greetsweet.plant.greetapp.Login.LoginActivity;

import com.greetsweet.plant.greetapp.Profile.ProfileActivity;

import com.greetsweet.plant.greetapp.SearchPack.SearchFragment;
import com.greetsweet.plant.greetapp.UserGreets.UserGreetingsActivity;
import com.greetsweet.plant.greetapp.WriteName.NewLanguageActivity;
import com.greetsweet.plant.greetapp.WriteName.WriteWebsiteNameActivity;
import com.greetsweet.plant.greetapp.WriteName.WriteYourName;
import com.greetsweet.plant.greetapp.helper.BottomNavigationBehavior;
import com.greetsweet.plant.mylibs.utility.Cons;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ActionBar toolbar;
    String profileurl ="http://13.232.178.62/api/user/profile/";
    TextView textname;
    ImageView prof;
    int action_type = 0; //[->1 for website, ->2 for name]
     GoogleApiClient mGoogleApiClient;
     SearchView searchView;
    private NewAdapterClass newAdapterClass;
    android.support.v7.app.AlertDialog dialog;
    ImageView searchact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        FirebaseApp.initializeApp(HomeActivity.this);
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);



    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_shop:
                  //  toolbar.setTitle("Home");
                    fragment = new HomeFragment1();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_gifts:
                  //  toolbar.setTitle("Go Pro");

                    fragment = new GoproFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_cart:
                   // toolbar.setTitle("Custom");


                    fragment = new CustomFragment();
                    loadFragment(fragment);
                    return true;
               case R.id.navigation_profile:
                   // toolbar.setTitle("Gallery");

                    fragment = new GalleryFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.gifts:
                   // toolbar.setTitle("Gifts");

                    fragment = new GiftsFragment();
                    loadFragment(fragment);
                    return true;

            }

            return false;
        }
    };
    public void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);

        if (!isFinishing()) {
            transaction.commit();
        }
    }
    boolean doubleBackToExitPressedOnce = false;


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            if (doubleBackToExitPressedOnce) {
                finish();
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }



/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment fragment;


        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            fragment = new HomeFragment1();
            loadFragment(fragment);
        }

        else if (id == R.id.nav_gallery) {

                               updateName();


        }

        else if (id == R.id.nav_slideshow) {

            if(PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.p_user_type).equalsIgnoreCase("NA")) {
                action_type = 1;
            new Task_ProfileGet().execute();
            }


            else {
            if ( PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.p_user_type).equalsIgnoreCase(Cons.u_type_enterprise) ) {
                updateWebsiteName();
            } else {
                PhotoApp.getInstance().ShowToast(getResources().getString(R.string.authorize_enterprise_user));
            }
        }
        }

        else if (id == R.id.nav_manage) {

            if(PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.p_user_type).equalsIgnoreCase("NA")) {
                action_type = 2;
                new Task_ProfileGet().execute();
            }


            else {
                if ( PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.p_user_type).equalsIgnoreCase(Cons.u_type_enterprise) ) {
                    updateLogo();
                } else {
                    PhotoApp.getInstance().ShowToast(getResources().getString(R.string.authorize_enterprise_user));
                }
            }



        }
        else if (id == R.id.lang) {
            Intent intent= new Intent(HomeActivity.this, NewLanguageActivity.class);
            startActivity(intent);
            finish();
        }
        /*else if (id == R.id.mygall) {
            Intent intent= new Intent(HomeActivity.this, UserGreetingsActivity.class);
            startActivity(intent);
            finish();
        }*/


        else if (id == R.id.plan) {

            fragment = new PlanFragment();
            loadFragment(fragment);

        }

        else if (id == R.id.refer) {
             Comingsoonfn();

            /*Intent intent= new Intent(HomeActivity.this, CatNewsActivity.class);
            startActivity(intent);
            finish();*/
        }
        else if (id == R.id.issues) {
            Comingsoonfn();

        }
        else if (id == R.id.help) {
            Comingsoonfn();

        }
        else if (id == R.id.aboutus) {
            Comingsoonfn();

        }
        else if (id == R.id.terms) {
            Comingsoonfn();

        }


        else if (id == R.id.logout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this); //Home is name of the activity
            builder.setMessage("Do you want to exit?");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    PhotoApp.getInstance().getstoreSharedpref().getSharedValue("apitoken");
                    PhotoApp.getInstance().getstoreSharedpref().resetValues();
                    mGoogleApiClient.connect();
                    mGoogleApiClient.registerConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                        @Override
                        public void onConnected(@Nullable Bundle bundle) {

                            FirebaseAuth.getInstance().signOut();
                            if(mGoogleApiClient.isConnected()) {
                                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
                                    @Override
                                    public void onResult(@NonNull Status status) {
                                        if (status.isSuccess()) {
                                            Log.d("logout", "User Logged out");
                                            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                });
                            }
                        }

                        @Override
                        public void onConnectionSuspended(int i) {
                            Log.d("logout", "Google API Client Connection Suspended");
                        }
                    });

//                    Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
//                            new ResultCallback<Status>() {
//                                @Override
//                                public void onResult(Status status) {
//// ...
//
//                                    Intent i=new Intent(getApplicationContext(),LoginActivity.class);
//                                   // startActivity(i);
//                                    i.putExtra("finish", true);
//                                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
//                                    startActivity(i);
//                                    finish();
//
//                                }
//                            });
//
             }
         });
            builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void Comingsoonfn() {

        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.comingsoon, null);
        final ImageView close = alertLayout.findViewById(R.id.imageView44);
       close.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               dialog.dismiss();
           }
       });

        android.support.v7.app.AlertDialog.Builder alert = new android.support.v7.app.AlertDialog.Builder(this);
     //   alert.setTitle("Info");
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(false);

       dialog = alert.create();
        dialog.show();
    }

    String date_valid[] = {

            "2019-07-03",
            "2019-07-04",
            "2019-07-05",
            "2019-07-06",
            "2019-07-07",
            "2019-07-08",
            "2019-07-09",
            "2019-07-10",
            "2019-07-11",
            "2019-07-12",
            "2019-07-13",
            "2019-07-14",
            "2019-07-15",
            "2019-07-16",
            "2019-07-17",
            "2019-07-18",
            "2019-07-19",
            "2019-07-20",
            "2019-07-21",
            "2019-07-22",
            "2019-07-23",
            "2019-07-24",
            "2019-07-25",
            "2019-07-26",
            "2019-07-27",
            "2019-07-28",
            "2019-07-29",
            "2019-07-30",
            "2019-07-31",
            "2019-08-01",
            "2019-08-02",
            "2019-08-03",
            "2019-08-04"


    };



    @Override
    public void onResume() {
        super.onResume();
        PhotoApp.getInstance().createLog("onResume");

        ///AlertBox

        Toolbar toolbar2 = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar2);
        toolbar = getSupportActionBar();
        searchact=(ImageView)findViewById(R.id.searchact);
        searchact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment;
                fragment = new SearchFragment();
                loadFragment(fragment);
            }
        });





        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        //    new Task_Home().execute();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar2, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        textname=(TextView) navigationView.getHeaderView(0).findViewById(R.id.name);
        textname.setText("Hello "+PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.sendname)+"!");
        textname.setTextIsSelectable(true);


        prof=(ImageView) navigationView.getHeaderView(0).findViewById(R.id.imageView);
        prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // attaching bottom sheet behaviour - hide / show on scroll
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigation.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());

        // load the store fragment by default
        toolbar.setTitle("");

        loadFragment(new HomeFragment1());

      if(validate_Date()) {
      }
    else
        {
           alertBox();        }
    }
    private boolean validate_Date()
    {
        boolean return_value = false;
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd/MM/yyyy");
        String date=mdformat.format(calendar.getTime());
        String[] split = date.split("/");
        String datevalue = split[2]+"-"+g_TwoDigit(split[1])+"-"+g_TwoDigit(split[0]);
        loop: for(int i = 0;i<date_valid.length;i++)
        {
            if(datevalue.equalsIgnoreCase(date_valid[i]))
            {
                return_value = true;
                break loop;
        }
        }

        return return_value;

    }

    private String g_TwoDigit(String val_s)
    {
        int val_= Integer.parseInt(val_s);
        String r_Value="";
        if(val_==1||val_==2||val_==3||val_==4||val_==5||val_==6||val_==7||val_==8||val_==9)
            r_Value = "0"+val_;
        else
            r_Value = val_+"";
        return r_Value;
    }


    void finishThisActivity()
    {
        finish();
    }
    private void alertBox()
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Please contact to VNS Developer!");
        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        finishThisActivity();
                    }
                });



        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
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

            progressDialog = new ProgressDialog(HomeActivity.this);
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
            if(action_type==1) {
                if ( PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.p_user_type).equalsIgnoreCase(Cons.u_type_enterprise) ) {
                    updateWebsiteName();
                } else {
                    PhotoApp.getInstance().ShowToast(getResources().getString(R.string.authorize_enterprise_user));
                }
            }
            else if(action_type==2)
            {
                if ( PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.p_user_type).equalsIgnoreCase(Cons.u_type_enterprise) ) {
                    updateLogo();
                } else {
                    PhotoApp.getInstance().ShowToast(getResources().getString(R.string.authorize_enterprise_user));
                }
            }

                // title.setText(PhotoApp.getInstance().getstoreSharedpref().getSharedValue("title"));



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


    private void updateWebsiteName()
    {
        Intent intent= new Intent(HomeActivity.this, WriteWebsiteNameActivity.class);
        startActivity(intent);
        finish();
    }

    private void updateName()
    {
        Intent intent= new Intent(HomeActivity.this, WriteYourName.class);
        startActivity(intent);
        finish();
    }


    private void updateLogo()
    {
        Intent homeIntent = new Intent(HomeActivity.this, AddLogoActivity.class);
        startActivity(homeIntent);
        finish();
    }

    @Override
    protected void onStart() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();
        super.onStart();
    }
}
