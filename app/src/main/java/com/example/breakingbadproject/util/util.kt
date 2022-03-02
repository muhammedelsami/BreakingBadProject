package com.example.breakingbadproject.util

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.example.breakingbadproject.R
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import de.hdodenhof.circleimageview.CircleImageView

fun CircleImageView.displayImage(url : String, placeholder : CircularProgressDrawable) {
   // val option = RequestCreator().placeholder(placeholder).error(R.mipmap.ic_launcher_round)
    Picasso.get().load(url).placeholder(placeholder).error(R.drawable.ic_launcher_foreground).into(this)
}
fun ImageView.displayImage(url : String, placeholder : CircularProgressDrawable) {
    // val option = RequestCreator().placeholder(placeholder).error(R.mipmap.ic_launcher_round)
    Picasso.get().load(url).placeholder(placeholder).error(R.drawable.ic_launcher_foreground).fit().into(this)
}
fun makePlaceholder(context: Context) : CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 25f
        start()
    }

}