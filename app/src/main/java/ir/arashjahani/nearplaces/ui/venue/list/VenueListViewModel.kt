package ir.arashjahani.nearplaces.ui.venue.list

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.PagedList
import androidx.room.Transaction
import ir.arashjahani.nearplaces.data.DataRepository
import ir.arashjahani.nearplaces.data.model.VenueWithCategoryItem
import ir.arashjahani.nearplaces.data.model.VenueListResult
import ir.arashjahani.nearplaces.ui.base.BaseViewModel
import ir.arashjahani.nearplaces.utils.AppConstants.DISATANCE
import ir.arashjahani.nearplaces.utils.toLocation
import javax.inject.Inject

/**
 * Created By ArashJahani on 2020/04/17
 */
class VenueListViewModel @Inject constructor(
    val dataRepository: DataRepository,
    val context: Context
) : BaseViewModel<VenueListNavigator>() {

    val trackLocation: MutableLiveData<String> = dataRepository.getLiveLocation()

    val updateLocationLiveData = MutableLiveData<String>()
    val venueResultLiveData: LiveData<VenueListResult> = Transformations.map(updateLocationLiveData)
    {
        navigator?.switchToLoadingView()
        dataRepository.getNearestVenues(it)
    }

    val venueWithCategoryListLiveData: LiveData<PagedList<VenueWithCategoryItem>> =
        Transformations.switchMap(venueResultLiveData) { it -> it.data }

    val networkErrors: LiveData<String> =
        Transformations.switchMap(venueResultLiveData) { it -> it.networkErrors }

    @Transaction
    fun clearPreviousVenuesThenSaveSomeNew() {

        navigator?.switchToLoadingView()

        dataRepository.clearAllVenues()

        this.updateLocationLiveData.value = getLastSavedLocation()

    }

    fun trackLocation() {
        navigator?.switchToLoadingView()
        dataRepository.trackLocation()
    }

    fun getLastSavedLocation(): String {
        return dataRepository.getLastSavedLocation()
    }

    fun saveLocation(location: String) {
        dataRepository.savedLocation(location)
    }

    fun isLocationChanged(newLocation: String): Boolean {
        var lastLocation = dataRepository.getLastSavedLocation()
        if (newLocation.isEmpty() || lastLocation.isEmpty()) {
            return false
        }
        if (newLocation.toLocation()?.distanceTo(lastLocation.toLocation())!! > DISATANCE) {
            return true
        }
        return false
    }

    fun stopLocationTracker() {
        dataRepository.stopLocationTracked()
    }

    override fun onCleared() {
        super.onCleared()
        dataRepository.onClearResources()
    }


    companion object {
        const val LOCATION_WORK_TAG = "LOCATION_WORK_TAG"
    }
}