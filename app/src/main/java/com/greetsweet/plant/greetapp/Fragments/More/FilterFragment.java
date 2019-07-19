package com.greetsweet.plant.greetapp.Fragments.More;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.greetsweet.plant.Handler.ServiceHandler;
import com.greetsweet.plant.PhotoApp;
import com.greetsweet.plant.R;
import com.greetsweet.plant.greetapp.AdapterClasses.FilterGetAdapterClass;
import com.greetsweet.plant.greetapp.ModalClasses.FilterGetModalClass;
import com.greetsweet.plant.greetapp.ModalClasses.Filter_tags;
import com.greetsweet.plant.mylibs.utility.Cons;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class FilterFragment extends Fragment {
RecyclerView filterrecycler;
     FilterGetModalClass valfilter;
    static String filterurl ="http://13.232.178.62/api/greeting/get/filters/";
    private  FilterGetAdapterClass filteradapter;

    TextView submitfilter, reset;
    public FilterFragment() {
        // Required empty public constructor
    }

    public static FilterFragment newInstance(String param1, String param2) {
        FilterFragment fragment = new FilterFragment();
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
        View view= inflater.inflate(R.layout.fragment_filter, container, false);

        filterrecycler=(RecyclerView)view.findViewById(R.id.filterrecycler);
        new Task_Filterget().execute();
        reset = (TextView) view.findViewById(R.id.textView59);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PhotoApp.getInstance().getstoreSharedpref().storeSharedValue(Cons.idfilter,"");

                filteradapter = new FilterGetAdapterClass(valfilter.filterget, getActivity());
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                filterrecycler.setLayoutManager(linearLayoutManager);
                filterrecycler.setAdapter(filteradapter);
            }
        });

        submitfilter = (TextView) view.findViewById(R.id.textView69);
        submitfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment myFragment = new GridFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.bottom_down, R.anim.bottom_down)
                        .replace(R.id.frame_container, myFragment)
                        .addToBackStack(null).commit();

            }
        });

        return  view;
    }
    public  class Task_Filterget extends AsyncTask<Void, Void, Void> {

        String statusCode;
        // private TransparentProgressDialog dialog;
        String responsemsg = "";
        private ProgressDialog progressDialog;



      /*  private Context mContext;

        public Task_Filterget (Context context){
            mContext = context;
        }
*/

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

                PhotoApp.getInstance().createLog("=====jobj+url======"+jobj+"========"+filterurl);

                String jsonStr = sh.postDataArrayGet(filterurl, jobj);



                if (jsonStr != null) {
                    //{"details":{"status":"post can not be deleted."},"statusCode":"200"}
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    PhotoApp.getInstance().createLog("FILTER jsonObj RESPONSE "+jsonObj);

                    statusCode = jsonObj.getString("status");

                    PhotoApp.getInstance().createLog("FILTER STATUS CODE  "+statusCode);
                    if (statusCode.equalsIgnoreCase("200")) {

                        responsemsg = "200";

                        JSONArray type_j = jsonObj.getJSONArray("filter_tags");
                        valfilter = new FilterGetModalClass();
                        for(int i=0;i<type_j.length();i++)
                        {


                            JSONObject j_homepage = type_j.getJSONObject(i);
                            Filter_tags val_filter = new Filter_tags();
                            val_filter.setName(j_homepage.getString("name"));
                            val_filter.setId(j_homepage.getString("id"));


                            valfilter.filterget.add(val_filter);




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


                filteradapter = new FilterGetAdapterClass(valfilter.filterget, getActivity());
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                filterrecycler.setLayoutManager(linearLayoutManager);
                filterrecycler.setAdapter(filteradapter);



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
     ArrayList<FilterGetModalClass> getLogoModalClasses = new ArrayList<FilterGetModalClass>();

}
