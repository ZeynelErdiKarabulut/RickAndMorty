package com.rickyandmorty.presentation.episode.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rickyandmorty.R
import com.rickyandmorty.databinding.EpisodeItemRowBinding
import com.rickyandmorty.databinding.SeparatorItemViewBinding
import com.rickyandmorty.domain.model.EpisodeDomain
import com.rickyandmorty.domain.model.EpisodeListItem
import com.rickyandmorty.util.ItemClickListener
import timber.log.Timber

class EpisodeListAdapter(val onclickListener: ItemClickListener) :
    PagingDataAdapter<EpisodeListItem, RecyclerView.ViewHolder>(DiffUtilEpisode()) {

    class EpisodeViewHolder(val binding: EpisodeItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): EpisodeViewHolder {
                val binding = EpisodeItemRowBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return EpisodeViewHolder(binding)
            }
        }


        fun bind(episode: EpisodeDomain) {
            binding.episode = episode
            binding.executePendingBindings()
        }
    }

    class SeparatorViewHolder(val binding: SeparatorItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): SeparatorViewHolder {
                val binding = SeparatorItemViewBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )

                return SeparatorViewHolder(binding)
            }
        }

        fun bind(separatorText: String) {
            binding.separatorText.text = separatorText
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == R.layout.separator_item_view) {
            SeparatorViewHolder.from(parent)
        } else {
            EpisodeViewHolder.from(parent)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is EpisodeListItem.EpisodeItem -> R.layout.episode_item_row
            is EpisodeListItem.SeparatorItem -> R.layout.separator_item_view
            null -> throw UnsupportedOperationException("Unknown view")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val episodeModel = getItem(position)



        episodeModel.let { episodeListItem ->
            when (episodeListItem) {
                is EpisodeListItem.EpisodeItem -> {
                    (holder as EpisodeViewHolder).bind(episodeListItem.episode)
                    holder.itemView.setOnClickListener {
                        onclickListener.onClick(episodeListItem.episode.id)
                    }
                }
                is EpisodeListItem.SeparatorItem -> (holder as SeparatorViewHolder).bind(
                    episodeListItem.season
                )
                else -> {
                    Timber.e("unknown type")
                }
            }
        }


    }

    class DiffUtilEpisode : DiffUtil.ItemCallback<EpisodeListItem>() {
        override fun areItemsTheSame(oldItem: EpisodeListItem, newItem: EpisodeListItem): Boolean {
            return (oldItem is EpisodeListItem.EpisodeItem && newItem is EpisodeListItem.EpisodeItem && oldItem.episode.id == newItem.episode.id) ||
                    (oldItem is EpisodeListItem.SeparatorItem && newItem is EpisodeListItem.SeparatorItem && oldItem.season == newItem.season)
        }

        override fun areContentsTheSame(
            oldItem: EpisodeListItem,
            newItem: EpisodeListItem
        ): Boolean {
            return oldItem == newItem
        }

    }


}