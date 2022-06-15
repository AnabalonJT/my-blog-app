package cl.uandes.myblogapp.ui.view.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import cl.uandes.myblogapp.R
import cl.uandes.myblogapp.databinding.LoginFragmentBinding
import cl.uandes.myblogapp.ui.viewmodel.signin.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

  private lateinit var binding: LoginFragmentBinding
  private lateinit var viewModel: LoginViewModel

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
    binding = DataBindingUtil.inflate(
      inflater, R.layout.login_fragment, container, false)

    binding.lifecycleOwner = this

    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding.loginViewModel = viewModel

    val signUpButton = binding.signUpButton

    signUpButton.setOnClickListener {
      goToSignUp()
    }

    observeViewModel()
  }



  private fun observeViewModel() {
    viewModel.credentialsAreValid.observe(viewLifecycleOwner) { areValid ->
      areValid?.let {
        if (it) {
          findNavController().navigate(R.id.posts_navigation)
        } else {
          Toast.makeText(context, "Credenciales inválidas", Toast.LENGTH_LONG).show()
        }
      }
    }

    viewModel.emailLiveData.observe(viewLifecycleOwner) { email ->
      email?.let {
        if (!viewModel.isValidEmail(it) && email.isNotEmpty()) {
          binding.emailErrorTextView.visibility = View.VISIBLE
        } else {
          binding.emailErrorTextView.visibility = View.GONE
        }
      }
    }
  }

  private fun goToSignUp() {
    findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
  }
}
