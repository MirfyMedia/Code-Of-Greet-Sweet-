package com.greetsweet.plant.Handler;


import android.util.Log;

import com.greetsweet.plant.PhotoApp;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;


public class ServiceHandler {

    private String response = null;
    public final static int GET = 1;
    public final static int POST = 2;
    public ServiceHandler() {

    }

    /*
     * Making service call
     * @url - url to make request
     * @method - http request method
     * */
    public String makeServiceCall(String url, int method) {
        return this.makeServiceCall(url, method, null);
    }

	/*
	 * Making service call
	 * @url - url to make request
	 * @method - http request method
	 * @params - http request params
	 * */


    public String postData(String url, JSONObject obj) {
        String temp="";
        HttpParams myParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(myParams, 60000);
        HttpConnectionParams.setSoTimeout(myParams, 60000);
        HttpClient httpclient = new DefaultHttpClient(myParams );

        try {

            HttpPost httppost = new HttpPost(url.toString());
            httppost.setHeader("Content-type", "application/json");

            StringEntity se = new StringEntity(obj.toString());
            se.setContentEncoding(new BasicHeader("Content-Type", "application/json"));
            httppost.setEntity(se);

            HttpResponse response = httpclient.execute(httppost);
            temp = EntityUtils.toString(response.getEntity());

        } catch (ClientProtocolException e) {

        } catch (IOException e) {
        }
        return temp;
    }

    public  final MediaType MEDIA_TYPE_JSON_ = MediaType.get("application/json");
    public String postDataHeader(String url, JSONObject obj) {


        String apitoken= PhotoApp.getInstance().getstoreSharedpref().getSharedValue("apitoken");

        PhotoApp.getInstance().createLog("CHECK APITOKEN "+apitoken);
        String temp="";
        HttpParams myParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(myParams, 60000);
        HttpConnectionParams.setSoTimeout(myParams, 60000);
        HttpClient httpclient = new DefaultHttpClient(myParams );

        try {

            HttpPost httppost = new HttpPost(url.toString());
            httppost.setHeader("Content-type", "application/json");
            httppost.setHeader("Authorization", apitoken);

            StringEntity se = new StringEntity(obj.toString());
            se.setContentEncoding(new BasicHeader("Content-Type", "application/json"));


            httppost.setEntity(se);

            HttpResponse response = httpclient.execute(httppost);
            temp = EntityUtils.toString(response.getEntity());

        } catch (ClientProtocolException e) {

        } catch (IOException e) {
        }
        return temp;
    }

    public String postDataHeaderString(String url, String obj) {


        String apitoken= PhotoApp.getInstance().getstoreSharedpref().getSharedValue("apitoken");

        PhotoApp.getInstance().createLog("CHECK APITOKEN "+apitoken);
        String temp="";
        HttpParams myParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(myParams, 60000);
        HttpConnectionParams.setSoTimeout(myParams, 60000);
        HttpClient httpclient = new DefaultHttpClient(myParams );

        try {

            HttpPost httppost = new HttpPost(url.toString());
            httppost.setHeader("Content-type", "application/json");
            httppost.setHeader("Authorization", apitoken);

            StringEntity se = new StringEntity(obj.toString());
            se.setContentEncoding(new BasicHeader("Content-Type", "application/json"));


            httppost.setEntity(se);

            HttpResponse response = httpclient.execute(httppost);
            temp = EntityUtils.toString(response.getEntity());

        } catch (ClientProtocolException e) {

        } catch (IOException e) {
        }
        return temp;
    }

