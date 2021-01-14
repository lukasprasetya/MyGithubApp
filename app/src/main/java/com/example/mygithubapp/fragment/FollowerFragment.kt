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
import com.example.mygithubapp.adapter.FollowerAdapter
import com.example.mygithubapp.entity.Favorite
import com.example.mygithubapp.model.*
import kotlinx.android.synthetic.main.fragment_follower.*


class FollowerFragment : Fragment() {

    companion object {
        val TAG = FollowerFragment::class.java.simpleName
        const val EXTRA_USER = "extra_user"
        const val EXTRA_NOTE = "extra_note"
    }

    private val listData: ArrayList<Follower> = ArrayList()
    private lateinit var adapter: FollowerAdapter
    private var favorites: Favorite? = null
    private lateinit var dataFavorite: Favorite
    private lateinit var dataUser: User
    private lateinit var followerViewModel: FollowerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_follower, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FollowerAdapter(listData)
        followerViewModel = ViewModelProvider(
            this, ViewModelProvider.NewInstanceFactory()
        ).get(FollowerViewModel::class.java)

        favorites = activity?.intent?.getParcelableExtra(DetailActivity.EXTRA_NOTE)
        if (favorites != null){
            dataFavorite = activity?.intent?.getParcelableExtra(EXTRA_NOTE) as Favorite
            followerViewModel.setListUserFollower(requireActivity(),dataFavorite.username.toString())
            showLoading(true)

        }else{
         dataUser = activity?.intent?.getParcelableExtra(EXTRA_USER) as User

        followerViewModel.setListUserFollower(requireActivity(),dataUser.username.toString())
        showLoading(true)
        }
        showRecyclerView()
        followerViewModel.getListFollower().observe(requireActivity(), Observer { listFollower ->
            if (listFollower != null) {
                adapter.setData(listFollower)
                showLoading(false)
            }
        })
    }

    private fun showRecyclerView() {
        rv_follower.layoutManager = LinearLayoutManager(activity)
        rv_follower.setHasFixedSize(true)
        rv_follower.adapter = adapter
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            pb_follower.visibility = View.VISIBLE
        } else {
            pb_follower.visibility = View.INVISIBLE
        }
    }
}