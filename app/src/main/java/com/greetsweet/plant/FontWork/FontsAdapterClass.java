package com.greetsweet.plant.FontWork;

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

import com.greetsweet.plant.R;

import java.util.ArrayList;


public class FontsAdapterClass extends RecyclerView.Adapter<FontsAdapterClass.MyViewHolder> {
    ArrayList<FontModelClass> data;
    Context context;
    MyViewHolder holder1;
    String key = "";


    public FontsAdapterClass(ArrayList<FontModelClass> data, Context context) {
        this.data = data;
        this.context = context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.font_list, parent, false);
        return new MyViewHolder(view);


    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder1 = holder;

        holder.fonttext.setText(data.get(position).getText());
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder  {

        TextView fonttext;

        public MyViewHolder(View itemView) {
            super(itemView);
            fonttext=(TextView)itemView.findViewById(R.id.textfont);
        }


    }


}

