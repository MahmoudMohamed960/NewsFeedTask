package com.example.newsFeedsApp.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.newsFeedsApp.viewmodel.HomeViewModel
import com.example.newsFeedsApp.R
import com.example.newsFeedsApp.databinding.FragmentArticleDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class ArticleDetailsFragment : Fragment() {
    lateinit var viewModel: HomeViewModel
    lateinit var binding: FragmentArticleDetailsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_article_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentArticleDetailsBinding.bind(view)
        viewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
        Glide.with(requireContext()).load(viewModel.article?.urlToImage).placeholder(R.drawable.placeholder).into(binding.articleImage)
        binding.authorName.text = viewModel.article?.author
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val outputFormat = SimpleDateFormat("MMM dd, yyyy")
        val parsedDate: Date = inputFormat.parse(viewModel.article?.publishedAt)
        val formattedDate: String = outputFormat.format(parsedDate)
        binding.createdAt.text = formattedDate
        binding.title.text = viewModel.article?.title
        binding.descriptionText.text = viewModel.article?.description

        binding.openWebsite.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_VIEW
            intent.addCategory(Intent.CATEGORY_BROWSABLE)
            intent.data = Uri.parse(viewModel.article?.url)
            activity?.startActivity(intent)
        }

    }
}