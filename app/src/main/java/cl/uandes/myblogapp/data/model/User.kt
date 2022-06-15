package cl.uandes.myblogapp.data.model

import com.google.gson.annotations.SerializedName

data class User(
  @SerializedName("id")
  var id: Long?,
  @SerializedName("email")
  val email: String,
  @SerializedName("password")
  val password: String
)

