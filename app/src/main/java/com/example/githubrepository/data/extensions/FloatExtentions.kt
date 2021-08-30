package com.example.githubrepository.data.extensions

import android.content.res.Resources

internal fun Float.fromDpToPx(): Int{
    return (this * Resources.getSystem().displayMetrics.density).toInt()
}