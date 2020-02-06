package com.ghosttouch.comicappkotlin.Common

import com.ghosttouch.comicappkotlin.Model.Chapter
import com.ghosttouch.comicappkotlin.Model.Comic

object Common1 {

    var comicList:List<Comic> = ArrayList<Comic>()
    var selected_comic: Comic?= null
    lateinit var chapterList: List<Chapter>
}