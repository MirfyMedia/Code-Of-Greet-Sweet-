package com.greetsweet.plant.greetapp.Fragments.More;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.greetsweet.plant.APIclient.ApiClient;
import com.greetsweet.plant.Handler.ServiceHandler;
import com.greetsweet.plant.Interface.All_plans;
import com.greetsweet.plant.Interface.ApiInterface;
import com.greetsweet.plant.Interface.CancelCoupon;
import com.greetsweet.plant.Interface.ConfirmPlan;
import com.greetsweet.plant.Interface.GetPlansPojo;
import com.greetsweet.plant.Interface.OrderCreatePojo;
import com.greetsweet.plant.Interface.ProceedPlans;
import com.greetsweet.plant.PhotoApp;
import com.greetsweet.plant.R;
import com.greetsweet.plant.greetapp.Fragments.HomeFragment1;
import com.greetsweet.plant.greetapp.Fragments.SeeAllFragments.GreetingFragment;
import com.greetsweet.plant.greetapp.HomeActivity;
import com.greetsweet.plant.greetapp.ModalClasses.ApplyCoupon;
import com.greetsweet.plant.greetapp.ModalClasses.greetingspojo;
import com.greetsweet.plant.greetapp.ModalClasses.home_page_pojo;
import com.greetsweet.plant.greetapp.ModalClasses.subcategories_pojo;
import com.greetsweet.plant.mylibs.utility.Cons;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlanFragment extends Fragment implements CallBackForProceed {
    RadioButton plan1, plan2, plan3;
    RecyclerView recycler_view;
    AdapterPlan adapterPlan;
    TextView invalidCode, planname, amount, amounttotak;
    public ArrayList<GetPlansPojo> getPlansPojoArrayList;
    public ArrayList<All_plans> getAllPlans;
    AlertDialog alertDialog;
    String current_plan;
    String key = "false";
    String url = "http://13.232.178.62/api/v1/plan/order/create/";

    public PlanFragment() {
        // Required empty public constructor
    }

    public static PlanFragment newInstance(String param1, String param2) {
        PlanFragment fragment = new PlanFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_plan, container, false);
        initView(view);


        //        plan1=(RadioButton)view.findViewById(R.id.plan1);
//        plan2=(RadioButton)view.findViewById(R.id.plan2);
//        plan3=(RadioButton)view.findViewById(R.id.plan3);
//
//        plan1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                plan2.setChecked(false);
//                plan3.setChecked(false);
//
//            }
//        });
//        plan2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                plan3.setChecked(false);
//                plan1.setChecked(false);
//            }
//        });
//        plan3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                plan1.setChecked(false);
//                plan2.setChecked(false);
//            }
//        });
//


        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void initView(View view) {
        recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);
        getPlanApis();
    }

    private void getPlanApis() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show();
        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<GetPlansPojo> call = apiService.getPlansPojoApi(PhotoApp.getInstance().getstoreSharedpref().getSharedValue("apitoken"));
        call.enqueue(new Callback<GetPlansPojo>() {
            @Override
            public void onResponse(Call<GetPlansPojo> call, Response<GetPlansPojo> response) {
                GetPlansPojo jsonResponse = response.body();
                PhotoApp.getInstance().getstoreSharedpref().storeSharedValue(Cons.repeatuser, jsonResponse.getRepeat_user());
                PhotoApp.getInstance().createLog("SUB CATEGORY RESPONSE " + jsonResponse);
                progressDialog.dismiss();
                getAllPlans = new ArrayList<>();
                current_plan = response.body().getCurrent_plan();
                getAllPlans = new ArrayList<>(Arrays.asList(jsonResponse.getAll_plans()));
                for (int i = 0; i < getAllPlans.size(); i++) {
//                    if (i == 0) {
//                        getAllPlans.get(i).setSelected(true);
//                    }
                    if (current_plan.equalsIgnoreCase("basic")) {
                        if(getAllPlans.get(0).getId().equalsIgnoreCase("1"))
                        {
                            getAllPlans.get(0).setSelected(true);

                        }
                        else if(getAllPlans.get(1).getId().equalsIgnoreCase("1"))
                        {
                            getAllPlans.get(1).setSelected(true);

                        }
                        else if(getAllPlans.get(2).getId().equalsIgnoreCase("1"))
                        {
                            getAllPlans.get(2).setSelected(true);

                        }
                    } else if (current_plan.equalsIgnoreCase("smart")) {
                        if(getAllPlans.get(0).getId().equalsIgnoreCase("2"))
                        {
                            getAllPlans.get(0).setSelected(true);

                        }
                        else if(getAllPlans.get(1).getId().equalsIgnoreCase("2"))
                        {
                            getAllPlans.get(1).setSelected(true);

                        }
                        else if(getAllPlans.get(2).getId().equalsIgnoreCase("2"))
                        {
                            getAllPlans.get(2).setSelected(true);

                        }
                       // getAllPlans.get(1).setSelected(true);
                    } else if (current_plan.equalsIgnoreCase("enterprise")) {

                        if(getAllPlans.get(0).getId().equalsIgnoreCase("3"))
                        {
                            getAllPlans.get(0).setSelected(true);
                        }
                        else if(getAllPlans.get(1).getId().equalsIgnoreCase("3"))
                        {
                            getAllPlans.get(1).setSelected(true);

                        }
                        else if(getAllPlans.get(2).getId().equalsIgnoreCase("3"))
                        {
                            getAllPlans.get(2).setSelected(true);

                        }
                    }
                }

                PhotoApp.getInstance().createLog("SUB CATEGORY RESPONSE " + getAllPlans.get(0).getPlan_name());
                PhotoApp.getInstance().createLog("SUB CATEGORY RESPONSE " + getAllPlans.get(1).getPlan_name());
                PhotoApp.getInstance().createLog("SUB CATEGORY RESPONSE " + getAllPlans.get(2).getPlan_name());
                recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));
                setAdapter();
            }

            @Override
            public void onFailure(Call<GetPlansPojo> call, Throwable t) {
//                // Log error here since request failed
//                //Log.e(TAG, t.toString());
                progressDialog.dismiss();
                PhotoApp.getInstance().createLog("excep " + t.getMessage());
            }
        });
    }

    private void setAdapter() {
        adapterPlan = new AdapterPlan(getContext(), getActivity(), getAllPlans, this, current_plan);
        recycler_view.setAdapter(adapterPlan);
    }


    private void CancelCouponApi() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show();
        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
