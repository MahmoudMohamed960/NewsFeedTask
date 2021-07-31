package com.example.newsFeedsApp.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsFeedsApp.R
import com.example.newsFeedsApp.models.Article
import kotlinx.android.synthetic.main.article_item_layout.view.*
import java.text.SimpleDateFormat
import java.util.*

class ArticlesAdapter(
    var context: Context,
    var list: MutableList<Article>,
    var onItemClicked: OnItemClicked
) :
    RecyclerView.Adapter<ArticlesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.article_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var article = list[position]
        holder.setData(article)
    }

    override fun getItemCount() = list.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setData(articleModel: Article) {
            Glide.with(context).load(articleModel.urlToImage).placeholder(R.drawable.placeholder)
                .into(itemView.article_image)
            itemView.title.text = articleModel.title
            itemView.author_name.text = articleModel.author
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val outputFormat = SimpleDateFormat("MMM dd, yyyy")
            val parsedDate: Date = inputFormat.parse(articleModel.publishedAt)
            val formattedDate: String = outputFormat.format(parsedDate)
            itemView.created_at.text = formattedDate
            itemView.setOnClickListener {
                onItemClicked.onItemClicked(articleModel)
            }

        }
    }

    interface OnItemClicked {
        fun onItemClicked(article: Article)
    }
}