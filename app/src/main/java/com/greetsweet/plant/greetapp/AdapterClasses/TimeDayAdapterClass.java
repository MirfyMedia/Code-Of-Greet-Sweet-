package com.greetsweet.plant.greetapp.AdapterClasses;

/**
 * Created by my on 9/21/2018.
 */

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.greetsweet.plant.PhotoApp;
import com.greetsweet.plant.R;
import com.greetsweet.plant.greetapp.Fragments.SeeAllFragments.TimeDayFragment;
import com.greetsweet.plant.greetapp.ModalClasses.TimeDayModalClass;
import com.greetsweet.plant.greetapp.ModalClasses.home_page_pojo;
import com.greetsweet.plant.greetapp.ModalClasses.subcategories_pojo;

import java.util.ArrayList;


public class TimeDayAdapterClass extends RecyclerView.Adapter<TimeDayAdapterClass.MyViewHolder> {
    ArrayList<subcategories_pojo> data_home_page_pojo;
    Context context;
    MyViewHolder holder1;
    String key = "";


    public TimeDayAdapterClass(ArrayList<subcategories_pojo> data_home_page_pojo, Context context) {
        this.data_home_page_pojo = data_home_page_pojo;
        this.context = context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.timeday_list, parent, false);
        return new MyViewHolder(view);


    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder1 = holder;
       // holder.timeimage.setImageResource(data_home_page_pojo.get(position).getImageid());
        holder.timetext.setText(data_home_page_pojo.get(position).getTitle());
        Picasso.with(context).load(data_home_page_pojo.get(position).getImage()).into(holder.timeimage);

        PhotoApp.getInstance().createLog("titleee"+ data_home_page_pojo.get(position).getTitle());
        PhotoApp.getInstance().createLog("imageee"+ data_home_page_pojo.get(position).getImage());


        holder.timeimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment myFragment = new TimeDayFragment();
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_container, myFragment)
                        .addToBackStack(null).commit();


            }
        });

    }


    @Override
    public int getItemCount() {
        return data_home_page_pojo.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder  {
        ImageView timeimage;
        TextView timetext;

        public MyViewHolder(View itemView) {
            super(itemView);
            timeimage = (ImageView) itemView.findViewById(R.id.imageView5);
            timetext=(TextView)itemView.findViewById(R.id.textView14);




        }


    }


}