    public String postDataHeaderParam(String url, JSONObject obj) {


        String apitoken= PhotoApp.getInstance().getstoreSharedpref().getSharedValue("apitoken");

        PhotoApp.getInstance().createLog("CHECK APITOKEN "+apitoken);
        String temp="";
        HttpParams myParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(myParams, 60000);
        HttpConnectionParams.setSoTimeout(myParams, 60000);
        HttpClient httpclient = new DefaultHttpClient(myParams );

/*        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("start", "0"));
        params.add(new BasicNameValuePair("count", "5"));
        params.add(new BasicNameValuePair("downloads", "1"));
        params.add(new BasicNameValuePair("views", "0"));
        params.add(new BasicNameValuePair("newest", "1"));*/

        try {

            HttpPost httppost = new HttpPost(url.toString());
            httppost.setHeader("Content-type", "application/json");
            httppost.setHeader("Authorization", apitoken);

            StringEntity se = new StringEntity(obj.toString());
            se.setContentEncoding(new BasicHeader("Content-Type", "application/json"));


            httppost.setEntity(se);

            HttpResponse response = httpclient.execute(httppost);
            temp = EntityUtils.toString(response.getEntity());

        } catch (ClientProtocolException e) {

        } catch (IOException e) {
        }
        return temp;
    }
    public String postDataHeadeRAuth(String url, JSONObject obj) {


        String apitoken= PhotoApp.getInstance().getstoreSharedpref().getSharedValue("apitoken");

        PhotoApp.getInstance().createLog("CHECK APITOKEN IMAGE "+apitoken);
        String temp="";
        HttpParams myParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(myParams, 60000);
        HttpConnectionParams.setSoTimeout(myParams, 60000);
        HttpClient httpclient = new DefaultHttpClient(myParams );

        try {

            HttpPost httppost = new HttpPost(url.toString());
            httppost.setHeader("Authorization", apitoken);

            StringEntity se = new StringEntity(obj.toString());
            se.setContentEncoding(new BasicHeader("Content-Type", "application/json"));


            httppost.setEntity(se);

            HttpResponse response = httpclient.execute(httppost);
            temp = EntityUtils.toString(response.getEntity());

        } catch (ClientProtocolException e) {

        } catch (IOException e) {
        }
        return temp;
    }


    public String postDataArrayGet(String url, JSONArray obj)
    {

        String apitoken= PhotoApp.getInstance().getstoreSharedpref().getSharedValue("apitoken");

        PhotoApp.getInstance().createLog("ARRAY APITOKEN "+apitoken);

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(25, TimeUnit.SECONDS)
                .writeTimeout(25, TimeUnit.SECONDS)
                .readTimeout(25, TimeUnit.SECONDS)


                .build();
        //  .cache(cache)

        okhttp3.Request request = new okhttp3.Request.Builder() .url(url).header("Authorization", apitoken) .build();




        try { okhttp3.Response responseobj = client.newCall(request).execute();
            response=responseobj.body().string();
        }
        catch (IOException e) { e.printStackTrace(); }
return response;
    }


    public String postDataArrayPostHeader(String url,JSONObject obj)


        {


            String apitoken= PhotoApp.getInstance().getstoreSharedpref().getSharedValue("apitoken");

            PhotoApp.getInstance().createLog("CHECK APITOKEN "+apitoken);
            String temp="";
            HttpParams myParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(myParams, 60000);
            HttpConnectionParams.setSoTimeout(myParams, 60000);
            HttpClient httpclient = new DefaultHttpClient(myParams );

            try {

                HttpPost httppost = new HttpPost(url.toString());
                httppost.setHeader("Content-type", "application/json");
                httppost.setHeader("Authorization", apitoken);

                StringEntity se = new StringEntity(obj.toString());
                se.setContentEncoding(new BasicHeader("Content-Type", "application/json"));


                httppost.setEntity(se);

                HttpResponse response = httpclient.execute(httppost);
                temp = EntityUtils.toString(response.getEntity());

            } catch (ClientProtocolException e) {

            } catch (IOException e) {
            }
            return temp;
        }



    public String postDataArrayGetHeader(String url, JSONObject obj)
    {

        String apitoken= PhotoApp.getInstance().getstoreSharedpref().getSharedValue("apitoken");

        PhotoApp.getInstance().createLog("ARRAY APITOKEN "+apitoken);

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(25, TimeUnit.SECONDS)
                .writeTimeout(25, TimeUnit.SECONDS)
                .readTimeout(25, TimeUnit.SECONDS)


                .build();
        //  .cache(cache)




        okhttp3.Request request = new okhttp3.Request.Builder() .url(url).header("Authorization", apitoken).addHeader("Content-Type","application/json").post(RequestBody.create(MEDIA_TYPE_JSON_, obj.toString())).build();




        try { okhttp3.Response responseobj = client.newCall(request).execute();
            response=responseobj.body().string();
        }
        catch (IOException e) { e.printStackTrace(); }
        return response;
    }

