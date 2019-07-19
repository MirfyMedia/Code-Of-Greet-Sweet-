package com.greetsweet.plant.greetapp.AdapterClasses;

/**
 * Created by my on 9/21/2018.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.greetsweet.plant.EditImageActivity;
import com.greetsweet.plant.PhotoApp;
import com.greetsweet.plant.R;
import com.greetsweet.plant.greetapp.Fragments.CategoryFragment;
import com.greetsweet.plant.greetapp.Fragments.More.GridFragment;
import com.greetsweet.plant.greetapp.Fragments.SeeAllFragments.TimeDayFragment;
import com.greetsweet.plant.greetapp.HomeActivity;
import com.greetsweet.plant.greetapp.ModalClasses.BirthdayModalClass;
import com.greetsweet.plant.greetapp.ModalClasses.TimeDayModalClass;
import com.greetsweet.plant.greetapp.ModalClasses.home_page_pojo;
import com.greetsweet.plant.greetapp.ModalClasses.subcategories_pojo;
import com.greetsweet.plant.mylibs.utility.Cons;

import java.util.ArrayList;


public class CategoryFragmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements GridItemClick {
    ArrayList<home_page_pojo> data_home_page_pojo;
    Context context;
    MyViewHolder holder1;
    String key = "";
    HomeActivity myActivity ;
    Fragment fragment;
    String imageid;
    public CategoryFragmentAdapter(ArrayList<home_page_pojo> data_home_page_pojo, Context context) {
        this.data_home_page_pojo = data_home_page_pojo;
        this.context = context;
        myActivity = (HomeActivity)context ;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.categoryfragmentadapter, parent, false);
        switch (viewType) {
            case 0: return new MyViewHolder(view);
            case 1: return new MyViewHolder1(view);

        }


        return new MyViewHolder(view);


    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        switch (holder.getItemViewType()) {
            case 0:
                MyViewHolder viewHolder0 = (MyViewHolder)holder;
                ((MyViewHolder) holder).title.setText(data_home_page_pojo.get(position).title);

                viewHolder0.button14.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PhotoApp.getInstance().getstoreSharedpref().storeSharedValue(Cons.category_id,data_home_page_pojo.get(position).getId()+"");
                        PhotoApp.getInstance().getstoreSharedpref().storeSharedValue(Cons.subcategory_id,"0");
                        AppCompatActivity activity = (AppCompatActivity) view.getContext();
                        Fragment myFragment = new CategoryFragment();
                        activity.getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frame_container, myFragment)
                                .addToBackStack(null).commit();


                    }
                });


                ItemAdapter newAdapterClass = new ItemAdapter(data_home_page_pojo.get(position).getSubcategories_pojos(), context,this,position);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                viewHolder0.newrecyclerlist.setLayoutManager(linearLayoutManager);
                viewHolder0.newrecyclerlist.setAdapter(newAdapterClass);



                break;

            case 1:
                MyViewHolder1 viewHolder2 = (MyViewHolder1)holder;
                viewHolder2.button14.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PhotoApp.getInstance().getstoreSharedpref().storeSharedValue(Cons.category_id,"0");
                        PhotoApp.getInstance().getstoreSharedpref().storeSharedValue(Cons.subcategory_id,data_home_page_pojo.get(position).getId()+"");

                       PhotoApp.getInstance().getstoreSharedpref().storeSharedValue(Cons.catval,"1");
                       PhotoApp.getInstance().getstoreSharedpref().storeSharedValue(Cons.greettitle,data_home_page_pojo.get(position).title);
                        AppCompatActivity activity = (AppCompatActivity) view.getContext();
                        Fragment myFragment = new GridFragment();
                        activity.getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frame_container, myFragment)
                                .addToBackStack(null).commit();


                    }
                });
                ((MyViewHolder1) holder).title.setText(data_home_page_pojo.get(position).title);
                ItemAdapter1 newAdapterClass1 = new ItemAdapter1(data_home_page_pojo.get(position).getGreetingspojo(), context,this,position);
                LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                viewHolder2.newrecyclerlist.setLayoutManager(linearLayoutManager1);
                viewHolder2.newrecyclerlist.setAdapter(newAdapterClass1);
                break;
        }








    }

   /* private void loadFragment(Fragment fragment) {
        // load fragment

        FragmentTransaction transaction =myActivity.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }*/

    @Override
    public int getItemCount() {
        return data_home_page_pojo.size();
    }

    @Override
    public void On_Item_Click(int position, int type) {



    }
    @Override
    public void On_Item_Click(int pos1, int pos2, int type) {

        if(type == 0) {
            PhotoApp.getInstance().getstoreSharedpref().storeSharedValue(Cons.category_id,"0");
            PhotoApp.getInstance().getstoreSharedpref().storeSharedValue(Cons.subcategory_id,data_home_page_pojo.get(pos2).getSubcategories_pojos().get(pos1).getId()+"");


            PhotoApp.getInstance().createLog("FLAG SUBCAT--"+data_home_page_pojo.get(pos2).getSubcategories_pojos().get(pos1).getTitle());
            PhotoApp.getInstance().getstoreSharedpref().storeSharedValue(Cons.greettitle,data_home_page_pojo.get(pos2).getSubcategories_pojos().get(pos1).getTitle());

            PhotoApp.getInstance().createLog("FLAG --CAT--"+data_home_page_pojo.get(pos2).getTitle());
            PhotoApp.getInstance().getstoreSharedpref().storeSharedValue(Cons.maintitle,data_home_page_pojo.get(pos2).getTitle());




            Fragment myFragment = new GridFragment();
            myActivity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_container, myFragment)
                    .addToBackStack(null).commit();
        }
       else if(type ==1)
        {
            PhotoApp.getInstance().getstoreSharedpref().storeSharedValue("getlogourl","NA");
            PhotoApp.getInstance().getstoreSharedpref().storeSharedValue("urlimage",data_home_page_pojo.get(pos2).getGreetingspojo().get(pos1).getImage());
            PhotoApp.getInstance().getstoreSharedpref().storeSharedValue(Cons.is_viewed_greetings,"false");

            imageid= String.valueOf((data_home_page_pojo.get(pos2).getGreetingspojo().get(pos1).getId()));
            PhotoApp.getInstance().createLog("New GET IMAGEID"+ imageid);
            PhotoApp.getInstance().getstoreSharedpref().storeSharedValue("imageid",imageid);

            PhotoApp.getInstance().createLog("SUB CAT--"+ data_home_page_pojo.get(pos2).getTitle());
            PhotoApp.getInstance().getstoreSharedpref().storeSharedValue(Cons.greettitle, data_home_page_pojo.get(pos2).getTitle());


            Intent intent =new Intent(context,EditImageActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            context.startActivity(intent);

        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder  {
        RecyclerView newrecyclerlist;
        TextView title,seeall;
        RelativeLayout button14;

        public MyViewHolder(View itemView) {
            super(itemView);
            newrecyclerlist = (RecyclerView) itemView.findViewById(R.id.newrecyclerlist);
            title=(TextView)itemView.findViewById(R.id.titletext);
            button14=(RelativeLayout) itemView.findViewById(R.id.button14);

            seeall=(TextView)itemView.findViewById(R.id.seeall);



        }


    }


    public class MyViewHolder1 extends RecyclerView.ViewHolder  {
        RecyclerView newrecyclerlist;

        TextView title,seeall;
        RelativeLayout button14;
        public MyViewHolder1(View itemView) {
            super(itemView);
            newrecyclerlist = (RecyclerView) itemView.findViewById(R.id.newrecyclerlist);
            button14=(RelativeLayout) itemView.findViewById(R.id.button14);
            title=(TextView)itemView.findViewById(R.id.titletext);
            seeall=(TextView)itemView.findViewById(R.id.seeall);




        }


    }

    @Override
    public int getItemViewType(int position) {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        int retrun_value = 0;
        if(data_home_page_pojo.get(position).getType().equalsIgnoreCase("category"))
        {
            retrun_value = 0;  //category
        }
        else if(data_home_page_pojo.get(position).getType().equalsIgnoreCase("subcategory"))
        {
            retrun_value =1;  //subcategory
        }

        return retrun_value;
    }

}

