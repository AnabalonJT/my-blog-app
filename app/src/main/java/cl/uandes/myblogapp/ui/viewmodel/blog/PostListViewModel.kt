package cl.uandes.myblogapp.ui.viewmodel.blog

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cl.uandes.myblogapp.data.MyBlogRepository
import cl.uandes.myblogapp.data.model.Post
import cl.uandes.myblogapp.services.location.ForegroundLocationLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostListViewModel @Inject constructor(
  application: Application,
  private val repository: MyBlogRepository
) : AndroidViewModel(application) {

  val title = MutableLiveData("Posts")
  val loading = MutableLiveData(true)
  val postsLiveData = MutableLiveData<List<Post>>()
  val foregroundLocationLiveData = ForegroundLocationLiveData(application)

  fun refresh() {
    loadPosts()
    loading.value = false
  }

  private fun loadPosts() {
    viewModelScope.launch {
      postsLiveData.postValue(repository.getPosts())
    }
  }
}
