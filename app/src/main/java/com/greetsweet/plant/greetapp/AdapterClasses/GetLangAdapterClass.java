package com.greetsweet.plant.greetapp.AdapterClasses;

/**
 * Created by my on 9/21/2018.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.greetsweet.plant.PhotoApp;
import com.greetsweet.plant.R;
import com.greetsweet.plant.greetapp.Login.LoginActivity;
import com.greetsweet.plant.greetapp.ModalClasses.GetLangModalClass;
import com.greetsweet.plant.greetapp.ModalClasses.Languages;
import com.greetsweet.plant.mylibs.utility.Cons;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;



public class GetLangAdapterClass extends RecyclerView.Adapter<GetLangAdapterClass.MyViewHolder> {
    ArrayList<Languages> data;
    Context context;
    MyViewHolder holder1;
    String key = "";
String languageid;

    public GetLangAdapterClass(ArrayList<Languages> data, Context context) {
        this.data = data;
        this.context = context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lang_list, parent, false);
        return new MyViewHolder(view);


    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder1 = holder;


        holder.langname.setText(data.get(position).getLanguage());
        Picasso.with(context).load(data.get(position).getImage()).into(holder.langimage);

        if(data.get(position).isIs_blueselected()) {
            holder.mainblock.setBackgroundResource(R.drawable.purpleborder);
        }
        else {
            holder.mainblock.setBackgroundResource(R.drawable.greyborder);
        }
        holder.mainblock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int j = 0; j < data.size(); j++)
                {
                    data.get(j).setIs_blueselected(false);
                }
                data.get(position).setIs_blueselected(true);

                languageid= data.get(position).getId();
                PhotoApp.getInstance().createLog("seelang id "+languageid);
                PhotoApp.getInstance().getstoreSharedpref().storeSharedValue("language",languageid);

                Toast.makeText(context, data.get(position).getLanguage()+" Selected",
                        Toast.LENGTH_LONG).show();

                notifyDataSetChanged();




            }
        });



    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder  {
        ImageView langimage, mainblock;
        TextView langname;

        public MyViewHolder(View itemView) {
            super(itemView);

            mainblock = (ImageView) itemView.findViewById(R.id.imageView42);
            langimage = (ImageView) itemView.findViewById(R.id.imageView43);
            langname=(TextView)itemView.findViewById(R.id.textView51);
        }


    }


}

