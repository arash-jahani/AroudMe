package ir.arashjahani.nearplaces.data

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ir.arashjahani.nearplaces.data.local.db.VenueDao
import ir.arashjahani.nearplaces.data.model.api.ApiGeneralResponse
import ir.arashjahani.nearplaces.data.model.api.VenuesResponse
import ir.arashjahani.nearplaces.data.remote.ApiService
import javax.inject.Inject

/**
 * Created By ArashJahani on 2020/04/17
 */
class DataRepositoryImpl @Inject constructor(
    private val mApiService: ApiService,
    private val mVenueDAO: VenueDao
) : DataRepository {

    private val subscriptions = CompositeDisposable()


    override fun getNearestVenues(location: String, accuracy: Int, limit: Int) {

        subscriptions.add(mApiService.getNearestVenue(location,accuracy,limit)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.newThread())
            .subscribe(
                { venue: ApiGeneralResponse<VenuesResponse> ->

                    venue.response.venues?.let {

                        //save to db
                    }

                }, { error ->

                })
        )

    }

    override fun onClearResources() {
        subscriptions.clear()
    }

}