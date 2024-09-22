package com.example.chessfrontend

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ChessApplication: Application()  {
    override fun onCreate() {
        super.onCreate()
    }
}