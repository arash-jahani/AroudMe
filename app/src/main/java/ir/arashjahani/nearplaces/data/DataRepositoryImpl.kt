package ir.arashjahani.nearplaces.data

import androidx.paging.LivePagedListBuilder
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ir.arashjahani.nearplaces.data.local.db.VenueDao
import ir.arashjahani.nearplaces.data.model.VenueListResult
import ir.arashjahani.nearplaces.data.model.api.ApiGeneralResponse
import ir.arashjahani.nearplaces.data.model.api.VenuesResponse
import ir.arashjahani.nearplaces.data.remote.ApiService
import ir.arashjahani.nearplaces.data.utils.VenueBoundaryCondition
import ir.arashjahani.nearplaces.utils.AppConstants.ACCURACY
import ir.arashjahani.nearplaces.utils.AppConstants.PAGE_SIZE
import javax.inject.Inject

/**
 * Created By ArashJahani on 2020/04/17
 */
class DataRepositoryImpl @Inject constructor(
    private val mApiService: ApiService,
    private val mVenueDAO: VenueDao
) : DataRepository {

    private val subscriptions = CompositeDisposable()

    val boundaryCallback by lazy {
        VenueBoundaryCondition(mApiService, mVenueDAO)
    }

    override fun getNearestVenues(location: String):VenueListResult {

        val networkErrors =boundaryCallback.networkErrors

        // Get the paged list
        val data = LivePagedListBuilder(mVenueDAO._loadVenues(), PAGE_SIZE)
            .setBoundaryCallback(boundaryCallback)
            .build()

        // Get the network errors exposed by the boundary callback
        return VenueListResult(data, networkErrors)

    }

    override fun onClearResources() {
        subscriptions.clear()
    }

}