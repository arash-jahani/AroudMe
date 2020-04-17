package ir.arashjahani.nearplaces

import android.app.Activity
import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import ir.arashjahani.nearplaces.di.DaggerApplicationComponent
import ir.arashjahani.nearplaces.utils.ActivitiesLifecycleListener
import javax.inject.Inject

/**
 * Created By ArashJahani on 2020/04/14
 */
class MyApplication:Application(), HasActivityInjector {

    @Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>


    override fun onCreate() {
        super.onCreate()

        registerActivityLifecycleCallbacks(ActivitiesLifecycleListener())

        DaggerApplicationComponent.builder()
            .application(this)
            .build()
            .inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return activityDispatchingAndroidInjector
    }

}