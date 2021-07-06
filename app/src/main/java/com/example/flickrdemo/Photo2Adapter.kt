package com.example.flickrdemo

import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Environment
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.flickrdemo.databinding.DialogPhotoBinding
import com.example.flickrdemo.databinding.PhotoItemBinding
import com.example.flickrdemo.network.Photo
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

class Photo2Adapter(
    diffCallback: DiffUtil.ItemCallback<Photo>
) :
    PagingDataAdapter<Photo, Photo2Adapter.PhotoViewHolder>(diffCallback) {

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val item = getItem(position)
        // Note that item may be null. ViewHolder must support binding a
        // null item as a placeholder.
        if (item != null) {
            holder.bind(item)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder(
            PhotoItemBinding.inflate(LayoutInflater.from(parent.context)),
        )
    }

    class PhotoViewHolder(
        private var binding: PhotoItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(photo: Photo) {

            binding.photo = photo

            binding.imageView.setOnClickListener {
                val dialog = Dialog(it.context)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setCancelable(true)

                val b = DialogPhotoBinding.inflate(LayoutInflater.from(dialog.context))

                b.photo = photo
                b.shareButton.setOnClickListener {
                    try {
                        val drawable = b.imageView2.drawable
                        val intent = Intent(Intent.ACTION_SEND)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        intent.type = "image/png"
                        val uri = Uri.parse("android.resource://com.example.flickrdemo/" + drawable)
                        intent.putExtra(Intent.EXTRA_STREAM, uri)
                        intent.putExtra(Intent.EXTRA_TEXT, "Hello, This is test Sharing")
                        val con = Intent.createChooser(intent, "Share your image")
                        ContextCompat.startActivity(dialog.context, con, null)

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                b.downloadButton.setOnClickListener {
                    val bitmap = (b.imageView2.drawable as BitmapDrawable).bitmap
                    val storageLoc =
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                    val file = File(storageLoc, photo.title + ".jpg")
                    try {
                        val fOut = FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut)

                        val scanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
                        scanIntent.setData(Uri.fromFile(file));
                        dialog.context.sendBroadcast(scanIntent)
                    } catch (e: FileNotFoundException) {
                        e.printStackTrace();
                    } catch (e: IOException) {
                        e.printStackTrace();
                    }
                }
                dialog.setContentView(b.root)
                dialog.show()
            }
            //viewModel.setResponseSent()
            binding.executePendingBindings()
        }

    }


}