package com.example.mygithubapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mygithubapp.R
import com.example.mygithubapp.activity.DetailActivity
import com.example.mygithubapp.adapter.FollowingAdapter
import com.example.mygithubapp.entity.Favorite
import com.example.mygithubapp.model.*
import kotlinx.android.synthetic.main.fragment_following.*


class FollowingFragment : Fragment() {

    companion object {
        val TAG = FollowingFragment::class.java.simpleName
        const val EXTRA_USER = "extra_user"
        const val EXTRA_NOTE = "extra_note"
    }

    private var listData: ArrayList<Following> = ArrayList()
    private lateinit var adapter: FollowingAdapter
    private var favorites: Favorite? = null
    private lateinit var dataFavorite: Favorite
    private lateinit var dataUser: User
    private lateinit var followingViewModel: FollowingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FollowingAdapter(listData)
        followingViewModel = ViewModelProvider(
            this, ViewModelProvider.NewInstanceFactory()
        ).get(FollowingViewModel::class.java)

        favorites = activity?.intent?.getParcelableExtra(DetailActivity.EXTRA_NOTE)
        if (favorites != null){
            dataFavorite = activity?.intent?.getParcelableExtra(EXTRA_NOTE) as Favorite
            followingViewModel.setListUserFollowing(requireActivity(), dataFavorite.username.toString())
            showLoading(true)
        }else {
            dataUser = activity?.intent?.getParcelableExtra(EXTRA_USER) as User


            followingViewModel.setListUserFollowing(requireActivity(), dataUser.username.toString())
            showLoading(true)
        }

        showRecyclerView()
        followingViewModel.getListFollowing().observe(requireActivity(), Observer { listFollowing ->
            if (listFollowing != null) {
                adapter.setData(listFollowing)
                showLoading(false)
            }
        })
    }

    private fun showRecyclerView() {
        rv_following.layoutManager = LinearLayoutManager(activity)
        rv_following.setHasFixedSize(true)
        rv_following.adapter = adapter
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            pb_following.visibility = View.VISIBLE
        } else {
            pb_following.visibility = View.INVISIBLE
        }
    }
}