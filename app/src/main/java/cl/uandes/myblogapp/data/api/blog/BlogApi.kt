package cl.uandes.myblogapp.data.api.blog

import cl.uandes.myblogapp.data.model.Comment
import cl.uandes.myblogapp.data.model.Post
import cl.uandes.myblogapp.data.model.Posts
import cl.uandes.myblogapp.data.model.User
import retrofit2.Response
import retrofit2.http.*

interface BlogApi {
  @GET("api/v1/posts")
  suspend fun getPosts(): Response<Posts>

  @GET("api/v1/posts/{post_id}")
  suspend fun getPost(@Path("post_id") postId: String): Response<HashMap<String, Post>>

  @POST("api/v1/posts")
  suspend fun createPost(@Body post: Post): Response<String>

  @POST("api/v1/posts/{post_id}")
  suspend fun createComment(@Path("post_id") postId: String, comment: Comment): Response<String>
}
