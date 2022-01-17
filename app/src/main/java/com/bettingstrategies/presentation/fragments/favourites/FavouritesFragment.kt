package com.bettingstrategies.presentation.fragments.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bettingstrategies.R
import com.bettingstrategies.data.model.BettingStrategyEntity
import com.bettingstrategies.databinding.FragmentFavouritesBinding
import com.bettingstrategies.presentation.fragments.BaseFragment
import com.bettingstrategies.presentation.fragments.adapter.BetAdapter
import com.bettingstrategies.presentation.fragments.adapter.OmItemClickCallBack
import com.bettingstrategies.presentation.fragments.strategies.events.StrategyEvent
import com.bettingstrategies.presentation.fragments.strategy.StrategyFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavouritesFragment : BaseFragment(), OmItemClickCallBack {

    private val viewModel by viewModel<FavouritesViewModel>()
    private lateinit var binding: FragmentFavouritesBinding
    private var favouritesAdapter = BetAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavouritesBinding.inflate(inflater,container, false)
        initViews()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getStrategies()
        observeViewModel()
    }

    override fun initViews() {
        binding.rvSavedNews.apply {
            adapter = favouritesAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    override fun observeViewModel() {
        viewModel.favouriteList.observe(viewLifecycleOwner, Observer {
            favouritesAdapter.differ.submitList(it)
            binding.tvEmpty.visibility = if(viewModel.isListEmpty()) View.VISIBLE else View.GONE

        })
    }

    override fun onFavouriteClick(strategy: BettingStrategyEntity) {
        viewModel.onEvent(event = StrategyEvent.ChangeFavouriteStatus(strategy = strategy))

    }

    override fun onMoreClick(strategy: BettingStrategyEntity) {
        val bundle = Bundle().apply {
            putSerializable(StrategyFragment.KEY, strategy)
        }
        findNavController().navigate(R.id.action_favouritesFragment_to_strategyFragment, bundle)
    }

    override fun onItemClick(strategy: BettingStrategyEntity) {
        val bundle = Bundle().apply {
            putSerializable(StrategyFragment.KEY, strategy)
        }
        findNavController().navigate(R.id.action_favouritesFragment_to_strategyFragment, bundle)
    }


}