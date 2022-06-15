package cl.uandes.myblogapp.ui.viewmodel.signin

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cl.uandes.myblogapp.data.MyBlogRepository
import cl.uandes.myblogapp.data.datasources.ApiDataSource
import cl.uandes.myblogapp.data.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
  application: Application,
  private val repository: MyBlogRepository
): AndroidViewModel(application) {

  var emailLiveData = MutableLiveData("")
  var passwordLiveData = MutableLiveData("")
  var credentialsAreValid: MutableLiveData<Boolean> = MutableLiveData()


  fun verifyUser() {
    viewModelScope.launch {
      val user = User(1, emailLiveData.value.toString(), passwordLiveData.value.toString())
      credentialsAreValid.postValue(
        repository.login(user)
      )
    }
  }

  fun isValidEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
  }
}
