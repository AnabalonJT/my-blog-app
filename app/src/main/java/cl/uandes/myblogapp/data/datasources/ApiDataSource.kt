package cl.uandes.myblogapp.data.datasources

import cl.uandes.myblogapp.data.api.blog.BlogApi
import cl.uandes.myblogapp.data.api.authentication.UserApi
import cl.uandes.myblogapp.data.model.Post
import cl.uandes.myblogapp.data.model.User
import javax.inject.Inject

class ApiDataSource @Inject constructor(
  private var userApi: UserApi,
  private var api: BlogApi?
) : RemoteDataSource {

  override suspend fun getPosts(): List<Post> {
    api?.getPosts().also {
      if (it?.isSuccessful == true) return it.body()?.posts ?: emptyList()
    }

    return emptyList()
  }

  override suspend fun getPost(postId: String): Post? {
    api?.getPost(postId).also {
      if(it?.isSuccessful == true) {
        return it.body()?.getValue("post")
      }
    }

    return null
  }

  override suspend fun createPost(post: Post): Boolean {
    api?.createPost(post).also {
      return it?.isSuccessful == true
    }
  }

  override suspend fun login(user: User): Boolean {
    userApi.login(user).also {
      if (it.isSuccessful) {
        InMemoryDataSource.authToken = it.body()?.getValue("token")
        InMemoryDataSource.currentUser = user
        InMemoryDataSource.email = user.email
        user.id = it.body()?.getValue("user_id")?.toLong()
      }
      return it.isSuccessful
    }
  }

  override suspend fun signUp(newUser: User): Boolean {
    userApi.signUp(newUser).also {
      return it.isSuccessful
    }
  }
}
