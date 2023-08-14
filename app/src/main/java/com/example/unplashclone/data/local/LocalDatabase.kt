package com.example.unplashclone.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.unplashclone.data.local.dao.UnsplashImageDao
import com.example.unplashclone.data.local.dao.UnsplashRemoteKeysDao
import com.example.unplashclone.model.UnsplashImage
import com.example.unplashclone.model.UnsplashRemoteKeys

@Database(entities = [UnsplashImage::class, UnsplashRemoteKeys::class], version = 1)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun unsplashImageDao(): UnsplashImageDao
    abstract fun unsplashRemoteKeysDao(): UnsplashRemoteKeysDao

}