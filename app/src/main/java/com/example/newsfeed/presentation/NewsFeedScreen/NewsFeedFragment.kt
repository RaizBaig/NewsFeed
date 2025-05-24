package com.example.newsfeed.presentation.NewsFeedScreen

import NewsAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsfeed.R
import com.example.newsfeed.databinding.FragmentNewsFeedBinding
import com.example.newsfeed.presentation.viewmodel.NewsListViewModel
import com.example.newsfeed.util.ResponseHandler
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsFeedFragment : Fragment() {

    private var _binding: FragmentNewsFeedBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NewsListViewModel by viewModels()
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

//        viewModel.fetchNews("\"us\"")
        viewModel.fetchNews("\"us\"", isInitialLoad = true)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.news.collectLatest { result ->
                when (result) {
                    is ResponseHandler.Loading -> {
                        // Show loading (optional)
                    }
                    is ResponseHandler.Success -> {
                        newsAdapter.updateList(result.data)
                    }
                    is ResponseHandler.Error -> {
                        // Show error
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter(emptyList()) { article ->
            val action = NewsFeedFragmentDirections.actionNewsFeedFragmentToNewsDetailsFragment(article)
            findNavController().navigate(action)
        }

        binding.newsRV.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = newsAdapter

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                    val isAtEnd = firstVisibleItemPosition + visibleItemCount >= totalItemCount
                    val shouldPaginate = isAtEnd && dy > 0

                    if (shouldPaginate) {
                        viewModel.fetchNews("\"us\"")
                    }
                }
            })

        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
