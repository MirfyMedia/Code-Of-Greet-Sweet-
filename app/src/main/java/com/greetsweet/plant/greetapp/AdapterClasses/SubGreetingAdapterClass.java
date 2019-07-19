package com.greetsweet.plant.greetapp.AdapterClasses;

/**
 * Created by my on 9/21/2018.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.greetsweet.plant.EditImageActivity;
import com.greetsweet.plant.PhotoApp;
import com.greetsweet.plant.R;
import com.greetsweet.plant.greetapp.ModalClasses.greetingspojo;

import java.util.ArrayList;


public class SubGreetingAdapterClass extends RecyclerView.Adapter<SubGreetingAdapterClass.MyViewHolder> {
    ArrayList<greetingspojo> data;
    Context context;
    MyViewHolder holder1;
    String key = "";


    public SubGreetingAdapterClass(ArrayList<greetingspojo> data, Context context) {
        this.data = data;
        this.context = context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.birthday_list, parent, false);
        return new MyViewHolder(view);


    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder1 = holder;

        Picasso.with(context).load(data.get(position).getImage()).into(holder.timeimage);
        PhotoApp.getInstance().createLog("GET IMAGE"+ data.get(position).getImage());
       /* holder.timeimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(context,EditImageActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                context.startActivity(intent);


            }
        });*/


    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder  {
        ImageView timeimage;
        TextView timetext;

        public MyViewHolder(View itemView) {
            super(itemView);
            timeimage = (ImageView) itemView.findViewById(R.id.imageView5);




        }


    }


}

