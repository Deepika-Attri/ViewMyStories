package com.viewmystories.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.viewmystories.R
import com.viewmystories.data.StoryList
import com.viewmystories.databinding.StoriesAdapterBinding
import com.viewmystories.ui.PlayStoriesActivity

class StoriesAdapter(
    private val mActivity: Activity, private var list: ArrayList<StoryList>
) : RecyclerView.Adapter<StoriesAdapter.DeviceHolder>() {

    private var itemBinding: StoriesAdapterBinding? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceHolder {
        itemBinding =
            StoriesAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DeviceHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: DeviceHolder, position: Int) {
        holder.bind(list, position)
    }

    override fun getItemCount(): Int = list.size

    inner class DeviceHolder(private val itemBinding: StoriesAdapterBinding?) :
        RecyclerView.ViewHolder(itemBinding?.root!!) {
        @SuppressLint("SetTextI18n")
        fun bind(
            list: ArrayList<StoryList>, position: Int
        ) {
            val item = list[position]

            itemBinding?.apply {
                titleTV.text = item.title

                if (item.isImage) {
                    Glide.with(root.context)
                        .load(item.image)
                        .placeholder(R.drawable.ic_placeholder)
                        .error(R.drawable.ic_placeholder)
                        .into(storyIv)
                } else {
                    setImageFromVideoUrl(storyIv, item.video)
                }
                root.setOnClickListener {
                    val intent = Intent(mActivity, PlayStoriesActivity::class.java)
                    intent.putExtra("position", position)
                    intent.putExtra("list", list)
                    mActivity.startActivity(intent)
                }
            }
        }

        @BindingAdapter("videoThumbnailFromUrl")
        fun setImageFromVideoUrl(imageView: ImageView, url: String) {
            val thumb = 10000L
            val options = RequestOptions().frame(thumb)
            Glide.with(imageView.context).load(url).apply(options)
                .placeholder(R.drawable.ic_placeholder).into(imageView)
        }
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }
}