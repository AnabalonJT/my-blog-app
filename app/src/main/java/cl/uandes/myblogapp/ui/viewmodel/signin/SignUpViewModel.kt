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
class SignUpViewModel @Inject constructor(
  application: Application,
  private val repository: MyBlogRepository
) : AndroidViewModel(application) {

  val emailLiveData = MutableLiveData("")
  val passwordLiveData = MutableLiveData("") // ojo: la password siempre debe ser secreta
  val isSignedUp = MutableLiveData(false)

  fun signUp() {
    var newUser = User(null, emailLiveData.value.toString(),passwordLiveData.value.toString())
    viewModelScope.launch {
      isSignedUp.postValue(repository.signUp(newUser))
    }
  }

  fun isValidEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
  }
}
