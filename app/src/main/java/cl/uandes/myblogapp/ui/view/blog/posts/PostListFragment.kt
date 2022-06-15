package cl.uandes.myblogapp.ui.view.blog.posts

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import cl.uandes.myblogapp.R
import cl.uandes.myblogapp.data.model.Post
import cl.uandes.myblogapp.databinding.PostListFragmentBinding
import cl.uandes.myblogapp.ui.viewmodel.blog.PostListViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostListFragment : Fragment(), PostsAdapter.ActionListener {

  private lateinit var binding: PostListFragmentBinding
  private lateinit var postsAdapter: PostsAdapter
  private lateinit var viewModel: PostListViewModel

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = DataBindingUtil.inflate(
      inflater, R.layout.post_list_fragment, container, false)
    viewModel = ViewModelProvider(this)[PostListViewModel::class.java]

    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    viewModel.refresh()

    postsAdapter = PostsAdapter(mutableListOf(), this)

    binding.postListViewModel = viewModel
    binding.postRecyclerView.apply {
      layoutManager = LinearLayoutManager(context)
      adapter = postsAdapter
    }

    observeViewModel()
    binding.createNewPostButton.setOnClickListener {
      setGoToCreatePostToNewPostButton()
    }
  }

  @SuppressLint("SetTextI18n")
  private fun observeViewModel() {
    viewModel.postsLiveData.observe(viewLifecycleOwner) { posts ->
      posts?.let {
        binding.postRecyclerView.visibility = View.VISIBLE
        postsAdapter.updatePosts(posts)
      }
    }
    viewModel.foregroundLocationLiveData.observe(viewLifecycleOwner) { location ->
      location?.let {
        binding.locationTextView.text = "${location.latitude} - ${location.longitude}"
      }
    }
  }

  private fun setGoToCreatePostToNewPostButton() {
    findNavController().navigate(R.id.action_postListFragment_to_createPostFragment)
  }

  override fun onClick(post: Post) {
    val bundle = bundleOf("postId" to post.id.toString())
    findNavController().navigate(R.id.action_postListFragment_to_postDetailFragment, bundle)
  }
}
