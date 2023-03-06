package com.mabrouk.sportingapp.persentaion

import android.app.Application
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.crashreporter.CrashReporterPlugin
import com.facebook.flipper.plugins.databases.DatabasesFlipperPlugin
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.soloader.SoLoader
import com.mabrouk.sportingapp.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

/**
 * @name Mohamed Mabrouk
 * Copyright (c) 2/21/23
 */
@HiltAndroidApp
class MyApp : Application(){
    @Inject
    lateinit var networkFlipperPlugin: NetworkFlipperPlugin
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            SoLoader.init(this, false)
            if (FlipperUtils.shouldEnableFlipper(this)) {
                AndroidFlipperClient.getInstance(this).apply {
                    addPlugin(InspectorFlipperPlugin(this@MyApp, DescriptorMapping.withDefaults()))
                    addPlugin(CrashReporterPlugin.getInstance())
                    addPlugin(DatabasesFlipperPlugin(this@MyApp))
                    addPlugin(networkFlipperPlugin)
                }.start()
            }
        }
    }
}