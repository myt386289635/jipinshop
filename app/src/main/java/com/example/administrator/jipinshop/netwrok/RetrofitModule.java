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

    public static final String UP_BASE_URL = "http://192.168.5.182:8081/";
    public static final String H5_URL = "http://192.168.5.126:8081/";

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
