package com.example.administrator.jipinshop.netwrok;

import com.example.administrator.jipinshop.auto.ApplicationScope;
import com.example.administrator.jipinshop.util.DebugHelper;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class RetrofitModule {

    public static final String URL = "https://www.jipincheng.cn/qualityshop-api/";//正式地址
    public static final String TEXT_URL = "http://192.168.1.70:8081/qualityshop-api/";//测试地址
    public static final String UP_BASE_URL = DebugHelper.getDebug() ? TEXT_URL : URL;
    public static final String H5_URL = "https://www.jipincheng.cn/";
    public static final String TEXT_H5_URL = "http://40.0.0.60:8082/";
    public static final String JP_H5_URL = DebugHelper.getDebug() ? TEXT_H5_URL : H5_URL;
    public static String needVerify = "1";//默认是需要验证的

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
