package org.androidtown.seoulsoom;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    /*
    public static final String API_URL="http://jsonplaceholder.typicode.com/";

    @GET("Comments")
    Call<ResponseBody>getComment(@Query("postId")int PostId);
*/

    public static final String API_URL = "http://54.180.81.90:3000";

    @FormUrlEncoded
    @POST("LogIn")
    Call<ResponseBody>postLogin(@Field("ID")String ID, @Field("password")String password);

    @POST("LogOut")
    Call<ResponseBody>postLogout();

    @FormUrlEncoded
    @POST("SignUp")
    Call<ResponseBody>postSignup(@Field("ID")String ID, @Field("password")String password, @Field("name")String name);


    @FormUrlEncoded
    @POST("UserInform")
    Call<ResponseBody> postUser(@Field("ID")String ID);

    @POST("SeeList")
    Call<ResponseBody>postList();

    @FormUrlEncoded
    @POST("SeeHistory")
    Call<HistoryDao>postHistory(@Field("user")String user);

    //userID, itemId,date, 구분(0이적립 1이사용), 가격, 수량
    @FormUrlEncoded
    @POST("Pay")
    Call<ResponseBody> postPay(@Field("user")String user, @Field("item")String item, @Field("date") String date,  @Field("flag")int flag, @Field("amount")int amount, @Field("number")int number);


}


