package com.ghosttouch.comicappkotlin.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ghosttouch.comicappkotlin.Interface.IRecyclerClick
import com.ghosttouch.comicappkotlin.Model.Chapter
import com.ghosttouch.comicappkotlin.R
import java.lang.StringBuilder


class MyChapterAdapter (internal var context:Context,
                        internal var chapterList:List<Chapter>):RecyclerView.Adapter<MyChapterAdapter.MyViewHolder>(){


    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView), View.OnClickListener {
        internal var txt_chapter_number:TextView
        internal lateinit var iRecyclerClick: IRecyclerClick

        fun setClick(iRecyclerClick: IRecyclerClick){
            this.iRecyclerClick = iRecyclerClick
        }

        init {
            txt_chapter_number = itemView.findViewById(R.id.txt_chapter_number)

            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            iRecyclerClick.onClick(v!!,adapterPosition)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.chapter_item,parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return chapterList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.txt_chapter_number.text = chapterList[position].Name?.let { StringBuilder(it) }

        holder.setClick(object :IRecyclerClick{
            override fun onClick(view: View, position: Int) {

            }

        })
    }


}

 