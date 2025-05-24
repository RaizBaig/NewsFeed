package com.example.newsfeed.presentation.newsdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.newsfeed.databinding.FragmentNewsDetailsBinding
import com.example.newsfeed.domain.model.NewsArticle
import com.example.newsfeed.util.DateTimeHandler
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsDetailFragment : Fragment() {

    private var _binding: FragmentNewsDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: NewsDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        args.article.let {
            binding.publishedAt.text  = DateTimeHandler.formatUtcToReadable(args.article.publishedAt)
            binding.article = it
            Glide.with(requireContext())
                .load(it?.imageUrl)
                .into(binding.imageView)
        }
    }

    companion object {
        fun newInstance(article: NewsArticle): NewsDetailFragment {
            return NewsDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("article", article)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
