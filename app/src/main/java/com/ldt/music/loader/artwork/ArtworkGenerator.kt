package com.ldt.music.loader.artwork

import android.graphics.Bitmap
import com.ldt.music.model.Media

interface ArtworkGenerator<T> where T : Media{
    fun get(media: T): Bitmap
}