package ir.arashjahani.nearplaces.ui.venue.list

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration

import ir.arashjahani.nearplaces.R
import ir.arashjahani.nearplaces.data.model.Venue
import ir.arashjahani.nearplaces.data.model.VenueWithCategoryItem
import ir.arashjahani.nearplaces.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_venue_list.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class VenueListFragment : BaseFragment(),VenuesAdapter.VenueAdapterItemClickListener{

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

        //mMoviesListViewModel.navigator = this //also need implement MoviesListNavigator

        initObserves()

        prepareView()
    }

    fun initObserves() {
        mVenuesListViewModel.venueWithCategoryListLiveData.observe(
            viewLifecycleOwner,
            Observer<PagedList<VenueWithCategoryItem>> {
                Log.d("Activity", "Venues list: ${it?.size}")
                mVenuesAdapter.submitList(it)
            })

        mVenuesListViewModel.networkErrors.observe(viewLifecycleOwner, Observer<String> {
            Toast.makeText(context, "$it", Toast.LENGTH_LONG).show()
        })
    }

    fun prepareView() {

        // add dividers between RecyclerView's row items
        val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        rv_venues.addItemDecoration(decoration)

        mVenuesAdapter.setOnItemClickListener(this)
        rv_venues.adapter = mVenuesAdapter

        mVenuesListViewModel.getVenues("35.758990, 51.410122")
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.show()
    }

    override fun onVenueItemClick(venueItem: Venue) {

    }

}
