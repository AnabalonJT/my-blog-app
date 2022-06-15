package cl.uandes.myblogapp.ui.viewmodel.blog

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cl.uandes.myblogapp.data.MyBlogRepository
import cl.uandes.myblogapp.data.model.Post
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
  application: Application,
  private val repository: MyBlogRepository
) : AndroidViewModel(application) {

  private val loading = MutableLiveData(true)
  val postLiveData = MutableLiveData<Post>()

  fun refresh(postId: String) {
    loadPost(postId)

    loading.value = false
  }

  private fun loadPost(postId: String) {
    viewModelScope.launch {
      postLiveData.postValue(repository.getPost(postId))
    }
  }
}
