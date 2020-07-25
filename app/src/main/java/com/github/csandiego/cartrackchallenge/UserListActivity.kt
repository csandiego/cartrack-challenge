package com.github.csandiego.cartrackchallenge

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.Toolbar
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.github.csandiego.cartrackchallenge.data.User
import com.github.csandiego.cartrackchallenge.databinding.UserListContentBinding
import com.mapbox.mapboxsdk.maps.MapView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserListActivity : AppCompatActivity() {

    private val viewModel by viewModels<UserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.title = title

        findViewById<RecyclerView>(R.id.user_list).adapter = adapter

        with(viewModel) {
            users.observe(this@UserListActivity) {
                adapter.submitList(it)
            }
            present.observe(this@UserListActivity) {
                if (it) {
                    presentationHandled()
                    if (findViewById<MapView>(R.id.mapView) == null) {
                        val intent =
                            Intent(this@UserListActivity, UserDetailActivity::class.java).apply {
                                putExtra("index", selected.value)
                            }
                        startActivity(intent)
                    }
                }
            }
        }
    }

    private val itemCallback = object : DiffUtil.ItemCallback<User>() {

        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }
    }

    class ViewHolder(val binding: UserListContentBinding) : RecyclerView.ViewHolder(binding.root)

    private val adapter = object : ListAdapter<User, ViewHolder>(itemCallback) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding =
                UserListContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            with(holder.binding) {
                index = position
                user = getItem(position)
                viewModel = this@UserListActivity.viewModel
            }
        }
    }
}