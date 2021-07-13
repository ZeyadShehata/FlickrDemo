package com.example.flickrdemo

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import coil.load

@BindingAdapter("imageUrl")
fun ImageView.bindImage(imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        this.load(imgUri) {
            placeholder(R.drawable.ic_baseline_loop_24)
            error(R.drawable.ic_not_loaded)
        }
    }
}

@BindingAdapter("progressBar")
fun ProgressBar.bindProgress(oldResponseRec:Boolean, newResponseRec: Boolean){
    if(newResponseRec != oldResponseRec)
        if(newResponseRec){
            this.visibility=  View.GONE
        }
        else
            this.visibility=  View.VISIBLE
}


