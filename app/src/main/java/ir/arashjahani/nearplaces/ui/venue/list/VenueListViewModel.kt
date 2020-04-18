package ir.arashjahani.nearplaces.ui.venue.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import ir.arashjahani.nearplaces.data.DataRepository
import ir.arashjahani.nearplaces.data.model.VenueWithCategoryItem
import ir.arashjahani.nearplaces.data.model.VenueListResult
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

    val venueWithCategoryListLiveData: LiveData<PagedList<VenueWithCategoryItem>> = Transformations.switchMap(venueResultLiveData) { it -> it.data }

    val networkErrors: LiveData<String> =  Transformations.switchMap(venueResultLiveData) { it -> it.networkErrors }


    fun getVenues(location: String) {
        locationLiveData.value = location
    }

    override fun onCleared() {
        super.onCleared()
        dataRepository.onClearResources()
    }

}