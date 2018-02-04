package com.gimenez.kent.spotify

import android.media.MediaPlayer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.gimenez.kent.spotify.model.SongModel
import kotlinx.android.synthetic.main.fragment_song.*
import kotlinx.android.synthetic.main.fragment_song.view.*
import java.io.IOException

class SongFragment : Fragment(), View.OnClickListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {

    private lateinit var SongPlay: TextView
    private lateinit var SingerPlay: TextView
    private lateinit var BtnPlay: ImageView
    private var mMediaPlayer: MediaPlayer? = null




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_song, container, false)
        val song = arguments!!.getParcelable(MainActivity.KEY) as SongModel

        setViews(rootView)
        mMediaPlayer = MediaPlayer()
        try {
            if (mMediaPlayer!!.isPlaying)
                mMediaPlayer?.reset()
            mMediaPlayer?.setDataSource(song.mSongPath)
            mMediaPlayer?.prepare()
        } catch (
                e: IllegalArgumentException) {
            e.printStackTrace()
        } catch (
                e: IllegalStateException
        ) {
            e.printStackTrace()
        } catch (
                e: IOException
        ) {
            e.printStackTrace()
        }

        SongPlay.text = song.Name
        SingerPlay.text = song.Singer
        BtnPlay.setOnClickListener(this)
        mMediaPlayer?.setOnPreparedListener(this)
        mMediaPlayer?.setOnCompletionListener(this)

        return rootView
    }

    override fun onClick(view: View?) {
        if (mMediaPlayer!!.isPlaying) {
            mMediaPlayer?.pause()
            setPlayImage(view)
        } else {
            mMediaPlayer?.seekTo(mMediaPlayer!!.currentPosition)
            mMediaPlayer?.start()
            setPauseImage(view)
        }
    }

    override fun onPrepared(mediaPlayer: MediaPlayer?) {
        mMediaPlayer!!.start()
        setPauseImage(play)
    }

    override fun onCompletion(mediaPlayer: MediaPlayer?) {
        setPlayImage(play)
    }

    private fun setPlayImage(view: View?) {
        view?.setBackgroundResource(R.drawable.ic_play)
    }

    private fun setPauseImage(view: View?) {
        view?.setBackgroundResource(R.drawable.ic_pause)
    }

    override fun onDestroy() {
        super.onDestroy()
        mMediaPlayer?.stop()
        mMediaPlayer?.release()
        mMediaPlayer = null
    }

    private fun setViews(view: View) {
        SongPlay = view.txtfragment_song
        SingerPlay = view.txtfragment_album
        BtnPlay = view.play
    }


}