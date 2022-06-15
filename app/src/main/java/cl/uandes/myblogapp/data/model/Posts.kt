package cl.uandes.myblogapp.data.model

import com.google.gson.annotations.SerializedName

data class Posts(
  @SerializedName("posts")
  val posts: List<Post>
)
