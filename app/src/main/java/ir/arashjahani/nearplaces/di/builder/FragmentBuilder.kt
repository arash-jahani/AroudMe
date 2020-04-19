package ir.arashjahani.nearplaces.di.builder

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ir.arashjahani.nearplaces.ui.venue.detail.VenueDetailFragment
import ir.arashjahani.nearplaces.ui.venue.list.VenueListFragment

/**
 * Created By ArashJahani on 2020/04/17
 */
@Module
abstract class FragmentBuilder {

    @ContributesAndroidInjector
    abstract fun bindVenueListFragment(): VenueListFragment

    @ContributesAndroidInjector
    abstract fun bindVenueDetailFragment(): VenueDetailFragment
}