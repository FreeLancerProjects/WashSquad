package com.creative.share.apps.wash_squad_driver.services;


import com.creative.share.apps.wash_squad_driver.models.AboutDataModel;
import com.creative.share.apps.wash_squad_driver.models.CarSizeDataModel;
import com.creative.share.apps.wash_squad_driver.models.CarTypeDataModel;
import com.creative.share.apps.wash_squad_driver.models.PlaceGeocodeData;
import com.creative.share.apps.wash_squad_driver.models.PlaceMapDetailsData;
import com.creative.share.apps.wash_squad_driver.models.QuestionDataModel;
import com.creative.share.apps.wash_squad_driver.models.ServiceDataModel;
import com.creative.share.apps.wash_squad_driver.models.TimeDataModel;
import com.creative.share.apps.wash_squad_driver.models.UserModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Service {


    @GET("place/findplacefromtext/json")
    Call<PlaceMapDetailsData> searchOnMap(@Query(value = "inputtype") String inputtype,
                                          @Query(value = "input") String input,
                                          @Query(value = "fields") String fields,
                                          @Query(value = "language") String language,
                                          @Query(value = "key") String key
    );

    @GET("geocode/json")
    Call<PlaceGeocodeData> getGeoData(@Query(value = "latlng") String latlng,
                                      @Query(value = "language") String language,
                                      @Query(value = "key") String key);

    @FormUrlEncoded
    @POST("api/client/register")
    Call<UserModel> signUp(@Field("full_name") String full_name,
                           @Field("phone_code") String phone_code,
                           @Field("phone") String phone,
                           @Field("password") String password,
                           @Field("software_type") int software_type
    );

    @FormUrlEncoded
    @POST("api/client/login")
    Call<UserModel> login(@Field("phone") String phone,
                          @Field("phone_code") String phone_code,
                          @Field("password") String password);

    @FormUrlEncoded
    @POST("api/client/cofirm-code")
    Call<ResponseBody> confirmCode(@Field("user_id") int user_id,
                                   @Field("code") String code
    );

    @FormUrlEncoded
    @POST("api/client/code/send")
    Call<ResponseBody> resendCode(@Field("user_id") int user_id
    );

    @GET("api/services")
    Call<ServiceDataModel> getAllServices();

    @GET("api/carSizes")
    Call<CarSizeDataModel> getCarSizes();

    @GET("api/carTypes")
    Call<CarTypeDataModel> getCarTypes();

    @GET("api/questions")
    Call<QuestionDataModel> getQuestion();

    @GET("api/about-us")
    Call<AboutDataModel> aboutApp();


    @FormUrlEncoded
    @POST("api/contact-us")
    Call<ResponseBody> sendContact(@Field("name") String name,
                                   @Field("email") String email,
                                   @Field("subject") String subject,
                                   @Field("message") String message
    );

    @FormUrlEncoded
    @POST("api/date/get/times")
    Call<TimeDataModel> getTime(@Field("date") String date);
}


