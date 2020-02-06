package com.ghosttouch.comicappkotlin.Interface

import com.ghosttouch.comicappkotlin.Model.Comic

interface IComicLoadDoneListener {
    fun onComicLoadDoneListener(comicList: List<Comic>)
}