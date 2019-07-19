package com.greetsweet.plant.greetapp.SearchPack;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.greetsweet.plant.GridSpacing.GridSpacingItemDecoration;
import com.greetsweet.plant.Handler.ServiceHandler;
import com.greetsweet.plant.PhotoApp;
import com.greetsweet.plant.R;
import com.greetsweet.plant.greetapp.AdapterClasses.SearchAdapterClass;
import com.greetsweet.plant.greetapp.ModalClasses.Subcategories;
import com.greetsweet.plant.greetapp.ModalClasses.SearchModalClass;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class SearchFragment extends Fragment {
    SearchModalClass val;
    String getsearchurl ="http://13.232.178.62/api/greeting/search/subcategory/?name=t";

    private SearchAdapterClass adapter;
    RecyclerView searchrecycler;

    SearchView searchView;
    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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
        View view= inflater.inflate(R.layout.activity_search, container, false);
        searchrecycler=(RecyclerView)view.findViewById(R.id.searchrecycler);
        new Task_Search().execute();

        searchView=(SearchView)view.findViewById(R.id.page_title);

        searchView.setActivated(true);
        searchView.setQueryHint(getResources().getString(R.string.Searchcate));
        searchView.onActionViewExpanded();
        searchView.setIconified(false);
        searchView.setFocusable(false);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return  view;
    }
    public class Task_Search extends AsyncTask<Void, Void, Void> {

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

                PhotoApp.getInstance().createLog("=====jobj+url======"+jobj+"========"+getsearchurl);

                String jsonStr = sh.postDataArrayGet(getsearchurl, jobj);



                if (jsonStr != null) {
                    //{"details":{"status":"post can not be deleted."},"statusCode":"200"}
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    PhotoApp.getInstance().createLog("Search jsonObj RESPONSE "+jsonObj);

                    statusCode = jsonObj.getString("status");

                    PhotoApp.getInstance().createLog("Search STATUS CODE  "+statusCode);
                    if (statusCode.equalsIgnoreCase("200")) {

                        responsemsg = "200";

                        JSONArray type_j = jsonObj.getJSONArray("subcategories");
                        val = new SearchModalClass();

                        for(int i=0;i<type_j.length();i++)
                        {


                            JSONObject j_homepage = type_j.getJSONObject(i);
                            Subcategories val_cat = new Subcategories();
                            val_cat.setCategory_title(j_homepage.getString("category_title"));
                            val_cat.setCategory_id(j_homepage.getString("category_id"));
                            val_cat.setSubcategory_title(j_homepage.getString("subcategory_title"));
                            val_cat.setSubcategory_id(j_homepage.getString("subcategory_id"));


                            val.searchsub.add(val_cat);




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
                GridLayoutManager linearLayoutManager=new GridLayoutManager(getActivity(),2);
                searchrecycler.setLayoutManager(linearLayoutManager);


                int spanCount = 2; // 3 columns
                int spacing = 20; // 50px
                boolean includeEdge = true;
                searchrecycler.setHasFixedSize(true);
                adapter=new SearchAdapterClass(val.getSearchsub(),getActivity());
                searchrecycler.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));

                searchrecycler.setAdapter(adapter);

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
    ArrayList<SearchModalClass> getLogoModalClasses = new ArrayList<SearchModalClass>();

}
