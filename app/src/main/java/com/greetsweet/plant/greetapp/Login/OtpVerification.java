package com.greetsweet.plant.greetapp.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.greetsweet.plant.Handler.ServiceHandler;
import com.greetsweet.plant.PhotoApp;
import com.greetsweet.plant.R;
import com.greetsweet.plant.greetapp.WriteName.NewLanguageActivity;
import com.greetsweet.plant.greetapp.WriteName.WriteYourName;

import org.json.JSONObject;

public class OtpVerification extends AppCompatActivity {
    EditText otp;
    Button submit;
TextView resendotp;
    String apitoken, sessionid;
 TextView changeno, othermeth;

 String getcontactno;


    String url ="http://13.232.178.62/api/user/login/verify/";
    String resendurl="http://13.232.178.62/api/user/resend/otp/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);

        sessionid= PhotoApp.getInstance().getstoreSharedpref().getSharedValue("session");
        PhotoApp.getInstance().createLog("PRINT SESSION"+ sessionid);

        getcontactno= PhotoApp.getInstance().getstoreSharedpref().getSharedValue("contactno");
        PhotoApp.getInstance().createLog("getcontactno"+ getcontactno);

       changeno=(TextView)findViewById(R.id.textView4);
       changeno.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent homeIntent=new Intent(OtpVerification.this,LoginActivity.class);
               startActivity(homeIntent);
               finish();

           }
       });
        othermeth=(TextView)findViewById(R.id.textView5);
        othermeth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeIntent=new Intent(OtpVerification.this,LoginActivity.class);
                startActivity(homeIntent);
                finish();

            }
        });



        otp=(EditText)findViewById(R.id.editText2);
        submit=(Button)findViewById(R.id.button5);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Task_Otp().execute();

            }
        });


        resendotp=(TextView)findViewById(R.id.textView3);
        resendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
new  Task_ResendOtp().execute();
            }
        });
    }

    public class Task_Otp extends AsyncTask<Void, Void, Void> {

        String statusCode;
        // private TransparentProgressDialog dialog;
        String responsemsg = "";
        private ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           /* dialog = new TransparentProgressDialog(_mContext, R.drawable.loader);
            dialog.show();*/

            progressDialog = new ProgressDialog(OtpVerification.this);
            progressDialog.setCancelable(false); // set cancelable to false
            progressDialog.setMessage("Please Wait"); // set message
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0)

        {

            try {


                JSONObject jobj = new JSONObject();
                jobj.put("session_id",sessionid);
                jobj.put("otp",otp.getText().toString().trim());
                ServiceHandler sh = new ServiceHandler();



                PhotoApp.getInstance().createLog("=====jobj+url======"+jobj+"========"+url);

                String jsonStr = sh.postData(url, jobj);



                if (jsonStr != null) {

                    //{"details":{"status":"post can not be deleted."},"statusCode":"200"}
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    statusCode = jsonObj.getString("status");
                    if (statusCode.equalsIgnoreCase("200")) {

                        responsemsg = "200";

                        apitoken=jsonObj.getString("api_token");
                        PhotoApp.getInstance().getstoreSharedpref().storeSharedValue("apitoken",apitoken);
                        PhotoApp.getInstance().createLog("apitoken"+ apitoken);

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

                    Toast.makeText(OtpVerification.this, "You are logged in successfully",
                            Toast.LENGTH_LONG).show();

                    Intent homeIntent=new Intent(OtpVerification.this,NewLanguageActivity.class);
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

////////resend otp
    public class Task_ResendOtp extends AsyncTask<Void, Void, Void> {

    String statusCode;
    // private TransparentProgressDialog dialog;
    String responsemsg = "";
    private ProgressDialog progressDialog;
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
           /* dialog = new TransparentProgressDialog(_mContext, R.drawable.loader);
            dialog.show();*/

        progressDialog = new ProgressDialog(OtpVerification.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show();
    }

    @Override
    protected Void doInBackground(Void... arg0)

    {

        try {


            JSONObject jobj = new JSONObject();
            jobj.put("session_id",sessionid);
            jobj.put("mobile",getcontactno);
            ServiceHandler sh = new ServiceHandler();



            PhotoApp.getInstance().createLog("=====jobj+url======"+jobj+"========"+resendurl);

            String jsonStr = sh.postData(resendurl, jobj);



            if (jsonStr != null) {

                //{"details":{"status":"post can not be deleted."},"statusCode":"200"}
                JSONObject jsonObj = new JSONObject(jsonStr);

                PhotoApp.getInstance().createLog("Resend RESPONSE "+jsonObj);

                statusCode = jsonObj.getString("status");
                if (statusCode.equalsIgnoreCase("200")) {

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

                Toast.makeText(OtpVerification.this, "Resend otp successful",
                        Toast.LENGTH_LONG).show();



            } else {

            }

        } catch (Exception e) {
        }


    }


}




}
