package com.greetsweet.plant.greetapp.Fragments.More;


import android.annotation.SuppressLint;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.greetsweet.plant.GridSpacing.EqualSpacingItemDecoration;
import com.greetsweet.plant.GridSpacing.GridSpacingItemDecoration;
import com.greetsweet.plant.Handler.ServiceHandler;
import com.greetsweet.plant.PhotoApp;
import com.greetsweet.plant.R;
import com.greetsweet.plant.greetapp.AdapterClasses.FilterGetAdapterClass;
import com.greetsweet.plant.greetapp.AdapterClasses.FilterGridAdapterClass;
import com.greetsweet.plant.greetapp.AdapterClasses.FramesAdapterClass;
import com.greetsweet.plant.greetapp.AdapterClasses.GreetingAdapterClass;
import com.greetsweet.plant.greetapp.AdapterClasses.GridAdapterClass;
import com.greetsweet.plant.greetapp.AdapterClasses.MorningAdapterClass;
import com.greetsweet.plant.greetapp.AdapterClasses.NewAdapterClass;
import com.greetsweet.plant.greetapp.Fragments.CategoryFragment;
import com.greetsweet.plant.greetapp.Fragments.HomeFragment1;
import com.greetsweet.plant.greetapp.ModalClasses.BirthdayModalClass;
import com.greetsweet.plant.greetapp.ModalClasses.FilterGetModalClass;
import com.greetsweet.plant.greetapp.ModalClasses.Filter_tags;
import com.greetsweet.plant.greetapp.ModalClasses.FrameModalClass;
import com.greetsweet.plant.greetapp.ModalClasses.TimeDayModalClass;
import com.greetsweet.plant.greetapp.ModalClasses.greetingspojo;
import com.greetsweet.plant.greetapp.ModalClasses.home_page_pojo;
import com.greetsweet.plant.greetapp.ModalClasses.subcategories_pojo;
import com.greetsweet.plant.mylibs.utility.Cons;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class GridFragment extends Fragment {
static RecyclerView gridrecycler;

    private static GridLayoutManager linearLayoutManager;

    private static GridAdapterClass adapter;
    private static FilterGridAdapterClass gridadapter;


    RecyclerView dayrecycler;
    private ArrayList<TimeDayModalClass> data;

    static home_page_pojo data_home_page_pojo = new home_page_pojo();
    private RecyclerView.LayoutManager layoutManager;


    static ArrayList<home_page_pojo> wholedata_home_page_pojo=new ArrayList<>();

    home_page_pojo val;
    TextView seeall, title;
    Fragment fragment;
    TextView all, corp;


    String url ="http://13.232.178.62/api/greeting/home/subcategory/greetings/";
    TextView subtitle , titletext, home;
    String maintitle, subgreettitle;


    RadioButton downlow, downhigh, vislow,vishigh , newest, oldest;
    static int sortdown;
    static  int vissort;
    static int newsort;


    TextView filtertext, corporate, sorttext;
    static RecyclerView filterrecycler;
    static FilterGetModalClass valfilter;
    static String filterurl ="http://13.232.178.62/api/greeting/get/filters/";
    private static FilterGetAdapterClass filteradapter;

    static String greetfilterurl ="http://13.232.178.62/api/greeting/home/subcategory/greetings/?start=";

    static String filtervalue;
    private AdView adView;
    AdRequest adRequest;


    static int start=0;
    static int count=10;
    static boolean no_more_data = false;


    android.support.v7.app.AlertDialog dialog;
    public GridFragment() {
        // Required empty public constructor
    }

    public static GridFragment newInstance(String param1, String param2) {
        GridFragment fragment = new GridFragment();
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

        PhotoApp.getInstance().createLog("WORKING GRIDFRAG 1---");
        View view= inflater.inflate(R.layout.fragment_grid, container, false);
        no_more_data = false;


        PhotoApp.getInstance().createLog("WORKING GRIDFRAG 1---");
        maintitle=PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.maintitle);
        subgreettitle=PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.greettitle);



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

        subtitle =(TextView)view.findViewById(R.id.textView55);
        subtitle.setVisibility(View.INVISIBLE);

        if(PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.catval).equals("1"))

        {
          //  titletext.setText("Home > "+maintitle+" > "+subgreettitle);
            subtitle.setVisibility(View.VISIBLE);

            home.setText("Home >");
            titletext.setText(maintitle);
            subtitle.setText("> "+subgreettitle);



            titletext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.m_category_id);
                    PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.m_subcategory_id);


                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    Fragment myFragment = new CategoryFragment();
                    activity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_container, myFragment)
                            .addToBackStack(null).commit();
                }
            });


            PhotoApp.getInstance().getstoreSharedpref().storeSharedValue(Cons.catval,"");
           // PhotoApp.getInstance().getstoreSharedpref().storeSharedValue(Cons.greettitle,"");
        }


