package com.rappi.movies.presentation.ui.player

import android.os.Bundle
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.rappi.movies.databinding.ActivityPlayerYoutubeBinding
import com.rappi.movies.domain.model.Video


class PlayerYoutubeActivity : AppCompatActivity() {

    private val video: Video? by lazy { intent.getParcelableExtra("video") }

    private lateinit var binding: ActivityPlayerYoutubeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityPlayerYoutubeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.initPlayer()
    }

    private fun initPlayer() {
        lifecycle.addObserver(this.binding.youtubePlayerView)

        this.binding.youtubePlayerView.addYouTubePlayerListener(object :
            AbstractYouTubePlayerListener() {
            override fun onReady(@NonNull youTubePlayer: YouTubePlayer) {
                val videoId = video?.key
                youTubePlayer.loadVideo(videoId ?: "", 0f)
            }
        })
    }
}