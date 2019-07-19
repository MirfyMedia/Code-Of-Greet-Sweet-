package com.greetsweet.plant.greetapp.Fragments;

import android.app.Application;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.greetsweet.plant.Handler.ServiceHandler;
import com.greetsweet.plant.PhotoApp;
import com.greetsweet.plant.R;
import com.greetsweet.plant.greetapp.AdapterClasses.CategoryFragmentAdapter;
import com.greetsweet.plant.greetapp.AdapterClasses.GreetingAdapterClass;
import com.greetsweet.plant.greetapp.AdapterClasses.NewAdapterClass;
import com.greetsweet.plant.greetapp.AdapterClasses.TimeDayAdapterClass;
import com.greetsweet.plant.greetapp.Fragments.More.GridFragment;
import com.greetsweet.plant.greetapp.Fragments.SeeAllFragments.GreetingFragment;
import com.greetsweet.plant.greetapp.Fragments.SeeAllFragments.TimeDayFragment;
import com.greetsweet.plant.greetapp.ModalClasses.BirthdayModalClass;
import com.greetsweet.plant.greetapp.ModalClasses.TimeDayModalClass;
import com.greetsweet.plant.greetapp.ModalClasses.greetingspojo;
import com.greetsweet.plant.greetapp.ModalClasses.home_page_pojo;
import com.greetsweet.plant.greetapp.ModalClasses.subcategories_pojo;
import com.greetsweet.plant.mylibs.utility.Cons;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class CategoryFragment extends Fragment {
    private TimeDayAdapterClass adapter;
    RecyclerView dayrecycler;
    private ArrayList<TimeDayModalClass> data;
    private RecyclerView.LayoutManager layoutManager;
    private GreetingAdapterClass greetingAdapterClass;
    RecyclerView birthrecycler, interrecycler;
    private ArrayList<BirthdayModalClass> birthdata;
    home_page_pojo val;
    TextView seeall, title, greetseeall;
    Fragment fragment;
    String url ="http://13.232.178.62/api/greeting/home/category/subcategory/?start=";
    private CategoryFragmentAdapter newAdapterClass;
    TextView titletext, home;
    String maintitle;
    int start=0;
    int count=5;
    ArrayList<home_page_pojo> wholedata_home_page_pojo=new ArrayList<>();

    boolean no_more_data = false;
    private  LinearLayoutManager linearLayoutManager;
    public CategoryFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view= inflater.inflate(R.layout.categoryfragment, container, false);
        no_more_data = false;

        maintitle=PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.maintitle);

        home =(TextView)view.findViewById(R.id.textView53);
        home.setText("Home >");
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new HomeFragment1();
                loadFragment(fragment);
            }
        });

        titletext =(TextView)view.findViewById(R.id.textView54);
        titletext.setText(maintitle);

        title =(TextView)view.findViewById(R.id.textView10);

       /* seeall =(TextView)view.findViewById(R.id.textView13);
        seeall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new GridFragment();
                loadFragment(fragment);

            }
        });*/


        dayrecycler=(RecyclerView)view.findViewById(R.id.dayrecycler);
        dayrecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                PhotoApp.getInstance().createLog("REFRESH LAYOUT" );

                PhotoApp.getInstance().createLog("size "+wholedata_home_page_pojo.size());
                PhotoApp.getInstance().createLog("number "+(count*(start+1)-1));




                if(!no_more_data) {
                    if (linearLayoutManager.findLastVisibleItemPosition() > wholedata_home_page_pojo.size() - 1)
                        PhotoApp.getInstance().createLog("if condition 2");
                    new  Task_Category().execute();
                }
            }


        });


        newAdapterClass = new CategoryFragmentAdapter(data_home_page_pojo, getActivity());
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        dayrecycler.setLayoutManager(linearLayoutManager);
        dayrecycler.setAdapter(newAdapterClass);
        data_home_page_pojo.clear();
        wholedata_home_page_pojo.clear();
        new  Task_Category().execute();
        return  view;

    }


    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    private void setData() {

        newAdapterClass.notifyDataSetChanged();

    }
    public class Task_Category extends AsyncTask<Void, Void, Void> {

        String statusCode;
        // private TransparentProgressDialog dialog;
        String responsemsg = "";
        private ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           /* dialog = new TransparentProgressDialog(_mContext, R.drawable.loader);
            dialog.show();*/

            if(data_home_page_pojo.size()==0) {
                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setCancelable(false); // set cancelable to false
                progressDialog.setMessage("Please Wait"); // set message
                progressDialog.show();
            }
        }

        @Override
        protected Void doInBackground(Void... arg0)

        {

            try {
             //   data_home_page_pojo.clear();
                PhotoApp.getInstance().createLog("API HITT");

                start = wholedata_home_page_pojo.size();
                PhotoApp.getInstance().createLog("start"+start);



                JSONObject jobj = new JSONObject();
                int category_id = Integer.parseInt(PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.m_category_id));
                int subcategory_id = Integer.parseInt(PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.m_subcategory_id));
                PhotoApp.getInstance().createLog("category category_id "+category_id);
                PhotoApp.getInstance().createLog("category subcategory_id "+subcategory_id);


                jobj.put("category_id",category_id);
                jobj.put("subcategory_id",subcategory_id);
                ServiceHandler sh = new ServiceHandler();



                String jsonStr = sh.postDataArrayPostHeader(url+start+"&count="+count, jobj);



                if (jsonStr != null) {
                    //{"details":{"status":"post can not be deleted."},"statusCode":"200"}
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    PhotoApp.getInstance().createLog("jsonObj CATEGORY RESPONSE "+jsonObj);

                    statusCode = jsonObj.getString("status");

                    PhotoApp.getInstance().createLog("STATUS CODE  "+statusCode);
                    if (statusCode.equalsIgnoreCase("200")) {

                        responsemsg = "200";

                        JSONArray type_j = jsonObj.getJSONArray("subcategories");

                        for(int i=0;i<type_j.length();i++)
                        {
                            val = new home_page_pojo();

                            JSONObject j_homepage = type_j.getJSONObject(i);
                        //    String type = j_homepage.getString("type");
                            val.setType(Cons.subcategory);
                            if(val.getType().equalsIgnoreCase("category"))
                            {
                                val.setId(j_homepage.getInt("id"));
                                val.setTitle(j_homepage.getString("title"));
                                val.setImage(j_homepage.getString("image"));
                                JSONArray j_subcategories = j_homepage.getJSONArray("subcategories");
                                for(int ii=0;ii<j_subcategories.length();ii++)
                                {
                                    subcategories_pojo val_sub = new subcategories_pojo();
                                    JSONObject j_sub = j_subcategories.getJSONObject(ii);
                                    val_sub.setTitle(j_sub.getString("title"));
                                    val_sub.setImage(j_sub.getString("image"));
                                    val_sub.setId(j_sub.getInt("id"));
                                    val_sub.setTypes(j_sub.getString("types"));
                                    val_sub.setPriority(j_sub.getInt("priority"));

                                    val.subcategories_pojos.add(val_sub);


                                }

                            }
                            else if(val.getType().equalsIgnoreCase("subcategory"))
                            {
                                val.setTitle(j_homepage.getString("title"));
                               // val.setImage(j_homepage.getString("image"));
                                val.setId(j_homepage.getInt("id"));
                                JSONArray j_greetings = j_homepage.getJSONArray("greetings");
                                for(int ii=0;ii<j_greetings.length();ii++)
                                {    greetingspojo val_gree = new greetingspojo();
                                    JSONObject j_gree = j_greetings.getJSONObject(ii);
                                    val_gree.setTitle(j_gree.getString("title"));
                                    val_gree.setId(j_gree.getInt("id"));
                                    val_gree.setImage(j_gree.getString("image"));
                                    val_gree.setDownloads(j_gree.getInt("downloads"));
                                    val_gree.setViews(j_gree.getInt("views"));

                                    val.greetingspojo.add(val_gree);

                                }

                            }
                            wholedata_home_page_pojo.add(val);
                            PhotoApp.getInstance().createLog("wholedata_home_page_pojo"+wholedata_home_page_pojo.size());

                            if(val.getSubcategories_pojos().size()>0||val.getGreetingspojo().size()>0)
                                data_home_page_pojo.add(val);



                        }

                        if(wholedata_home_page_pojo.size()>start)
                        {

                            PhotoApp.getInstance().createLog("if condtion");
                        }
                        else
                        {
                            no_more_data = true;
                            PhotoApp.getInstance().createLog("else  nomore data "+no_more_data);

                        }
                        PhotoApp.getInstance().createLog("=======wholedata_home_page_pojo========="+wholedata_home_page_pojo.size());



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
                if(start == 0)

                    progressDialog.dismiss();
                // title.setText(PhotoApp.getInstance().getstoreSharedpref().getSharedValue("title"));

                setData();

               /* if(data_home_page_pojo.size()>0)
                {
                    if(data_home_page_pojo.get(0).getType().equalsIgnoreCase("category"))
                    {
                        set_SubcateogryAdapter(0,data_home_page_pojo.get(0).getSubcategories_pojos());
                    }

                    if(data_home_page_pojo.get(1).getType().equalsIgnoreCase("subcategory"))
                    {

                        PhotoApp.getInstance().createLog("GREETINGSSS");
                        //set_GreetingAdapter(1,data_home_page_pojo.get(1).getGreetingspojo());
                    }


                }
*/



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


    private void set_SubcateogryAdapter(int position, ArrayList<subcategories_pojo> val_sub)
    {
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        // groceryRecyclerView.setLayoutManager(horizontalLayoutManager);
        dayrecycler.setLayoutManager(horizontalLayoutManager);
        dayrecycler.setHasFixedSize(true);
        adapter=new TimeDayAdapterClass(val_sub,getActivity());
        dayrecycler.setAdapter(adapter);
    }


  /*  private void set_GreetingAdapter(int position, ArrayList<greetingspojo> val_gree)
    {
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        // groceryRecyclerView.setLayoutManager(horizontalLayoutManager);
        birthrecycler.setLayoutManager(horizontalLayoutManager);
        birthrecycler.setHasFixedSize(true);
        greetingAdapterClass =new GreetingAdapterClass(val_gree,getActivity());
        birthrecycler.setAdapter(greetingAdapterClass);
    }
*/

    ArrayList<home_page_pojo> data_home_page_pojo = new ArrayList<home_page_pojo>();

}