//put something inside the map, could be null
        Call<CancelCoupon> call = apiService.CancelCouponApi(PhotoApp.getInstance().getstoreSharedpref().getSharedValue("apitoken"));
        call.enqueue(new Callback<CancelCoupon>() {
            @Override
            public void onResponse(Call<CancelCoupon> call, Response<CancelCoupon> response) {
                PhotoApp.getInstance().showToast(response.body().getMessage());
                PhotoApp.getInstance().createLog("CancelCouponApi" + response.body().toString());
//                HomeFragment1 fragment = new HomeFragment1();
//                HomeActivity homeActivity=new HomeActivity();
//                homeActivity.loadFragment(fragment);

            }

            @Override
            public void onFailure(Call<CancelCoupon> call, Throwable t) {
                PhotoApp.getInstance().showToast("Failure issue regarding web service");
                progressDialog.dismiss();
                PhotoApp.getInstance().createLog("excep " + t.getMessage());
            }
        });
    }

    private void ConfirmPlanApiApis(String type, String message) {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show();
        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
//put something inside the map, could be null
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("plan_id", type);
        Call<ConfirmPlan> call = apiService.ConfirmPlanApi(PhotoApp.getInstance().getstoreSharedpref().getSharedValue("apitoken"), jsonObject);
        call.enqueue(new Callback<ConfirmPlan>() {
            @Override
            public void onResponse(Call<ConfirmPlan> call, Response<ConfirmPlan> response) {
                if (response.code() == 200) {

                    ConfirmPlan jsonResponse = response.body();
                    PhotoApp.getInstance().showToast(response.body().getMesssage());
                    PhotoApp.getInstance().createLog("SUB CATEGORY RESPONSE " + jsonResponse);
                    progressDialog.dismiss();
                    CancelCouponApi();
                } else if (response.code() == 400) {

                    PhotoApp.getInstance().showToast("invalid coupon code");
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ConfirmPlan> call, Throwable t) {

                PhotoApp.getInstance().showToast("Failure issue regarding web service");
                progressDialog.dismiss();
                PhotoApp.getInstance().createLog("excep " + t.getMessage());
            }
        });
    }


    private void MakePaymentApiApis(String value, float price, String discount, String orderid) {
        PhotoApp.getInstance().createLog("MakePaymentApiApis");

        PhotoApp.getInstance().createLog("value" + value);
        PhotoApp.getInstance().createLog("price" + price+"fload"+Math.round(price));
        PhotoApp.getInstance().createLog("discount" + discount);
        PhotoApp.getInstance().createLog("orderid" + orderid);
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show();
        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
//put something inside the map, could be null
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("amount", Math.round(price));
        jsonObject.addProperty("order_id", orderid);
        jsonObject.addProperty("discount", Integer.parseInt(discount));
        jsonObject.addProperty("type", "plan");
        jsonObject.addProperty("value", Integer.parseInt(value));


        Call<ResponseBody> call = apiService.MakePaymewntApi(PhotoApp.getInstance().getstoreSharedpref().getSharedValue("apitoken"), jsonObject);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                PhotoApp.getInstance().createLog("response.code()"+response.code());
                if (response.code() == 200) {
                    String html = null;
                    try {
                        html = response.body().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Document document = Jsoup.parse(html);
                    Elements elements = document.select("title");
                    PhotoApp.getInstance().createLog("html" + html);
                    PhotoApp.getInstance().createLog("MakePayment Respinse");

                    alertDialog.dismiss();
                    progressDialog.dismiss();
                    loadWebView(html,orderid);
                } else if (response.code() == 400) {

                    //PhotoApp.getInstance().showToast("invalid coupon code");
                    progressDialog.dismiss();
                    PhotoApp.getInstance().createLog("400");

                    alertDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                PhotoApp.getInstance().showToast("Failure issue regarding web service");
                progressDialog.dismiss();

                PhotoApp.getInstance().createLog("excep " + t.getMessage());
            }
        });
    }

    private void loadWebView(String myHtmlString,String orderid) {
        Intent i=new Intent(getActivity(),LoadHtmlActivity.class);
        i.putExtra(Cons.myHtmlString,myHtmlString);
        i.putExtra(Cons.orderid,orderid);
        startActivity(i);

        }

    private void CreateDialogForConfirm(String price) {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = getView().findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_confirm, viewGroup, false);


        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        TextView totalAmount = (TextView) dialogView.findViewById(R.id.totalAmount);

        Button coupon_button = (Button) dialogView.findViewById(R.id.buttonOk);

        totalAmount.setText(getResources().getString(R.string.totalAmount) + " " + price);
        //finally creating the alert dialog and displaying it
        alertDialog = builder.create();
        alertDialog.show();
        coupon_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  MakePaymentApiApis(price);

            }
        });

    }
