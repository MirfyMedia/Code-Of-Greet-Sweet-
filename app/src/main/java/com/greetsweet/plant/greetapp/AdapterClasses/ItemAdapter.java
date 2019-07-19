package com.greetsweet.plant.greetapp.AdapterClasses;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.greetsweet.plant.R;
import com.greetsweet.plant.greetapp.HomeActivity;
import com.greetsweet.plant.greetapp.ModalClasses.TimeDayModalClass;
import com.greetsweet.plant.greetapp.ModalClasses.subcategories_pojo;

import java.util.ArrayList;

/**
 * Created by Nancy on 2/11/2019.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {
    ArrayList<subcategories_pojo> data_home_page_pojo;
    Context context;
    ItemAdapter.MyViewHolder holder1;
    String key = "";
    HomeActivity myActivity = (HomeActivity)context ;
    Fragment fragment;
    GridItemClick griditemClick;
    int prev_position;
    public ItemAdapter(ArrayList<subcategories_pojo> data_home_page_pojo, Context context,GridItemClick griditemClick,int prev_position) {
        this.data_home_page_pojo = data_home_page_pojo;
        this.context = context;
        this.griditemClick = griditemClick;
        this.prev_position = prev_position;

    }

    @Override
    public ItemAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.itemadapter, parent, false);
        return new ItemAdapter.MyViewHolder(view);


    }

    @Override
    public void onBindViewHolder(final ItemAdapter.MyViewHolder holder, final int position) {
        holder1 = holder;
        //holder.timeimage.setImageResource(data_home_page_pojo.get(position).getImage());
        holder.timetext.setText(data_home_page_pojo.get(position).getTitle());
           Picasso.with(context).load(data_home_page_pojo.get(position).getImage()).into(holder.timeimage);
        holder.top_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                griditemClick.On_Item_Click(position,prev_position,0);


            }
        });
        //  PhotoApp.getInstance().createLog("titleee"+ data_home_page_pojo.get(position).getTitle());


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
        ConstraintLayout top_view;

        public MyViewHolder(View itemView) {
            super(itemView);
            timeimage = (ImageView) itemView.findViewById(R.id.imageView5);
            timetext=(TextView)itemView.findViewById(R.id.textView14);

            top_view = (ConstraintLayout)itemView.findViewById(R.id.top_view);


        }


    }


}
