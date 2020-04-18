package ir.arashjahani.nearplaces.di.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import ir.arashjahani.nearplaces.MyApplication
import ir.arashjahani.nearplaces.data.DataRepository
import ir.arashjahani.nearplaces.data.DataRepositoryImpl
import javax.inject.Singleton

/**
 * Created By ArashJahani on 2020/04/16
 */
@Module
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

    @Provides
    @Singleton
    fun provideDataRepository(dataRepositoryImpl: DataRepositoryImpl): DataRepository =dataRepositoryImpl

}