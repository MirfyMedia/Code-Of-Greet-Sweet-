package com.greetsweet.plant.greetapp.Fragments;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.greetsweet.plant.GridSpacing.GridSpacingItemDecoration;
import com.greetsweet.plant.Handler.ServiceHandler;
import com.greetsweet.plant.PhotoApp;
import com.greetsweet.plant.R;
import com.greetsweet.plant.greetapp.AdapterClasses.AdapterCallback;
import com.greetsweet.plant.greetapp.AdapterClasses.UserGreetingAdapterClass;
import com.greetsweet.plant.greetapp.ModalClasses.UserGreetingModalClass;
import com.greetsweet.plant.greetapp.ModalClasses.UserGreetings;
import com.greetsweet.plant.greetapp.UserGreets.UserGreetingsActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class GalleryFragment extends Fragment implements AdapterCallback {
    UserGreetingModalClass val;
    String getgreeturl ="http://13.232.178.62/api/greeting/all/?page=1";
    private UserGreetingAdapterClass adapter;
    RecyclerView usergreetrecycler;
    ImageView back;
    public GalleryFragment() {
        // Required empty public constructor
    }

    public static GalleryFragment newInstance(String param1, String param2) {
        GalleryFragment fragment = new GalleryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_gallery, container, false);
        usergreetrecycler=(RecyclerView)view.findViewById(R.id.greetrecycler);
        new Task_Allgreet().execute();
        return  view;
    }
    public class Task_Allgreet extends AsyncTask<Void, Void, Void> {

        String statusCode;
        // private TransparentProgressDialog dialog;
        String responsemsg = "";
        private ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           /* dialog = new TransparentProgressDialog(_mContext, R.drawable.loader);
            dialog.show();*/

            progressDialog = new ProgressDialog(getActivity());
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

                PhotoApp.getInstance().createLog("=====jobj+url======"+jobj+"========"+getgreeturl);

                String jsonStr = sh.postDataArrayGet(getgreeturl, jobj);



                if (jsonStr != null) {
                    //{"details":{"status":"post can not be deleted."},"statusCode":"200"}
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    PhotoApp.getInstance().createLog("ALL GREET jsonObj RESPONSE "+jsonObj);

                    statusCode = jsonObj.getString("status");

                    PhotoApp.getInstance().createLog("GREET STATUS CODE  "+statusCode);
                    if (statusCode.equalsIgnoreCase("200")) {

                        responsemsg = "200";
                        JSONArray type_j = jsonObj.getJSONArray("greetings");
                        val = new UserGreetingModalClass();

                        for(int i=0;i<type_j.length();i++)
                        {


                            JSONObject j_homepage = type_j.getJSONObject(i);
                            UserGreetings val_ugreet = new UserGreetings();
                            val_ugreet.setIs_favourite(j_homepage.getString("is_favourite"));
                            val_ugreet.setImage(j_homepage.getString("image"));
                            val_ugreet.setId(j_homepage.getString("id"));


                            val.usergreet.add(val_ugreet);




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
                if(adapter!=null)
                {
                    adapter.notifyDataSetChanged();
                }

                else {
                    GridLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(), 3);
                    usergreetrecycler.setLayoutManager(linearLayoutManager);


                    int spanCount = 3; // 3 columns
                    int spacing = 5; // 50px
                    boolean includeEdge = true;
                    usergreetrecycler.setHasFixedSize(true);

                    adapter = new UserGreetingAdapterClass(val.getUsergreet(), getActivity(), GalleryFragment.this);
                    usergreetrecycler.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));


                    usergreetrecycler.setAdapter(adapter);


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
    ArrayList<UserGreetingModalClass> getLogoModalClasses = new ArrayList<UserGreetingModalClass>();

    @Override
    public void onItemClicked(int position){

        PhotoApp.getInstance().createLog("calling callback");
        val.usergreet.remove(position);
        if(adapter!=null)
        adapter.notifyDataSetChanged();




    }
    @Override
    public void onResume() {
        super.onResume();

       // new Task_Allgreet().execute();
    }
}
