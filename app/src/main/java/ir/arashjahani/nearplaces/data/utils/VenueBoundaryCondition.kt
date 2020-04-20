package ir.arashjahani.nearplaces.data.utils

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ir.arashjahani.nearplaces.data.local.db.VenueDao
import ir.arashjahani.nearplaces.data.local.shared.PreferencesHelper
import ir.arashjahani.nearplaces.data.model.VenueWithCategoryItem
import ir.arashjahani.nearplaces.data.model.api.ApiGeneralResponse
import ir.arashjahani.nearplaces.data.model.api.VenuesResponse
import ir.arashjahani.nearplaces.data.remote.ApiService
import ir.arashjahani.nearplaces.utils.AppConstants
import ir.arashjahani.nearplaces.utils.AppConstants.KEY_LAST_OFFSET
import ir.arashjahani.nearplaces.utils.AppConstants.PAGE_SIZE

/**
 * Created By ArashJahani on 2020/04/18
 */
class VenueBoundaryCondition(
    private val mApiService: ApiService,
    private val mVenueDAO: VenueDao,
    private val preferencesHelper: PreferencesHelper
) :
    PagedList.BoundaryCallback<VenueWithCategoryItem>() {

    private val subscriptions = CompositeDisposable()

    private var location: String = ""
    private var lastRequestOffset = 1

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
            AppConstants.RADIUS, PAGE_SIZE, lastRequestOffset
        )
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.newThread())
            .subscribe(
                { venues: ApiGeneralResponse<VenuesResponse> ->

                    venues.response.groups[0].items?.let {

                        mVenueDAO.insertAll(it)

                        isRequestInProgress = false
                        lastRequestOffset += PAGE_SIZE;
                        preferencesHelper.putInt(KEY_LAST_OFFSET, lastRequestOffset)

                    }

                }, { error ->
                    _networkErrors.value = error.message ?: "Connection Error"
                    isRequestInProgress = false
                })
        )
    }

    fun freshRequest(loc: String) {
        location = loc
        isRequestInProgress = false
        lastRequestOffset = preferencesHelper.getInt(KEY_LAST_OFFSET)
    }

    fun onClear() {
        subscriptions.clear()
    }

}