package com.miage.movieapp.ui.youtube

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.miage.movieapp.databinding.FragmentYoutubeBinding
import com.miage.movieapp.ui.fragment.BaseFragment
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback

class YoutubeFragment: BaseFragment<FragmentYoutubeBinding>() {

    private val args by navArgs<YoutubeFragmentArgs>()

    override fun createBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentYoutubeBinding {
        return FragmentYoutubeBinding.inflate(inflater, container, false)
    }

    override fun setupBinding(){
        binding.youtube = args.movieVideo
        binding.youtubePlayerView.getYouTubePlayerWhenReady(object : YouTubePlayerCallback {
            override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(args.movieVideo.id, 0f)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.youtubePlayerView.release()
    }
}