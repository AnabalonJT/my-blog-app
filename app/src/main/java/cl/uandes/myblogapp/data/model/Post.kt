package cl.uandes.myblogapp.data.model

import com.google.gson.annotations.SerializedName

data class Post(
  @SerializedName("id")
  val id: Long?,
  @SerializedName("author")
  val author: User,
  @SerializedName("title")
  val title: String,
  @SerializedName("body")
  val body: String,
  @SerializedName("comments")
  val comments: List<Comment>?
)
