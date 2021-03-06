package com.ldt.music.ui.maintab.library.viewholder

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.ldt.music.common.MediaManager
import com.ldt.music.contract.AbsMediaAdapter
import com.ldt.music.contract.AbsSongAdapter
import com.ldt.music.helper.menu.SongMenuHelper
import com.ldt.music.helper.songpreview.SongPreviewController
import com.ldt.music.interactors.postDelayedOnUiThread
import com.ldt.music.model.Media
import com.ldt.music.model.Song
import com.ldt.music.service.MusicPlayerRemote
import com.ldt.music.ui.bottomsheet.OptionBottomSheet
import com.ldt.music.utils.KeyboardUtils
import com.ldt.music.utils.Utils

object ItemViewUtils {
    private val mOptionRes = SongMenuHelper.SONG_OPTION

    fun selectOptionByMediaType(media: Media?): IntArray? {
        return when(media) {
            is Song -> {
                if(SongPreviewController.getInstance().isPlayingPreview) SongMenuHelper.SONG_OPTION_STOP_PREVIEW else SongMenuHelper.SONG_OPTION
            }
            else -> null
        }
    }
    fun handleMenuClickOnNormalSongItem(viewHolder: RecyclerView.ViewHolder, song: Song?, position: Int) {
        song ?: return
        val options = selectOptionByMediaType(song) ?: run {
            Utils.showGeneralErrorToast()
            return
        }
        KeyboardUtils.hideSoftInput(viewHolder.itemView)
        OptionBottomSheet
            .newInstance(options, song)
            .show((viewHolder.itemView.context as AppCompatActivity).supportFragmentManager, "song_popup_menu")
    }

    fun processPlayAll(songs: List<Song>, startPosition: Int, playNow: Boolean, viewHolder: RecyclerView.ViewHolder) {
        postDelayedOnUiThread(100) {
            MusicPlayerRemote.openQueue(songs, startPosition, playNow)
            notifyPlayingStateForLegacyAdapter(startPosition, viewHolder)
        }

    }

    private fun notifyPlayingStateForLegacyAdapter(startPosition: Int, viewHolder: RecyclerView.ViewHolder) {
        postDelayedOnUiThread(50) {
            // Behavior c???a Adapter c??: c???p nh???t l???i playing state cho item c?? v?? m???i
            (viewHolder.bindingAdapter as? AbsSongAdapter)?.apply {
                notifyItemChanged(getMediaHolderPosition(mMediaPlayDataItem), AbsMediaAdapter.PLAY_STATE_CHANGED)
                notifyItemChanged(getMediaHolderPosition(startPosition), AbsMediaAdapter.PLAY_STATE_CHANGED)
                mMediaPlayDataItem = startPosition
            }
        }
    }

    /**
     * Stop current and preview this song
     */
    fun previewSongsAndStopCurrent(song: Song) {
        SongPreviewController.getInstance().previewSongsAndStopCurrent(song)
    }

    /**
     * Preview this song or stop it if this one is the current previewing
     */
    fun previewSongsOrStopCurrent(song: Song) {
        SongPreviewController.getInstance().apply {
            if(isPlayingPreview && isPreviewingSong(song)) {
                cancelPreview()
            } else {
                previewSongs(song)
            }
        }
    }

    /**
     * Play all songs in given playlist
     */
    fun playPlaylist(playlistId: Int, firstSongId: Int? = null, shuffle: Boolean = true): Boolean {
        val playlist = MediaManager.getPlaylist(playlistId) ?: return false
        val songs = mutableListOf<Song>()
        var firstSongIndex = -1

        playlist.songs.forEachIndexed { index, id ->
            MediaManager.getSong(id)?.apply {
                if(id == firstSongId) {
                    firstSongIndex = index
                }
                songs.add(this)
            }
        }

        // The playlist doesn't contain song
        if(firstSongId != null && firstSongIndex == -1) return false
        MusicPlayerRemote.openQueue(songs, firstSongIndex, true)
        return false
    }
}