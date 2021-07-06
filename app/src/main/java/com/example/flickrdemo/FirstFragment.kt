package com.example.flickrdemo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.example.flickrdemo.databinding.FragmentFirstBinding
import com.example.flickrdemo.model.FlickrViewModel
import com.example.flickrdemo.network.Photo
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {
    private val navigationArgs: FirstFragmentArgs by navArgs()
    private var _binding: FragmentFirstBinding? = null

    object PhotoComparator : DiffUtil.ItemCallback<Photo>() {
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            // Id is unique.
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem == newItem
        }
    }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var viewModel = FlickrViewModel(navigationArgs.lat, navigationArgs.lon)
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_first, container, false)
        binding.flickrViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.recyclerView.layoutManager = GridLayoutManager(context, 2)
        val pagingAdapter = Photo2Adapter(PhotoComparator)
        binding.recyclerView.adapter = pagingAdapter
        pagingAdapter.addLoadStateListener { loadState ->


            if (pagingAdapter.itemCount < 1)
                viewModel.setResponseSent(false)
            else
                viewModel.setResponseSent(true)

        }

        viewLifecycleOwner.lifecycleScope.launch {

            viewModel.flow.collectLatest { pagingData ->

                pagingAdapter.submitData(pagingData)

            }

        }

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        //
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}