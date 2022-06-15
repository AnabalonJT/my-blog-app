package cl.uandes.myblogapp.data.datasources

import cl.uandes.myblogapp.data.model.Comment
import cl.uandes.myblogapp.data.model.Post
import cl.uandes.myblogapp.data.model.User

interface RemoteDataSource {
  suspend fun getPosts(): List<Post>
  suspend fun getPost(postId: String): Post?
  suspend fun createPost(post: Post): Boolean
  suspend fun login(user: User): Boolean
  suspend fun signUp(newUser: User): Boolean
}
