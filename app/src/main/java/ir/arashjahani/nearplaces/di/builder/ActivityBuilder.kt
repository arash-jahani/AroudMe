package ir.arashjahani.nearplaces.di.builder

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ir.arashjahani.nearplaces.ui.MainActivity

/**
 * Created By ArashJahani on 2020/04/17
 */
@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector
abstract fun bindMainActivity():MainActivity
}