package com.example.flickrdemo

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import coil.load

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        imgView.load(imgUri) {
            placeholder(R.drawable.ic_baseline_loop_24)
            error(R.drawable.ic_not_loaded)
        }
    }
}

@BindingAdapter("progressBar")
fun bindProgress(pBar: ProgressBar,responseRec:Boolean){
    if(responseRec == true){
        pBar.visibility=  View.GONE
    }
    else
        pBar.visibility=  View.VISIBLE
}


