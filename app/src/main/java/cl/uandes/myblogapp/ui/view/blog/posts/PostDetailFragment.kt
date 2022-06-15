package cl.uandes.myblogapp.ui.view.blog.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import cl.uandes.myblogapp.R
import cl.uandes.myblogapp.databinding.PostDetailFragmentBinding
import cl.uandes.myblogapp.ui.view.blog.comments.CommentsAdapter
import cl.uandes.myblogapp.ui.viewmodel.blog.PostViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostDetailFragment: Fragment() {

  private lateinit var binding: PostDetailFragmentBinding
  private lateinit var viewModel: PostViewModel
  private lateinit var postId: String
  private lateinit var commentsAdapter: CommentsAdapter

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = DataBindingUtil.inflate(
      inflater, R.layout.post_detail_fragment, container, false)
    viewModel = ViewModelProvider(this)[PostViewModel::class.java]

    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    arguments?.let {
      postId = PostDetailFragmentArgs.fromBundle(it).postId
    }
    viewModel.refresh(postId)
    binding.postViewModel = viewModel

    commentsAdapter = CommentsAdapter(mutableListOf())
    binding.postComments.apply {
      layoutManager = LinearLayoutManager(context)
      adapter = commentsAdapter
    }

    observeViewModel()
  }

  private fun observeViewModel() {
    viewModel.postLiveData.observe(viewLifecycleOwner) { post ->
      post?.let {
        binding.postComments.visibility = View.VISIBLE
        binding.postDetailTitleTextView.text = post.title
        binding.postDetailAuthorTextView.text = post.author.email
        binding.postDetailDescriptionTextView.text = post.body

        if (!post.comments.isNullOrEmpty()) {
          commentsAdapter.updateComments(post.comments)
        }
      }
    }
  }

}
