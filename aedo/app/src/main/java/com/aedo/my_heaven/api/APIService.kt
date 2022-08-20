package com.aedo.my_heaven.api

import com.aedo.my_heaven.model.coun.CounGet
import com.aedo.my_heaven.model.coun.CounPost
import com.aedo.my_heaven.model.list.Condole
import com.aedo.my_heaven.model.list.ListDelete
import com.aedo.my_heaven.model.list.RecyclerList
import com.aedo.my_heaven.model.naverMap.ResultPath
import com.aedo.my_heaven.model.notice.NoticeDetailModel
import com.aedo.my_heaven.model.notice.NoticeModel
import com.aedo.my_heaven.model.restapi.base.*
import com.aedo.my_heaven.model.restapi.login.*
import com.aedo.my_heaven.model.shop.MyOrder
import com.aedo.my_heaven.model.shop.Orders
import com.aedo.my_heaven.model.shop.ShopModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


interface APIService {
    // 검증 API
    @GET("v1/app/verification")
    fun getVerification(@Header("abcd-ef")abcdef: String?): Call<Verification>

    // 정책 API
    @GET("v1/app/policy")
    fun getPolicy(): Call<AppPolicy>

    // 자동로그인 API
    @PUT("v1/user/auto")
    fun getautoLogin(@Header("Accesstoken")accesstoken: String?): Call<AutoLogin>

    // 로그인 API
    @PUT("v1/user")
    fun getLogin(@Body loginSend: LoginSend):Call<LoginSend>

    // 회원가입 API
    @POST("v1/user")
    fun getSignUp(@Body loginResult:LoginResult):Call<LoginResult>

    // 문자인증 API
    @POST("v1/user/sms")
    fun getSMS(@Body loginSMS: LoginSMS): Call<LoginSMS>

    // 회원정보 API
    @GET("v1/user")
    fun getUser(@Header("Accesstoken")accesstoken: String?,): Call<GetUser>

    // 약관보기 API
    @GET("v1/user/terms")
    fun getTerms(): Call<TremModel>

    // 로그아웃 API
    @DELETE
    fun getLogOut(@Header("Accesstoken")accesstoken: String?): Call<LogOut>

    // 부고작성 API
    @Multipart
    @POST("v1/obituary")
    fun getImgCreate(@Header("Accesstoken") accesstoken: String?,
                     @Part img: MutableList<MultipartBody.Part?>,
                     @PartMap data: HashMap<String, RequestBody>): Call<CreateModel>

    // 부고조회 API
    @GET("v1/obituary")
    fun getCreateName(@Query("name")name: String?,
                      @Header("Accesstoken")accesstoken: String?) : Call<CreateName>

    //부고 이미지 받기API
    @GET("v1/obituary/image")
    @Streaming
    fun getImg(@Query("imgname") imgname: String?,
               @Header("Accesstoken") accesstoken: String?) : Call<ResponseBody>

    // 나의부고 API
    @GET("v1/obituary/my")
    fun getCreateGet(@Header("Accesstoken")accesstoken: String?): Call<RecyclerList>

    // 부고삭제
    @DELETE("v1/obituary/:id")
    fun getCreateDelete(@Header("Accesstoken")accesstoken: String?,
                        @Query("id")id: String?) : Call<ListDelete>

    // 조문메세지 API
    @POST("v1/condole")
    fun getCondole(@Header("Accesstoken")accesstoken: String?,
                   @Body createMessage: CreateMessage): Call<CreateMessage>

    // 조문메세지 조회 API
    @GET("v1/condole")
    fun getConID(@Query("id")id: String? ,
                 @Header("Accesstoken")accesstoken: String?) : Call<Condole>

    // 공지사항 모두조회 API
    @GET("v1/center/announcement")
    fun getNoti(@Header("Accesstoken")accesstoken: String?) : Call<NoticeModel>

    // 1:1문의 조회
    @GET("v1/center/request")
    fun getCounRequest(@Header("Accesstoken")accesstoken: String?) : Call<CounGet>

    // 1:1문의 작성
    @POST("v1/center/request")
    fun getCounPost(@Header("Accesstoken")accesstoken: String?,
                    @Body counPost: CounPost) : Call<CounPost>

    //화환 주문 API
    @POST("v1/order")
    fun getOrder(@Header("Accesstoken")accesstoken:String?,
                 @Body orders: Orders) : Call<ShopModel>

    //내 주문 조회 API
    @GET("v1/order/my")
    fun getMyOrder(@Header("Accesstoken")accesstoken:String?) : Call<MyOrder>

    @GET("v1/driving")
    fun getPath(
        @Header("X-NCP-APIGW-API-KEY-ID") apiKeyID: String,
        @Header("X-NCP-APIGW-API-KEY") apiKey: String,
        @Query("start") start: String,
        @Query("goal") goal: String,
    ): Call<ResultPath>


    // 공지사항 세부조회 API
    @GET("v1/center/announcement/:id")
    fun getNotiDetail(@Header("Accesstoken")accesstoken: String?,
                      @Query("id")id: String?) : Call<NoticeDetailModel>

    // 조문메세지 수정 API
    @PUT("v1/condole/:id")
    fun getConPut(@Header("Accesstoken")accesstoken: String?)

    // 조문메세지 삭제 API
    @DELETE("v1/condole/:id")
    fun getConDelete(@Header("Accesstoken")accesstoken: String?)

    // 부고수정 API
    @PUT("v1/obituary/:id")
    fun getCreatePut(@Header("Accesstoken")accesstoken: String?)
}