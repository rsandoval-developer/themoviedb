package com.rappi.movies.di

import android.annotation.SuppressLint
import android.content.Context
import androidx.room.Room
import com.google.gson.*
import com.google.gson.annotations.SerializedName
import com.rappi.movies.AppConstants
import com.rappi.movies.data.db.AppDatabase
import com.rappi.movies.data.remote.exceptions.AppException
import com.rappi.movies.data.remote.services.MoviesServices
import com.rappi.movies.data.remote.UtilsNetwork
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRoomInstance(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        AppConstants.NAME_DATA_BASE
    ).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideCocktailDao(db: AppDatabase) = db.MoviesDao()


    @Provides
    @Singleton
    fun provideOkHttpClient(utilsNetwork: UtilsNetwork): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor {
            if (utilsNetwork.isConnected()) {
                it.proceed(it.request())
            } else {
                throw AppException(AppException.Type.ERROR_NETWORK)
            }
        }
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .readTimeout(180, TimeUnit.SECONDS)
        .connectTimeout(180, TimeUnit.SECONDS)
        .build()

    @Singleton
    @Provides
    fun provideCountersService(retrofit: Retrofit): MoviesServices =
        retrofit.create(MoviesServices::class.java)


    @SuppressLint("SimpleDateFormat")
    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder()
        .setExclusionStrategies(object : ExclusionStrategy {
            override fun shouldSkipField(f: FieldAttributes): Boolean {
                return f.annotations.none { it is SerializedName }
            }

            override fun shouldSkipClass(clazz: Class<*>): Boolean {
                return false
            }
        })
        .registerTypeAdapter(Date::class.java, JsonDeserializer<Date> { json, _, _ ->
            val formatter = SimpleDateFormat("yyyy-MM-dd")
            formatter.timeZone = TimeZone.getTimeZone("UTC")
            formatter.parse(if (json.asString.isNotBlank()) json.asString else "0000-00-00")
        })
        .create()

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(AppConstants.BASE_URL)
        .client(okHttpClient)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    @Singleton
    fun providUtilsNetwork(
        @ApplicationContext context: Context
    ): UtilsNetwork = UtilsNetwork(context)
}