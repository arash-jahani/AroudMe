package ir.arashjahani.nearplaces.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import ir.arashjahani.nearplaces.MyApplication
import ir.arashjahani.nearplaces.di.module.ApiModule
import ir.arashjahani.nearplaces.di.module.ApplicationModule
import ir.arashjahani.nearplaces.di.module.DatabaseModule
import javax.inject.Singleton

/**
 * Created By ArashJahani on 2020/04/16
 */

@Singleton
@Component(modules = [ApplicationModule::class, ApiModule::class, DatabaseModule::class])
interface ApplicationComponent {

    fun inject(app:MyApplication)

    @Component.Builder
    interface Builder{

        @BindsInstance
        fun application(application: Application):Builder

        fun build(): ApplicationComponent
    }

}