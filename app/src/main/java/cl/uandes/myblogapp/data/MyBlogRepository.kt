package cl.uandes.myblogapp.data

import cl.uandes.myblogapp.data.datasources.LocalDataSource
import cl.uandes.myblogapp.data.datasources.RemoteDataSource
import cl.uandes.myblogapp.data.model.Comment
import cl.uandes.myblogapp.data.model.Post
import cl.uandes.myblogapp.data.model.User
import javax.inject.Inject

class MyBlogRepository @Inject constructor(
  private val remoteDataSource: RemoteDataSource?,
  val localDataSource: LocalDataSource? = null
) {

  suspend fun login(user: User) = remoteDataSource?.login(user)

  suspend fun signUp(newUser: User) = remoteDataSource?.signUp(newUser)

  suspend fun getPosts(): List<Post> = remoteDataSource?.getPosts()!!

  suspend fun createPost(newPost: Post) = remoteDataSource?.createPost(newPost)

  suspend fun getPost(postId: String): Post? = remoteDataSource?.getPost(postId)
}
