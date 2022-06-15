package cl.uandes.myblogapp.ui.view.blog.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import cl.uandes.myblogapp.R
import cl.uandes.myblogapp.databinding.CreatePostFragmentBinding
import cl.uandes.myblogapp.ui.viewmodel.blog.CreatePostViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreatePostFragment: Fragment() {

  private lateinit var binding: CreatePostFragmentBinding
  private lateinit var viewModel: CreatePostViewModel

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {

    binding = DataBindingUtil.inflate(
      inflater, R.layout.create_post_fragment, container, false)
    viewModel = ViewModelProvider(this)[CreatePostViewModel::class.java]

    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding.createPostViewModel = viewModel

    observeViewModel()
  }

  private fun observeViewModel() {
    viewModel.isSuccessful.observe(viewLifecycleOwner) { successful ->
      successful.let {
        if(successful) {
          goToPostListFragment()
        }
      }
    }
  }

  private fun goToPostListFragment() {
    findNavController().navigate(R.id.action_createPostFragment_to_postListFragment)
  }

}