//
//    private void OrderCreateApis(String type, String message, String coupon) {
//        PhotoApp.getInstance().createLog("type"+type);
//        PhotoApp.getInstance().createLog("coupon"+coupon);
//        PhotoApp.getInstance().createLog("Order Api");
//
//        final ProgressDialog progressDialog = new ProgressDialog(getContext());
//        progressDialog.setCancelable(false); // set cancelable to false
//        progressDialog.setMessage("Please Wait"); // set message
//        progressDialog.show();
//        final ApiInterface apiService =
//                ApiClient.getClient().create(ApiInterface.class);
//        //put something inside the map, could be null
//        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty("value",type);
//        jsonObject.addProperty("coupon",coupon);
//        jsonObject.addProperty("type","plan");
//        Map<String,String> hashMap=new HashMap<>();
//        hashMap.put("value",type);
//        hashMap.put("coupon",coupon);
//        hashMap.put("type","plan");
//        Call<OrderCreatePojo> call = apiService.OrderCreateApi(PhotoApp.getInstance().getstoreSharedpref().getSharedValue("apitoken"),hashMap);
//        call.enqueue(new Callback<OrderCreatePojo>() {
//            @Override
//            public void onResponse(Call<OrderCreatePojo> call, Response<OrderCreatePojo> response) {
//                if (response.code() == 200) {
//                    progressDialog.dismiss();
//                    OrderCreatePojo jsonResponse = response.body();
//                    PhotoApp.getInstance().showToast(response.body().getMessage());
//                    if (response.body().getPlan_type().equalsIgnoreCase("smart")) {
//                        planname.setText(getResources().getString(R.string.smartPlan));
//                    } else {
//                        planname.setText(getResources().getString(R.string.enterpriseplan));
//                    }
//                    amount.setText("\u20B9" + " " + response.body().getTotal_amount() + " " + "/" + "-");
//                    amounttotak.setText("\u20B9" + " " + response.body().getTotal_amount() + " " + "/" + "-");
//                    PhotoApp.getInstance().createLog("Order  RESPONSE " + jsonResponse);
//                    progressDialog.dismiss();
//                    String totalAmount = jsonResponse.getTotal_amount();
//                    String plan = jsonResponse.getPlan_type();
//                    String order_id = jsonResponse.getOrder_id();
//                    MakePaymentApiApis(type,String.valueOf(Integer.parseInt(totalAmount)*100),"0",order_id);
//                } else if (response.code() == 400) {
//                    progressDialog.dismiss();
//                    PhotoApp.getInstance().createLog("400 Code response"+response.body());
//
//                    //alertDialog.dismiss();
//
//                    PhotoApp.getInstance().showToast("400");
//                    progressDialog.dismiss();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<OrderCreatePojo> call, Throwable t) {
//                //alertDialog.dismiss();
//
//                PhotoApp.getInstance().showToast("Failure issue regarding web service");
//                progressDialog.dismiss();
//                PhotoApp.getInstance().createLog("excep " + t.getMessage());
//            }
//        });
//    }

    private void ApplyCouponApis(String type, String message, String coupon) {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show();
        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
//put something inside the map, could be null
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("coupon", coupon);
        jsonObject.addProperty("plan_id", type);
        Call<ApplyCoupon> call = apiService.ApplyCouponApi(PhotoApp.getInstance().getstoreSharedpref().getSharedValue("apitoken"), jsonObject);
        call.enqueue(new Callback<ApplyCoupon>() {
            @Override
            public void onResponse(Call<ApplyCoupon> call, Response<ApplyCoupon> response) {
                if (response.code() == 200) {
                    //alertDialog.dismiss();
                    ApplyCoupon jsonResponse = response.body();
                    PhotoApp.getInstance().showToast(response.body().getMessage());
                    PhotoApp.getInstance().createLog("SUB CATEGORY RESPONSE " + jsonResponse);
                    PhotoApp.getInstance().getstoreSharedpref().storeSharedValue(Cons.coupon, coupon);
                    Cons.coupon = coupon;
                    key = "true";
                    progressDialog.dismiss();
                    invalidCode.setTextColor(getResources().getColor(R.color.green_color_picker));
                    invalidCode.setText(getResources().getString(R.string.suceesfullyapplied));


                } else if (response.code() == 400) {
                    //alertDialog.dismiss();
                    invalidCode.setTextColor(getResources().getColor(R.color.red));

                    invalidCode.setText(getResources().getString(R.string.invalid));

                    PhotoApp.getInstance().showToast("invalid coupon code");
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ApplyCoupon> call, Throwable t) {
                alertDialog.dismiss();
                PhotoApp.getInstance().showToast("Failure issue regarding web service");
                progressDialog.dismiss();
                PhotoApp.getInstance().createLog("excep " + t.getMessage());
            }
        });
    }

    private void showSevenDayCustomDialog(String type, String message1) {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = getView().findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialogseventext_coupon, viewGroup, false);


        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);
        TextView coupon_button = (TextView) dialogView.findViewById(R.id.button_ok);

        //finally creating the alert dialog and displaying it
        alertDialog = builder.create();
        alertDialog.show();
        coupon_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PhotoApp.getInstance().showToast(getResources().getString(R.string.novalue));
                alertDialog.dismiss();
            }
        });
    }

    private void showCancelDialog(String type, String message1) {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = getView().findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_cancel, viewGroup, false);


        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        TextView button_no = (TextView) dialogView.findViewById(R.id.button_no);
        TextView btn_yes = (TextView) dialogView.findViewById(R.id.btn_yes);


        //finally creating the alert dialog and displaying it
        alertDialog = builder.create();
        alertDialog.show();
        button_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                CancelCouponApi();

            }
        });
    }


    private void showCustomDialog(String type, String message1, String strplanname) {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = getView().findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_coupon, viewGroup, false);


        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);
        EditText et_coupon = (EditText) dialogView.findViewById(R.id.et_coupon);
        Button coupon_button = (Button) dialogView.findViewById(R.id.buttonOk);
        TextView message = (TextView) dialogView.findViewById(R.id.message);
        planname = (TextView) dialogView.findViewById(R.id.textView94);
        amount = (TextView) dialogView.findViewById(R.id.textView106);
        amounttotak = (TextView) dialogView.findViewById(R.id.amounbt);
        ImageView close=(ImageView)dialogView.findViewById(R.id.imageView68);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        planname.setText(strplanname);

        Button buttonmakePayment = (Button) dialogView.findViewById(R.id.buttonmakePayment);
        key = "false";
        invalidCode = (TextView) dialogView.findViewById(R.id.invalidCode);
        message.setText(message1);
        //finally creating the alert dialog and displaying it
        alertDialog = builder.create();
        alertDialog.show();
        //OrderCreateApis(type, message1, "");
        buttonmakePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhotoApp.getInstance().createLog("key=\"false\"" + key);
                PhotoApp.getInstance().createLog("Cons.coupon" + Cons.coupon);
                if (key.equalsIgnoreCase("true")) {
                    PhotoApp.getInstance().createLog("key=\"false\"" + key);
                    PhotoApp.getInstance().createLog("Cons.coupon" + Cons.coupon);
                    new OrderCreateApis(type, message1, Cons.coupon).execute();
                } else {
                    new OrderCreateApis(type, message1, "").execute();
                }

            }
        });
        coupon_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!et_coupon.getText().toString().matches("")) {

                    ApplyCouponApis(type, message1, et_coupon.getText().toString());

                }
            }
        });
    }

    public class OrderCreateApis extends AsyncTask<Void, Void, Void> {
        String type, message1, coupon, order_id;
        float total_amount;
        String statusCode;
        // private TransparentProgressDialog dialog;
        String responsemsg = "";
        private ProgressDialog progressDialog;

        public OrderCreateApis(String type, String message1, String coupon) {
            this.type = type;
            this.message1 = message1;
            this.coupon = coupon;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

           /* dialog = new TransparentProgressDialog(_mContext, R.drawable.loader);
            dialog.show();*/
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setCancelable(false); // set cancelable to false
            progressDialog.setMessage("Please Wait"); // set message
            progressDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {

            try {
                //   data_home_page_pojo.clear();
                PhotoApp.getInstance().createLog("API HITT");


                JSONObject jobj = new JSONObject();


                jobj.put("value", Integer.parseInt(type));
                jobj.put("coupon", coupon);
                jobj.put("type", "plan");
                PhotoApp.getInstance().createLog("jsonobj"+jobj.toString());

                ServiceHandler sh = new ServiceHandler();


                String jsonStr = sh.postDataArrayPostHeader(url, jobj);


                if (jsonStr != null) {
                    //{"details":{"status":"post can not be deleted."},"statusCode":"200"}
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    PhotoApp.getInstance().createLog("jsonObj CATEGORY RESPONSE " + jsonObj);

                    statusCode = jsonObj.getString("status");

                    PhotoApp.getInstance().createLog("STATUS CODE  " + statusCode);
                    PhotoApp.getInstance().createLog("jsonObj"+jsonObj.toString());

                    if (statusCode.equalsIgnoreCase("200")) {

                        responsemsg = "200";
                        JSONObject jsonObject = new JSONObject(jsonObj.toString());
                        order_id = jsonObject.getString("order_id");
                        total_amount =Float.valueOf(jsonObject.getString("total_amount"));


                        String plan_type = jsonObject.getString("plan_type");
                        PhotoApp.getInstance().createLog("order_id " + order_id);

                    } else {

                        PhotoApp.getInstance().createLog("===============no response =========");
                        responsemsg = "No response";
                    }


                }


            } catch (Exception e) {
                PhotoApp.getInstance().createLog("===============Exception e =========" + e.getMessage());
                e.printStackTrace();
                PhotoApp.getInstance().createLog("order_id " + order_id);
            }

            return null;
        }


        @Override
        protected void onPostExecute(Void result) {

            super.onPostExecute(result);
            try {


                progressDialog.dismiss();
                PhotoApp.getInstance().createLog("onPostExecute");
                // title.setText(PhotoApp.getInstance().getstoreSharedpref().getSharedValue("title"));
                MakePaymentApiApis(type,total_amount * 100, "0", order_id);



               /* if(data_home_page_pojo.size()>0)
                {
                    if(data_home_page_pojo.get(0).getType().equalsIgnoreCase("category"))
                    {
                        set_SubcateogryAdapter(0,data_home_page_pojo.get(0).getSubcategories_pojos());
                    }

                    if(data_home_page_pojo.get(1).getType().equalsIgnoreCase("subcategory"))
                    {

                        PhotoApp.getInstance().createLog("GREETINGSSS");
                        //set_GreetingAdapter(1,data_home_page_pojo.get(1).getGreetingspojo());
                    }


                }
*/


            } catch (Exception e) {
                PhotoApp.getInstance().createLog("Exception"+e.toString());
            }


        }


    }

    @Override
    public void callforProceed(String id, String code, String message, String type) {
        PhotoApp.getInstance().createLog("code" + code);
        PhotoApp.getInstance().createLog("type" + type);
        PhotoApp.getInstance().createLog("PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.repeatuser)" + PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.repeatuser));
        if (PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.repeatuser).equalsIgnoreCase("false")) {
            PhotoApp.getInstance().createLog("first time");
            if (code.equalsIgnoreCase("400")) {
                PhotoApp.getInstance().createLog("Basic Plans");
                PhotoApp.getInstance().showToast(message);
                Intent i = new Intent(getActivity(), HomeActivity.class);
                startActivity(i);
            } else if (code.equalsIgnoreCase("200")) {
                PhotoApp.getInstance().showToast(message);
                showSevenDayCustomDialog(type, message);
            } else if (code.equalsIgnoreCase("802")) {
                PhotoApp.getInstance().showToast(message);
                showSevenDayCustomDialog(type, message);
            }
        } else {
            PhotoApp.getInstance().createLog("PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.repeatuser)" + PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.repeatuser));
            PhotoApp.getInstance().createLog("id" + id);
            PhotoApp.getInstance().createLog("id" + id);



                PhotoApp.getInstance().createLog("id"+id);


                   if (id.equalsIgnoreCase("2")) {
                       if (current_plan.equalsIgnoreCase("enterprise")) {
                           PhotoApp.getInstance().createLog("current_plan" + current_plan+"is ");
                           PhotoApp.getInstance().showToast(message);
                           PhotoApp.getInstance().createLog("Confirm PlanApi");
                           ConfirmPlanApiApis(type, message);
                       } else {
                           PhotoApp.getInstance().createLog("current_plan" + current_plan+"is else ");
                           if (code.equalsIgnoreCase("200")) {
                               PhotoApp.getInstance().createLog("Code current_plan" + "200");
//                if (code.equalsIgnoreCase("200")) {
                               showCustomDialog(type, message, getResources().getString(R.string.smartplan));
                           }
                       }
                   }
                   else if (id.equalsIgnoreCase("3")) {
                       PhotoApp.getInstance().createLog("id"+id);
                       if (current_plan.equalsIgnoreCase("basic")) {
                           PhotoApp.getInstance().createLog("current_plan"+current_plan);
                           PhotoApp.getInstance().createLog("code"+code);
                           if (code.equalsIgnoreCase("200")) {
                               //// 400 cnacel
                               PhotoApp.getInstance().createLog("Enterprise Plans");
                               showCustomDialog(type, message, getResources().getString(R.string.enterpriseplan));

                           }
                           else if(code.equalsIgnoreCase("400")) {
                               //// 400 cnacel
                               PhotoApp.getInstance().createLog("Basic Plans");
                               Intent i = new Intent(getActivity(), HomeActivity.class);
                               startActivity(i);

                           }
                       } else if (code.equalsIgnoreCase("802")) {
                           PhotoApp.getInstance().createLog("Enterprise Plans");
                           showCustomDialog(type, message, getResources().getString(R.string.enterpriseplan));
                       }
                       else if (code.equalsIgnoreCase("200")) {
                           PhotoApp.getInstance().createLog("Enterprise Plans");
                           showCustomDialog(type, message, getResources().getString(R.string.enterpriseplan));
                       }

                   }

           else if (code.equalsIgnoreCase("400")) {
                PhotoApp.getInstance().showToast(message);
                PhotoApp.getInstance().createLog("Smart Plans");
           }

            else if (id.equalsIgnoreCase("1")) {
                PhotoApp.getInstance().createLog("id"+id);
                if (current_plan.equalsIgnoreCase("basic")) {
                    PhotoApp.getInstance().createLog("current_plan"+current_plan);
                    PhotoApp.getInstance().createLog("code"+code);
                    if (code.equalsIgnoreCase("200")) {
                        //// 400 cnacel
                        PhotoApp.getInstance().createLog("Basic Plans");
                        Intent i = new Intent(getActivity(), HomeActivity.class);
                        startActivity(i);

                    }
                    else if(code.equalsIgnoreCase("400")) {
                        //// 400 cnacel
                        PhotoApp.getInstance().createLog("Basic Plans");
                        Intent i = new Intent(getActivity(), HomeActivity.class);
                        startActivity(i);

                    }
                } else if (current_plan.equalsIgnoreCase("smart")) {
                    PhotoApp.getInstance().createLog("current_plan"+current_plan);
                    PhotoApp.getInstance().showToast(message);
                    showCancelDialog(type,message);
                } else if (current_plan.equalsIgnoreCase("enterprise")) {
                    PhotoApp.getInstance().createLog("current_plan"+current_plan);
                    PhotoApp.getInstance().showToast(message);
                    showCancelDialog(type,message);
                }

            }
        }


//      if(code.equalsIgnoreCase("803"))
//      {
//          CancelCouponApi();
//
//      }
//      else if(code.equalsIgnoreCase("802"))
//      {
//         // showCustomDialog(type,message);
//          ConfirmPlanApiApis(type,message);
//      }
//
//      else if(code.equalsIgnoreCase("801"))
//      {
//          ConfirmPlanApiApis(type,message);
//      }
//        else if(code.equalsIgnoreCase("400"))
//        {
//            ConfirmPlanApiApis(type,message);
//        }


    }
}
