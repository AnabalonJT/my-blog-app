package cl.uandes.myblogapp.ui.view.blog.comments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cl.uandes.myblogapp.R
import cl.uandes.myblogapp.data.model.Comment

class CommentsAdapter (
  private val commentList: MutableList<Comment>
  ): RecyclerView.Adapter<CommentsAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) :
      RecyclerView.ViewHolder(itemView) {
      private val comment: TextView = itemView.findViewById(R.id.commentTextView)
      private val commentAuthor: TextView = itemView.findViewById(R.id.commentAuthorTextView)
      private var currentComment: Comment? = null

      fun bind(comment: Comment) {
        currentComment = comment

        this.comment.text = currentComment!!.body
        commentAuthor.text = currentComment!!.author.email
      }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      val context = parent.context
      val inflater = LayoutInflater.from(context)

      val breedView: View = inflater.inflate(R.layout.comment_item, parent, false)

      return ViewHolder(breedView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      val comment = commentList[position]

      holder.bind(comment)
    }

    override fun getItemCount(): Int {
      return commentList.size
    }

    fun updateComments(comments: List<Comment>) {
      commentList.clear()
      commentList.addAll(comments)

      notifyDataSetChanged()
    }

}
