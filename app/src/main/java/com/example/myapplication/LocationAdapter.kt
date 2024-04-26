package com.example.myapplication

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LocationAdapter(private val locations: List<Location>) : RecyclerView.Adapter<LocationAdapter.LocationViewHolder>() {

    class LocationViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.location_item, parent, false)
        return LocationViewHolder(view)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val location = locations[position]
        val nameView = holder.view.findViewById<TextView>(R.id.location_name)
        nameView.text = location.name

        // Set an OnClickListener
        nameView.setOnClickListener {
            val intent = Intent(it.context, TaskListActivity::class.java)
            intent.putExtra("tasks", location.activities.toTypedArray())
            it.context.startActivity(intent)
        }
    }



    override fun getItemCount() = locations.size
}
