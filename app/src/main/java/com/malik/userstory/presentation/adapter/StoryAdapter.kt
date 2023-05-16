package com.malik.userstory.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.malik.userstory.databinding.StoriesCardBinding
import com.malik.userstory.data.response.ListStoryItem

class StoryAdapter(
    private val stories: List<ListStoryItem>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<StoryAdapter.ViewHolder>() {
    interface OnItemClickListener {
        fun onItemClick(id: String)
    }

    class ViewHolder(private var binding: StoriesCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(stories: ListStoryItem) {
            binding.apply {
                nameTv.text = stories.name
                descriptionTv.text = stories.description
                Glide.with(itemView)
                    .load(stories.photoUrl)
                    .into(binding.storyImage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = StoriesCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(stories[position])

        holder.itemView.setOnClickListener {
            listener.onItemClick(stories[position].id.toString())
        }
    }

    override fun getItemCount(): Int = stories.size

}