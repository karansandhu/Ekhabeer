package com.app.ekhabeer.restClient;

import android.content.Context;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.app.ekhabeer.utils.Constants;
import com.app.ekhabeer.utils.Model;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Controller {
    static JSONObject retrieveJson=null;
    APICallbackListener mListener;
    Context context;
    String responseString = "";

    public Controller(APICallbackListener listener) {
        mListener = listener;

    }

    public void RegisterClientAPI(final HashMap<String,String> hashMap, final Context context) {
        mListener.onFetchStart();

        Call<Object> call = null;

        call =APIClient.createWrbServiceRequest().create(APIInterface.class).RegisterClient(hashMap);

        call.enqueue(new Callback<Object>() {

            @Override
            public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {

                Log.e("checkRegisterAPI", "onResponse>>>: " + response.message() + "><><" + response.body() + "><>" + response.code());
                if (response.message().equals("OK")) {

                    JSONObject value2 = null;
                    JSONObject value = null;
                    String status = "";
                    String message = "";
                    try {
                        value2 = new JSONObject(String.valueOf(new JSONObject(new Gson().toJson(response.body()))));
                        status = value2.getString("status");
                        message = value2.getString("message");
                        Log.e("checkNewMet", "1>>: " + status);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (status.equals("done")){

                        Toast.makeText(context, "Registration Successfull", Toast.LENGTH_SHORT).show();

                        mListener.onFetchProgress(value);
                        mListener.onFetchComplete();
                    }else if (status.equals("error")){

                        Toast.makeText(context, "Error: " + message, Toast.LENGTH_SHORT).show();
                        mListener.onFetchFailed();
                    }


                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("checkRegisterAPI", "onFailure>>>: " + t.getMessage());
//                AppController.constants.dismissProgressDialog();
            }
        });
    }

    public void RegisterUserAPI(final HashMap<String,String> hashMap, final Context context) {
        mListener.onFetchStart();

        Call<Object> call = null;

        Log.e("checkHasmapReg",">>" + hashMap);
        call =APIClient.createWrbServiceRequest().create(APIInterface.class).RegisterUser(hashMap);

        call.enqueue(new Callback<Object>() {

            @Override
            public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {

                Log.e("checkRegisterAPI", "onResponse>>>: " + response.message() + "><><" + response.body() + "><>" + response.code());
//                AppController.constants.dismissProgressDialog();
                if (response.message().equals("OK")) {

                    JSONObject value2 = null;
                    JSONObject value = null;
                    String status = "";
                    String message = "";
                    try {
                        value2 = new JSONObject(String.valueOf(new JSONObject(new Gson().toJson(response.body()))));
                        status = value2.getString("status");
                        message = value2.getString("message");
                        Log.e("checkNewMet", "1>>: " + status);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (status.equals("success")){

                        Toast.makeText(context, "Registration Successfull", Toast.LENGTH_SHORT).show();

                        mListener.onFetchProgress(value);
                        mListener.onFetchComplete();
                    }else if (status.equals("error")){

                        Toast.makeText(context, "Error: " + message, Toast.LENGTH_SHORT).show();
                        mListener.onFetchFailed();
                    }


                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("checkRegisterAPI", "onFailure>>>: " + t.getMessage());
//                AppController.constants.dismissProgressDialog();
            }
        });
    }
    public void LoginConsultantAPI(final HashMap<String,String> hashMap, final Context context) {
        mListener.onFetchStart();

        Call<Object> call = null;

        call =APIClient.createWrbServiceRequest().create(APIInterface.class).LoginConsultant(hashMap);

        call.enqueue(new Callback<Object>() {

            @Override
            public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {

                Log.e("checkConsultLogin", "onResponse>>>: " + response.message() + "><><" + response.body() + "><>" + response.code());
                if (response.message().equals("OK")) {

                    JSONObject value2 = null;
                    JSONObject value = null;
                    String status = "";
                    String message = "";
                    try {
                        value2 = new JSONObject(String.valueOf(new JSONObject(new Gson().toJson(response.body()))));
                        status = value2.getString("status");
                        message = value2.getString("message");
                        Log.e("checkConsultLogin", "1>>: " + status);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (status.equals("success")){
                        try {
                            value = new JSONObject(new JSONObject(new Gson().toJson(response.body())).getString(Constants.DATA));
                            Log.e("checkConsultLogin", "1>>: " + value);
                        } catch (JSONException e) {
                            Log.e("checkConsultLogin", "JSONException>6>>: " + e.getMessage());
                            e.printStackTrace();
                        }

                        mListener.onFetchProgress(value);
                        mListener.onFetchComplete();
                    }else{
                        Toast.makeText(context, "Error: " + message, Toast.LENGTH_SHORT).show();
                        mListener.onFetchFailed();
                    }


                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("checkConsultLogin", "onFailure>>>: " + t.getMessage());
//                AppController.constants.dismissProgressDialog();
            }
        });
    }
    public void LoginUserAPI(final HashMap<String,String> hashMap, final Context context) {
        mListener.onFetchStart();

        Call<Object> call = null;

        Log.e("checkHasmapLOgin",">>" + hashMap);
        call =APIClient.createWrbServiceRequest().create(APIInterface.class).LoginUser(hashMap);

        call.enqueue(new Callback<Object>() {

            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.e("checkHasmapLOgin", "onResponse>>>: " + response.message() + "><><" + response.body() + "><>" + response.code());
                if (response.message().equals("OK")) {

                    JSONObject value2 = null;
                    JSONObject value = null;
                    String status = "";
                    String message = "";
                    try {
                        value2 = new JSONObject(String.valueOf(new JSONObject(new Gson().toJson(response.body()))));
                        status = value2.getString("status");
                        message = value2.getString("message");
                        Log.e("checkNewMet", "1>>: " + status);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (status.equals("success")){
                        try {
                            value = new JSONObject(new JSONObject(new Gson().toJson(response.body())).getString(Constants.DATA));
                            Log.e("checkNewMet", "1>>: " + value);
                        } catch (JSONException e) {
                            Log.e("checkNewMet", "JSONException>6>>: " + e.getMessage());
                            e.printStackTrace();
                        }

                        mListener.onFetchProgress(value);
                        mListener.onFetchComplete();
                    }else{
                        Toast.makeText(context, "Error: " + message, Toast.LENGTH_SHORT).show();
                        mListener.onFetchFailed();
                    }


                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("checkHasmapLOgin", "onFailure>>>: " + t.getMessage());
            }
        });
    }

    public void UserProfileAPI(final HashMap<String,String> hashMap, final Context context) {
        mListener.onFetchStart();

        Call<Object> call = null;

        Log.e("checkUserProfileAPI",">>" + hashMap);
        call =APIClient.createWrbServiceRequest().create(APIInterface.class).UserProfileAPI(hashMap);

        Model model = new Model();
        Constants.model.setLastApiHit(Constants.GET_PROFILE_INFO);
        call.enqueue(new Callback<Object>() {

            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.e("checkUserProfileAPI", "onResponse>>>: " + response.message() + "><><" + response.body() + "><>" + response.code());
                if (response.message().equals("OK")) {

                    JSONObject value2 = null;
                    JSONObject value = null;
                    String status = "";
                    String message = "";
                    try {
                        value2 = new JSONObject(String.valueOf(new JSONObject(new Gson().toJson(response.body()))));
                        status = value2.getString("status");
                        message = value2.getString("message");
                        Log.e("checkUserProfileAPI", "1>>: " + status);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (status.equals("success")){
                        try {
                            value = new JSONObject(new JSONObject(new Gson().toJson(response.body())).getString(Constants.DATA));
                            Log.e("checkUserProfileAPI", "1>>: " + value);
                        } catch (JSONException e) {
                            Log.e("checkUserProfileAPI", "JSONException>6>>: " + e.getMessage());
                            e.printStackTrace();
                        }

                        mListener.onFetchProgress(value);
                        mListener.onFetchComplete();
                    }else{
                        Toast.makeText(context, "Error: " + message, Toast.LENGTH_SHORT).show();
                        mListener.onFetchFailed();
                    }


                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("checkHasmapLOgin", "onFailure>>>: " + t.getMessage());
            }
        });
    }

    public void SubCategoryAPI(final HashMap<String,String> hashMap, final Context context) {
        mListener.onFetchStart();

        Call<Object> call = null;

        Log.e("checkSubCate",">>" + hashMap);
        call =APIClient.createWrbServiceRequest().create(APIInterface.class).SubCategoryAPI(hashMap);

        call.enqueue(new Callback<Object>() {

            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.e("checkSubCate", "onResponse>>>: " + response.message() + "><><" + response.body() + "><>" + response.code());
                if (response.message().equals("OK")) {
                    HashMap hashMap1 = null;
                    JSONObject value2 = null;
                    JSONArray value = null;
                    String status = "";
                    String message = "";
                    ArrayList<HashMap<String,String>> arrayList;
                    arrayList = new ArrayList<>();
                    try {
                        value2 = new JSONObject(String.valueOf(new JSONObject(new Gson().toJson(response.body()))));
                        status = value2.getString("status");
                        message = value2.getString("message");
                        Log.e("checkSubCate", "1>>: " + status);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (status.equals("success")){
                        try {
                            value = new JSONArray(new JSONObject(new Gson().toJson(response.body())).getString(Constants.DATA));
                            Log.e("checkSubCate", "1>>: " + value);
                            for(int i = 0; i < value.length(); i++) {
                                JSONObject dataObject = value.getJSONObject(i);
                                hashMap1 = new HashMap();
                                hashMap1.put(Constants.NAME,dataObject.getString(Constants.NAME));
                                hashMap1.put(Constants.ID,dataObject.getString(Constants.ID));
//                                hashMap1.put(Constants.ID,dataObject.getString(Constants.ID));
//                                Constants.model.categories_list.add(hashMap);
                                arrayList.add(hashMap1);

                                Log.e("checkSubCate", "hashMap1>>: " + arrayList);
                            }

                            Constants.model.setCategories_list(arrayList);
                            Log.e("checkSubCate", "HashMap>6>>: " + Constants.model.getCategories_list());
                        } catch (JSONException e) {
                            Log.e("checkSubCate", "JSONException>6>>: " + e.getMessage());
                            e.printStackTrace();
                        }

                        mListener.onFetchProgress(Constants.model.getCategories_list(),null);
                        mListener.onFetchComplete();
                    }else{
                        Toast.makeText(context, "Error: " + message, Toast.LENGTH_SHORT).show();
                        mListener.onFetchFailed();
                    }


                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("checkSubCate", "onFailure>>>: " + t.getMessage());
            }
        });
    }

    public void GetDocList(final HashMap<String,String> hashMap, final Context context) {
        mListener.onFetchStart();

        Call<Object> call = null;

        Log.e("checkDocFlow",">>" + hashMap);
        call =APIClient.createWrbServiceRequest().create(APIInterface.class).GetDocList(hashMap);

        call.enqueue(new Callback<Object>() {

            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.e("checkDocFlow", "onResponse>>>: " + response.message() + "><><" + response.body() + "><>" + response.code());
                if (response.message().equals("OK")) {
                    HashMap hashMap1 = null;
                    JSONObject value2 = null;
                    JSONArray value = null;
                    String status = "";
                    String message = "";
                    ArrayList<HashMap<String,String>> arrayList;
                    arrayList = new ArrayList<>();
                    try {
                        value2 = new JSONObject(String.valueOf(new JSONObject(new Gson().toJson(response.body()))));
                        status = value2.getString("status");
                        message = value2.getString("message");
                        Log.e("checkDocFlow", "1>>: " + status);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (status.equals("success")){
                        try {
                            value = new JSONArray(new JSONObject(new Gson().toJson(response.body())).getString(Constants.DATA));
                            Log.e("checkDocFlow", "1>>: " + value);
                            for(int i = 0; i < value.length(); i++) {
                                JSONObject dataObject = value.getJSONObject(i);
                                hashMap1 = new HashMap();
                                hashMap1.put(Constants.FNAME,dataObject.getString(Constants.FNAME));
                                hashMap1.put(Constants.PROFILE,dataObject.getString(Constants.PROFILE));
                                hashMap1.put(Constants.ID,dataObject.getString(Constants.ID));
                                hashMap1.put(Constants.LNAME,dataObject.getString(Constants.LNAME));
                                hashMap1.put(Constants.DOB,dataObject.getString(Constants.DOB));
                                hashMap1.put(Constants.AGE,dataObject.getString(Constants.AGE));
                                hashMap1.put(Constants.COUNTRY,dataObject.getString(Constants.COUNTRY));
                                hashMap1.put(Constants.GENDER,dataObject.getString(Constants.GENDER));
                                hashMap1.put(Constants.MOBILE,dataObject.getString(Constants.MOBILE));
                                hashMap1.put(Constants.EMAIL,dataObject.getString(Constants.EMAIL));
                                hashMap1.put(Constants.ADDRESS,dataObject.getString(Constants.ADDRESS));
                                hashMap1.put(Constants.MEMBERSHIP,dataObject.getString(Constants.MEMBERSHIP));
                                hashMap1.put(Constants.CONSULTATION,dataObject.getString(Constants.CONSULTATION));
                                hashMap1.put(Constants.ONE_EST_COST,dataObject.getString(Constants.ONE_EST_COST));
                                hashMap1.put(Constants.THREE_EST_COST,dataObject.getString(Constants.THREE_EST_COST));
                                hashMap1.put(Constants.THIRTY_EST_COST,dataObject.getString(Constants.THIRTY_EST_COST));
                                hashMap1.put(Constants.FORTYFIVE_EST_COST,dataObject.getString(Constants.FORTYFIVE_EST_COST));
                                hashMap1.put(Constants.SIXTY_EST_COST,dataObject.getString(Constants.SIXTY_EST_COST));
                                hashMap1.put(Constants.SPECIAL,dataObject.getString(Constants.SPECIAL));
//                                Constants.model.categories_list.add(hashMap);
                                arrayList.add(hashMap1);

                                Log.e("checkDocFlow", "hashMap1>>: " + arrayList);
                            }

                            Constants.model.setCategories_list(arrayList);
                            Log.e("checkDocFlow", "HashMap>6>>: " + Constants.model.getCategories_list());
                        } catch (JSONException e) {
                            Log.e("checkDocFlow", "JSONException>6>>: " + e.getMessage());
                            e.printStackTrace();
                        }

                        Log.e("checkDocFlow", "main>6>>: " + Constants.model.getJsonArray());
                        mListener.onFetchProgress(Constants.model.getCategories_list(),null);
                        mListener.onFetchComplete();
                    }else{
                        Toast.makeText(context, "Error: " + message, Toast.LENGTH_SHORT).show();
                        mListener.onFetchFailed();
                    }


                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("checkDocFlow", "onFailure>>>: " + t.getMessage());
            }
        });
    }

    public void GetDocInfo(final HashMap<String,String> hashMap, final Context context) {
        mListener.onFetchStart();

        Call<Object> call = null;

        call =APIClient.createWrbServiceRequest().create(APIInterface.class).GetDocInfo(hashMap);

        call.enqueue(new Callback<Object>() {

            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.e("checkDocINFO", "onResponse>>>: " + response.message() + "><><" + response.body() + "><>" + response.code());
                if (response.message().equals("OK")) {
                    HashMap hashMap1 = null;
                    JSONObject value2 = null;
                    JSONObject value = null;
                    String status = "";
                    ArrayList<String> listdata = null;
                    String message = "";
                    Model model = new Model();
                    ArrayList<HashMap<String,String>> arrayList;
                    arrayList = new ArrayList<>();
                    try {
                        value2 = new JSONObject(String.valueOf(new JSONObject(new Gson().toJson(response.body()))));
                        status = value2.getString("status");
                        message = value2.getString("message");
                        Log.e("checkDocINFO", "1>>: " + status);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (status.equals("success")){
                        try {
                            value = new JSONObject(new JSONObject(new Gson().toJson(response.body())).getString(Constants.DATA));
                            Log.e("checkDocINFO", "2>>: " + value);
//                            for(int i = 0; i < value.length(); i++) {
//                                JSONObject dataObject = value.getJSONObject();
                                hashMap1 = new HashMap();
                                model.setJsonArray((JSONArray) value.get(Constants.ITEM_DATA));
                                model.setDataArray((JSONArray) value.get(Constants.ITEM_DATA));
//                                Log.e("checkDocINFO", "3>>: " + model.getDataArray());
                                hashMap1.put(Constants.FNAME,value.getString(Constants.FNAME));
                                hashMap1.put(Constants.PROFILE,value.getString(Constants.PROFILE));
                                hashMap1.put(Constants.ID,value.getString(Constants.ID));
                                hashMap1.put(Constants.LNAME,value.getString(Constants.LNAME));
                                hashMap1.put(Constants.DOB,value.getString(Constants.DOB));
                                hashMap1.put(Constants.AGE,value.getString(Constants.AGE));
                                hashMap1.put(Constants.COUNTRY,value.getString(Constants.COUNTRY));
                                hashMap1.put(Constants.GENDER,value.getString(Constants.GENDER));
                                hashMap1.put(Constants.MOBILE,value.getString(Constants.MOBILE));
                                hashMap1.put(Constants.EMAIL,value.getString(Constants.EMAIL));
                                hashMap1.put(Constants.ADDRESS,value.getString(Constants.ADDRESS));
                                hashMap1.put(Constants.MEMBERSHIP,value.getString(Constants.MEMBERSHIP));
                                hashMap1.put(Constants.CONSULTATION,value.getString(Constants.CONSULTATION));
                                hashMap1.put(Constants.ONE_EST_COST,value.getString(Constants.ONE_EST_COST));
                                hashMap1.put(Constants.THREE_EST_COST,value.getString(Constants.THREE_EST_COST));
                                hashMap1.put(Constants.THIRTY_EST_COST,value.getString(Constants.THIRTY_EST_COST));
                                hashMap1.put(Constants.FORTYFIVE_EST_COST,value.getString(Constants.FORTYFIVE_EST_COST));
                                hashMap1.put(Constants.SIXTY_EST_COST,value.getString(Constants.SIXTY_EST_COST));
                                hashMap1.put(Constants.SPECIAL,value.getString(Constants.SPECIAL));
                                hashMap1.put(Constants.ITEM_DATA,model.getDataArray());
//                                Constants.model.categories_list.add(hashMap);
                                arrayList.add(hashMap1);

                                Log.e("checkDocINFO", "hashMap1>>: " + arrayList);
//                            }
                            listdata = new ArrayList<String>();
                            JSONArray jArray = model.getDataArray();
                            if (jArray != null) {
                                for (int i=0;i<jArray.length();i++){
                                    listdata.add(jArray.getString(i));
                                }
                            }
                            Constants.model.setCategories_list(arrayList);
                            Log.e("checkDocINFO", "HashMap>6>>: " + listdata + "><>||||><>" +  Constants.model.getCategories_list());
                        } catch (JSONException e) {
                            Log.e("checkDocINFO", "JSONException>6>>: " + e.getMessage());
                            e.printStackTrace();
                        }

                        mListener.onFetchProgress(Constants.model.getCategories_list(),model.getDataArray());
                        mListener.onFetchComplete();
                    }else{
                        Toast.makeText(context, "Error: " + message, Toast.LENGTH_SHORT).show();
                        mListener.onFetchFailed();
                    }


                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("checkDocINFO", "onFailure>>>: " + t.getMessage());
            }
        });
    }


    public void UpdateUserProfile(final HashMap<String,String> hashMap, final Context context) {
        mListener.onFetchStart();

        Call<Object> call = null;

        call =APIClient.createWrbServiceRequest().create(APIInterface.class).UpdateUserProfile(hashMap);

        Model model = new Model();
        model.setLastApiHit(Constants.UPDATE_PROFILE);
        call.enqueue(new Callback<Object>() {

            @Override
            public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {

                Log.e("checkUpdateUserAPI", "onResponse>>>: " + response.message() + "><><" + response.body() + "><>" + response.code());
                if (response.message().equals("OK")) {

                    JSONObject value2 = null;
                    JSONObject value = null;
                    String status = "";
                    String message = "";
                    try {
                        value2 = new JSONObject(String.valueOf(new JSONObject(new Gson().toJson(response.body()))));
                        status = value2.getString("status");
                        message = value2.getString("message");
                        Log.e("checkUpdateUserAPI", "1>>: " + status);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (status.equals("success")){
                        try {
                            value = new JSONObject(new JSONObject(new Gson().toJson(response.body())).getString(Constants.DATA));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(context, "Profile Updated Successfully", Toast.LENGTH_SHORT).show();

                        mListener.onFetchProgress(value);
                        mListener.onFetchComplete();
                    }else if (status.equals("error")){

                        Toast.makeText(context, "Error: " + message, Toast.LENGTH_SHORT).show();
                        mListener.onFetchFailed();
                        mListener.onSuccessORError("",message,null);
                    }


                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("checkUpdateUserAPI", "onFailure>>>: " + t.getMessage());
//                AppController.constants.dismissProgressDialog();
            }
        });
    }

    public void UpdateConsulProfile(final HashMap<String,String> hashMap, final Context context) {
        mListener.onFetchStart();

        Call<Object> call = null;

        call =APIClient.createWrbServiceRequest().create(APIInterface.class).UpdateConsulProfile(hashMap);

        Model model = new Model();
        model.setLastApiHit(Constants.UPDATE_PROFILE);
        call.enqueue(new Callback<Object>() {

            @Override
            public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {

                Log.e("checkUpdateConsulAPI", "onResponse>>>: " + response.message() + "><><" + response.body() + "><>" + response.code());
                if (response.message().equals("OK")) {

                    JSONObject value2 = null;
                    JSONObject value = null;
                    String status = "";
                    String message = "";
                    try {
                        value2 = new JSONObject(String.valueOf(new JSONObject(new Gson().toJson(response.body()))));
                        status = value2.getString("status");
                        message = value2.getString("message");
                        Log.e("checkUpdateConsulAPI", "1>>: " + status);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (status.equals("success")){
                        try {
                            value = new JSONObject(new JSONObject(new Gson().toJson(response.body())).getString(Constants.DATA));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(context, "Profile Updated Successfully", Toast.LENGTH_SHORT).show();

                        mListener.onFetchProgress(value);
                        mListener.onFetchComplete();
                    }else if (status.equals("error")){

                        Toast.makeText(context, "Error: " + message, Toast.LENGTH_SHORT).show();
                        mListener.onSuccessORError("",message,null);
                        mListener.onFetchFailed();
                    }


                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("checkUpdateConsulAPI", "onFailure>>>: " + t.getMessage());
//                AppController.constants.dismissProgressDialog();
            }
        });
    }


    public void GetListForUser(final HashMap<String,String> hashMap, final Context context) {
        mListener.onFetchStart();

        Call<Object> call = null;

        Log.e("checkInboxFlow",">>" + hashMap);
        call =APIClient.createWrbServiceRequest().create(APIInterface.class).GetListForUser(hashMap);

        call.enqueue(new Callback<Object>() {

            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.e("checkInboxFlow", "onResponse>>>: " + response.message() + "><><" + response.body() + "><>" + response.code());
                if (response.message().equals("OK")) {
                    HashMap hashMap1 = null;
                    JSONObject value2 = null;
                    JSONArray value = null;
                    String status = "";
                    Model model = new Model();
                    String message = "";
                    ArrayList<HashMap<String,String>> arrayList;
                    arrayList = new ArrayList<>();
                    try {
                        value2 = new JSONObject(String.valueOf(new JSONObject(new Gson().toJson(response.body()))));
                        status = value2.getString("status");
                        message = value2.getString("message");
                        Log.e("checkInboxFlow", "1>>: " + status);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (status.equals("success")){
                        try {
                            value = new JSONArray(new JSONObject(new Gson().toJson(response.body())).getString(Constants.DATA));
                            Log.e("checkInboxFlow", "2>>: " + value);
//                            model.setJsonArray((JSONArray) value.get(Constants.MESSAGE_LIST));
                            for(int i = 0; i < value.length(); i++) {
                                JSONObject dataObject = value.getJSONObject(i);
                                hashMap1 = new HashMap();
                                hashMap1.put(Constants.ID, dataObject.getString(Constants.ID));
                                hashMap1.put(Constants.CONSULTANT_ID, dataObject.getString(Constants.CONSULTANT_ID));
                                hashMap1.put(Constants.FNAME, dataObject.getString(Constants.FNAME));
                                hashMap1.put(Constants.LNAME, dataObject.getString(Constants.LNAME));
                                hashMap1.put(Constants.PROFILE, dataObject.getString(Constants.PROFILE));
                            }
                            arrayList.add(hashMap1);

                            Log.e("checkInboxFlow", "hashMap1>>: " + arrayList);

                            Constants.model.setCategories_list(arrayList);
                        } catch (JSONException e) {
                            Log.e("checkInboxFlow", "JSONException>6>>: " + e.getMessage());
                            e.printStackTrace();
                        }

                        mListener.onFetchProgress(Constants.model.getCategories_list(), null);
                        mListener.onFetchComplete();
                    }else{
                        Toast.makeText(context, "Error: " + message, Toast.LENGTH_SHORT).show();
                        mListener.onFetchFailed();
                    }


                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("checkInboxFlow", "onFailure>>>: " + t.getMessage());
            }
        });
    }


    public void GetListForConsul(final HashMap<String,String> hashMap, final Context context) {
        mListener.onFetchStart();

        Call<Object> call = null;

        Log.e("checkInboxFlow",">>" + hashMap);
        call =APIClient.createWrbServiceRequest().create(APIInterface.class).GetListForConsul(hashMap);

        call.enqueue(new Callback<Object>() {

            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.e("checkInboxFlow", "onResponse>>>: " + response.message() + "><><" + response.body() + "><>" + response.code());
                if (response.message().equals("OK")) {
                    HashMap hashMap1 = null;
                    JSONObject value2 = null;
                    JSONArray value = null;
                    String status = "";
                    Model model = new Model();
                    String message = "";
                    ArrayList<HashMap<String,String>> arrayList;
                    arrayList = new ArrayList<>();
                    try {
                        value2 = new JSONObject(String.valueOf(new JSONObject(new Gson().toJson(response.body()))));
                        status = value2.getString("status");
                        message = value2.getString("message");
                        Log.e("checkInboxFlow", "1>>: " + status);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (status.equals("success")){
                        try {
                            value = new JSONArray(new JSONObject(new Gson().toJson(response.body())).getString(Constants.DATA));
                            Log.e("checkInboxFlow", "2>>: " + value);
//                            model.setJsonArray((JSONArray) value.get(Constants.MESSAGE_LIST));
                            for(int i = 0; i < value.length(); i++) {
                                JSONObject dataObject = value.getJSONObject(i);
                                hashMap1 = new HashMap();
                                hashMap1.put(Constants.ID,dataObject.getString(Constants.ID));
                                hashMap1.put(Constants.USER_ID,dataObject.getString(Constants.USER_ID));
                                hashMap1.put(Constants.FNAME,dataObject.getString(Constants.FNAME));
                                hashMap1.put(Constants.LNAME,dataObject.getString(Constants.LNAME));
                                hashMap1.put(Constants.PROFILE,dataObject.getString(Constants.PROFILE));
                                arrayList.add(hashMap1);

                            }

                            Log.e("checkInboxFlow", "hashMap1>>: " + arrayList);

                            Constants.model.setCategories_list(arrayList);
                        } catch (JSONException e) {
                            Log.e("checkInboxFlow", "JSONException>6>>: " + e.getMessage());
                            e.printStackTrace();
                        }

                        mListener.onFetchProgress(Constants.model.getCategories_list(), null);
                        mListener.onFetchComplete();
                    }else{
                        Toast.makeText(context, "Error: " + message, Toast.LENGTH_SHORT).show();
                        mListener.onFetchFailed();
                    }


                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("checkInboxFlow", "onFailure>>>: " + t.getMessage());
            }
        });
    }


    public void GetInboxList(final HashMap<String,String> hashMap, final Context context) {
        mListener.onFetchStart();

        Call<Object> call = null;

        Log.e("checkInboxFlow",">>" + hashMap);
        call =APIClient.createWrbServiceRequest().create(APIInterface.class).GetInboxList(hashMap);
        Model model = new Model();
        Constants.model.setLastApiHit(Constants.GET_MESSAGES_LIST);
        Log.e("checkSMSflow",">GetInboxList0>" + Constants.model.getLastApiHit());

        call.enqueue(new Callback<Object>() {

            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.e("checkInboxFlow", "onResponse>>>: " + response.message() + "><><" + response.body() + "><>" + response.code());
                if (response.message().equals("OK")) {
                    HashMap hashMap1 = null;
                    JSONObject value2 = null;
                    JSONObject value = null;
                    String status = "";
                    Model model = new Model();
                    String message = "";
                    ArrayList<HashMap<String,String>> arrayList;
                    arrayList = new ArrayList<>();
                    try {
                        value2 = new JSONObject(String.valueOf(new JSONObject(new Gson().toJson(response.body()))));
                        status = value2.getString("status");
                        message = value2.getString("message");
                        Log.e("checkInboxFlow", "1>>: " + status);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (status.equals("success")){
                        try {
                            value = new JSONObject(new JSONObject(new Gson().toJson(response.body())).getString(Constants.DATA));
                            Log.e("checkInboxFlow", "2>>: " + value);
                                model.setJsonArray((JSONArray) value.get(Constants.MESSAGE_LIST));
                                hashMap1 = new HashMap();
                                hashMap1.put(Constants.CONS_ID,value.getString(Constants.CONS_ID));
                                hashMap1.put(Constants.CONS_FIRSTNAME,value.getString(Constants.CONS_FIRSTNAME));
                                hashMap1.put(Constants.CONS_LASTNAME,value.getString(Constants.CONS_LASTNAME));
                                hashMap1.put(Constants.CONS_PROFILE,value.getString(Constants.CONS_PROFILE));
                                hashMap1.put(Constants.USER_PROFILE,value.getString(Constants.USER_PROFILE));
                                hashMap1.put(Constants.USER_FIRSTNAME,value.getString(Constants.USER_FIRSTNAME));
                                hashMap1.put(Constants.USER_LASTNAME,value.getString(Constants.USER_LASTNAME));
                                arrayList.add(hashMap1);

                                Log.e("checkInboxFlow", "hashMap1>>: " + arrayList + ">>>" + model.getJsonArray());

                            Constants.model.setCategories_list(arrayList);
                            Log.e("checkInboxFlow", "HashMap>6>>: " + Constants.model.getCategories_list());
                        } catch (JSONException e) {
                            Log.e("checkInboxFlow", "JSONException>6>>: " + e.getMessage());
                            e.printStackTrace();
                        }

                        Log.e("checkInboxFlow", "main>6>>: " + model.getJsonArray());
                        Log.e("checkSMSflow",">GetInboxList1>" + Constants.model.getLastApiHit());
                        mListener.onFetchProgress(Constants.model.getCategories_list(), model.getJsonArray());
                        mListener.onFetchComplete();
                    }else{
                        Toast.makeText(context, "Error: " + message, Toast.LENGTH_SHORT).show();
                        mListener.onFetchFailed();
                    }


                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("checkInboxFlow", "onFailure>>>: " + t.getMessage());
            }
        });
    }


    public void SendFeedback(final HashMap<String,String> hashMap, final Context context) {
        mListener.onFetchStart();

        Call<Object> call = null;

        call =APIClient.createWrbServiceRequest().create(APIInterface.class).SendFeedback(hashMap);

        call.enqueue(new Callback<Object>() {

            @Override
            public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {

                Log.e("checkSendFeedback", "onResponse>>>: " + response.message() + "><><" + response.body() + "><>" + response.code());
                if (response.message().equals("OK")) {

                    JSONObject value2 = null;
                    JSONObject value = null;
                    String status = "";
                    String message = "";
                    try {
                        value2 = new JSONObject(String.valueOf(new JSONObject(new Gson().toJson(response.body()))));
                        status = value2.getString("status");
                        message = value2.getString("message");
                        Log.e("checkSendFeedback", "1>>: " + status);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (status.equals("success")){
                        try {
                            value = new JSONObject(new JSONObject(new Gson().toJson(response.body())).getString(Constants.DATA));
                            Log.e("checkSendFeedback", "1>>: " + value);
                        } catch (JSONException e) {
                            Log.e("checkSendFeedback", "JSONException>6>>: " + e.getMessage());
                            e.printStackTrace();
                        }

                        mListener.onFetchProgress(value);
                        mListener.onFetchComplete();
                    }else{
                        Toast.makeText(context, "Error: " + message, Toast.LENGTH_SHORT).show();
                        mListener.onFetchFailed();
                    }


                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("checkSendFeedback", "onFailure>>>: " + t.getMessage());
//                AppController.constants.dismissProgressDialog();
            }
        });
    }

    public void SendMessage(final HashMap<String,String> hashMap, final Context context) {
        mListener.onFetchStart();

        Call<Object> call = null;

        Log.e("checkSendMessage",">>" + hashMap);
        call =APIClient.createWrbServiceRequest().create(APIInterface.class).SendMessage(hashMap);
        Constants.model.setLastApiHit(Constants.INSERT_MESSAGE);
        call.enqueue(new Callback<Object>() {

            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.e("checkSendMessage", "onResponse>>>: " + response.message() + "><><" + response.body() + "><>" + response.code());
                if (response.message().equals("OK")) {
                    HashMap hashMap1 = null;
                    JSONObject value2 = null;
                    JSONArray value = null;
                    String status = "";
                    String message = "";
                    ArrayList<HashMap<String,String>> arrayList;
                    arrayList = new ArrayList<>();
                    try {
                        value2 = new JSONObject(String.valueOf(new JSONObject(new Gson().toJson(response.body()))));
                        status = value2.getString("status");
                        message = value2.getString("message");
                        Log.e("checkSendMessage", "1>>: " + status);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (status.equals("success")){
                        mListener.onFetchProgress(null,null);
                        mListener.onFetchComplete();
                        Log.e("checkSendMessage", "main>6>>: " + Constants.model.getLastApiHit());
                    }else{
                        Toast.makeText(context, "Error: " + message, Toast.LENGTH_SHORT).show();
                        mListener.onFetchFailed();
                    }


                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("checkSendMessage", "onFailure>>>: " + t.getMessage());
            }
        });
    }


    public void GetConsultaionsForConsultants(final HashMap<String,String> hashMap, final Context context) {
        mListener.onFetchStart();

        Call<Object> call = null;

        Log.e("checkconsulcon",">>" + hashMap);
        call =APIClient.createWrbServiceRequest().create(APIInterface.class).GetConsultaionsForConsultants(hashMap);

        Constants.model.setLastApiHit(Constants.APPOINTMENT_LIST_CONSUL);
        call.enqueue(new Callback<Object>() {

            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.e("checkconsulcon", "onResponse>>>: " + response.message() + "><><" + response.body() + "><>" + response.code());
                if (response.message().equals("OK")) {
                    HashMap hashMap1 = null;
                    JSONObject value2 = null;
                    JSONArray value = null;
                    String status = "";
                    String message = "";
                    ArrayList<HashMap<String,String>> arrayList;
                    arrayList = new ArrayList<>();
                    try {
                        value2 = new JSONObject(String.valueOf(new JSONObject(new Gson().toJson(response.body()))));
                        status = value2.getString("status");
                        message = value2.getString("message");
                        Log.e("checkconsulcon", "1>>: " + status);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (status.equals("success")){
                        try {
                            value = new JSONArray(new JSONObject(new Gson().toJson(response.body())).getString(Constants.DATA));
                            Log.e("checkconsulcon", "1>>: " + value);
                            for(int i = 0; i < value.length(); i++) {
                                JSONObject dataObject = value.getJSONObject(i);
                                hashMap1 = new HashMap();
                                hashMap1.put(Constants.ID,dataObject.getString(Constants.ID));
                                hashMap1.put(Constants.FNAME,dataObject.getString(Constants.FNAME));
                                hashMap1.put(Constants.LNAME,dataObject.getString(Constants.LNAME));
                                hashMap1.put(Constants.CITY,dataObject.getString(Constants.CITY));
                                hashMap1.put(Constants.COUNTRY,dataObject.getString(Constants.COUNTRY));
                                hashMap1.put(Constants.TO_TIME,dataObject.getString(Constants.TO_TIME));
                                hashMap1.put(Constants.FROM_TIME,dataObject.getString(Constants.FROM_TIME));
                                hashMap1.put(Constants.EMAIL,dataObject.getString(Constants.EMAIL));
                                hashMap1.put(Constants.STATUS,dataObject.getString(Constants.STATUS));
                                hashMap1.put(Constants.DATE,dataObject.getString(Constants.DATE));
                                hashMap1.put(Constants.FEE,dataObject.getString(Constants.FEE));
                                hashMap1.put(Constants.DAYS ,dataObject.getString(Constants.DAYS));
                                hashMap1.put(Constants.PROFILE,dataObject.getString(Constants.PROFILE));
//                                Constants.model.categories_list.add(hashMap);
                                arrayList.add(hashMap1);

                                Log.e("checkconsulcon", "hashMap1>>: " + arrayList);
                            }

                            Constants.model.setCategories_list(arrayList);
                            Log.e("checkconsulcon", "HashMap>6>>: " + Constants.model.getLastApiHit());
                        } catch (JSONException e) {
                            Log.e("checkconsulcon", "JSONException>6>>: " + e.getMessage());
                            e.printStackTrace();
                        }

                        mListener.onFetchProgress(Constants.model.getCategories_list(),null);
                        mListener.onFetchComplete();
                    }else{
                        Toast.makeText(context, "Error: " + message, Toast.LENGTH_SHORT).show();
                        mListener.onFetchFailed();
                    }


                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("checkconsulcon", "onFailure>>>: " + t.getMessage());
            }
        });
    }


    public void GetConsultaionsForUsers(final HashMap<String,String> hashMap, final Context context) {
        mListener.onFetchStart();

        Call<Object> call = null;

        Log.e("checkconsulcon",">>" + hashMap);
        call =APIClient.createWrbServiceRequest().create(APIInterface.class).GetConsultaionsForUsers(hashMap);

        Constants.model.setLastApiHit(Constants.APPOINTMENT_LIST_USER);
        call.enqueue(new Callback<Object>() {

            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.e("checkuserscon", "onResponse>>>: " + response.message() + "><><" + response.body() + "><>" + response.code());
                if (response.message().equals("OK")) {
                    HashMap hashMap1 = null;
                    JSONObject value2 = null;
                    JSONArray value = null;
                    String status = "";
                    String message = "";
                    ArrayList<HashMap<String,String>> arrayList;
                    arrayList = new ArrayList<>();
                    try {
                        value2 = new JSONObject(String.valueOf(new JSONObject(new Gson().toJson(response.body()))));
                        status = value2.getString("status");
                        message = value2.getString("message");
                        Log.e("checkuserscon", "1>>: " + status);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (status.equals("success")){
                        try {
                            value = new JSONArray(new JSONObject(new Gson().toJson(response.body())).getString(Constants.DATA));
                            Log.e("checkuserscon", "1>>: " + value);
                            for(int i = 0; i < value.length(); i++) {
                                JSONObject dataObject = value.getJSONObject(i);
                                hashMap1 = new HashMap();
                                hashMap1.put(Constants.ID,dataObject.getString(Constants.ID));
                                hashMap1.put(Constants.FNAME,dataObject.getString(Constants.FNAME));
                                hashMap1.put(Constants.CONSULTANT_ID,dataObject.getString(Constants.CONSULTANT_ID));
                                hashMap1.put(Constants.LNAME,dataObject.getString(Constants.LNAME));
                                hashMap1.put(Constants.CITY,dataObject.getString(Constants.CITY));
                                hashMap1.put(Constants.COUNTRY,dataObject.getString(Constants.COUNTRY));
                                hashMap1.put(Constants.TO_TIME,dataObject.getString(Constants.TO_TIME));
                                hashMap1.put(Constants.FROM_TIME,dataObject.getString(Constants.FROM_TIME));
                                hashMap1.put(Constants.EMAIL,dataObject.getString(Constants.EMAIL));
                                hashMap1.put(Constants.STATUS,dataObject.getString(Constants.STATUS));
                                hashMap1.put(Constants.DATE,dataObject.getString(Constants.DATE));
                                hashMap1.put(Constants.FEE,dataObject.getString(Constants.FEE));
                                hashMap1.put(Constants.DAYS ,dataObject.getString(Constants.DAYS));
                                hashMap1.put(Constants.PROFILE,dataObject.getString(Constants.PROFILE));
//                                Constants.model.categories_list.add(hashMap);
                                arrayList.add(hashMap1);

                                Log.e("checkuserscon", "hashMap1>>: " + arrayList);
                            }

                            Constants.model.setCategories_list(arrayList);
                            Log.e("checkuserscon", "HashMap>6>>: " + Constants.model.getLastApiHit());
                        } catch (JSONException e) {
                            Log.e("checkuserscon", "JSONException>6>>: " + e.getMessage());
                            e.printStackTrace();
                        }

                        mListener.onFetchProgress(Constants.model.getCategories_list(),null);
                        mListener.onFetchComplete();
                    }else{
                        Toast.makeText(context, "Error: " + message, Toast.LENGTH_SHORT).show();
                        mListener.onFetchFailed();
                    }


                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("checkuserscon", "onFailure>>>: " + t.getMessage());
            }
        });
    }

    public void BookApointment(final HashMap<String,String> hashMap, final Context context) {
        mListener.onFetchStart();

        Call<Object> call = null;

        call =APIClient.createWrbServiceRequest().create(APIInterface.class).BookApointment(hashMap);

        call.enqueue(new Callback<Object>() {

            @Override
            public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {

                Log.e("checkBooking", "onResponse>>>: " + response.message() + "><><" + response.body() + "><>" + response.code());
                if (response.message().equals("OK")) {

                    JSONObject value2 = null;
                    JSONObject value = null;
                    String status = "";
                    String message = "";
                    try {
                        value2 = new JSONObject(String.valueOf(new JSONObject(new Gson().toJson(response.body()))));
                        status = value2.getString("status");
                        message = value2.getString("message");
                        Log.e("checkBooking", "1>>: " + status);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (status.equals("success")){
//                        try {
//                            value = new JSONObject(new JSONObject(new Gson().toJson(response.body())).getString(Constants.DATA));
//                            Log.e("checkBooking", "1>>: " + value);
//                        } catch (JSONException e) {
//                            Log.e("checkBooking", "JSONException>6>>: " + e.getMessage());
//                            e.printStackTrace();
//                        }

                        Toast.makeText(context, "Booked successfully", Toast.LENGTH_LONG).show();
                        mListener.onFetchProgress(value);
                        mListener.onFetchComplete();
                    }else{
                        Toast.makeText(context, "Error: " + message, Toast.LENGTH_SHORT).show();
                        mListener.onFetchFailed();
                    }


                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("checkBooking", "onFailure>>>: " + t.getMessage());
//                AppController.constants.dismissProgressDialog();
            }
        });
    }


    public void SubmitLoanAPI(final HashMap<String,String> hashMap, ArrayList<String> filePath) {
        mListener.onFetchStart();
        Call<Object> call = null;
        MultipartBody.Part[] filePart = new MultipartBody.Part[filePath.size()];
        Log.e("parametersConn", "hashMap>>>: "+hashMap + "<>filepaths<>" + filePath.size());

        for (int i = 0;i<=filePath.size() -1;i++){
            File file = new File(filePath.get(i));
            if (file.exists()){
                Log.e("checkFilePath", "fianl>>>: "+file.getAbsolutePath() + "<SIZE>" + file.getTotalSpace());
            }
            String filename = file.getName();
            Log.e("checkFileName", "fianl>>>: "+filename);
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/pdf"), file);
//            filePart[i] = MultipartBody.Part.createFormData(Constants.ATTACHMENTS, filename,
//                    requestBody);
        }

        Log.e("parametersCon", "images>>>: "+filePart.toString());
//        if (!filePath.equals("")){

//        RequestBody company_id = RequestBody.create(MediaType.parse("text/plain"),hashMap.get(Constants.COMPANY_ID));
//        RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"),hashMap.get(Constants.USER_ID_API));
//        RequestBody type = RequestBody.create(MediaType.parse("text/plain"),hashMap.get(Constants.TYPE_API));
//        RequestBody title = RequestBody.create(MediaType.parse("text/plain"),hashMap.get(Constants.TITLE));
//            RequestBody items = RequestBody.create(MediaType.parse("text/plain"),hashMap.get(Constants.ITEMS));
//        Log.e("parametersCon", "finall0>>>\n: "+filePart.toString() + "><" + user_id + "><" + type + "><" + title + "><" + firstname + "<>" + middlename);
//        call =APIClient.createWrbServiceRequest().create(APIInterface.class).SubmitLoanAPI(company_id,user_id,type,title,filePart);

        call.enqueue(new Callback<Object>() {

            @Override
            public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {
                Log.e("parametersCon", "onResponse>>>: " + response.message() + "><><" + response.body());
//                AppController.constants.dismissProgressDialog();
                if (response.message().equals("OK")){

                    mListener.onFetchComplete();
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("parametersCon", "onFailure>>>: ");
//                AppController.constants.dismissProgressDialog();
            }
        });
    }
    public void GetCategoriesAPI(final HashMap<String,String> hashMap, ArrayList<String> filePath) {
        mListener.onFetchStart();
        Call<Object> call = null;

        RequestBody username = RequestBody.create(MediaType.parse("text/plain"),hashMap.get(Constants.USER_NAME));
        RequestBody access = RequestBody.create(MediaType.parse("text/plain"),hashMap.get(Constants.ACCESS));
//        RequestBody transcode = RequestBody.create(MediaType.parse("text/plain"),hashMap.get(Constants.TRANSCODE));
//        call =APIClient.createWrbServiceRequest().create(APIInterface.class).GetCategoriesAPI(username,access,transcode);

        call.enqueue(new Callback<Object>() {

            @Override
            public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {
                Log.e("parametersCon", "onResponse>>>: " + response.message() + "><><" + response.errorBody() + "><><" + response.body());
//                AppController.constants.dismissProgressDialog();
                if (response.message().equals("OK")){

                    mListener.onFetchComplete();
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("parametersCon", "onFailure>>>: " + t.getMessage());
//                AppController.constants.dismissProgressDialog();
            }
        });
    }

    private String getMimeType(String url){
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null){
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }

    /***
     * Callback methods
     */
    public interface APICallbackListener {

        void onFetchStart();
        void onFetchProgress(JSONObject pojo);
        void onFetchProgress(ArrayList<HashMap<String,String>> pojoList,JSONArray jsonArrays);
        void onFetchComplete();
        void onFetchFailed();
        void onSuccessORError(String status, String message, JSONObject data);
        void onSuccessORError(String status, String message, JSONObject data,boolean isTimeMatched);

    }

}
