package com.megamind.apttutorials

import android.app.Application
import com.megamind.apttutorials.runtime.ActivityBuilder

/**
 * @description
 * @date 17:26 9/4/20
 * @author HanZhengYa
 * @since 1.0
 **/
class MyApp:Application() {
    override fun onCreate() {
        super.onCreate()
        ActivityBuilder.INSTANCE.init(this)
    }
}