package ir.arashjahani.nearplaces.ui.venue.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ir.arashjahani.nearplaces.R
import ir.arashjahani.nearplaces.data.model.Venue
import ir.arashjahani.nearplaces.data.model.VenueWithCategoryItem
import ir.arashjahani.nearplaces.utils.getUrl
import kotlinx.android.synthetic.main.item_venue.view.*

/**
 * Created By ArashJahani on 2020/04/17
 */

class VenuesAdapter() :
    PagedListAdapter<VenueWithCategoryItem, RecyclerView.ViewHolder>(VENUE_COMPARATOR) {

    private lateinit var itemClickListener: VenueAdapterItemClickListener


    interface VenueAdapterItemClickListener {

        fun onVenueItemClick(venue: Venue)

    }

    fun setOnItemClickListener(clickListener: VenueAdapterItemClickListener) {

        this.itemClickListener = clickListener

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_venue, parent, false)
        val venueItemHolder = VenueViewHolder(view)
        venueItemHolder.setUpClickableView(this.itemClickListener)
        return venueItemHolder

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val venue = getItem(position)
        if (venue != null) {
            (holder as VenueViewHolder).bindData(venue)
        }

    }

    class VenueViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var venue: Venue? = null

        fun bindData(venueWithCategoryItem: VenueWithCategoryItem) {

            venue = venueWithCategoryItem.venue
            venue!!.categories = venueWithCategoryItem.categories

            itemView.lbl_title.text = venue!!.name

            itemView.lbl_distance.text = "${venue!!.location?.distance} m"

            venue!!.categories?.let {

                if (it.isEmpty())
                    return@let

                itemView.lbl_category.text = it[0].name

                Glide.with(itemView.context)
                    .load(it[0].icon?.getUrl(88))
                    .centerInside().into(itemView.img_cat)
            }


        }

        fun setUpClickableView(clickListener: VenueAdapterItemClickListener) {

            itemView.setOnClickListener { view ->
                venue?.let { clickListener.onVenueItemClick(it) }
            }

        }
    }


    companion object {
        private val VENUE_COMPARATOR = object : DiffUtil.ItemCallback<VenueWithCategoryItem>() {
            override fun areItemsTheSame(
                oldWithCategoryItem: VenueWithCategoryItem,
                newWithCategoryItem: VenueWithCategoryItem
            ): Boolean =
                oldWithCategoryItem.venue.venueId == newWithCategoryItem.venue.venueId

            override fun areContentsTheSame(
                oldWithCategoryItem: VenueWithCategoryItem,
                newWithCategoryItem: VenueWithCategoryItem
            ): Boolean =
                oldWithCategoryItem.venue == newWithCategoryItem.venue
        }
    }


}