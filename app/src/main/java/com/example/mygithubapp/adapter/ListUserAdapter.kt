package com.example.mygithubapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.mygithubapp.R
import com.example.mygithubapp.activity.DetailActivity
import com.example.mygithubapp.model.User
import kotlinx.android.synthetic.main.item_row_user.view.*

lateinit var mContext: Context

class ListUserAdapter(private var listUser: ArrayList<User>) :
    RecyclerView.Adapter<ListUserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserViewHolder {
        return UserViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_row_user, parent, false)
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(listUser[position])
        val data = listUser[position]
        holder.itemView.setOnClickListener {
            val dataUser = User(
                data.username,
                data.name,
                data.photo,
                data.location,
                data.company,
                data.repository,
                data.follower,
                data.following,
                data.isFav
            )

            val mIntent = Intent(it.context, DetailActivity::class.java)
            mIntent.putExtra(DetailActivity.EXTRA_USER, dataUser)
            mIntent.putExtra(DetailActivity.EXTRA_FAV, dataUser)
            it.context.startActivity(mIntent)
        }
    }

    override fun getItemCount(): Int = listUser.size


    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(dataUser: User) {
            with(itemView) {
                Glide.with(context)
                    .load(dataUser.photo)
                    .apply(RequestOptions().override(250, 250))
                    .into(cv_user)

                tv_user_name.text = dataUser.name
                tv_username.text = dataUser.username
            }
        }
    }

    fun setData(items: ArrayList<User>) {
        listUser.clear()
        listUser.addAll(items)
        notifyDataSetChanged()
    }
}


