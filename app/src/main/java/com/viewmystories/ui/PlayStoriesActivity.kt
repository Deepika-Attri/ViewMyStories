package com.viewmystories.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.bumptech.glide.Glide
import com.storyview.StoryProgressView
import com.viewmystories.R
import com.viewmystories.data.StoriesPlaybackInterface
import com.viewmystories.data.StoryList
import com.viewmystories.databinding.ActivityStoryBinding
import com.viewmystories.utils.getSerializableList
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PlayStoriesActivity : AppCompatActivity(), StoryProgressView.StoriesListener, StoriesPlaybackInterface {
    private var binding: ActivityStoryBinding? = null
    private var player: ExoPlayer? = null
    private var counter = 0
    private var pressTime = 0L
    private val internal = 1000L
    private val progressUpdateHandler = Handler(Looper.getMainLooper())
    private var storyList: ArrayList<StoryList>? = ArrayList()
    private var videoFinished = false
    private var delayJob: Job? = null

    private val progressUpdateRunnable = object : Runnable {
        override fun run() {
            val currentPosition = player?.currentPosition ?: 0
            val duration = player?.duration ?: 1000
            binding?.storyProgressView?.updateProgress(currentPosition, duration)
            progressUpdateHandler.postDelayed(this, internal)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStoryBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        clickListeners()
        getIntentData()
    }

    private fun getIntentData() {
        binding?.apply {
            counter = intent.getIntExtra("position", 0)

            storyList = intent.getSerializableList("list")

            storyProgressView.setStoriesCount(storyList?.size!!)
            storyProgressView.setStoriesListener(this@PlayStoriesActivity)

            for (i in counter until (storyList?.size ?: 0)) {
                if (i != counter) {
                    storyProgressView.setCurrentStory(i)
                    storyProgressView.updateProgress(0, 1000)
                }
            }

            setStoryData()
            storyProgressView.setCurrentStory(counter)

        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        })
    }

    private fun clickListeners() {
        binding?.apply {
            val reverse = findViewById<View>(R.id.reverse)
            reverse.setOnClickListener {
                delayJob?.cancel()
                if (counter > 0) {
                    storyProgressView.updateProgress(0, player?.duration ?: 1000)
                    counter--
                    storyProgressView.setCurrentStory(counter)
                    setStoryData()
                }
            }
            reverse.setOnTouchListener(onTouchListener)

            val skip = findViewById<View>(R.id.skip)
            skip.setOnClickListener {
                delayJob?.cancel()
                if (counter < storyList!!.size - 1) {
                    storyProgressView.updateProgress(
                        player?.duration ?: 1000, player?.duration ?: 1000
                    )
                    counter++
                    storyProgressView.setCurrentStory(counter)
                    setStoryData()
                } else {
                    finish()
                }
            }
            skip.setOnTouchListener(onTouchListener)

            backIv.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    private fun setStoryData() {
        binding?.apply {
            if (storyList?.get(counter)?.isImage == true) {
                // for image
                imageView.visibility = View.VISIBLE
                videoView.visibility = View.GONE

                val imageUrl = storyList?.get(counter)?.image
                imageView.let {
                    Glide.with(this@PlayStoriesActivity)
                        .load(imageUrl)
                        .placeholder(R.drawable.ic_placeholder)
                        .error(R.drawable.ic_placeholder)
                        .into(it)
                }

                player?.stop()
                progressUpdateHandler.removeCallbacks(progressUpdateRunnable)
                binding?.storyProgressView?.updateProgress(3000, 3000)

                delayJob = lifecycleScope.launch {
                    delay(3000) // Delay for 3 seconds
                    counter++
                    if (counter < storyList?.size!!) {
                        binding?.storyProgressView?.setCurrentStory(counter)
                        setStoryData()
                    } else {
                        finish()
                    }
                }

            } else {
                // for video
                imageView.visibility = View.GONE
                videoView.visibility = View.VISIBLE

                val videoUrl = storyList?.get(counter)?.video
                playVideo(videoUrl.toString())

                // Start the progress update handler
                progressUpdateHandler.post(progressUpdateRunnable)
            }
        }
    }

    private fun playVideo(url: String) {
        videoFinished = false

        player?.stop()
        player = ExoPlayer.Builder(this).build()
        binding?.videoView?.player = player

        val mediaItem = MediaItem.fromUri(url)
        player?.setMediaItem(mediaItem)

        player?.prepare()
        player?.playWhenReady = true

        player?.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                if (state == Player.STATE_ENDED) {
                    videoFinished = true

                    counter++
                    if (counter < storyList?.size!!) {
                        binding?.storyProgressView?.setCurrentStory(counter)
                        setStoryData()
                    } else {
                        finish()
                    }
                }
            }

            override fun onPlayerError(error: PlaybackException) {
                super.onPlayerError(error)
                Log.d("EXO_ERROR", error.toString())
                Log.d("TAG", "videoId: vonPlayerError $error")
            }
        })
    }

    override fun onNext() {
        // Not needed for this approach
    }

    override fun onPrev() {
        // Not needed for this approach
    }

    override fun onComplete() {
        finish()
    }

    override fun onDestroy() {
        player?.release()
        progressUpdateHandler.removeCallbacks(progressUpdateRunnable)
        super.onDestroy()
    }

    override fun pauseStory() {
        binding?.storyProgressView?.pause()
        player?.pause()
    }

    override fun resumeStory() {
        binding?.storyProgressView?.resume()
        player?.play()
    }

    @SuppressLint("ClickableViewAccessibility")
    private val onTouchListener = View.OnTouchListener { _, event ->
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                pressTime = System.currentTimeMillis()
                binding?.storyProgressView?.pause()
                player?.pause()
                return@OnTouchListener false
            }

            MotionEvent.ACTION_UP -> {
                val now = System.currentTimeMillis()
                binding?.storyProgressView?.resume()
                player?.play()
                return@OnTouchListener internal < now - pressTime
            }
        }
        false
    }
}



