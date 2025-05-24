import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsfeed.databinding.ItemNewsBinding
import com.example.newsfeed.domain.model.NewsArticle
import com.example.newsfeed.util.DateTimeHandler

class NewsAdapter(
    private var items: List<NewsArticle>,
    private val onItemClick: (NewsArticle) -> Unit
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    inner class NewsViewHolder(private val binding: ItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: NewsArticle) {
            val formattedDate = DateTimeHandler.formatUtcToReadable(article.publishedAt)
            binding.newsDate.text = formattedDate
            binding.article = article
            binding.onClick = onItemClick

            // Load image manually
            Glide.with(binding.root.context)
                .load(article.imageUrl)
                .into(binding.newsImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ItemNewsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun updateList(newItems: List<NewsArticle>) {
        items = newItems
        notifyDataSetChanged()
    }
}