    public String postDataArray(String url, JSONArray obj) {

        String apitoken= PhotoApp.getInstance().getstoreSharedpref().getSharedValue("apitoken");

        PhotoApp.getInstance().createLog("ARRAY APITOKEN "+apitoken);
        String temp="";
        HttpParams myParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(myParams, 60000);
        HttpConnectionParams.setSoTimeout(myParams, 60000);
        HttpClient httpclient = new DefaultHttpClient(myParams );

        try {


				/*added now*/	//Cache cache = new Cache(new File(getCacheDir(), CACHE_DIR), CACHE_SIZE);




            HttpPost httppost = new HttpPost(url.toString());
            httppost.setHeader("Authorization", apitoken);

            StringEntity se = new StringEntity(obj.toString());
            se.setContentEncoding(new BasicHeader("Content-Type", "application/json"));
            httppost.setEntity(se);

            HttpResponse response = httpclient.execute(httppost);
            temp = EntityUtils.toString(response.getEntity());

        } catch (ClientProtocolException e) {

        } catch (IOException e) {
        }
        return temp;
    }

    public String postData(String url, String obj) {
        String temp="";
        Log.i("AlmaBay", "========ServiceHandler====1" );
        HttpParams myParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(myParams, 25000);
        HttpConnectionParams.setSoTimeout(myParams, 25000);
        HttpClient httpclient = new DefaultHttpClient(myParams );
        Log.i("AlmaBay", "========ServiceHandler====2" );
        try {
            Log.i("AlmaBay", "========ServiceHandler====3" );
            HttpPost httppost = new HttpPost(url.toString());
            httppost.setHeader("Content-type", "application/json");

            StringEntity se = new StringEntity(obj.toString());
            se.setContentEncoding(new BasicHeader("Content-Type", "application/json"));
            httppost.setEntity(se);
            Log.i("AlmaBay", "========ServiceHandler====4" );
            HttpResponse response = httpclient.execute(httppost);
            temp = EntityUtils.toString(response.getEntity());
            Log.i("AlmaBay", "========ServiceHandler====5"+ temp );
        } catch (ClientProtocolException e) {
            Log.i("AlmaBay", "ClientProtocolException====" + e.getMessage());
        } catch (IOException e) {
            Log.i("AlmaBay", "IOException====" + e.getMessage());
        }
        return temp;
    }

    public static final int CACHE_SIZE = 10 * 1024 * 1024;
    public static final String CACHE_DIR = "httpCache";
    public String makeServiceCall(String url, int method,
                                  List<NameValuePair> params) {
        try {
            // http client





            HttpClient httpClient = new DefaultHttpClient();
            HttpEntity httpEntity = null;
            HttpResponse httpResponse = null;

            // Checking http request method type
            if (method == POST) {
                HttpPost httpPost = new HttpPost(url);
                // adding post params
                if (params != null) {
                    httpPost.setEntity(new UrlEncodedFormEntity(params));
                }

                httpResponse = httpClient.execute(httpPost);
                httpEntity = httpResponse.getEntity();
                response = EntityUtils.toString(httpEntity);

            } else if (method == GET) {
                // appending params to url





				/*if (params != null) {
					String paramString = URLEncodedUtils
							.format(params, "utf-8
					url += "?" + paramString;
				}*/




                try { OkHttpClient client = new OkHttpClient();
				/*added now*/	//Cache cache = new Cache(new File(getCacheDir(), CACHE_DIR), CACHE_SIZE);
                    client = new OkHttpClient.Builder()
                            .connectTimeout(25, TimeUnit.SECONDS)
                            .writeTimeout(25, TimeUnit.SECONDS)
                            .readTimeout(25, TimeUnit.SECONDS)


                            .build();
                   //  .cache(cache)

                    okhttp3.Request request = new okhttp3.Request.Builder() .url(url).header("Cache-Control", "public, max-age=" + 120) .build();




                    try { okhttp3.Response responseobj = client.newCall(request).execute();
                        response=responseobj.body().string();
                    }
                    catch (IOException e) { e.printStackTrace(); }
                } catch (Exception e) {
                }

				/*HttpGet httpGet = new HttpGet(url);
				ResponseHandler<String> responseHandler = new BasicResponseHandler();
				response = httpClient.execute(httpGet,responseHandler);*/

            }


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;

    }
}
