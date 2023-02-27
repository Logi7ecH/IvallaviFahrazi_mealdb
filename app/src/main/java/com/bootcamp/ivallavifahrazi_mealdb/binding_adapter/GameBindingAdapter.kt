package com.bootcamp.ivallavifahrazi_mealdb.binding_adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bootcamp.ivallavifahrazi_mealdb.R
import com.bumptech.glide.Glide

object GameBindingAdapter {
    @BindingAdapter("loadImageFromUrl")
    @JvmStatic
    fun loadImageFromUrl(imageView: ImageView, imageUrl: String){
        val placeholder = R.drawable.ic_launcher_background
        Glide.with(imageView.context).load(imageUrl).placeholder(placeholder).error(placeholder).into(imageView)
    }
}