// title =(TextView)view.findViewById(R.id.textView10);

        gridrecycler=(RecyclerView)view.findViewById(R.id.gridrecycler);
        gridrecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                PhotoApp.getInstance().createLog("REFRESH LAYOUT" );

                PhotoApp.getInstance().createLog("size "+wholedata_home_page_pojo.size());
                PhotoApp.getInstance().createLog("number "+(count*(start+1)-1));




                if(!no_more_data) {
                    if (linearLayoutManager.findLastVisibleItemPosition() > wholedata_home_page_pojo.size() - 1)
                        PhotoApp.getInstance().createLog("if condition 2");
                    new Task_greetfilter(getActivity()).execute();
                }
            }


        });




        //   PhotoApp.getInstance().getstoreSharedpref().storeSharedValue(Cons.idfilter,"");
        data_home_page_pojo.getGreetingspojo().clear();
        wholedata_home_page_pojo.clear();
        linearLayoutManager = new GridLayoutManager(getActivity(), 2);
        gridrecycler.setLayoutManager(linearLayoutManager);

        int spanCount = 2; // 3 columns
        int spacing = 30; // 50px
        boolean includeEdge = true;

      //  gridrecycler.setHasFixedSize(true);
        linearLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {

  int count_item = 0;
                if(position>22){

                    count_item = position - 23;

                    return (count_item%9==0 ? 2:1);
                }

                else if (position > 6 && position < 23 )
                {


                    count_item = position - 2;
                    if(position > 14)
                        count_item = position - 3;

                    return (position%14==0 ? 2:1);
                }
                else
                {
                    count_item = position - 2;
                    return (position%5==0 ? 2:1);
                }





            }
        });

        adapter = new GridAdapterClass(data_home_page_pojo.getGreetingspojo(), getActivity());
    //    gridrecycler.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
        gridrecycler.addItemDecoration(new EqualSpacingItemDecoration(25, EqualSpacingItemDecoration.HORIZONTAL)); // 16px. In practice, you'll want to use getDimensionPixelSize
        gridrecycler.addItemDecoration(new EqualSpacingItemDecoration( 10,EqualSpacingItemDecoration.VERTICAL)); // 16px. In practice, you'll want to use getDimensionPixelSize

        gridrecycler.setAdapter(adapter);
        data_home_page_pojo.getGreetingspojo().clear();
        wholedata_home_page_pojo.clear();

        new Task_greetfilter(getActivity()).execute();









        filtertext=(TextView)view.findViewById(R.id.textView38);
