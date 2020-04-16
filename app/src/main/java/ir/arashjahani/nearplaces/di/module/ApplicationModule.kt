package ir.arashjahani.nearplaces.di.module

import android.app.Application
import android.content.Context
import dagger.Provides
import ir.arashjahani.nearplaces.MyApplication
import javax.inject.Singleton

/**
 * Created By ArashJahani on 2020/04/16
 */
class ApplicationModule {

    @Provides
    @Singleton
    fun provideApplication(myApplication: MyApplication):MyApplication{
        return myApplication
    }

    @Provides
    @Singleton
    fun provideContext(application: Application):Context{
        return application
    }
}