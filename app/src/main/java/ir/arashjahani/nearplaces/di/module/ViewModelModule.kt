package ir.arashjahani.nearplaces.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ir.arashjahani.nearplaces.ui.base.BaseViewModel
import ir.arashjahani.nearplaces.ui.venue.list.VenueListViewModel
import ir.arashjahani.nearplaces.viewmodel.ViewModelProviderFactory

/**
 * Created By ArashJahani on 2020/04/17
 */
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(VenueListViewModel::class)
    abstract fun bindVenueListViewModel(venueListViewModel: VenueListViewModel): ViewModel


    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelProviderFactory): ViewModelProvider.Factory
}