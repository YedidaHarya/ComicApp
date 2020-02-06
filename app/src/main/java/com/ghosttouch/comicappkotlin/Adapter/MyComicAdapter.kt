package com.ghosttouch.comicappkotlin.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.ghosttouch.comicappkotlin.ChapterActivity
import com.ghosttouch.comicappkotlin.Common.Common1
import com.ghosttouch.comicappkotlin.Interface.IRecyleClick
import com.ghosttouch.comicappkotlin.Model.Comic
import com.ghosttouch.comicappkotlin.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.comic_item.view.*

class MyComicAdapter(internal var context : Context,
                     internal var comicList:List<Comic>) :RecyclerView.Adapter<MyComicAdapter.MyViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyComicAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.comic_item,parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
       return comicList.size
    }

    override fun onBindViewHolder(holder: MyComicAdapter.MyViewHolder, position: Int) {
        Picasso.get().load(comicList[position].Image).into(holder.imageView)
        holder.textView.text = comicList[position].Name
         holder.setClick(object :IRecyleClick{
             override fun onClick(view: View, position: Int) {
                 context.startActivity(Intent(context,ChapterActivity::class.java))
                 Common1.selected_comic=comicList[position]
             }

         })
    }

    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var imageView:ImageView
        var textView:TextView
        lateinit var iRecycleClick: IRecyleClick

        fun setClick (iRecyleClick: IRecyleClick){
            this.iRecycleClick = iRecycleClick
        }

        init {
            imageView = itemView.findViewById(R.id.comic_image) as ImageView
            textView = itemView.findViewById(R.id.comic_name) as TextView

            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            iRecycleClick.onClick(v!!, adapterPosition)

        }
    }
}

