package com.example.mygithubapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.mygithubapp.R
import com.example.mygithubapp.model.Follower
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.item_row_user.view.*

class FollowerAdapter(private var listDataFollower: ArrayList<Follower>) :
    RecyclerView.Adapter<FollowerAdapter.UserViewHolder>() {

    fun setData(items: ArrayList<Follower>) {
        listDataFollower.clear()
        listDataFollower.addAll(items)
        notifyDataSetChanged()
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgPhoto: CircleImageView = itemView.cv_user
        var tvName: TextView = itemView.tv_user_name
        var tvUsername: TextView = itemView.tv_username
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_row_user, parent, false)
        val data = UserViewHolder(view)
        mContext = parent.context

        return data
    }

    override fun getItemCount(): Int = listDataFollower.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val data = listDataFollower[position]
        Glide.with(holder.itemView.context)
            .load(data.photo)
            .apply(RequestOptions().override(250, 250))
            .into(holder.imgPhoto)
        holder.tvName.text = data.name
        holder.tvUsername.text = data.username
        holder.itemView.setOnClickListener {

        }
    }
}