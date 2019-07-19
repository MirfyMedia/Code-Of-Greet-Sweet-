package com.greetsweet.plant.greetapp.AdapterClasses;

/**
 * Created by my on 9/21/2018.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.greetsweet.plant.EditImageActivity;
import com.greetsweet.plant.Handler.ServiceHandler;
import com.greetsweet.plant.PhotoApp;
import com.greetsweet.plant.R;
import com.greetsweet.plant.greetapp.Fragments.More.PlanFragment;
import com.greetsweet.plant.greetapp.HomeActivity;
import com.greetsweet.plant.greetapp.ModalClasses.UserGreetings;
import com.greetsweet.plant.mylibs.utility.Cons;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;


public class UserGreetingAdapterClass extends RecyclerView.Adapter<UserGreetingAdapterClass.MyViewHolder> {
    ArrayList<UserGreetings> data_greet_pojo;
    Context context;
    MyViewHolder holder1;
    int pos;

    String key = "";
    HomeActivity myActivity = (HomeActivity)context ;
    Fragment fragment;
String getfavid;
    String favimageurl ="http://13.232.178.62/api/greeting/favourite/";
    String deleteimageurl ="http://13.232.178.62/api/greeting/delete/";
    AdapterCallback callback;

    int unlikevalue;

    Bitmap bt;
    Uri fetchuri;
    URL url;
    OutputStream output;
    String image_url;
    URI myURI;


    String favimageid;
    public UserGreetingAdapterClass(ArrayList<UserGreetings> data_home_page_pojo, Context context, AdapterCallback callback) {
        this.data_greet_pojo = data_home_page_pojo;
        this.context = context;
        this.callback = callback;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.usergreet_list, parent, false);
        return new MyViewHolder(view);


    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder1 = holder;
       // pos= position;

        Picasso.with(context).load(data_greet_pojo.get(position).getImage()).into(holder.greetiamage);

        if(data_greet_pojo.get(position).getIs_favourite().equals("true")){

            holder.like.setBackgroundResource(R.drawable.ic_favorite_red_24dp);
            unlikevalue= 0;
        }
        else{
            holder.like.setBackgroundResource(R.drawable.blackheart);

        }
        ///////frame back
        if(data_greet_pojo.get(position).isIs_blueselected()) {
            holder.framelay.setVisibility(View.VISIBLE);
        }
        else {
            holder.framelay.setVisibility(View.INVISIBLE);
        }




        ///const click listner
        holder.constmain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                for (int j = 0; j < data_greet_pojo.size(); j++)
                {
                    data_greet_pojo.get(j).setIs_blueselected(false);
                }
                data_greet_pojo.get(position).setIs_blueselected(true);
                notifyDataSetChanged();



            }
        });





        ////like click listener

        holder.like.setOnClickListener(new View.OnClickListener(){
            int check = 1; //When check = 1 ,you have your FIRST background set to the button


            @Override
            public void onClick(View view){

                getfavid= data_greet_pojo.get(position).getId();
                PhotoApp.getInstance().createLog("getfavid "+getfavid);

                if(check == 1){


                    holder.like.setBackgroundResource(R.drawable.ic_favorite_red_24dp);
                    PhotoApp.getInstance().getstoreSharedpref().storeSharedValue(Cons.fav,"1");
                    PhotoApp.getInstance().createLog("see fav"+PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.fav));

                    new Task_Favimage().execute();
                    check = 0;
                }else{
                    holder.like.setBackgroundResource(R.drawable.blackheart);
                    PhotoApp.getInstance().getstoreSharedpref().storeSharedValue(Cons.fav,"0");
                    PhotoApp.getInstance().createLog("see fav"+PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.fav));

                    new Task_Favimage().execute();
                    check = 1;
                }
            }

        });

        holder.deletegreet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(context); //Home is name of the activity
                builder.setMessage("Do you want to remove this image?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        getfavid= data_greet_pojo.get(position).getId();

                        pos= position;

                        // deleteitem();
                        new Task_Delteimage().execute();


                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

                AlertDialog alert=builder.create();
                alert.show();



            }
        });


        ///////edit image click listener
        holder.editimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PhotoApp.getInstance().getstoreSharedpref().storeSharedValue("urlimage",data_greet_pojo.get(position).getImage());

                Intent intent =new Intent(context,EditImageActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                context.startActivity(intent);

            }
        });

        /////share image click listener
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                image_url =data_greet_pojo.get(position).getImage();

                try {
                     url = new URL(image_url);
                     PhotoApp.getInstance().createLog("share url "+url);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }


             new Task_sharebitmap().execute();






            }
        });

        ///// downloadpost click listener

        holder.downloadpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.p_user_type).equals("basic")) {


                    Fragment myFragment = new PlanFragment();
                    myActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_container, myFragment)
                            .addToBackStack(null).commit();


                } else {


                    try {
                        url = new URL(data_greet_pojo.get(position).getImage());
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }

                    new Task_download().execute();


                }
            }
        });


    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 60, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);

    }

    public class Task_Favimage extends AsyncTask<Void, Void, Void> {

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

            progressDialog = new ProgressDialog(context);
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

                jobj.put("id",getfavid);
                jobj.put("is_favourite",PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.fav));

                ServiceHandler sh = new ServiceHandler();
                PhotoApp.getInstance().createLog("see id"+getfavid);


                PhotoApp.getInstance().createLog("=====jobj+url======"+jobj+"========"+favimageurl);

                String jsonStr = sh.postDataHeader(favimageurl, jobj);



                if (jsonStr != null) {

                    //{"details":{"status":"post can not be deleted."},"statusCode":"200"}
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    PhotoApp.getInstance().createLog("FAVIMAGE jsonObj RESPONSE "+jsonObj);

                    statusCode = jsonObj.getString("status");

                    PhotoApp.getInstance().createLog("FAVIMAGE STATUS CODE  "+statusCode);
                    if (statusCode.equalsIgnoreCase("200")) {

                        responsemsg = "200";

                        messagelang = jsonObj.getString("message");
                        PhotoApp.getInstance().createLog("messagelang "+messagelang);





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

    public class Task_Delteimage extends AsyncTask<Void, Void, Void> {

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

            progressDialog = new ProgressDialog(context);
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


                ServiceHandler sh = new ServiceHandler();
                PhotoApp.getInstance().createLog("delete see id"+getfavid);


                PhotoApp.getInstance().createLog("=====jobj+url======"+jobj+"========"+deleteimageurl+getfavid+"/");

                String jsonStr = sh.postDataHeader(deleteimageurl+getfavid+"/", jobj);



                if (jsonStr != null) {

                    //{"details":{"status":"post can not be deleted."},"statusCode":"200"}
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    PhotoApp.getInstance().createLog("DELETE jsonObj RESPONSE "+jsonObj);

                    statusCode = jsonObj.getString("status");

                    PhotoApp.getInstance().createLog("DELETE STATUS CODE  "+statusCode);
                    if (statusCode.equalsIgnoreCase("200")) {

                        responsemsg = "200";

                        messagelang = jsonObj.getString("message");
                        PhotoApp.getInstance().createLog("messagelang "+messagelang);





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

                PhotoApp.getInstance().ShowToast(context.getResources().getString(R.string.deleteimage));


              /*  Fragment myFragment = new GalleryFragment();
                myActivity.getSupportFragmentManager().beginTransaction()
                        .add(R.id.frame_container, myFragment)
                        .addToBackStack(null).commit();*/


                if(callback != null) {
                    callback.onItemClicked(pos);
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
    public int getItemCount() {
        return data_greet_pojo.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder  {
        ImageView greetiamage, like, deletegreet, share, editimage;
        TextView downloadpost;

        ConstraintLayout constmain;
        FrameLayout framelay;


        public MyViewHolder(View itemView) {
            super(itemView);
            greetiamage = (ImageView) itemView.findViewById(R.id.imageView5);
            like = (ImageView) itemView.findViewById(R.id.imageView46);
            deletegreet = (ImageView) itemView.findViewById(R.id.imageView51);
            share = (ImageView) itemView.findViewById(R.id.imageView66);
            editimage = (ImageView) itemView.findViewById(R.id.imageView67);
            downloadpost = (TextView) itemView.findViewById(R.id.textView84);



            constmain = (ConstraintLayout) itemView.findViewById(R.id.constmain);
            framelay = (FrameLayout) itemView.findViewById(R.id.framelay);


        }


    }





    public class Task_sharebitmap extends AsyncTask<Void, Void, Void> {

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

            progressDialog = new ProgressDialog(context);
            progressDialog.setCancelable(false); // set cancelable to false
            progressDialog.setMessage("Please Wait"); // set message
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0)

        {

            try {
                try {
                    bt = BitmapFactory.decodeStream(url.openConnection().getInputStream());

                    PhotoApp.getInstance().createLog("share bitmap "+bt);

                } catch (IOException e) {

                    PhotoApp.getInstance().createLog("bitmap exce "+e.getMessage());

                    e.printStackTrace();

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

                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject");

                share.putExtra(Intent.EXTRA_TEXT, "Made via Greetsweet");
                fetchuri= getImageUri(context, bt);

                PhotoApp.getInstance().createLog("share URIII"+fetchuri);
                share.putExtra(Intent.EXTRA_STREAM,fetchuri);
                share.setType("image*//*");

                share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                context.startActivity(Intent.createChooser(share, "Share Via"));



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

    public class Task_download extends AsyncTask<Void, Void, Void> {

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

            progressDialog = new ProgressDialog(context);
            progressDialog.setCancelable(false); // set cancelable to false
            progressDialog.setMessage("Please Wait"); // set message
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0)

        {

            try {
                try {
                    bt = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                } catch (IOException e) {
                    e.printStackTrace();
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

                // Find the SD Card path
                File filepath = Environment.getExternalStorageDirectory();

                File dir = new File(filepath.getAbsolutePath()
                        + "/Greet Sweet/");
                dir.mkdirs();

                // Create a name for the saved image
                File file = new File(dir, File.separator + ""
                        + System.currentTimeMillis() + ".png");
                PhotoApp.getInstance().createLog("directIMG "+file);




                // Show a toast message on successful save
                Toast.makeText(context, "Image Saved Successfully",
                        Toast.LENGTH_SHORT).show();
                try {

                    output = new FileOutputStream(file);

                    // Compress into png format image from 0% - 100%
                    bt.compress(Bitmap.CompressFormat.PNG, 100, output);
                    output.flush();
                    output.close();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
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

