package com.greetsweet.plant.greetapp.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.auth.api.signin.GoogleSignInStatusCodes;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.greetsweet.plant.Handler.ServiceHandler;
import com.greetsweet.plant.PhotoApp;
import com.greetsweet.plant.R;
import com.greetsweet.plant.greetapp.HomeActivity;
import com.greetsweet.plant.greetapp.WriteName.NewLanguageActivity;
import com.greetsweet.plant.mylibs.utility.Cons;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("ALL")
public class LoginActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {
    Button numblog;
    String url_delete_sin_post = "http://13.232.178.62/api/user/login/";

    EditText mobileno;
    CheckBox showPasswordCheckBox;

    String sendcontactno;

    String sessionid;

    String apitoken;
    GoogleApiClient mGoogleApiClient;
    GoogleSignInOptions gso;
    String google_email, google_name, google_image;
    private static final int RC_SIGN_IN = 0;

    Button googlesignin;

    //forfb
    String hashKey;
    CallbackManager callbackManager;
    CallbackManager mCallbackManager;
    String name, email, id, birthday, gender;
    private URL profilePicture;
    Button fbbutton;

    String apiemailid, signmethod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        mCallbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_login);

        fbbutton = (Button) findViewById(R.id.button3);


        customizeSignBtn();
        setBtnClickListeners();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        buidNewGoogleApiClient();

        if (!(PhotoApp.getInstance().getstoreSharedpref().getSharedValue("apitoken").equalsIgnoreCase("NA"))) {
            Intent i = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(i);
            finish();
        }

        showPasswordCheckBox = (CheckBox) findViewById(R.id.checkBox);

        mobileno = (EditText) findViewById(R.id.editText);


        mobileno.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub


                if (mobileno.getText().toString().length() > 10) {

                    mobileno.setError("Warning: Exceeded 10 digits");
                    mobileno.requestFocus();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                // TODO Auto-generated method stub
            }
        });
        numblog = (Button) findViewById(R.id.button2);
        numblog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            /*    Intent homeIntent=new Intent(LoginActivity.this,OtpVerification.class);
                startActivity(homeIntent);
                finish();*/
                apiemailid = "";
                signmethod = "mobile";
                if (mobileno.getText().toString().length() == 0) {
                    mobileno.setError("Mobile Number not entered");
                    mobileno.requestFocus();
                } else if (!isValidContact(R.id.editText)) {
                    mobileno.setError("Invalid Format");
                } else if (!showPasswordCheckBox.isChecked()) {
                    Toast.makeText(LoginActivity.this, "First Sign in the Policy", Toast.LENGTH_LONG).show();
                } else {
                    new Task_Login_().execute();
                }
            }
        });
    }

    private boolean isValidContact(int contact) {

        String CONTACT_PATTERN = "^[2-9]{2}[0-9]{8}$";

        Pattern pattern = Pattern.compile(CONTACT_PATTERN);
        Matcher matcher = pattern.matcher(mobileno.getText().toString());
        return matcher.matches();

    }

    public void createLog(String msg) {
        Log.i("Greetings", msg);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onClick(View view) {
        if (view == googlesignin) {

            //   Toast.makeText(this, "start sign process", Toast.LENGTH_SHORT).show();
            gSignIn();

        }

        ////FB
        if (view == fbbutton) {

            try {
                PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
                for (Signature signature : info.signatures) {
                    MessageDigest md = MessageDigest.getInstance("SHA");
                    md.update(signature.toByteArray());
                    hashKey = new String(Base64.encode(md.digest(), 0));
                    Log.i("hashkey", "printHashKey() Hash Key: " + hashKey);
                    PhotoApp.getInstance().createLog("hashkeyhashkey" + hashKey);

                }
            } catch (NoSuchAlgorithmException e) {
                Log.e("TAG", "printHashKey()", e);
            } catch (Exception e) {
                Log.e("TAG", "printHashKey()", e);
            }
//            LoginManager.getInstance().logInWithReadPermissions(
//                    this,
//                    Arrays.asList("user_photos", "email","user_birthday", "public_profile")
//            );
            LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("email", "public_profile"));
// LoginManager.getInstance().logInWithPublishPermissions(activity, Arrays.asList("publish_actions"));
            LoginManager.getInstance().registerCallback(mCallbackManager,
                    new FacebookCallback<LoginResult>() {
                        @Override
                        public void onSuccess(LoginResult loginResult) {
                            GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                                @Override
                                public void onCompleted(JSONObject object, GraphResponse response) {
                                    Log.e("a", object.toString());
                                    Log.e("av", response.toString());

                                    try {
                                        id = object.getString("id");
                                        profilePicture = new URL("https://graph.facebook.com/" + id + "/picture?width=500&height=500");
                                        if (object.has("first_name"))
                                            name = object.getString("first_name");
                                        if (object.has("last_name"))
                                            name = object.getString("last_name");
                                        //  PhotoApp.getInstance().getstoreSharedpref().storeSharedValue(Cons.facebook_name, name);

                                        if (object.has("email"))
                                            email = object.getString("email");
                                        //   PhotoApp.getInstance().getstoreSharedpref().storeSharedValue(Cons.facebook_email, email);
                                        PhotoApp.getInstance().createLog("FACEBOOK EMAIL " + email);


                                        if (object.has("birthday"))
                                            birthday = object.getString("birthday");
                                        if (object.has("gender"))
                                            gender = object.getString("gender");
                                        //  App.getInstance().createLog("google values"+userId+firstName+lastName+email);

                                        apiemailid = email;
                                        signmethod = "facebook";

                                        new TaskGoogle_Login_().execute();

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    } catch (MalformedURLException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            Bundle parameters = new Bundle();
                            parameters.putString("fields", "id, first_name, last_name, email, birthday, gender , location");
                            request.setParameters(parameters);
                            request.executeAsync();
                            // App code
                        }

                        @Override
                        public void onCancel() {
                            // App code
                        }

                        @Override
                        public void onError(FacebookException exception) {
                            // App code
                        }
                    });
        }
    }

    private void gSignIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
// progress_dialog.show();
    }


    public class Task_Login_ extends AsyncTask<Void, Void, Void> {

        String statusCode;
        // private TransparentProgressDialog dialog;
        String responsemsg = "";
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           /* dialog = new TransparentProgressDialog(_mContext, R.drawable.loader);
            dialog.show();*/

            progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setCancelable(false); // set cancelable to false
            progressDialog.setMessage("Please Wait"); // set message
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            try {
                sendcontactno = mobileno.getText().toString().trim();
                PhotoApp.getInstance().getstoreSharedpref().storeSharedValue("contactno", sendcontactno);
                PhotoApp.getInstance().createLog("sendcontactno" + sendcontactno);
                PhotoApp.getInstance().createLog("apiemailid " + apiemailid);
                PhotoApp.getInstance().createLog("signmethod " + signmethod);


                JSONObject jobj = new JSONObject();
                jobj.put("mobile", sendcontactno);
                jobj.put("email", apiemailid);
                jobj.put("login_with", signmethod);


                ServiceHandler sh = new ServiceHandler();


                createLog("=====jobj+url======" + jobj + "========" + url_delete_sin_post);

                String jsonStr = sh.postData(url_delete_sin_post, jobj);

                createLog("=====jsonStr======" + jsonStr);

                if (jsonStr != null) {

                    //{"details":{"status":"post can not be deleted."},"statusCode":"200"}
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    statusCode = jsonObj.getString("status");
                    if (statusCode.equalsIgnoreCase("200")) {

                        responsemsg = "200";

                        sessionid = jsonObj.getString("session_id");
                        PhotoApp.getInstance().getstoreSharedpref().storeSharedValue("session", sessionid);
                        PhotoApp.getInstance().createLog("SESSION RESPONSE" + sessionid);

                        createLog("SESSION " + sessionid);


                    } else
                        responsemsg = "No response";


                }


            } catch (Exception e) {
                createLog("=====jsonStr======" + e.getMessage());
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

                    Toast.makeText(LoginActivity.this, "OTP sent successfully.",
                            Toast.LENGTH_LONG).show();

                    Intent homeIntent = new Intent(LoginActivity.this, OtpVerification.class);
                    startActivity(homeIntent);
                    finish();

                } else {
                    Toast.makeText(LoginActivity.this, "Time Out...Please Wait",
                            Toast.LENGTH_LONG).show();

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

    ///////google sign in work/////
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    private void buidNewGoogleApiClient() {

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        // Build a GoogleSignInClient with the options specified by gso.

    }

    private void customizeSignBtn() {

        googlesignin = (Button) findViewById(R.id.button4);
       /* signIn_btn.setSize(SignInButton.SIZE_STANDARD);
        signIn_btn.setScopes(gso.getScopeArray());
*/
    }

    private void setBtnClickListeners() {
        // Button listeners
        googlesignin.setOnClickListener(this);
        fbbutton.setOnClickListener(this);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
/*
        if (requestCode == RC_SIGN_IN) {
            if (resultCode != RESULT_OK) {
                PhotoApp.getInstance().createLog("REQUEST CODE "+requestCode);
                PhotoApp.getInstance().createLog("Result CODE "+resultCode);

            }
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            getSignInResult(result);
        }
*/

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }


    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.i("rresult", "handleSignInResult:" + result.isSuccess());

        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

            //   Log.i(TAG, "display name: " + acct.getDisplayName());

            String personName = acct.getDisplayName();
            //  String personPhotoUrl = acct.getPhotoUrl().toString();
            String email = acct.getEmail();

        /*    Log.i(TAG, "Name: " + personName + ", email: " + email
                    + ", Image: " + personPhotoUrl);*/

            google_email = acct.getEmail();
            google_name = acct.getDisplayName();
            google_image = String.valueOf(acct.getPhotoUrl());

            apiemailid = google_email;
            signmethod = "google";


            new TaskGoogle_Login_().execute();

        } else {
            // Signed out, show unauthenticated UI.

            //  updateUI(false);
        }
    }

    private void getSignInResult(GoogleSignInResult result) {
        GoogleSignInStatusCodes.getStatusCodeString(result.getStatus().getStatusCode());


        if (result.isSuccess()) {

            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            //  Toast.makeText(this, "email: " + acct.getEmail(), Toast.LENGTH_SHORT).show();
            google_email = acct.getEmail();
            google_name = acct.getDisplayName();
            google_image = String.valueOf(acct.getPhotoUrl());

            apiemailid = google_email;
            signmethod = "google";


            new TaskGoogle_Login_().execute();


        } else {
            // Signed out, show unauthenticated UI.
            try {


            } catch (Exception e) {
                Toast.makeText(this, "Exception: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }

            //updateUI(false);
        }

    }


    public class TaskGoogle_Login_ extends AsyncTask<Void, Void, Void> {

        String statusCode;
        // private TransparentProgressDialog dialog;
        String responsemsg = "";
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           /* dialog = new TransparentProgressDialog(_mContext, R.drawable.loader);
            dialog.show();*/

            progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setCancelable(false); // set cancelable to false
            progressDialog.setMessage("Please Wait"); // set message
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            try {
                sendcontactno = mobileno.getText().toString().trim();
                PhotoApp.getInstance().getstoreSharedpref().storeSharedValue("contactno", sendcontactno);
                PhotoApp.getInstance().createLog("sendcontactno" + sendcontactno);
                PhotoApp.getInstance().createLog("apiemailid " + apiemailid);
                PhotoApp.getInstance().createLog("signmethod " + signmethod);


                JSONObject jobj = new JSONObject();
                jobj.put("mobile", "");
                jobj.put("email", apiemailid);
                jobj.put("login_with", signmethod);


                ServiceHandler sh = new ServiceHandler();


                createLog("=====jobj+url======" + jobj + "========" + url_delete_sin_post);

                String jsonStr = sh.postData(url_delete_sin_post, jobj);

                createLog("=====jsonStr======" + jsonStr);

                if (jsonStr != null) {

                    //{"details":{"status":"post can not be deleted."},"statusCode":"200"}
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    statusCode = jsonObj.getString("status");
                    if (statusCode.equalsIgnoreCase("200")) {

                        responsemsg = "200";

                        apitoken = jsonObj.getString("api_token");

                        PhotoApp.getInstance().getstoreSharedpref().storeSharedValue("apitoken", apitoken);
                        PhotoApp.getInstance().createLog("apitoken RESPONSE" + apitoken);

                        createLog("SESSION " + sessionid);


                    } else
                        responsemsg = "No response";


                }


            } catch (Exception e) {
                createLog("=====jsonStr======" + e.getMessage());
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

                    Toast.makeText(LoginActivity.this, "You are logged in successfully",
                            Toast.LENGTH_LONG).show();

                    Intent homeIntent = new Intent(LoginActivity.this, NewLanguageActivity.class);
                    startActivity(homeIntent);
                    finish();

                } else {
                    Toast.makeText(LoginActivity.this, "Time Out...Please Wait",
                            Toast.LENGTH_LONG).show();

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

}
