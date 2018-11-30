package com.example.administrator.jipinshop.netwrok;

import com.example.administrator.jipinshop.auto.ApplicationScope;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class RetrofitModule {

//    public static final String URL = "https://api.jipincheng.cn/";//正式地址
    public static final String URL = "http://192.168.1.48:8081/";//测试地址
    public static final String UP_BASE_URL = URL;
    public static final String H5_URL = "https://www.jipincheng.cn/";

    @ApplicationScope
    @Provides
    RxJava2CallAdapterFactory provideRxJava2CallAdapterFactory() {
        return RxJava2CallAdapterFactory.create();
    }

    @ApplicationScope
    @Provides
    GsonConverterFactory provideGsonConvertFactory() {
        return GsonConverterFactory.create();
    }

    @Provides
    @ApplicationScope
    Retrofit provideRetrofit(OkHttpClient client, RxJava2CallAdapterFactory adapter, GsonConverterFactory converter) {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(UP_BASE_URL)
                .client(client)
                .addConverterFactory(converter)
                .addCallAdapterFactory(adapter);
        return builder.build();
    }


    @Provides
    @ApplicationScope
    APIService provideUpService(Retrofit retrofit) {
        return retrofit.create(APIService.class);
    }

}
