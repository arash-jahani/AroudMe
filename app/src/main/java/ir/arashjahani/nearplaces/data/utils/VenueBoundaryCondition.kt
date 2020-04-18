package ir.arashjahani.nearplaces.data.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ir.arashjahani.nearplaces.data.local.db.VenueDao
import ir.arashjahani.nearplaces.data.model.Venue
import ir.arashjahani.nearplaces.data.model.api.ApiGeneralResponse
import ir.arashjahani.nearplaces.data.model.api.VenuesResponse
import ir.arashjahani.nearplaces.data.remote.ApiService
import ir.arashjahani.nearplaces.utils.AppConstants

/**
 * Created By ArashJahani on 2020/04/18
 */
class VenueBoundaryCondition(private val mApiService: ApiService, private val mVenueDAO: VenueDao) : PagedList.BoundaryCallback<Venue>() {

    private val subscriptions = CompositeDisposable()


    private var lastRequestPageNumber = 1

    private val _networkErrors = MutableLiveData<String>()

    // LiveData of network errors.
    val networkErrors: LiveData<String>
        get() = _networkErrors

    // avoid triggering multiple requests in the same time
    private var isRequestInProgress = false

    override fun onZeroItemsLoaded() {
        requestAndSaveData()
    }

    override fun onItemAtEndLoaded(itemAtEnd: Venue) {
        requestAndSaveData()
    }

    private fun requestAndSaveData() {
        if (isRequestInProgress) return

        isRequestInProgress = true

        subscriptions.add(mApiService.getNearestVenue("35.758990, 51.410122",
            AppConstants.ACCURACY,lastRequestPageNumber)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.newThread())
            .subscribe(
                { venues: ApiGeneralResponse<VenuesResponse> ->

                    venues.response.venues?.let {

                        mVenueDAO.insertAll(it).also {
                            lastRequestPageNumber++
                            isRequestInProgress = false
                        }

                    }

                }, { error ->
                    isRequestInProgress = false
                })
        )
    }

    fun onClear(){
        subscriptions.clear()
    }
}