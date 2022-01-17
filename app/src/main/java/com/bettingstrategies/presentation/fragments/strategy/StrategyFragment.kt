package com.bettingstrategies.presentation.fragments.strategy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bettingstrategies.R
import com.bettingstrategies.databinding.FragmentStrategyBinding
import com.bettingstrategies.presentation.fragments.BaseFragment
import com.bettingstrategies.presentation.fragments.strategies.StrategiesViewModel
import com.bettingstrategies.presentation.fragments.strategies.events.StrategyEvent
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel

class StrategyFragment : BaseFragment() {

    private lateinit var binding: FragmentStrategyBinding
    private val args: StrategyFragmentArgs by navArgs()
    private val viewModel by viewModel<StrategyViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStrategyBinding.inflate(inflater, container,false)

        binding.fbFavourite.setOnClickListener {
            viewModel.onEvent(StrategyEvent.ChangeFavouriteStatus(args.strategy))
        }

        return binding.root
    }

    override fun initViews() {
        TODO("Not yet implemented")
    }

    override fun observeViewModel() {
        TODO("Not yet implemented")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val strategy = args.strategy
        binding.tvTitle.text = strategy.title
        binding.tvDescription.text = strategy.description
        Glide.with(requireActivity()).load(strategy.urlToImage).into(binding.ivStrategy)
    }

    companion object {
        const val KEY = "strategy"
    }

}