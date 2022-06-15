package cl.uandes.myblogapp.ui.view.blog.posts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cl.uandes.myblogapp.R
import cl.uandes.myblogapp.data.model.Post

class PostsAdapter (
  private val postList: MutableList<Post>,
  private val actionListener: ActionListener
):
  RecyclerView.Adapter<PostsAdapter.ViewHolder>() {

  inner class ViewHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    private val postTitle: TextView = itemView.findViewById(R.id.postTitleTextView)
    private val postAuthor: TextView = itemView.findViewById(R.id.authorTextView)
    private var currentPost: Post? = null

    init {
      itemView.setOnClickListener{
        currentPost?.let {
          actionListener.onClick(it)
        }
      }
    }

    fun bind(post: Post) {
      currentPost = post

      postTitle.text = currentPost!!.title
      postAuthor.text = currentPost!!.author.email
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val context = parent.context
    val inflater = LayoutInflater.from(context)

    val breedView: View = inflater.inflate(R.layout.post_item, parent, false)

    return ViewHolder(breedView)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val post = postList[position]

    holder.bind(post)
  }

  override fun getItemCount(): Int {
    return postList.size
  }

  fun updatePosts(posts: List<Post>) {
    postList.clear()
    postList.addAll(posts)

    notifyDataSetChanged()
  }

  interface ActionListener {
    fun onClick(post: Post)
  }
}
