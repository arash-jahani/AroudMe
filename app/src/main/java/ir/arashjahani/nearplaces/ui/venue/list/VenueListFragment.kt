package ir.arashjahani.nearplaces.ui.venue.list

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration
import ir.arashjahani.nearplaces.R
import ir.arashjahani.nearplaces.data.model.Venue
import ir.arashjahani.nearplaces.data.model.VenueWithCategoryItem
import ir.arashjahani.nearplaces.ui.base.BaseFragment
import ir.arashjahani.nearplaces.utils.AppConstants
import ir.arashjahani.nearplaces.utils.AppConstants.KEY_VENUE
import ir.arashjahani.nearplaces.utils.AppConstants.PERMISSION_ID
import ir.arashjahani.nearplaces.utils.checkLocationPermission
import ir.arashjahani.nearplaces.utils.isLocationEnabled
import ir.arashjahani.nearplaces.utils.toAddress
import kotlinx.android.synthetic.main.fragment_venue_list.*
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 */
class VenueListFragment : BaseFragment(), VenuesAdapter.VenueAdapterItemClickListener,VenueListNavigator {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val mVenuesListViewModel: VenueListViewModel by viewModels {
        viewModelFactory
    }

    private val mVenuesAdapter = VenuesAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_venue_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mVenuesListViewModel.navigator = this //also need implement MoviesListNavigator

        initObserves()

        prepareView()

        val savedLocation = mVenuesListViewModel.getLastSavedLocation()

        if (!savedLocation.isEmpty()) {

            mVenuesListViewModel.updateLocationLiveData.value = savedLocation

            updateAddress(savedLocation)

        }
        getLocation()
    }

    fun initObserves() {

        mVenuesListViewModel.networkErrors.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context,it,Toast.LENGTH_SHORT).show()
        })

        mVenuesListViewModel.trackLocation.observe(viewLifecycleOwner, Observer {
            Log.v("Location Finder", it)
            switchToContentView()
            if (mVenuesListViewModel.getLastSavedLocation().isEmpty()) {
                //save location
                mVenuesListViewModel.saveLocation(it)
                mVenuesListViewModel.updateLocationLiveData.value = it

                updateAddress(it)

            } else if (mVenuesListViewModel.isLocationChanged(it)) {
                mVenuesListViewModel.saveLocation(it)
                lbl_new_place.visibility = View.VISIBLE

                updateAddress(it)
            }

        })

        mVenuesListViewModel.venueWithCategoryListLiveData.observe(
            viewLifecycleOwner,
            Observer<PagedList<VenueWithCategoryItem>> {
                Log.d("Activity", "Venues list: ${it?.size}")
                switchToContentView()
                mVenuesAdapter.submitList(it)
            })

        mVenuesListViewModel.networkErrors.observe(viewLifecycleOwner, Observer<String> {
            Toast.makeText(context, "$it", Toast.LENGTH_LONG).show()
        })
    }

    fun updateAddress(location: String) {
        lbl_address.text = context?.let { it1 -> location.toAddress(it1) }
    }

    fun prepareView() {


        fab_detect_location.setOnClickListener { getLocation() }

        lbl_new_place.setOnClickListener {
            mVenuesListViewModel.clearPreviousVenuesThenSaveSomeNew()
            lbl_new_place.visibility = View.INVISIBLE
        }

        // add dividers between RecyclerView's row items
        val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        rv_venues.addItemDecoration(decoration)

        mVenuesAdapter.setOnItemClickListener(this)
        rv_venues.adapter = mVenuesAdapter

    }

    override fun onPause() {
        super.onPause()
        Log.v("Location Finder:", "onPause")
        mVenuesListViewModel.stopLocationTracker()
    }

    override fun onResume() {
        super.onResume()
        Log.v("Location Finder:", "restart")
        getLocation()
    }

    override fun onVenueItemClick(venueItem: Venue) {
        val bundle = bundleOf(KEY_VENUE to venueItem)

        NavHostFragment.findNavController(this).navigate(
            R.id.venueDetailFragment,
            bundle
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLocation()
            }
        }
    }

    fun getLocation() {

        if (context?.isLocationEnabled()!! && context?.checkLocationPermission()!!) {
            mVenuesListViewModel.trackLocation()
        } else {

            requestPermissions(
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
                AppConstants.PERMISSION_ID
            )

        }
    }

    override fun switchToLoadingView() {
        progress_loading.visibility=View.VISIBLE
    }

    override fun switchToErrorView() {
    }

    override fun switchToEmptyView() {
    }

    override fun switchToContentView() {
        progress_loading.visibility=View.INVISIBLE

    }

    override fun showToast(message: Int, tr: Throwable?) {
    }
}
