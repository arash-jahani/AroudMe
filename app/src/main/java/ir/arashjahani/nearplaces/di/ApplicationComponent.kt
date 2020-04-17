package ir.arashjahani.nearplaces.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import ir.arashjahani.nearplaces.MyApplication
import ir.arashjahani.nearplaces.di.builder.ActivityBuilder
import ir.arashjahani.nearplaces.di.builder.FragmentBuilder
import ir.arashjahani.nearplaces.di.module.ApiModule
import ir.arashjahani.nearplaces.di.module.ApplicationModule
import ir.arashjahani.nearplaces.di.module.DatabaseModule
import ir.arashjahani.nearplaces.di.module.ViewModelModule
import javax.inject.Singleton

/**
 * Created By ArashJahani on 2020/04/16
 */

@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        ApiModule::class,
        DatabaseModule::class,
        AndroidSupportInjectionModule::class,
        ActivityBuilder::class,
        FragmentBuilder::class,
        ViewModelModule::class]
)
interface ApplicationComponent {

    fun inject(app: MyApplication)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

}