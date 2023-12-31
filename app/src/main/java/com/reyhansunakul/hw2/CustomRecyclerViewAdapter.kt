package com.reyhansunakul.hw2
import android.content.Context
import android.provider.ContactsContract.CommonDataKinds.Event.TYPE_BIRTHDAY
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.reyhansunakul.hw2.Type.TYPE_MOVIE
import com.reyhansunakul.hw2.Type.TYPE_THEATRE



class CustomRecyclerViewAdapter(
    private val context: Context,
    private val recyclerItemValues: ArrayList<Organization> = ArrayList()
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), Type {

    interface OrganizationRecyclerAdapterInterface {
        fun displayItem(org: Organization)
    }

    private var orgAdapterInterface: OrganizationRecyclerAdapterInterface =
        context as OrganizationRecyclerAdapterInterface

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView: View
        val inflator = LayoutInflater.from(parent.context)

        if (viewType == TYPE_MOVIE) {
            itemView = inflator.inflate(R.layout.recycler_movie, parent, false)
            return BirthdayItemHolder(itemView)
        } else {
            itemView = inflator.inflate(R.layout.recycler_theatre, parent, false)
            return WeddingItemHolder(itemView)
        }
    }

    override fun onBindViewHolder(viewItemHolder: RecyclerView.ViewHolder, position: Int) {
        val org = recyclerItemValues[position]

        if (getItemViewType(position) == TYPE_MOVIE) {
            val itemHolder = viewItemHolder as BirthdayItemHolder
            itemHolder.tvItemBirthdayCompanyName.text = org.producerName
            itemHolder.tvItemBirthdayConcept.text = (org as Movie).genre
            itemHolder.tvItemBirthdaayNumOfGuest.text = "${(org as Movie).revenue} "

            itemHolder.parentLayout.setOnClickListener {
                orgAdapterInterface.displayItem(org)
            }
        } else {
            val itemHolder = viewItemHolder as WeddingItemHolder
            itemHolder.tvItemWeddingCompanyName.text = org.producerName
            itemHolder.tvItemWeddingType.text = (org as Theatre).genre

            val imgName = (org as Theatre).director.toLowerCase()
            val resImageID =
                context.resources.getIdentifier(imgName, "drawable", context.packageName)
            itemHolder.imgItemWeddingColor.setImageResource(resImageID)

            itemHolder.parentLayout.setOnClickListener {
                orgAdapterInterface.displayItem(org)
            }
        }
    }

    override fun getItemCount(): Int {
        return recyclerItemValues.size
    }

    override fun getItemViewType(position: Int): Int {
        val org = recyclerItemValues[position]
        return if (org is Movie) TYPE_MOVIE else TYPE_THEATRE
    }

    inner class BirthdayItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvItemBirthdayCompanyName: TextView = itemView.findViewById(R.id.tvItemBirthdayCompanyName)
        val tvItemBirthdayConcept: TextView = itemView.findViewById(R.id.tvItemBirthdayConcept)
        val tvItemBirthdaayNumOfGuest: TextView = itemView.findViewById(R.id.tvItemBirthdaayNumOfGuest)
        val parentLayout: ConstraintLayout = itemView.findViewById(R.id.itemBirthdayConstraintLayout)
    }

    inner class WeddingItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvItemWeddingCompanyName: TextView = itemView.findViewById(R.id.tvItemWeddingCompanyName)
        val tvItemWeddingType: TextView = itemView.findViewById(R.id.tvItemWeddingType)
        val imgItemWeddingColor: ImageView = itemView.findViewById(R.id.imgItemWeddingColor)
        val parentLayout: ConstraintLayout = itemView.findViewById(R.id.itemWeddingConstraintLayout)
    }
}
