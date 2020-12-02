package com.imassage.data.remote.api

import com.haroldadmin.cnradapter.NetworkResponse
import com.imassage.data.model.*
import com.imassage.data.remote.model.*
import okhttp3.MultipartBody
import retrofit2.http.*

interface AuthApiInterface{

    // get mainPage
    @POST("mainPage")
    suspend fun mainPage(
    ): NetworkResponse<MainPageResponse , ErrorResponse>

    // get Massages
    @GET("massage")
    suspend fun massages(
    ): NetworkResponse<MassageResponse, ErrorResponse>

    // get packages
    @GET("packages")
    suspend fun packages(
    ): NetworkResponse<PackageResponse, ErrorResponse>

    // get questions
    @GET("questions")
    suspend fun questions(
    ): NetworkResponse<QuestionResponse , ErrorResponse>

    // send answers
    @POST("questions")
    suspend fun answers(
            @Body answers: AnswerRequest
    ): NetworkResponse<Form, ErrorResponse>

    // update user information
    @PUT("user_info/1")
    @Headers(
        "Content-Type: application/x-www-form-urlencoded"
    )
    suspend fun updateAccount(
        @Query("name") name: String? ,
        @Query("family") family: String? ,
        @Query("gender") gender: String?
    ):NetworkResponse<Account, ErrorResponse>

    @Multipart
    @POST("user_info/1")
    suspend fun updatePhotoAccount(
            @Part photo: MultipartBody.Part ,
            @Query("_method") method: String = "PUT"
    ): NetworkResponse<Account , ErrorResponse>

    // ger User information
    @GET("user_info")
    suspend fun userInformation(
    ): NetworkResponse<Account , ErrorResponse>


    // get available dates for reserve
    @GET("available_dates")
    suspend fun availableDates(
    ): NetworkResponse<DatesResponse , ErrorResponse>

    @POST("available_date_time")
    suspend fun availableDateTime(
            @Query("date") date: String
    ): NetworkResponse<DateTimes, ErrorResponse>
    // check time
    @POST("check_reserve_time")
    suspend fun checkSelectedTime(
            @Query("reserved_time_date_id") date: String ,
            @Query("time") time: String ,
            @Query("massage_id") massageId: String ,
            @Query("gender") gender: String
    ): NetworkResponse<SuccessResponse, ErrorResponse>
    // start pay transactions
    @POST("transaction")
    suspend fun transaction(
            @Query("package_id") packageIs: String ,
            @Query("offer_code") offerCode: String?
    ): NetworkResponse<Transactions, ErrorResponse>
    // pay transaction
    @POST("payTransaction")
    suspend fun payTransaction(
            @Query("transaction_id") transactionId: String ,
            @Query("ref_id") refId: String
    ): NetworkResponse<Transactions , ErrorResponse>
    // orders history
    @GET("orders_history")
    suspend fun ordersHistory(
    ): NetworkResponse<OrdersResponse, ErrorResponse>
    // paginate
    @GET("orders_history_")
    suspend fun ordersHistory_(
            @Query("page") page: Int?
    ): NetworkResponse<OrderResponse, ErrorResponse>
    //  get orders
    @POST("orders")
    suspend fun orders(
    ): NetworkResponse<OrdersResponse , ErrorResponse>
    // make offer
    @POST("transaction_offer")
    suspend fun setOffer(
            @Query("transaction_id") transactionId: String ,
            @Query("offer_code") offerCode: String
    ): NetworkResponse<Transactions , ErrorResponse>
    // order
    @POST("order")
    suspend fun order(
            @Query("time") time: String ,
            @Query("reserved_time_date_id") reservedTimeDateId: String ,
            @Query("massage_id") massageId: String ,
            @Query("package_id") packageId: String,
            @Query("transactions_id") transactionId: String,
            @Query("gender") gender: String
    ): NetworkResponse<Order , ErrorResponse>

    // reserve time
    @POST("reserve")
    suspend fun reserveTime(
            @Query("time") time: String ,
            @Query("reserved_time_date_id") reserveTime: String ,
            @Query("massage_id") massageId: String ,
            @Query("package_id") packageId: String ,
            @Query("gender") gender: String
    ): NetworkResponse< ErrorResponse , ErrorResponse>

    // send consulting
    @POST("consulting")
    suspend fun consulting(
    ): NetworkResponse< SuccessResponse , ErrorResponse>
}