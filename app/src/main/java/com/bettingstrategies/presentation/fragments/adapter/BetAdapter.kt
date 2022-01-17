package com.bettingstrategies.presentation.fragments.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bettingstrategies.R
import com.bettingstrategies.data.model.BettingStrategyEntity
import com.bettingstrategies.databinding.StrategyItemBinding
import com.bumptech.glide.Glide

interface OmItemClickCallBack {

    fun onFavouriteClick(strategy: BettingStrategyEntity)

    fun onMoreClick(strategy: BettingStrategyEntity)

    fun onItemClick(strategy: BettingStrategyEntity)

}

class BetAdapter(private val onItemClickCallback: OmItemClickCallBack): RecyclerView.Adapter<BetAdapter.StrategiesListViewHolder>(){

    private val differCallBack = object: DiffUtil.ItemCallback<BettingStrategyEntity>() {
        override fun areItemsTheSame(oldItem: BettingStrategyEntity, newItem: BettingStrategyEntity): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: BettingStrategyEntity,
            newItem: BettingStrategyEntity
        ): Boolean {
            return oldItem.id == newItem.id
        }
    }

    val differ = AsyncListDiffer(this,differCallBack)

    class StrategiesListViewHolder(val binding: StrategyItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(bettingStrategy: BettingStrategyEntity, onItemClickCallback: OmItemClickCallBack) {

            binding.tvTitle.text = bettingStrategy.title
            binding.tvDescription.text = bettingStrategy.description
            Glide.with(itemView).load(bettingStrategy.urlToImage).into(binding.ivStrategies)

            binding.ivAddToFavorite.setImageResource(
                if (bettingStrategy.isFavourite) {
                    R.drawable.ic_filled_favorite
                } else {
                    R.drawable.ic_empty_favourite
                }
            )

            binding.ivAddToFavorite.setOnClickListener {
                onItemClickCallback.onFavouriteClick(bettingStrategy)
            }

            binding.ivMoreInfo.setOnClickListener {
                onItemClickCallback.onMoreClick(bettingStrategy)
            }

            itemView.setOnClickListener {
                onItemClickCallback.onItemClick(bettingStrategy)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StrategiesListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = StrategyItemBinding.inflate(inflater,parent,false)
        return StrategiesListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StrategiesListViewHolder, position: Int) {
        holder.bind(differ.currentList[position], onItemClickCallback)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}