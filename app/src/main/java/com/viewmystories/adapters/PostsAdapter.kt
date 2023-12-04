package com.viewmystories.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.viewmystories.data.PostList
import com.viewmystories.databinding.PostsAdapterBinding

class PostsAdapter(
    private var list: ArrayList<PostList>
) : RecyclerView.Adapter<PostsAdapter.DeviceHolder>() {

    private var itemBinding: PostsAdapterBinding? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceHolder {
        itemBinding =
            PostsAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DeviceHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: DeviceHolder, position: Int) {
        holder.bind(list, position)
    }

    override fun getItemCount(): Int = list.size

    inner class DeviceHolder(private val itemBinding: PostsAdapterBinding?) :
        RecyclerView.ViewHolder(itemBinding?.root!!) {
        @SuppressLint("SetTextI18n")
        fun bind(
            list: ArrayList<PostList>, position: Int
        ) {
            val item = list[position]

            itemBinding?.apply {
                titleTV.text = item.title
                Glide.with(root.context)
                    .load(item.image)
                    .into(postIv)
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }
}