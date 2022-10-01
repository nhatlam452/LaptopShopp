package com.example.myapplication.Controllers;

import android.content.Context;

import com.example.myapplication.Models.AddressModel;
import com.example.myapplication.Models.Cart;
import com.example.myapplication.Models.ModelResponse.AddressResponse;
import com.example.myapplication.Models.ModelResponse.CartResponse;
import com.example.myapplication.Models.ModelResponse.OrderResponse;
import com.example.myapplication.Models.ModelResponse.ProductDetailResponse;
import com.example.myapplication.Models.ModelResponse.ProductResponse;
import com.example.myapplication.Models.ModelResponse.UserResponse;
import com.example.myapplication.Models.Order;
import com.example.myapplication.Models.ProductDetail;
import com.example.myapplication.Models.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public class ApiController {
    private static class ApiBuilder {

        private static final String BASE_URL = "http://10.82.79.8/API_LTS/";//Đương dẫn để như lúc đầu cũng được
        private final Retrofit retrofit = buildRetrofit();
        private static ApiBuilder instance;
        private final Context context;

        private ApiBuilder(Context context) {
            this.context = context;
        }


        public static ApiBuilder getInstance(Context context) {
            if (instance == null) {
                instance = new ApiBuilder(context);
            }
            return instance;
        }

        private static Retrofit buildRetrofit() {
            //Interceptor để quan sát, điều chỉnh và có khả năng chặn các request và những phản hồi.
            Interceptor interceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
//                      /*
//                       chain.request() returns original request that you can work with(modify, rewrite)
//                       */
//
//                    // Here you can rewrite the request
//
//                    /*
//                     chain.proceed(request) is the call which will initiate the HTTP work. This call invokes the request and returns the response as per the request.
//                    */
//                    Response response = chain.proceed(request);
//
//                    //Here you can rewrite/modify the response
//
//                    return response;
//                    String token = AppHelper.getInstance(instance.context);
//                    Request request = chain.request().newBuilder().addHeader("Authorization", "Bearer " + token).build();
                    Request request = chain.request();
                    return chain.proceed(request);
                }
            };
            OkHttpClient okHttpClient = (
                    new OkHttpClient.Builder())
                    .addInterceptor(interceptor)// dùng để thêm Interceptor của App
                   // .addNetworkInterceptor(new CustomInterceptor()) Dùng để thêm Interceptor của Network
                    .build();
            Gson gson = new GsonBuilder().setLenient().create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(okHttpClient) // The Htttp client dùng để gửi request
                    .build();
            return retrofit;
        }
        public  <T> T createService(Class<T> service){
            return retrofit.create(service);
        }

    }
    public static class ApiService{

        private static IApiService service;
        public static IApiService getService(Context context){
            return service = ApiBuilder.getInstance(context).createService(IApiService.class);
        }

    }
    public interface IApiService{
        @POST("views/insert-user.php")
        Call<UserResponse> insert_account(@Body User User);
        @POST("views/check-login.php")
        Call<UserResponse> check_login(@Body User user);
        @GET("views/get-all-product.php")
        Call<ProductResponse> get_all_product();
        @POST("views/get-product-detail.php")
        Call<ProductDetailResponse> get_product_detail(@Body ProductDetail productDetail );
        @POST("views/add-to-cart.php")
        Call<CartResponse> add_to_cart(@Body Cart cart );
        @POST("views/get-cart-by-phonenumber.php")
        Call<CartResponse> get_cart_by_phonenumber(@Body Cart cart );
        @POST("views/insert-order.php")
        Call<OrderResponse> insert_order(@Body Order order );
        @POST("views/update-cart.php")
        Call<CartResponse> update_cart(@Body Cart cart );
        @POST("views/remove-cart.php")
        Call<CartResponse> remove_cart(@Body Cart cart );
        @POST("views/insert-address.php")
        Call<AddressResponse> insert_address(@Body AddressModel address );
        @POST("views/get-all-address.php")
        Call<AddressResponse> get_all_address(@Body AddressModel address );


    }
}
