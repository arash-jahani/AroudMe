package ir.arashjahani.nearplaces.data.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ir.arashjahani.nearplaces.data.local.db.VenueDao
import ir.arashjahani.nearplaces.data.model.VenueWithCategoryItem
import ir.arashjahani.nearplaces.data.model.api.ApiGeneralResponse
import ir.arashjahani.nearplaces.data.model.api.VenuesResponse
import ir.arashjahani.nearplaces.data.remote.ApiService
import ir.arashjahani.nearplaces.utils.AppConstants
import ir.arashjahani.nearplaces.utils.AppConstants.PAGE_SIZE

/**
 * Created By ArashJahani on 2020/04/18
 */
class VenueBoundaryCondition(private val mApiService: ApiService, private val mVenueDAO: VenueDao) :
    PagedList.BoundaryCallback<VenueWithCategoryItem>() {

    private val subscriptions = CompositeDisposable()

    internal var location: String = ""
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

    override fun onItemAtEndLoaded(withCategoryItemAtEnd: VenueWithCategoryItem) {
        requestAndSaveData()
    }

    private fun requestAndSaveData() {
        if (isRequestInProgress) return

        isRequestInProgress = true

        subscriptions.add(mApiService.getNearestVenue(
            location,
            AppConstants.RADIUS, PAGE_SIZE, lastRequestPageNumber
        )
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.newThread())
            .subscribe(
                { venues: ApiGeneralResponse<VenuesResponse> ->

                    venues.response.groups[0].items?.let {

                        mVenueDAO.insertAll(it)

                        if (it.size == 40) {
                            isRequestInProgress = false
                            lastRequestPageNumber++;
                        }
                    }

                }, { error ->
                    isRequestInProgress = false
                })
        )
    }

    fun onClear() {
        subscriptions.clear()
    }

}