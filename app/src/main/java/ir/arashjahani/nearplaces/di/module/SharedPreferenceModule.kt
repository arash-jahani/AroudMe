package ir.arashjahani.nearplaces.di.module

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import ir.arashjahani.nearplaces.data.local.shared.PreferencesHelper
import javax.inject.Singleton


/**
 * Created By ArashJahani on 2020/04/18
 */
@Module
class SharedPreferenceModule {

    @Provides
    @Singleton
    fun provideSharedPreference(context: Context): SharedPreferences {
        return context.getSharedPreferences("venue_shared", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun preferencesHelper(sharedPreferences: SharedPreferences): PreferencesHelper {
        return PreferencesHelper(sharedPreferences)
    }

}
