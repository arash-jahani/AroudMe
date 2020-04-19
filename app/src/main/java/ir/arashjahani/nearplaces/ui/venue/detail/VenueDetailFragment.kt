package ir.arashjahani.nearplaces.ui.venue.detail

import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

import ir.arashjahani.nearplaces.R
import ir.arashjahani.nearplaces.data.model.Venue
import ir.arashjahani.nearplaces.ui.base.BaseFragment
import ir.arashjahani.nearplaces.utils.AppConstants.KEY_VENUE
import ir.arashjahani.nearplaces.utils.getUrl
import kotlinx.android.synthetic.main.fragment_venue_detail.*
import kotlinx.android.synthetic.main.item_venue.view.*

/**
 * A simple [Fragment] subclass.
 */
class VenueDetailFragment : BaseFragment(), OnMapReadyCallback {

    private lateinit var venueItem: Venue
    private lateinit var mGoogleMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        arguments?.let {
            venueItem = it.get(KEY_VENUE) as Venue
        }


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_venue_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mapView.onCreate(savedInstanceState)


        if (venueItem == null) {
            //todo back to list fragment
        }

        prepareView()

    }

    fun prepareView() {
        mapView.getMapAsync(this)

        btn_back.setOnClickListener { NavHostFragment.findNavController(this).popBackStack() }

        lbl_title.text = venueItem.name

        lbl_distance.text = "${venueItem.location?.distance} m"

        lbl_address.text = venueItem.location?.address

        venueItem.categories?.let {

            if (it.isEmpty())
                return@let

            lbl_category.text = it[0].name

            Glide.with(this)
                .load(it[0].icon?.getUrl(88))
                .centerInside().into(img_cat)
        }
    }

    override fun onMapReady(p0: GoogleMap?) {

        if (p0 == null)
            return

        val location = LatLng(venueItem.location?.lat!!, venueItem.location?.lng!!)

        mGoogleMap = p0;
        mGoogleMap.uiSettings.isZoomControlsEnabled = true
        mGoogleMap.addMarker(MarkerOptions().position(location));
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 16f));

        mGoogleMap.uiSettings.isMyLocationButtonEnabled = true
        mGoogleMap.isMyLocationEnabled=true

    }

    override fun onResume() {
        mapView.onResume()
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }



}
