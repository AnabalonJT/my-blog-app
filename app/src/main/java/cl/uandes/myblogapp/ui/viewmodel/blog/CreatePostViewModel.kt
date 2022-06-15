package cl.uandes.myblogapp.ui.viewmodel.blog

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cl.uandes.myblogapp.data.MyBlogRepository
import cl.uandes.myblogapp.data.datasources.InMemoryDataSource
import cl.uandes.myblogapp.data.model.Post
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreatePostViewModel @Inject constructor(
  application: Application,
  private val repository: MyBlogRepository
) : AndroidViewModel(application) {

  val titleLiveData = MutableLiveData("")
  val bodyLiveData = MutableLiveData("")
  val isSuccessful = MutableLiveData(false)

  fun createPost() {
    val newPost = Post(
      null,
      InMemoryDataSource.currentUser!!,
      titleLiveData.value!!,
      bodyLiveData.value!!,
      null
    )

    viewModelScope.launch {
      repository.createPost(newPost)
      isSuccessful.postValue(true)
    }

  }
}
