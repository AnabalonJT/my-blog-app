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
import cl.uandes.myblogapp.databinding.SignUpFragmentBinding
import cl.uandes.myblogapp.ui.viewmodel.signin.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.sign

@AndroidEntryPoint
class SignUpFragment : Fragment() {

  private lateinit var binding: SignUpFragmentBinding
  private lateinit var viewModel: SignUpViewModel

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    viewModel = ViewModelProvider(this)[SignUpViewModel::class.java]

    binding = DataBindingUtil.inflate(
      inflater, R.layout.sign_up_fragment, container, false)

    binding.lifecycleOwner = this

    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding.signUpViewModel = viewModel

    observeViewModel()
  }

  private fun observeViewModel() {
    viewModel.emailLiveData.observe(viewLifecycleOwner) { email ->
      email?.let {
        if (!viewModel.isValidEmail(it) && email.isNotEmpty()) {
          binding.signUpEmailErrorTextView.visibility = View.VISIBLE
        } else {
          binding.signUpEmailErrorTextView.visibility = View.GONE
        }
      }
    }
    viewModel.isSignedUp.observe(viewLifecycleOwner) { signedUp ->
      signedUp?.let {
        if(signedUp) {
          goToSignIn()
          Toast.makeText(
            context,
            "Ya estás registrada. Ahora puedes hacer login!",
            Toast.LENGTH_LONG
          ).show()
        } else {
          if (viewModel.emailLiveData.value!!.isNotEmpty()) {
            Toast
              .makeText(context, "Error, intente nuevamente", Toast.LENGTH_LONG)
              .show()
          }
        }
      }

    }
  }


  private fun goToSignIn() {
    findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
  }
}
