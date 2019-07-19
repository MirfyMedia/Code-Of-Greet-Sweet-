package com.greetsweet.plant.greetapp.AdapterClasses;

/**
 * Created by my on 9/21/2018.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.greetsweet.plant.EditImageActivity;
import com.greetsweet.plant.PhotoApp;
import com.greetsweet.plant.R;
import com.greetsweet.plant.greetapp.HomeActivity;
import com.greetsweet.plant.greetapp.ModalClasses.greetingspojo;
import com.greetsweet.plant.mylibs.utility.Cons;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class FilterGridAdapterClass extends RecyclerView.Adapter<FilterGridAdapterClass.MyViewHolder> {
    ArrayList<greetingspojo> data_home_page_pojo;
    Context context;
    MyViewHolder holder1;
    String key = "";
    HomeActivity myActivity = (HomeActivity)context ;
    Fragment fragment;

    String imageid;
    public FilterGridAdapterClass(ArrayList<greetingspojo> data_home_page_pojo, Context context) {
        this.data_home_page_pojo = data_home_page_pojo;
        this.context = context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.gridadapterclass, parent, false);
        return new MyViewHolder(view);


    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder1 = holder;
        // holder.timeimage.setImageResource(data_home_page_pojo.get(position).getImageid());
        // holder.timetext.setText(data_home_page_pojo.get(position).getTitle());
        holder.view.setText(String.valueOf(data_home_page_pojo.get(position).getViews()));
        holder.down.setText(String.valueOf(data_home_page_pojo.get(position).getDownloads()));

        Picasso.with(context).load(data_home_page_pojo.get(position).getImage()).into(holder.timeimage);
        holder.timeimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PhotoApp.getInstance().getstoreSharedpref().storeSharedValue("urlimage",data_home_page_pojo.get(position).getImage());
                imageid= String.valueOf((data_home_page_pojo.get(position).getId()));
                PhotoApp.getInstance().createLog("GET IMAGEID"+ imageid);
                PhotoApp.getInstance().getstoreSharedpref().storeSharedValue("imageid",imageid);

                PhotoApp.getInstance().getstoreSharedpref().storeSharedValue("getlogourl","NA");
                PhotoApp.getInstance().getstoreSharedpref().storeSharedValue(Cons.is_viewed_greetings,"false");
                Intent intent =new Intent(context,EditImageActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                context.startActivity(intent);
            }
        });
        //  PhotoApp.getInstance().createLog("titleee"+ data_home_page_pojo.get(position).getTitle());
        PhotoApp.getInstance().createLog("imageee"+ data_home_page_pojo.get(position).getImage());


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

    public class MyViewHolder extends RecyclerView.ViewHolder  {
        ImageView timeimage;
        TextView timetext;
        TextView view, down;

        public MyViewHolder(View itemView) {
            super(itemView);
            timeimage = (ImageView) itemView.findViewById(R.id.imageView5);
            //   timetext=(TextView)itemView.findViewById(R.id.textView14);

            view = (TextView) itemView.findViewById(R.id.view);
            down = (TextView) itemView.findViewById(R.id.down);



        }


    }


}

