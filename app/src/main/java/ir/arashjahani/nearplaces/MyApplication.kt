package ir.arashjahani.nearplaces

import android.app.Application
import ir.arashjahani.nearplaces.di.DaggerApplicationComponent

/**
 * Created By ArashJahani on 2020/04/14
 */
class MyApplication:Application() {

    override fun onCreate() {
        super.onCreate()

        DaggerApplicationComponent.builder()
            .application(this)
            .build()
            .inject(this)
    }

}