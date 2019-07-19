package com.greetsweet.plant.greetapp.AdapterClasses;

/**
 * Created by my on 9/21/2018.
 */

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.greetsweet.plant.PhotoApp;
import com.greetsweet.plant.R;
import com.greetsweet.plant.greetapp.HomeActivity;
import com.greetsweet.plant.greetapp.ModalClasses.Filter_tags;
import com.greetsweet.plant.greetapp.ModalClasses.Frame_list;
import com.greetsweet.plant.mylibs.utility.Cons;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class FilterGetAdapterClass extends RecyclerView.Adapter<FilterGetAdapterClass.MyViewHolder> {
    ArrayList<Filter_tags> filtertags;
    Context context;
    MyViewHolder holder1;
    String key = "";
    HomeActivity myActivity = (HomeActivity)context ;
    Fragment fragment;
    String frameimagecheck;

    String idfilter = "";

    public FilterGetAdapterClass(ArrayList<Filter_tags> data_home_page_pojo, Context context) {
        this.filtertags = data_home_page_pojo;
        this.context = context;
        idfilter = "";
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.filter_list, parent, false);
        return new MyViewHolder(view);


    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder1 = holder;

holder.filtertext.setText(filtertags.get(position).getName());

        /*if(filtertags.get(position).isIs_blueselected()) {
            holder.filterimage.setBackgroundResource(R.drawable.ic_check_box_black_24dp);
        }
        else {
            holder.filterimage.setBackgroundResource(R.drawable.ic_check_box_outline_blank);
        }*/

        holder.filterconst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*for (int j = 0; j < filtertags.size(); j++)
                {
                    filtertags.get(j).setIs_blueselected(false);
                }
                filtertags.get(position).setIs_blueselected(true);

                notifyDataSetChanged();*/

                holder.filterimage.setBackgroundResource(R.drawable.ic_check_box_black_24dp);
                if (idfilter.equalsIgnoreCase("")) {
                    idfilter = filtertags.get(position).getId();

                }
                else
                    {
                        idfilter = idfilter + "," + filtertags.get(position).getId();

                    }
                PhotoApp.getInstance().createLog("idfilter--- " + idfilter);
                PhotoApp.getInstance().getstoreSharedpref().storeSharedValue(Cons.idfilter,idfilter);

            }
        });


    }


    @Override
    public int getItemCount() {
        return filtertags.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder  {
        ImageView filterimage;
        ConstraintLayout filterconst;
        TextView filtertext;


        public MyViewHolder(View itemView) {
            super(itemView);
            filterimage = (ImageView) itemView.findViewById(R.id.imageView47);
            filterconst = (ConstraintLayout) itemView.findViewById(R.id.filterconst);
            filtertext = (TextView) itemView.findViewById(R.id.textView70);




        }


    }


}

