package com.greetsweet.plant.greetapp.AdapterClasses;

/**
 * Created by my on 9/21/2018.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.greetsweet.plant.Handler.ServiceHandler;
import com.greetsweet.plant.PhotoApp;
import com.greetsweet.plant.R;
import com.greetsweet.plant.greetapp.HomeActivity;
import com.greetsweet.plant.greetapp.ModalClasses.FrameModalClass;
import com.greetsweet.plant.greetapp.ModalClasses.Frame_list;
import com.greetsweet.plant.greetapp.ModalClasses.UserGreetings;
import com.greetsweet.plant.mylibs.utility.Cons;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;


public class FramesAdapterClass extends RecyclerView.Adapter<FramesAdapterClass.MyViewHolder> {
    ArrayList<Frame_list> data_greet_pojo;
    Context context;
    MyViewHolder holder1;
    String key = "";
    HomeActivity myActivity = (HomeActivity)context ;
    Fragment fragment;
    String frameimagecheck;

    public FramesAdapterClass(ArrayList<Frame_list> data_home_page_pojo, Context context) {
        this.data_greet_pojo = data_home_page_pojo;
        this.context = context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.frame_list, parent, false);
        return new MyViewHolder(view);


    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder1 = holder;

        Picasso.with(context).load(String.valueOf(data_greet_pojo.get(position).getImage())).into(holder.frameiamage);
        if(data_greet_pojo.get(position).isIs_blueselected()) {
            holder.frameconst.setBackgroundResource(R.drawable.whiteborder);
        }
        else {
            holder.frameconst.setBackgroundResource(0);
        }
        holder.frameiamage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frameimagecheck = data_greet_pojo.get(position).getImage();
                PhotoApp.getInstance().createLog("frameimagecheck "+frameimagecheck);
                PhotoApp.getInstance().getstoreSharedpref().storeSharedValue(Cons.frameimage,frameimagecheck);

                for (int j = 0; j < data_greet_pojo.size(); j++)
                {
                    data_greet_pojo.get(j).setIs_blueselected(false);
                }
                data_greet_pojo.get(position).setIs_blueselected(true);
                notifyDataSetChanged();


            }
        });




    }


    @Override
    public int getItemCount() {
        return data_greet_pojo.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder  {
        ImageView frameiamage;
        ConstraintLayout frameconst;


        public MyViewHolder(View itemView) {
            super(itemView);
            frameiamage = (ImageView) itemView.findViewById(R.id.imageView45);
            frameconst = (ConstraintLayout) itemView.findViewById(R.id.frameconst);




        }


    }


}

