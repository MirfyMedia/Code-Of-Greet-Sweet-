package com.greetsweet.plant.greetapp.AdapterClasses;

/**
 * Created by my on 9/21/2018.
 */

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.squareup.picasso.Picasso;
import com.greetsweet.plant.EditImageActivity;
import com.greetsweet.plant.PhotoApp;
import com.greetsweet.plant.R;
import com.greetsweet.plant.greetapp.HomeActivity;
import com.greetsweet.plant.greetapp.ModalClasses.greetingspojo;
import com.greetsweet.plant.mylibs.utility.Cons;

import java.util.ArrayList;


public class GridAdapterClass extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<greetingspojo> data_home_page_pojo;
    Context context;
    MyViewHolder holder1;
    String key = "";
    HomeActivity myActivity = (HomeActivity)context ;
    Fragment fragment;

    String imageid;

    private static final int TYPE_ITEMGRID = 0;
    private static final int TYPE_ADS = 1;

    public GridAdapterClass(ArrayList<greetingspojo> data_home_page_pojo, Context context) {
        this.data_home_page_pojo = data_home_page_pojo;
        this.context = context;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {



        View itemView;


        //Since we are using same holder for both header and footer so we can return same holder
        if (/*viewType == TYPE_HEADER ||*/ viewType == TYPE_ADS) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.banneradd, parent, false);
            return new MyViewHolderAd(itemView);
        } else {
            itemView =LayoutInflater.from(context).inflate(R.layout.gridadapterclass, parent, false);
            return new MyViewHolder(itemView);
        }




    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder1, final int position) {

        // holder.timeimage.setImageResource(data_home_page_pojo.get(position).getImageid());
        // holder.timetext.setText(data_home_page_pojo.get(position).getTitle());


                if(getItemViewType(position)==TYPE_ITEMGRID) {
                    MyViewHolder holder = (MyViewHolder)holder1;
                    holder.view.setText(String.valueOf(data_home_page_pojo.get(position).getViews()));
                    holder.down.setText(String.valueOf(data_home_page_pojo.get(position).getDownloads()));

                    Picasso.with(context).load(data_home_page_pojo.get(position).getImage()).into(holder.timeimage);
                    holder.timeimage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            PhotoApp.getInstance().getstoreSharedpref().storeSharedValue("urlimage", data_home_page_pojo.get(position).getImage());
                            imageid = String.valueOf((data_home_page_pojo.get(position).getId()));
                            PhotoApp.getInstance().createLog("GET IMAGEID" + imageid);
                            PhotoApp.getInstance().getstoreSharedpref().storeSharedValue("imageid", imageid);

                            PhotoApp.getInstance().getstoreSharedpref().storeSharedValue("getlogourl", "NA");
                            PhotoApp.getInstance().getstoreSharedpref().storeSharedValue(Cons.is_viewed_greetings, "false");




                            Intent intent = new Intent(context, EditImageActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                            context.startActivity(intent);
                        }
                    });
                    //  PhotoApp.getInstance().createLog("titleee"+ data_home_page_pojo.get(position).getTitle());
                    PhotoApp.getInstance().createLog("imageee" + data_home_page_pojo.get(position).getImage());

                }
                else
                {
                    MyViewHolderAd holder = (MyViewHolderAd)holder1;

                    if(PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.p_user_type).equals("basic")) {
                        holder.adView.loadAd(holder.adRequest);
                            }
                    else
                    {
                        holder.adView.setVisibility(View.GONE);
                    }
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



    public class MyViewHolderAd extends RecyclerView.ViewHolder  {
        private AdView adView;
        AdRequest adRequest;

        public MyViewHolderAd(View itemView) {
            super(itemView);
            adView = (AdView)itemView.findViewById(R.id.ad_view);
            adRequest = new AdRequest.Builder().build();

        }


    }

    @Override
    public int getItemViewType(int position) {
        //Return item type according to requirement

        int return_Value = TYPE_ITEMGRID;
        if (isPositionAds(position))
            return_Value = TYPE_ADS;
        else
            return_Value = TYPE_ITEMGRID;
        return return_Value;

    }
    //if position is 0 then type is header
    private boolean isPositionAds(int position) {
        int count_item = 0;
        if(position>22){
            count_item = position - 23;
            return (count_item%9==0 ? true:false);
        }
        else if (position > 6 )
        {
            return (position%14==0 ? true:false);
        }
//       else if(position>20){
//
//            return (position%23==0 ? true:false);
//        }



        return (position%5==0 ? true:false);





    }


}

