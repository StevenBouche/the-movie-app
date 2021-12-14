package com.miage.movieapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.example.core.models.api.Review
import com.miage.movieapp.databinding.ReviewItemBinding
import com.miage.movieapp.extension.setRatingBar
import com.miage.movieapp.extension.setAvatarImage
import com.miage.movieapp.utils.RecyclerScrollListener
import android.animation.ObjectAnimator

class MovieReviewsAdapter(
    infiniteContentScrollListener: RecyclerScrollListener
) : ListRecyclerContScrollAdapter<Review, ReviewItemBinding, MovieReviewsAdapter.ViewHolderContent>(
    DiffCallback(), infiniteContentScrollListener)  {

    override fun bindingInflate(layout: LayoutInflater, parent: ViewGroup): ReviewItemBinding {
        return ReviewItemBinding.inflate(layout, parent, false)
    }

    override fun bindContentViewHolder(): ViewHolderContent {
        return ViewHolderContent()
    }

    override fun bindViewHolder(
        item: Review,
        viewHolder: ListRecyclerContAdapter<Review, ReviewItemBinding, ViewHolderContent>.ViewHolder
    ) {
        val binding = viewHolder.binding
        val content = viewHolder.content
        binding.movie = item
        binding.reviewsButton.setOnClickListener { actionButtonExpendable(content, binding) }
        binding.image.setAvatarImage(item.authorDetails?.avatarPath)
        setRating(item, binding)
        setText(item, binding)
    }

    private fun actionButtonExpendable(content: ViewHolderContent, binding: ReviewItemBinding){
        if (!content.expandable) {
            content.expandable = true
            val animation = ObjectAnimator.ofInt(binding.contentText, "maxLines", 400)
            animation.setDuration(100).start()
        } else {
            content.expandable = false
            val animation = ObjectAnimator.ofInt(binding.contentText, "maxLines", 4)
            animation.setDuration(100).start()
        }
    }

    private fun setRating(item: Review, binding: ReviewItemBinding){
        try {
            item.authorDetails?.let {
                val value = it.rating?.toFloat();
                binding.ratingBar.setRatingBar(value,5)
            }
        } catch (e: Exception){
            e.printStackTrace()
        }
    }

    private fun setText(item: Review, binding: ReviewItemBinding){
        val textView = binding.contentText
        textView.text = item.content
    }

    class ViewHolderContent {
        var expandable: Boolean = false
    }

    private class DiffCallback : DiffUtil.ItemCallback<Review>() {
        override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean {
            return oldItem.id == newItem.id
        }
    }
}