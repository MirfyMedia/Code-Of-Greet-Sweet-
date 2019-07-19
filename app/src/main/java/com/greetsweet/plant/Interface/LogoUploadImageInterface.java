package com.greetsweet.plant.Interface;


import com.greetsweet.plant.greetapp.ModalClasses.LogoUploadModalClass;
import com.greetsweet.plant.greetapp.ModalClasses.UploadImageModalClass;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by Nancy on 2/1/2019.
 */

public interface LogoUploadImageInterface {

    @Multipart
    @POST("api/greeting/upload/logo/")
    Call<LogoUploadModalClass> logoupload(@Part MultipartBody.Part image, @Header("Authorization") String token);






    @Multipart
    @POST("api/greeting/logo/update/{id}/")
    Call<LogoUploadModalClass> logouploadUpdate(@Path("id") int id, @Part MultipartBody.Part image, @Header("Authorization") String token);

}
