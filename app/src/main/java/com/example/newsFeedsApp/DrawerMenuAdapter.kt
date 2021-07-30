package com.example.newsFeedsApp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.menu_item_layout.view.*

class DrawerMenuAdapter(
    var context: Context,
    items: LinkedHashMap<Int, Int>,
    var onItemClicked: OnItemClicked
) :
    RecyclerView.Adapter<DrawerMenuAdapter.ViewHolder>() {
    private var titlesList = items.keys.toList()
    private var iconsList = items.values.toList()
    var selectedPosition = 0
    var currentPosition = 0
    var lastSelectedPosition = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.menu_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(iconsList[position], titlesList[position])
        currentPosition = position
        holder.showLine()
        holder.itemView.setOnClickListener {
            onItemClicked.OnItemSelected(context.getString(titlesList[position]))
            selectedPosition = position
            notifyItemChanged(lastSelectedPosition)
            notifyItemChanged(selectedPosition)
        }

    }


    override fun getItemCount() = iconsList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun setData(icon: Int, title: Int) {
            Glide.with(context).load(icon).into(itemView.item_icon)
            itemView.item_title.text = context.getString(title)
        }

        fun showLine() {
            if (selectedPosition == currentPosition) {
                itemView.selected_line.visibility = View.VISIBLE
                lastSelectedPosition = selectedPosition
            } else
                itemView.selected_line.visibility = View.GONE
        }
    }


    interface OnItemClicked {
        fun OnItemSelected(title: String)
    }
}