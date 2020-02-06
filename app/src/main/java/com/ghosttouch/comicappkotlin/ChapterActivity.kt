package com.ghosttouch.comicappkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ghosttouch.comicappkotlin.Common.Common1
import com.ghosttouch.comicappkotlin.Model.Comic
import kotlinx.android.synthetic.main.activity_chapter.*
import kotlinx.android.synthetic.main.chapter_item.*
import java.lang.StringBuilder

class ChapterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chapter)


        toolbar.title = Common1.selected_comic!!.Name
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp)
        toolbar.setNavigationOnClickListener{
            finish()
        }
        recycler_chapter.setHasFixedSize(true)
        val layoutMAnager = LinearLayoutManager(this@ChapterActivity)
        recycler_chapter.layoutManager = layoutMAnager
        recycler_chapter.addItemDecoration(DividerItemDecoration(this,layoutMAnager.orientation))

        fetchChapter(Common1.selected_comic!!)
    }

    private fun fetchChapter(comic: Comic) {
        Common1.chapterList = comic.Chapters!!
        txt_chapter_name.text = StringBuilder("Chapter (")
            .append(comic.Chapters!!.size)
            .append(")")


    }


}
