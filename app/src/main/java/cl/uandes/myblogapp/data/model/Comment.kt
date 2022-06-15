package cl.uandes.myblogapp.data.model

import com.google.gson.annotations.SerializedName

data class Comment (
  @SerializedName("id")
  val id: Long,
  @SerializedName("body")
  val body: String,
  @SerializedName("author")
  val author: User
)
