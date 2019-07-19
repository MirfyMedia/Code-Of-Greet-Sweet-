package com.greetsweet.plant.greetapp.Fragments.More;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.ArrayMap;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.greetsweet.plant.APIclient.ApiClient;
import com.greetsweet.plant.Interface.All_plans;
import com.greetsweet.plant.Interface.ApiInterface;
import com.greetsweet.plant.Interface.GetPlansPojo;
import com.greetsweet.plant.Interface.ProceedPlans;
import com.greetsweet.plant.Interface.RawData;
import com.greetsweet.plant.PhotoApp;
import com.greetsweet.plant.R;
import com.greetsweet.plant.mylibs.utility.Cons;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterPlan extends RecyclerView.Adapter<AdapterPlan.MyViewHolder> {
    Context context;
    Activity activity;
    private int selectedPosition = 0;
    ArrayList<All_plans> getPlansPojoArrayList;
    CallBackForProceed callBackForProceed;
    String current_plan;
    String key = null;
    public AdapterPlan(Context context,Activity activity,ArrayList<All_plans> getPlansPojoArrayList,CallBackForProceed callBackForProceed,String current_plan) {
        this.context=context;
        this.activity=activity;
        this.callBackForProceed=callBackForProceed;
        this.getPlansPojoArrayList=getPlansPojoArrayList;
        this.current_plan=current_plan;
    }

    @Override
    public AdapterPlan.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_plans, parent, false);
        return new AdapterPlan.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterPlan.MyViewHolder holder, final int position) {
//        if (position==0) {
//            holder.checkboxplan.setChecked(true);
//        } else {
//            holder.checkboxplan.setChecked(false);
//        }
        holder.checkboxplan.setChecked(getPlansPojoArrayList.get(position).getSelected());
        PhotoApp.getInstance().createLog("selewcted"+current_plan);


//        holder.checkboxplan3.setChecked(false);
        holder.checkboxplan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0; i<getPlansPojoArrayList.size();i++)
                {
                    getPlansPojoArrayList.get(i).setSelected(false);
                }
                getPlansPojoArrayList.get(position).setSelected(true);
                notifyDataSetChanged();

            }
        });

        holder.checkboxplan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {
                    if(position==0)
                    {
                        selectedPosition = 0;
                    }
                    else if(position==1)
                    {
                        selectedPosition = 1;
                    }
                    else if(position==2)
                    {
                        selectedPosition = 2;
                    }
                    PhotoApp.getInstance().createLog("checkbox checked position"+position);
                }

            }
        });


        holder.ProceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoApp.getInstance().createLog("checkbox checked position"+getPlansPojoArrayList.get(selectedPosition).getId());
                PhotoApp.getInstance().createLog("selectedPosition"+selectedPosition);

                if(selectedPosition==0)
                {
                    if (getPlansPojoArrayList.get(selectedPosition).getPlan_name().equalsIgnoreCase("basic")) {
                        proceedPlanApis("1");

                    }
                        else if (getPlansPojoArrayList.get(selectedPosition).getPlan_name().equalsIgnoreCase("smart")) {
                        proceedPlanApis("2");

                    }
                        else if (getPlansPojoArrayList.get(selectedPosition).getPlan_name().equalsIgnoreCase("enterprise")) {
                        proceedPlanApis("3");

                    }

                    }


                else if(selectedPosition==1)
                {
                    if (getPlansPojoArrayList.get(selectedPosition).getPlan_name().equalsIgnoreCase("basic")) {
                        proceedPlanApis("1");

                    }
                    else if (getPlansPojoArrayList.get(selectedPosition).getPlan_name().equalsIgnoreCase("smart")) {
                        proceedPlanApis("2");

                    }
                    else if (getPlansPojoArrayList.get(selectedPosition).getPlan_name().equalsIgnoreCase("enterprise")) {
                        proceedPlanApis("3");

                    }

                }
                else if(selectedPosition==2) {
                    if (getPlansPojoArrayList.get(selectedPosition).getPlan_name().equalsIgnoreCase("basic")) {
                        proceedPlanApis("1");

                    }
                    else if (getPlansPojoArrayList.get(selectedPosition).getPlan_name().equalsIgnoreCase("smart")) {
                        proceedPlanApis("2");

                    }
                    else if (getPlansPojoArrayList.get(selectedPosition).getPlan_name().equalsIgnoreCase("enterprise")) {
                        proceedPlanApis("3");

                    }

                }
            }
        });
       if(position==2)
       {
           holder.ProceedButton.setVisibility(View.VISIBLE);

       }
       else
       {            holder.ProceedButton.setVisibility(View.GONE);


       }
        //PhotoApp.getInstance().createLog("size"+getPlansPojoArrayList.size());

       // PhotoApp.getInstance().createLog("Arraylist"+getPlansPojoArrayList.get(position).getPlan_name().toString());
        if(getPlansPojoArrayList.get(position).getPlan_name().equalsIgnoreCase("basic"))
        {
            holder.sevenDayTrial.setVisibility(View.GONE);
            holder.constraintLayoutBasePlans.setBackgroundColor(context.getResources().getColor(R.color.colorbaseplan));
//            holder.constraintLayoutSmartPlan.setVisibility(View.GONE);
//            holder.constraintLayoutEnterprisePlan.setVisibility(View.GONE);
            if (getPlansPojoArrayList.get(0).getPlan_name().equalsIgnoreCase("basic")) {
                holder.price.setText("\u20B9"+getPlansPojoArrayList.get(0).getPrice());

            }
            else if (getPlansPojoArrayList.get(1).getPlan_name().equalsIgnoreCase("basic")) {
                holder.price.setText("\u20B9"+getPlansPojoArrayList.get(1).getPrice());

            }
            else if (getPlansPojoArrayList.get(2).getPlan_name().equalsIgnoreCase("basic")) {
                holder.price.setText("\u20B9"+getPlansPojoArrayList.get(2).getPrice());

            }
            holder.typeofPlan.setText(context.getResources().getString(R.string.basicplans));
            holder.textItsFree.setVisibility(View.VISIBLE);
            holder.annualsubscription.setVisibility(View.GONE);
            holder.textCancelBill.setVisibility(View.GONE);
        }
        else if(getPlansPojoArrayList.get(position).getPlan_name().equalsIgnoreCase("smart"))
        {
            if (PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.repeatuser).equalsIgnoreCase("false")) {
                PhotoApp.getInstance().createLog("first time");
                holder.sevenDayTrial.setVisibility(View.VISIBLE);

            }
            else
            {
                holder.sevenDayTrial.setVisibility(View.GONE);

            }
            holder.constraintLayoutBasePlans.setBackgroundColor(context.getResources().getColor(R.color.colorsmartplan));
            if (getPlansPojoArrayList.get(0).getPlan_name().equalsIgnoreCase("smart")) {
                holder.price.setText("\u20B9"+getPlansPojoArrayList.get(0).getPrice()+" "+context.getResources().getString(R.string.permonth));

            }
            else if (getPlansPojoArrayList.get(1).getPlan_name().equalsIgnoreCase("smart")) {
                holder.price.setText("\u20B9"+getPlansPojoArrayList.get(1).getPrice()+" "+context.getResources().getString(R.string.permonth));

            }
            else if (getPlansPojoArrayList.get(2).getPlan_name().equalsIgnoreCase("smart")) {
                holder.price.setText("\u20B9"+getPlansPojoArrayList.get(2).getPrice()+" "+context.getResources().getString(R.string.permonth));

            }

            holder.typeofPlan.setText(context.getResources().getString(R.string.smartPlan));
            holder.textItsFree.setVisibility(View.GONE);
            holder.annualsubscription.setVisibility(View.VISIBLE);
            holder.textCancelBill.setVisibility(View.VISIBLE);

        }
        else if(getPlansPojoArrayList.get(position).getPlan_name().equalsIgnoreCase("enterprise"))
        {
            if (PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.repeatuser).equalsIgnoreCase("false")) {
                PhotoApp.getInstance().createLog("first time");
                holder.sevenDayTrial.setVisibility(View.VISIBLE);

            }
            else
            {
                holder.sevenDayTrial.setVisibility(View.GONE);

            }
            holder.constraintLayoutBasePlans.setBackgroundColor(context.getResources().getColor(R.color.colorenterpriseplan));
            if (getPlansPojoArrayList.get(0).getPlan_name().equalsIgnoreCase("enterprise")) {
                holder.price.setText("\u20B9"+getPlansPojoArrayList.get(0).getPrice()+" "+context.getResources().getString(R.string.permonth));

            }
            else if (getPlansPojoArrayList.get(1).getPlan_name().equalsIgnoreCase("enterprise")) {
                holder.price.setText("\u20B9"+getPlansPojoArrayList.get(1).getPrice()+" "+context.getResources().getString(R.string.permonth));

            }
            else if (getPlansPojoArrayList.get(2).getPlan_name().equalsIgnoreCase("enterprise")) {
                holder.price.setText("\u20B9"+getPlansPojoArrayList.get(2).getPrice()+" "+context.getResources().getString(R.string.permonth));

            }
            holder.typeofPlan.setText(context.getResources().getString(R.string.enterpriseplan));
            holder.textItsFree.setVisibility(View.GONE);
            holder.annualsubscription.setVisibility(View.VISIBLE);
            holder.textCancelBill.setVisibility(View.VISIBLE);

        }
    }

    private void proceedPlanApis(String type) {
        PhotoApp.getInstance().createLog("type proceed"+type);
        final ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.setCancelable(false); // set cancelable to false
            progressDialog.setMessage("Please Wait"); // set message
            progressDialog.show();
            final ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
//put something inside the map, could be null
      //  JSONObject paramObject = new JSONObject();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("plan_id",type);
            //paramObject.put("plan_id", type);
            Call<ProceedPlans> call = apiService.getProceedApi(PhotoApp.getInstance().getstoreSharedpref().getSharedValue("apitoken"),jsonObject);
            call.enqueue(new Callback<ProceedPlans>() {
                @Override
                public void onResponse(Call<ProceedPlans>call, Response<ProceedPlans> response) {
                    if(response.code() == 200) {
                        ProceedPlans jsonResponse = response.body();
                        PhotoApp.getInstance().createLog("SUB CATEGORY RESPONSE " + jsonResponse);
                        callBackForProceed.callforProceed(type,response.body().getStatus(), response.body().getMessage(), type);
                        progressDialog.dismiss();
                    }
                    else if(response.code() == 400) {
                        PhotoApp.getInstance().createLog("400");
                        callBackForProceed.callforProceed(type,"400",context.getResources().getString(R.string.couponcode), type);
                        progressDialog.dismiss();
                    }

                }
                @Override
                public void onFailure(Call<ProceedPlans>call, Throwable t) {
//                // Log error here since request failed
//                //Log.e(TAG, t.toString());
                    progressDialog.dismiss();
                    PhotoApp.getInstance().createLog("excep "+ t.getMessage());
                }
            });
        }


    @Override
    public int getItemCount() {
        return getPlansPojoArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout constraintLayoutBasePlans;
        private TextView price,typeofPlan,textCancelBill,annualsubscription,textItsFree;
        CheckBox checkboxplan;
        private TextView ProceedButton;
        private ImageView sevenDayTrial;
        public MyViewHolder(View itemView) {
            super(itemView);
            price=(TextView)itemView.findViewById(R.id.textView96);
            textCancelBill=(TextView)itemView.findViewById(R.id.textView1011);
            annualsubscription=(TextView)itemView.findViewById(R.id.textView1021);
            textItsFree=(TextView)itemView.findViewById(R.id.textView97);
            constraintLayoutBasePlans=(ConstraintLayout)itemView.findViewById(R.id.constraintLayout);
          checkboxplan=(CheckBox)itemView.findViewById(R.id.plan1);
           sevenDayTrial=(ImageView)itemView.findViewById(R.id.imageView69);
            typeofPlan=(TextView)itemView.findViewById(R.id.textView31);
            ProceedButton=(TextView)itemView.findViewById(R.id.textView104);

        }




    }
}