filtertext.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {


        try{
            Fragment myFragment = new FilterFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
            .setCustomAnimations(R.anim.bottom_up, R.anim.bottom_down)
                    .replace(R.id.frame_container, myFragment)
                    .addToBackStack(null).commit();


        }
catch (Exception e)
        {

            PhotoApp.getInstance().createLog("EXCEPTION "+ e.getMessage());
        }



    }


});

        sorttext=(TextView)view.findViewById(R.id.textView37);
        sorttext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sortdialog();
            }


        });



        return  view;
    }




    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    public static class Task_greetfilter extends AsyncTask<Void, Void, Void> {

        String statusCode;
        // private TransparentProgressDialog dialog;
        String responsemsg = "";
        private ProgressDialog progressDialog;



        private Context mContext;

        public Task_greetfilter (Context context){
            mContext = context;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           /* dialog = new TransparentProgressDialog(_mContext, R.drawable.loader);
            dialog.show();*/

                progressDialog = new ProgressDialog(mContext);
                progressDialog.setCancelable(false); // set cancelable to false
                progressDialog.setMessage("Please Wait"); // set message
            if(data_home_page_pojo.getGreetingspojo().size()==0) {
                progressDialog.show();

            }

        }

        @Override
        protected Void doInBackground(Void... arg0)

        {

            try {
                PhotoApp.getInstance().createLog("API HITT");
                start = wholedata_home_page_pojo.size();



                int category_id = Integer.parseInt(PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.category_id));
                int subcategory_id = Integer.parseInt(PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.subcategory_id));
                 filtervalue= PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.idfilter);
                if(filtervalue.equalsIgnoreCase("NA"))
                {
                    filtervalue = "";
                }



                PhotoApp.getInstance().createLog("FILTER category_id "+category_id);
                PhotoApp.getInstance().createLog(" FILTER subcategory_id "+subcategory_id);
                PhotoApp.getInstance().createLog(" FILTER filtervalue "+filtervalue);



                String post_value = "{"+"\"category_id\":"+""+category_id+","+"\"subcategory_id\":"+""+subcategory_id+","+"\"tags\":["+filtervalue+"]}";

                PhotoApp.getInstance().createLog("post_value "+post_value);




                ServiceHandler sh = new ServiceHandler();

                PhotoApp.getInstance().createLog("=====post_value+url======"+post_value+"========"+greetfilterurl+"downloads="+sortdown+"&views="+vissort+"&newest="+newsort);

               // String jsonStr = sh.postDataHeaderString(greetfilterurl+start+"&count="+count+"&downloads="+sortdown+"&views="+vissort+"&newest="+newsort, post_value);

                String jsonStr = "";
                jsonStr = sh.postDataHeaderString(greetfilterurl+start+"&count="+count+"&downloads="+sortdown, post_value);
                PhotoApp.getInstance().createLog("URL 1 "+greetfilterurl+start+"&count="+count+"&downloads="+sortdown);


                if(PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.clicksorttype).equals("downloadsclick"))
                {
                    jsonStr = sh.postDataHeaderString(greetfilterurl+start+"&count="+count+"&downloads="+sortdown, post_value);
                    PhotoApp.getInstance().createLog("URL 2 "+greetfilterurl+start+"&count="+count+"&downloads="+sortdown);

                }
                else if(PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.clicksorttype).equals("viewsclick"))
                {
                    jsonStr = sh.postDataHeaderString(greetfilterurl+start+"&count="+count+"&views="+vissort, post_value);
                    PhotoApp.getInstance().createLog("URL 3 "+greetfilterurl+start+"&count="+count+"&views="+vissort);

                }
                else if(PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.clicksorttype).equals("newestclick"))
                {
                    jsonStr = sh.postDataHeaderString(greetfilterurl+start+"&count="+count+"&newest="+newsort, post_value);
                    PhotoApp.getInstance().createLog("URL 4 "+greetfilterurl+start+"&count="+count+"&newest="+newsort);

                }









                PhotoApp.getInstance().createLog("=====jsonStr====== "+jsonStr);

                if (jsonStr != null) {
                    //{"details":{"status":"post can not be deleted."},"statusCode":"200"}
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    PhotoApp.getInstance().createLog("greet FILTER jsonObj RESPONSE "+jsonObj);

                    statusCode = jsonObj.getString("status");

                    PhotoApp.getInstance().createLog("greet FILTER STATUS CODE  "+statusCode);
                    if (statusCode.equalsIgnoreCase("200")) {

                        responsemsg = "200";


//                        data_home_page_pojo.setId(jsonObj.getInt("id"));
//                        data_home_page_pojo.setTitle(jsonObj.getString("title"));
//                        data_home_page_pojo.setType(Cons.subcategory);
                        JSONArray type_j = jsonObj.getJSONArray("greetings");

                        for (int i = 0; i < type_j.length(); i++) {
                            greetingspojo val_gree = new greetingspojo();
                            JSONObject j_gree = type_j.getJSONObject(i);
                            val_gree.setTitle(j_gree.getString("title"));
                            val_gree.setId(j_gree.getInt("id"));
                            val_gree.setImage(j_gree.getString("image"));
                            val_gree.setDownloads(j_gree.getInt("downloads"));
                            val_gree.setViews(j_gree.getInt("views"));
                            val_gree.setCreated(j_gree.getString("created"));

                            data_home_page_pojo.getGreetingspojo().add(val_gree);

                        }
                        wholedata_home_page_pojo.add(data_home_page_pojo);
                        PhotoApp.getInstance().createLog("wholedata_home_page_pojo size"+wholedata_home_page_pojo.size());

                        if(wholedata_home_page_pojo.size()>start)
                        {

                            PhotoApp.getInstance().createLog("if condtion");
                        }
                        else
                        {
                            no_more_data = true;
                            PhotoApp.getInstance().createLog("else  nomore data "+no_more_data);

                        }

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
            PhotoApp.getInstance().createLog("onPostExecute");

            try {

                progressDialog.dismiss();




                    if(adapter!=null)
                    {
                        adapter.notifyDataSetChanged();
                        PhotoApp.getInstance().createLog("Notify adapter");
                    }


                    else {
                        PhotoApp.getInstance().createLog("SET GRID adapter");

                        linearLayoutManager = new GridLayoutManager(mContext, EqualSpacingItemDecoration.HORIZONTAL);
                        gridrecycler.setLayoutManager(linearLayoutManager);


                        //Two items in a row


                        int spanCount = 2; // 3 columns
                        int spacing = 30; // 50px
                        boolean includeEdge = true;

                        //  gridrecycler.setHasFixedSize(true);
                        linearLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                            @Override
                            public int getSpanSize(int position) {

                                    return (position%5==0 ? 2:1);



                            }



                        });

                        adapter = new GridAdapterClass(data_home_page_pojo.getGreetingspojo(), mContext);
                       // gridrecycler.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
                        gridrecycler.addItemDecoration(new EqualSpacingItemDecoration(25, EqualSpacingItemDecoration.HORIZONTAL)); // 16px. In practice, you'll want to use getDimensionPixelSize
                        gridrecycler.addItemDecoration(new EqualSpacingItemDecoration( 10,EqualSpacingItemDecoration.VERTICAL)); // 16px. In practice, you'll want to use getDimensionPixelSize

                        gridrecycler.setAdapter(adapter);


                        //   PhotoApp.getInstance().getstoreSharedpref().storeSharedValue(Cons.idfilter,"");
                    }



            } catch (Exception e) {
                PhotoApp.getInstance().createLog("Exception"+e.toString());
            }

            finally {
                PhotoApp.getInstance().createLog("finallyh");

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


    private void sortdialog() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.sort_layout, null);
        final ImageView close = alertLayout.findViewById(R.id.imageView64);
        downlow=(RadioButton)alertLayout.findViewById(R.id.radioButton2);
        downhigh=(RadioButton)alertLayout.findViewById(R.id.radioButton3);
        vislow=(RadioButton)alertLayout.findViewById(R.id.radioButton7);
        vishigh=(RadioButton)alertLayout.findViewById(R.id.radioButton10);
        newest=(RadioButton)alertLayout.findViewById(R.id.radioButton8);
        oldest=(RadioButton)alertLayout.findViewById(R.id.radioButton9);

        downlow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortdown= 1;
                PhotoApp.getInstance().getstoreSharedpref().storeSharedValue(Cons.clicksorttype,"downloadsclick");

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        dialog.dismiss();
                        data_home_page_pojo.getGreetingspojo().clear();
                        wholedata_home_page_pojo.clear();
                        new Task_greetfilter(getActivity()).execute();

                    }
                }, 1000);

            }
        });
        downhigh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortdown= 0;
                PhotoApp.getInstance().getstoreSharedpref().storeSharedValue(Cons.clicksorttype,"downloadsclick");

                //   dialog.dismiss();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        dialog.dismiss();
                        data_home_page_pojo.getGreetingspojo().clear();
                        wholedata_home_page_pojo.clear();
                        new Task_greetfilter(getActivity()).execute();

                    }
                }, 1000);

            }
        });
        vislow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vissort= 1;
                PhotoApp.getInstance().getstoreSharedpref().storeSharedValue(Cons.clicksorttype,"viewsclick");

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        dialog.dismiss();
                        data_home_page_pojo.getGreetingspojo().clear();
                        wholedata_home_page_pojo.clear();
                        new Task_greetfilter(getActivity()).execute();

                    }
                }, 1000);
            }
        });
        vishigh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vissort= 0;
                PhotoApp.getInstance().getstoreSharedpref().storeSharedValue(Cons.clicksorttype,"viewsclick");

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        dialog.dismiss();
                        data_home_page_pojo.getGreetingspojo().clear();
                        wholedata_home_page_pojo.clear();
                        new Task_greetfilter(getActivity()).execute();

                    }
                }, 1000);
            }
        });
        newest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newsort= 0;
                PhotoApp.getInstance().getstoreSharedpref().storeSharedValue(Cons.clicksorttype,"newestclick");

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        dialog.dismiss();
                        data_home_page_pojo.getGreetingspojo().clear();
                        wholedata_home_page_pojo.clear();
                        new Task_greetfilter(getActivity()).execute();

                    }
                }, 1000);
            }
        });
        oldest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newsort= 1;
                PhotoApp.getInstance().getstoreSharedpref().storeSharedValue(Cons.clicksorttype,"newestclick");

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        dialog.dismiss();
                        data_home_page_pojo.getGreetingspojo().clear();
                        wholedata_home_page_pojo.clear();
                        new Task_greetfilter(getActivity()).execute();

                    }
                }, 1000);
            }
        });


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        android.support.v7.app.AlertDialog.Builder alert = new android.support.v7.app.AlertDialog.Builder(getActivity());
        //   alert.setTitle("Info");
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(false);

        dialog = alert.create();
        dialog.show();

    }

}
