package ir.arashjahani.nearplaces.ui.venue.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import ir.arashjahani.nearplaces.data.DataRepository
import ir.arashjahani.nearplaces.data.model.Venue
import ir.arashjahani.nearplaces.data.model.VenueListResult
import ir.arashjahani.nearplaces.data.remote.ApiService
import ir.arashjahani.nearplaces.ui.base.BaseViewModel
import javax.inject.Inject

/**
 * Created By ArashJahani on 2020/04/17
 */
class VenueListViewModel @Inject constructor(val dataRepository: DataRepository) :
    ViewModel() {

    val locationLiveData = MutableLiveData<String>()

    val venueResultLiveData: LiveData<VenueListResult> = Transformations.map(locationLiveData)
    {
        dataRepository.getNearestVenues(it)
    }

    val venueListLiveData: LiveData<PagedList<Venue>> = Transformations.switchMap(venueResultLiveData) { it -> it.data }

    val networkErrors: LiveData<String> =  Transformations.switchMap(venueResultLiveData) { it -> it.networkErrors }


    fun getVenues(location: String) {
        locationLiveData.value = location
    }

    override fun onCleared() {
        super.onCleared()
        dataRepository.onClearResources()
    }

}