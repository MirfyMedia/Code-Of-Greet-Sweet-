package com.greetsweet.plant.Interface;

import com.google.gson.JsonObject;
import com.greetsweet.plant.PhotoApp;
import com.greetsweet.plant.greetapp.ModalClasses.ApplyCoupon;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;

public interface ApiInterface {

    @GET("api/v1/plan/get/")
        //public void ,
    Call<GetPlansPojo> getPlansPojoApi(@Header("Authorization") String token);

    @POST("api/v1/plan/proceed/")
        //public void ,
    Call<ProceedPlans> getProceedApi(@Header("Authorization") String token,@Body JsonObject text);


    @POST("api/v1/plan/coupon/apply/")
        //public void ,
    Call<ApplyCoupon> ApplyCouponApi(@Header("Authorization") String token,@Body JsonObject text);

    @POST("api/v1/plan/cancel/")
        //public void ,
    Call<CancelCoupon> CancelCouponApi(@Header("Authorization") String token);

    @POST("api/v1/plan/confirm/change/")
        //public void ,
    Call<ConfirmPlan> ConfirmPlanApi(@Header("Authorization") String token,@Body JsonObject text);

    @POST("/api/v1/plan/order/create/")
    Call<OrderCreatePojo> OrderCreateApi(@Header("Authorization") String token,@Body JsonObject  c);

    @POST("api/v1/payments/")
        //public void ,
    Call<ResponseBody> MakePaymewntApi(@Header("Authorization") String token, @Body JsonObject text);


}



