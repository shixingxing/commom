package org.common.http;

import org.commom.library.http.BaseHttpSercive;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by peter on 2016/3/28.
 */
public interface ApiService extends BaseHttpSercive {

//    @Headers({
//            "version: 3.0.2",
//            "os: android",
//            "X-Ip: 192.168.1.1",
//            "X-DeviceNo: 0000000000000000",
//            "X-Lng: 0.100000",
//            "X-Lat: 0.200000",
//            "Authorization: Basic c3VpeGluZGFpOjFxYXohQCMk"
//    })

//    @POST("api/v2/secure/getBasic")
//    Observable<BaseInfo> getBaseInfo(@Header("X-Token") String token);

    class Factory {
        public static ApiService create() {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://www.ezloan.cn/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            return retrofit.create(ApiService.class);
        }
    }
}
