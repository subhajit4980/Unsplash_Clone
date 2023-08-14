package com.example.unplashclone.download

interface Download {
    fun downloadFile(url:String,filename:String,path:String):Long
}