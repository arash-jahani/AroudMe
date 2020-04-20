package ir.arashjahani.nearplaces.di.module

import android.app.Application
import android.content.Context
import com.google.android.gms.location.LocationRequest
import dagger.Module
import dagger.Provides
import ir.arashjahani.nearplaces.MyApplication
import ir.arashjahani.nearplaces.data.DataRepository
import ir.arashjahani.nearplaces.data.DataRepositoryImpl
import ir.arashjahani.nearplaces.utils.AppConstants.LOCATION_FASTESTINTERVAL
import ir.arashjahani.nearplaces.utils.AppConstants.LOCATION_INTERVAL
import javax.inject.Singleton

/**
 * Created By ArashJahani on 2020/04/16
 */
@Module
class ApplicationModule {

    @Provides
    @Singleton
    fun provideApplication(myApplication: MyApplication): MyApplication {
        return myApplication
    }

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideLocationRequest(): LocationRequest {
        var mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = LOCATION_INTERVAL
        mLocationRequest.fastestInterval = LOCATION_FASTESTINTERVAL
        return mLocationRequest
    }

    @Provides
    @Singleton
    fun provideDataRepository(dataRepositoryImpl: DataRepositoryImpl): DataRepository =
        dataRepositoryImpl

}