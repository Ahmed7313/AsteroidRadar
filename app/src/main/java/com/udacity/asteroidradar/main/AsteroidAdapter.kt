package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.api.domain.Asteroid
import com.udacity.asteroidradar.databinding.AsteroidListItemBinding
import com.udacity.asteroidradar.main.AsteroidAdapter.ViewHolder.Companion.from

class AsteroidAdapter : ListAdapter<Asteroid, AsteroidAdapter.ViewHolder>(AsteroidDiffCallback()){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }


    class ViewHolder private constructor(var binding: AsteroidListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        var asteroidName : TextView = binding.asteroidNameListItem
        var approchDate : TextView = binding.asteroidApproachDateListItem
        var asteroidIsHazardous : ImageView = binding.asteroidIsHazardousListItem

        fun bind(item: Asteroid) {
            asteroidName.text = item.codename
            approchDate.text = item.closeApproachDate
            if (item.isPotentiallyHazardous) {
                asteroidIsHazardous.setImageResource(R.drawable.asteroid_hazardous)
            } else {
                asteroidIsHazardous.setImageResource(R.drawable.asteroid_safe)
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater  = LayoutInflater.from(parent.context)
                val binding = AsteroidListItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class AsteroidDiffCallback :
        DiffUtil.ItemCallback<Asteroid>() {
        override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem == newItem
        }
    }

}

