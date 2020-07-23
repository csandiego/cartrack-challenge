package com.github.csandiego.cartrackchallenge

import android.content.Intent
import android.os.Bundle
import androidx.core.widget.NestedScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.Toolbar
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.github.csandiego.cartrackchallenge.data.User
import com.github.csandiego.cartrackchallenge.databinding.UserListContentBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Provider

@AndroidEntryPoint
class UserListActivity : AppCompatActivity() {

    private val viewModel by viewModels<UserListViewModel>()

    @Inject
    lateinit var provider: Provider<UserDetailFragment>

    private var twoPane: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.fragmentFactory = object : FragmentFactory() {

            override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
                return provider.get()
            }
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.title = title

        twoPane = findViewById<NestedScrollView>(R.id.user_detail_container) != null

        val adapter = Adapter(this::present)
        findViewById<RecyclerView>(R.id.user_list).adapter = adapter

        viewModel.users.observe(this) {
            adapter.submitList(it)
        }
    }

    private fun present(index: Int) {
        if (twoPane) {
            val fragment = provider.get().apply {
                arguments = Bundle().apply {
                    putInt(UserDetailFragment.ARG_ITEM_ID, index)
                }
            }
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.user_detail_container, fragment)
                .commit()
        } else {
            val intent = Intent(this, UserDetailActivity::class.java).apply {
                putExtra(UserDetailFragment.ARG_ITEM_ID, index)
            }
            startActivity(intent)
        }
    }

    class Adapter(private val callback: (Int) -> Unit) :
        ListAdapter<User, Adapter.ViewHolder>(ItemCallback()) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding =
                UserListContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            with(holder.binding) {
                user = getItem(position)
                root.setOnClickListener {
                    callback(position)
                }
            }
        }

        class ViewHolder(val binding: UserListContentBinding) :
            RecyclerView.ViewHolder(binding.root)

        class ItemCallback : DiffUtil.ItemCallback<User>() {

            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}