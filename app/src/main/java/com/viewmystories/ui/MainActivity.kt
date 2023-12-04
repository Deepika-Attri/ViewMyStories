package com.viewmystories.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.viewmystories.data.PostList
import com.viewmystories.adapters.PostsAdapter
import com.viewmystories.adapters.StoriesAdapter
import com.viewmystories.data.StoryList
import com.viewmystories.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    private var postList: ArrayList<PostList> = ArrayList()
    private var storyList: ArrayList<StoryList> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        addDataInLists()
        setPostsAdapter()
        setStoriesAdapter()
    }

    private fun setStoriesAdapter() {
        binding?.apply {
            val adapter = StoriesAdapter(
                this@MainActivity, storyList
            )
            val layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            storiesRV.layoutManager = layoutManager
            storiesRV.adapter = adapter
        }
    }

    private fun setPostsAdapter() {
        binding?.apply {
            val adapter = PostsAdapter(
                postList
            )
            val layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            postsRV.layoutManager = layoutManager
            postsRV.adapter = adapter
        }
    }

    private fun addDataInLists() {
        // set stories data
        storyList.add(
            StoryList(
                1,
                "Taylor Swift",
                "https://images.unsplash.com/photo-1563713665854-e72327bf780e?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1350&q=80",
                "",
                true
            )
        )
        storyList.add(
            StoryList(
                2,
                "Shawn Mendes",
                "",
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4",
                false
            )
        )
        storyList.add(
            StoryList(
                3,
                "Charlie",
                "https://fastly.picsum.photos/id/521/1200/600.jpg?hmac=2hGLPIQg47T_xdofdOmCJ0sjbQ73JXJv0Ks88JGWuQA",
                "",
                true
            )
        )
        storyList.add(
            StoryList(
                4,
                "Taylor Swift",
                "",
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerMeltdowns.mp4",
                false
            )
        )
        storyList.add(
            StoryList(
                5,
                "Shawn Mendes",
                "https://i2.wp.com/beebom.com/wp-content/uploads/2016/01/Reverse-Image-Search-Engines-Apps-And-Its-Uses-2016.jpg",
                "",
                true
            )
        )
        storyList.add(
            StoryList(
                6,
                "Charlie",
                "",
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4",
                false
            )
        )
        storyList.add(
            StoryList(
                7,
                "Shawn Mendes",
                "",
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4",
                false
            )
        )
        storyList.add(
            StoryList(
                8,
                "Charlie",
                "",
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerJoyrides.mp4",
                false
            )
        )
        storyList.add(
            StoryList(
                9,
                "Taylor Swift",
                "",
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WeAreGoingOnBullrun.mp4",
                false
            )
        )

        // set posts data
        postList.add(
            PostList(
                1,
                "Taylor Swift",
                "https://images.unsplash.com/photo-1563713665854-e72327bf780e?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1350&q=80"
            )
        )
        postList.add(
            PostList(
                2,
                "Shawn Mendes",
                "https://fastly.picsum.photos/id/521/1200/600.jpg?hmac=2hGLPIQg47T_xdofdOmCJ0sjbQ73JXJv0Ks88JGWuQA"
            )
        )
        postList.add(
            PostList(
                3,
                "Charlie",
                "https://i2.wp.com/beebom.com/wp-content/uploads/2016/01/Reverse-Image-Search-Engines-Apps-And-Its-Uses-2016.jpg"
            )
        )
    }
}