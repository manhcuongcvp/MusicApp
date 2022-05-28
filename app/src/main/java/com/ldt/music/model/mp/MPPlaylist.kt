package com.ldt.music.model.mp

import com.ldt.music.model.Playlist

class MPPlaylist(id: Int, name: String): Playlist(id, name) {
    val songs = mutableListOf<Int>()
}