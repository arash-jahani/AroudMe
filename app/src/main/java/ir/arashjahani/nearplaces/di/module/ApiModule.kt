package ir.arashjahani.nearplaces.di.module

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import ir.arashjahani.nearplaces.BuildConfig
import ir.arashjahani.nearplaces.data.remote.ApiService
import ir.arashjahani.nearplaces.utils.AppConstants
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


/**
 * Created By ArashJahani on 2020/04/16
 */
@Module
class ApiModule {


    @Provides
    @Singleton
    fun providesOKHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

        val clientInterceptor =
            Interceptor { chain: Interceptor.Chain ->
                var request: Request = chain.request()
                val url: HttpUrl =
                    request.url().newBuilder()
                        .addQueryParameter("client_id", "value")
                        .addQueryParameter("client_secret", "value")
                        .addQueryParameter("v", "value")
                        .build()
                request = request.newBuilder().url(url).build()
                chain.proceed(request)
            }

        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addNetworkInterceptor(clientInterceptor)
            .retryOnConnectionFailure(true)
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(5, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun providesApiService( retrofit: Retrofit): ApiService
    {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun providesApiRetrofit(okHttpClient: OkHttpClient, gsonBuilder: GsonBuilder): Retrofit
    {
        return Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesGsonBuilder(): GsonBuilder
    {
        val gsonBuilder = GsonBuilder()

        return gsonBuilder
    }

}