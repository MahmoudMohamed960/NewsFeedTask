package com.example.newsFeedsApp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsFeedsApp.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    lateinit var viewModel: HomeViewModel
    lateinit var binding: FragmentHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        viewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)

        viewModel.getArticlesList()
        viewModel.articlesList.observe(viewLifecycleOwner, Observer { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    if (resource.responseOne?.status == "ok" && resource.responseTwo?.status == "ok") {
                        var articlesList = resource.responseOne?.articles
                        resource.responseTwo?.articles?.let {
                            articlesList?.addAll(it)
                            binding.newsList.layoutManager = LinearLayoutManager(
                                requireContext(),
                                LinearLayoutManager.VERTICAL,
                                false
                            )
                            binding.newsList.adapter =
                                articlesList?.let { it1 -> ArticlesAdapter(requireContext(), it1) }
                        }
                    }
                }
                Status.ERROR -> Toast.makeText(context, resource.message, Toast.LENGTH_LONG).show()
                Status.LOADING -> Toast.makeText(context, "loading", Toast.LENGTH_LONG).show()
            }

        })
    }
}