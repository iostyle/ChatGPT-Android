package com.iostyle.gpt.application

import android.app.Application
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initLogger()
    }

    private fun initLogger() {
        Logger.addLogAdapter(AndroidLogAdapter())
    }
}