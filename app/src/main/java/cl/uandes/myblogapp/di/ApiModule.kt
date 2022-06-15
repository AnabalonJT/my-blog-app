package cl.uandes.myblogapp.di

import cl.uandes.myblogapp.BuildConfig
import cl.uandes.myblogapp.data.MyBlogRepository
import cl.uandes.myblogapp.data.api.authentication.UserApi
import cl.uandes.myblogapp.data.api.blog.BlogApi
import cl.uandes.myblogapp.data.datasources.ApiDataSource
import cl.uandes.myblogapp.data.datasources.InMemoryDataSource
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
  private fun baseUrl() = BuildConfig.BASE_URL

  @Provides
  fun providesOkHttpClient(): OkHttpClient =
    OkHttpClient.Builder()
      .addInterceptor {
        val request = it.request()
        val newRequest = request.newBuilder()
          .addHeader("x-user-email", InMemoryDataSource.email.toString())
          .addHeader("x-user-token", InMemoryDataSource.authToken.toString())
          .build()
        it.proceed(newRequest)
      }.build()

  @Singleton
  @Provides
  fun provideRetrofit(gson: Gson): Retrofit.Builder =
    Retrofit.Builder()
      .baseUrl(baseUrl())
      .addConverterFactory(GsonConverterFactory.create(gson))

  @Provides
  fun providesGson(): Gson = GsonBuilder().create()

  @Provides
  fun provideUserApi(retrofit: Retrofit.Builder): UserApi =
    retrofit.build().create(UserApi::class.java)

  @Provides
  fun provideBlogApi(retrofit: Retrofit.Builder, okHttpClient: OkHttpClient): BlogApi =
    retrofit.client(okHttpClient).build().create(BlogApi::class.java)

  @Singleton
  @Provides
  fun provideApiDataSource(userApi: UserApi, blogApi: BlogApi): ApiDataSource =
    ApiDataSource(userApi = userApi, api = blogApi)

  @Singleton
  @Provides
  fun provideMyBlogRepository(remoteDataSource: ApiDataSource) =
    MyBlogRepository(remoteDataSource = remoteDataSource)
}
