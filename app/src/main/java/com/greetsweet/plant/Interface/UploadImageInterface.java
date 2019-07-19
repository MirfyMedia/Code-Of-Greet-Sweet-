package com.greetsweet.plant.Interface;


import com.greetsweet.plant.greetapp.ModalClasses.UploadImageModalClass;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Nancy on 2/1/2019.
 */

public interface UploadImageInterface {

    @Multipart
    @POST("api/greeting/upload/")
    Call<UploadImageModalClass> updateImage(@Part MultipartBody.Part image,@Part("is_favourite") RequestBody is_favourite,  @Header("Authorization") String token);

}
