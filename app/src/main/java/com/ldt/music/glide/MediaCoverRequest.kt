package com.ldt.music.glide

import android.content.Context
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ldt.music.R
import com.bumptech.glide.RequestManager
import com.ldt.music.model.Song
import android.graphics.Bitmap
import android.os.Build
import com.ldt.music.glide.audiocover.AudioFileCover
import com.ldt.music.util.MusicUtil
import com.bumptech.glide.signature.MediaStoreSignature
import com.ldt.music.util.PreferenceUtil
import com.bumptech.glide.GenericTransitionOptions
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.Key
import com.ldt.music.model.Media

object MediaCoverRequest {
    val defaultDiskCacheStrategy: DiskCacheStrategy = DiskCacheStrategy.NONE
    const val defaultErrorImage = R.drawable.ic_music_style
    const val defaultAnimation = android.R.anim.fade_in
}