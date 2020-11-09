package com.app.ekhabeer.restClient;

import com.app.ekhabeer.utils.Constants;
import com.app.ekhabeer.utils.Model;

import org.json.JSONArray;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIInterface {

    @FormUrlEncoded
    @POST(Constants.BASE_URL_TWO)
    Call<Object> RegisterClient(@FieldMap HashMap<String,String> data);

    @FormUrlEncoded
    @POST(Constants.BASE_URL_TWO)
    Call<Object> RegisterUser(@FieldMap HashMap<String,String> data);

    @FormUrlEncoded
    @POST(Constants.BASE_URL_TWO)
    Call<Object> LoginUser(@FieldMap HashMap<String,String> data);

    @FormUrlEncoded
    @POST(Constants.BASE_URL_TWO)
    Call<Object> LoginConsultant(@FieldMap HashMap<String,String> data);

    @FormUrlEncoded
    @POST(Constants.BASE_URL_TWO)
    Call<Object> UserProfileAPI(@FieldMap HashMap<String,String> data);

    @FormUrlEncoded
    @POST(Constants.BASE_URL_TWO)
    Call<Object> SubCategoryAPI(@FieldMap HashMap<String,String> data);

    @FormUrlEncoded
    @POST(Constants.BASE_URL_TWO)
    Call<Object> GetDocList(@FieldMap HashMap<String,String> data);

    @FormUrlEncoded
    @POST(Constants.BASE_URL_TWO)
    Call<Object> GetDocInfo(@FieldMap HashMap<String,String> data);

    @FormUrlEncoded
    @POST(Constants.BASE_URL_TWO)
    Call<Object> UpdateUserProfile(@FieldMap HashMap<String,String> data);

    @FormUrlEncoded
    @POST(Constants.BASE_URL_TWO)
    Call<Object> UpdateConsulProfile(@FieldMap HashMap<String,String> data);

    @FormUrlEncoded
    @POST(Constants.BASE_URL_TWO)
    Call<Object> GetListForUser(@FieldMap HashMap<String,String> data);

    @FormUrlEncoded
    @POST(Constants.BASE_URL_TWO)
    Call<Object> GetListForConsul(@FieldMap HashMap<String,String> data);

    @FormUrlEncoded
    @POST(Constants.BASE_URL_TWO)
    Call<Object> GetInboxList(@FieldMap HashMap<String,String> data);

    @FormUrlEncoded
    @POST(Constants.BASE_URL_TWO)
    Call<Object> SendFeedback(@FieldMap HashMap<String,String> data);

    @FormUrlEncoded
    @POST(Constants.BASE_URL_TWO)
    Call<Object> SendMessage(@FieldMap HashMap<String,String> data);

    @FormUrlEncoded
    @POST(Constants.BASE_URL_TWO)
    Call<Object> GetConsultaionsForConsultants(@FieldMap HashMap<String,String> data);

    @FormUrlEncoded
    @POST(Constants.BASE_URL_TWO)
    Call<Object> GetConsultaionsForUsers(@FieldMap HashMap<String,String> data);

    @FormUrlEncoded
    @POST(Constants.BASE_URL_TWO)
    Call<Object> BookApointment(@FieldMap HashMap<String,String> data);

}
