package com.example.consumerapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.consumerapp.R
import com.example.consumerapp.model.Following
import kotlinx.android.synthetic.main.item_row_user.view.*

class FollowingAdapter(private var listUser: ArrayList<Following>) :
    RecyclerView.Adapter<FollowingAdapter.UserViewHolder>() {

    fun setData(item: ArrayList<Following>) {
        listUser.clear()
        listUser.addAll(item)
        notifyDataSetChanged()
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(dataFollowing: Following) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load(dataFollowing.photo)
                    .apply(RequestOptions().override(100, 100))
                    .into(cv_user)
                tv_user_name.text = dataFollowing.name
                tv_username.text = dataFollowing.username
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_row_user, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(listUser[position])
    }
}