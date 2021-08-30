package com.example.githubrepository.data.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory

// 이미지의 애니메이션 효과를 넣어줄 수 있는 Factory
private val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()

internal fun ImageView.clear() = Glide.with(context).clear(this)

internal fun ImageView.loadCenterInside(url: String, corner: Float  = 0f){
    Glide.with(this).load(url).transition(DrawableTransitionOptions.withCrossFade(factory))
        .diskCacheStrategy(DiskCacheStrategy.ALL).apply { // 캐시 정책 정함,
            if(corner > 0) transform(CenterInside(), RoundedCorners(corner.fromDpToPx()))
        }.into(this)
}
