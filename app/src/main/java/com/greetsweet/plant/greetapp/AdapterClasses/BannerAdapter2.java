package com.greetsweet.plant.greetapp.AdapterClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.greetsweet.plant.APIclient.ApiClient;
import com.greetsweet.plant.PhotoApp;
import com.greetsweet.plant.R;
import com.greetsweet.plant.greetapp.ModalClasses.Intro_screen;
import com.squareup.picasso.Picasso;
import com.switcher.base.BaseSwitchView;

import java.util.ArrayList;


/**
 * Created by shenxl on 2018/11/26.
 */
public class BannerAdapter2 extends BaseSwitchView.AbsBaseAdapter {
    private String title = "This is banner";
    private static Context _ctx;
    ArrayList<Intro_screen> intro_screenArrayList1;


    public void updateImageSlider(ArrayList<Intro_screen> intro_screenArrayList) {
        intro_screenArrayList1 = intro_screenArrayList;
    }


    @Override
    public View makeView(Context context) {
        _ctx = context;
        return LayoutInflater.from(context).inflate(R.layout.start_banner_2, null, false);
    }

    @Override
    public void updateItem(View view, int position) {
        View container = view.findViewById(R.id.banner_container);
        final ImageView image = (ImageView) view.findViewById(R.id.pagerimage);
        PhotoApp.getInstance().createLog("position" + position);
        //   ((TextView) view.findViewById(R.id.banner_title)).setText(title);
        //    ((TextView) view.findViewById(R.id.banner_pos)).setText("X"+position);

        //PhotoApp.getInstance().createLog("====image url for adapter===="+ApiClient.BASE_URL + imageurl);
        try {
            if (intro_screenArrayList1.size() > 0) {
                    PhotoApp.getInstance().createLog("intro_screenArrayList1.get(i).getImage()" + intro_screenArrayList1.get(position).getImage());
                    Picasso.with(_ctx).load(intro_screenArrayList1.get(position).getImage()).placeholder(R.drawable.loading).into(image);
                }
             else {
                Picasso.with(_ctx).load(R.drawable.loading).placeholder(R.drawable.loading).into(image);

            }
        } catch (NullPointerException e) {
            Picasso.with(_ctx).load(R.drawable.loading).placeholder(R.drawable.loading).into(image);

        }
//        switch (position % 3) {
//            case 0:
//                if(imageurl.length()>0) {
//                    Picasso.with(_ctx).load(imageurl)
//                            .error(R.drawable.loading)
//                            .into(image);
//
//                }
//                break;
//            case 1:
//                if(imageurl2.length()>0) {
//                    Picasso.with(_ctx).load(imageurl2)
//                            .error(R.drawable.loading)
//                            .into(image);
//
//                }                break;
//            default:
//                if(imageurl3.length()>0) {
//                    Picasso.with(_ctx).load(imageurl3)
//                            .error(R.drawable.loading)
//                            .into(image);
//
//                }                break;
//        }

    }


    public void setTitle(String title) {
        this.title = title;
    }

    public int getCount() {
        return intro_screenArrayList1.size();
    }

    public String getTitle() {
        return title;
    }

    @Override
    public int getItemCount() {
        try {
            if (intro_screenArrayList1.size() > 0) {
                return intro_screenArrayList1.size();
            } else {
                return intro_screenArrayList1.size();
            }
        } catch (NullPointerException e) {
            return 2;
        }
    }
}



