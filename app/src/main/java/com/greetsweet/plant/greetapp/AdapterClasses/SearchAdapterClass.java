package com.greetsweet.plant.greetapp.AdapterClasses;

/**
 * Created by my on 9/21/2018.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.greetsweet.plant.GridSpacing.GridSpacingItemDecoration;
import com.greetsweet.plant.Handler.ServiceHandler;
import com.greetsweet.plant.PhotoApp;
import com.greetsweet.plant.R;
import com.greetsweet.plant.greetapp.Fragments.CategoryFragment;
import com.greetsweet.plant.greetapp.Fragments.More.GridFragment;
import com.greetsweet.plant.greetapp.ModalClasses.SearchModalClass;
import com.greetsweet.plant.greetapp.ModalClasses.Subcategories;
import com.greetsweet.plant.greetapp.SearchPack.SearchActivity;
import com.greetsweet.plant.mylibs.utility.Cons;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class SearchAdapterClass extends RecyclerView.Adapter<SearchAdapterClass.MyViewHolder> implements Filterable {
    ArrayList<Subcategories> data;
    Context context;
    MyViewHolder holder1;
    String key = "";
    SearchModalClass val;
    String getsearchurl ="http://13.232.178.62/api/greeting/search/subcategory/?name=";

    private ArrayList<Subcategories> contactListFiltered;
    public SearchAdapterClass(ArrayList<Subcategories> data, Context context) {
        this.data = data;
        this.context = context;


        this.contactListFiltered=data;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.searchlist_item, parent, false);
        return new MyViewHolder(view);


    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder1 = holder;

       holder.searchtext.setText(contactListFiltered.get(position).getSubcategory_title());
      //  holder.searchtext.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        holder.searchtext.setSelected(true);


        holder.searchtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.searchtext.setBackgroundColor(Color.parseColor("#800080"));
                holder.searchtext.setTextColor(Color.parseColor("#ffffff"));
                PhotoApp.getInstance().getstoreSharedpref().storeSharedValue(Cons.category_id,"0");
                PhotoApp.getInstance().getstoreSharedpref().storeSharedValue(Cons.subcategory_id,contactListFiltered.get(position).getSubcategory_id()+"");

                PhotoApp.getInstance().createLog("search check catid--  "+contactListFiltered.get(position).getCategory_id());
                PhotoApp.getInstance().createLog("search check subcatid--  "+contactListFiltered.get(position).getSubcategory_id());

                PhotoApp.getInstance().getstoreSharedpref().storeSharedValue(Cons.maintitle,contactListFiltered.get(position).getSubcategory_title());


                AppCompatActivity activity = (AppCompatActivity) view.getContext();

                Fragment myFragment = new GridFragment();
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_container, myFragment)
                        .addToBackStack(null).commit();

            }
        });


    }


    @Override
    public int getItemCount() {
        return contactListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        //   return null;

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    contactListFiltered = data;
                } else {
                    ArrayList<Subcategories> filteredList = new ArrayList<>();


                    new Task_Search(charString).execute();
                    /*for (Subcategories row : data) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getSubcategory_title().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }*/
                   // contactListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = contactListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                //contactListFiltered = (ArrayList<Subcategories>) filterResults.values;
               // notifyDataSetChanged();
            }
        };
    }

    public class Task_Search extends AsyncTask<Void, Void, Void> {

        String statusCode;
        // private TransparentProgressDialog dialog;
        String responsemsg = "";
        private ProgressDialog progressDialog;
        String search_txt;
        public Task_Search(String search_txt)
        {

          super();
          this.search_txt=search_txt;
        }

     /*   @Override
        protected void onPreExecute() {
            super.onPreExecute();
           *//* dialog = new TransparentProgressDialog(_mContext, R.drawable.loader);
            dialog.show();*//*

            progressDialog = new ProgressDialog(context);
            progressDialog.setCancelable(false); // set cancelable to false
            progressDialog.setMessage("Please Wait"); // set message
            progressDialog.show();
        }*/

        @Override
        protected Void doInBackground(Void... arg0)

        {

            try {
                PhotoApp.getInstance().createLog("API HITT");

                JSONArray jobj = new JSONArray();

                ServiceHandler sh = new ServiceHandler();

                PhotoApp.getInstance().createLog("=====jobj+url======"+jobj+"========"+getsearchurl+search_txt);

                String jsonStr = sh.postDataArrayGet(getsearchurl+search_txt, jobj);



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
                        contactListFiltered.clear();
                        for(int i=0;i<type_j.length();i++)
                        {


                            JSONObject j_homepage = type_j.getJSONObject(i);
                            Subcategories val_cat = new Subcategories();
                            val_cat.setCategory_title(j_homepage.getString("category_title"));
                            val_cat.setCategory_id(j_homepage.getString("category_id"));
                            val_cat.setSubcategory_title(j_homepage.getString("subcategory_title"));
                            val_cat.setSubcategory_id(j_homepage.getString("category_id"));


                            contactListFiltered.add(val_cat);




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
                notifyDataSetChanged();


            } catch (Exception e) {
            }


        }


    }
    ArrayList<SearchModalClass> getLogoModalClasses = new ArrayList<SearchModalClass>();

    public class MyViewHolder extends RecyclerView.ViewHolder  {

        TextView searchtext;

        public MyViewHolder(View itemView) {
            super(itemView);
            searchtext=(TextView)itemView.findViewById(R.id.textView57);
        }


    }


}

