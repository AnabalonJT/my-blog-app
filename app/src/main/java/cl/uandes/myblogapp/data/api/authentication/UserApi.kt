package cl.uandes.myblogapp.data.api.authentication

import cl.uandes.myblogapp.data.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.POST
import java.util.HashMap

interface UserApi {
  @POST("api/v1/login")
  suspend fun login(@Body user: User): Response<HashMap<String, String>>

  @POST("api/v1/signup")
  suspend fun signUp(@Body user: User): Response<String>
}
