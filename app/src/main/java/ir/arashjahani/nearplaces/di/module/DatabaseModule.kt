package ir.arashjahani.nearplaces.di.module

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ir.arashjahani.nearplaces.data.local.db.AppDatabase
import ir.arashjahani.nearplaces.data.local.db.VenueDao
import javax.inject.Singleton

/**
 * Created By ArashJahani on 2020/04/16
 */

@Module
class DatabaseModule {

    @Provides
    @Singleton
    open fun provideApplicationDatabase(context: Context): AppDatabase {
        var applicationDatabase: AppDatabase =
            Room.databaseBuilder(context, AppDatabase::class.java, "venue-db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
        return applicationDatabase
    }

    @Provides
    @Singleton
    open fun provideVenueDao(applicationDatabase: AppDatabase): VenueDao {
        return applicationDatabase.venueDao()
    }

}