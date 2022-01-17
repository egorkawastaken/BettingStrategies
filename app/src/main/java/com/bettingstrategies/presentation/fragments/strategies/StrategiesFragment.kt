package com.bettingstrategies.presentation.fragments.strategies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bettingstrategies.R
import com.bettingstrategies.data.model.BettingStrategyEntity
import com.bettingstrategies.databinding.FragmentStrategiesBinding
import com.bettingstrategies.presentation.fragments.BaseFragment
import com.bettingstrategies.presentation.fragments.adapter.BetAdapter
import com.bettingstrategies.presentation.fragments.adapter.OmItemClickCallBack
import com.bettingstrategies.presentation.fragments.strategies.events.StrategyEvent
import com.bettingstrategies.presentation.fragments.strategy.StrategyFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class StrategiesFragment : BaseFragment(), OmItemClickCallBack {

    private val viewModel by viewModel<StrategiesViewModel>()
    private lateinit var binding: FragmentStrategiesBinding
    private var strategiesAdapter = BetAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStrategiesBinding.inflate(inflater,container, false)
        initViews()
        observeViewModel()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getStrategies()
        observeViewModel()
        binding.refreshLayout.setOnRefreshListener {
            viewModel.getStrategies()
            binding.refreshLayout.isRefreshing = false
        }
    }


    override fun initViews() {
        binding.rvStrategies.apply {
            adapter = strategiesAdapter
            layoutManager = LinearLayoutManager(activity)
        }


    }

    override fun observeViewModel() {

        viewModel.strategiesList.observe(viewLifecycleOwner, Observer {
            strategiesAdapter.differ.submitList(it)
        })

    }

    override fun onFavouriteClick(strategy: BettingStrategyEntity) {
        viewModel.onEvent(event = StrategyEvent.ChangeFavouriteStatus(strategy = strategy))
    }

    override fun onMoreClick(strategy: BettingStrategyEntity) {
        val bundle = Bundle().apply {
            putSerializable(StrategyFragment.KEY, strategy)
        }
        findNavController().navigate(R.id.action_strategiesFragment_to_strategyFragment, bundle)

    }

    override fun onItemClick(strategy: BettingStrategyEntity) {
        val bundle = Bundle().apply {
            putSerializable(StrategyFragment.KEY, strategy)
        }
        findNavController().navigate(R.id.action_strategiesFragment_to_strategyFragment, bundle)
    }



}