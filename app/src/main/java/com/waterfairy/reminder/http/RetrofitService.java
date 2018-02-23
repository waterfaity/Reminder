package com.waterfairy.reminder.http;

import com.waterfairy.http.response.BaseResponse;
import com.waterfairy.reminder.bean.UserBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * user : water_fairy
 * email:995637517@qq.com
 * date :2018/1/6
 * des  :
 */

public interface RetrofitService {


    @GET("login")
    Call<BaseResponse<UserBean>> login(@Query("userName") String userName,
                                       @Query("userPwd") String passWord);


    @GET("regist")
    Call<BaseResponse> regist(@Query("userName") String userName,
                              @Query("userPwd") String passWord,
                              @Query("tel") String tel);

    @GET()
    Call<BaseResponse> queryMemorandum();
}
