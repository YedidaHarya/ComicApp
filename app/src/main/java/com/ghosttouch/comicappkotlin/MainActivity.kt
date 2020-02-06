package com.ghosttouch.comicappkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
//import androidx.appcompat.app.AlertDialog
import com.ghosttouch.comicappkotlin.Adapter.MySliderAdapter
import com.ghosttouch.comicappkotlin.Interface.IBannerLoadDoneListener
import com.ghosttouch.comicappkotlin.Interface.IComicLoadDoneListener
import com.ghosttouch.comicappkotlin.Model.Comic
import com.ghosttouch.comicappkotlin.Service.PicassoImageLoadingService
import com.google.firebase.database.*
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_main.*
import ss.com.bannerslider.Slider
import android.app.AlertDialog
import android.widget.GridView
import androidx.recyclerview.widget.GridLayoutManager
import com.ghosttouch.comicappkotlin.Adapter.MyComicAdapter
import com.ghosttouch.comicappkotlin.Common.Common1

class MainActivity : AppCompatActivity(), IBannerLoadDoneListener, IComicLoadDoneListener {

    //database
    lateinit var banners_ref : DatabaseReference
    lateinit var comic_ref : DatabaseReference

    //Listener
    lateinit var iBannerLoadDoneListener: IBannerLoadDoneListener
    lateinit var iComicLoadDoneListener: IComicLoadDoneListener
    lateinit var alertDialog: AlertDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //init listener
        iBannerLoadDoneListener = this
        iComicLoadDoneListener = this

        //init dialog
        alertDialog = SpotsDialog.Builder().setContext(this@MainActivity)
            .setCancelable(false)
            .setMessage("Please Wait....")
            .build()


        //init db
        banners_ref = FirebaseDatabase.getInstance().getReference("Banners")
        comic_ref = FirebaseDatabase.getInstance().getReference("Comic")

        //first load banner and comic
        swipe_to_refresh.setColorSchemeResources(R.color.colorPrimary,R.color.colorPrimaryDark)
        swipe_to_refresh.setOnRefreshListener {
            loadBanners()
            loadComic()
        }
        Slider.init(PicassoImageLoadingService())

        recycler_comic.setHasFixedSize(true)
        recycler_comic.layoutManager = GridLayoutManager(this@MainActivity,2)
    }

    private fun loadComic() {

        alertDialog.show()

        comic_ref.addListenerForSingleValueEvent(object :ValueEventListener{
            var comic_load : MutableList<Comic> = ArrayList<Comic>()
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(this@MainActivity,""+p0.message,Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(p0: DataSnapshot) {
                for(comicSnapShot in p0.children){
                    val comic = comicSnapShot.getValue(Comic::class.java)
                    comic_load.add(comic!!)
                }
                iComicLoadDoneListener.onComicLoadDoneListener(comic_load)
            }

        })
    }

    private fun loadBanners() {
        banners_ref.addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(this@MainActivity,""+p0.message,Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(p0: DataSnapshot) {
                val banner_list = ArrayList<String>()
                for(banner in p0.children){
                    val image = banner.getValue(String::class.java)
                    banner_list.add(image!!)
                }

                iBannerLoadDoneListener.onBannerLoadDoneListener(banner_list)
            }

        })
    }

    override fun onBannerLoadDoneListener(banners: List<String>) {
        slider.setAdapter(MySliderAdapter(banners))
    }

    override fun onComicLoadDoneListener(comicList: List<Comic>) {
        alertDialog.dismiss()

        Common1.comicList = comicList;
        recycler_comic.adapter =MyComicAdapter(baseContext,comicList)
        txt_comic.text = StringBuilder("NEW COMIC (")
            .append(comicList.size)
            .append(")")

        if(swipe_to_refresh.isRefreshing)
            swipe_to_refresh.isRefreshing=false

    }
}


