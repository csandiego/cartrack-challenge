package com.github.csandiego.cartrackchallenge

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.csandiego.cartrackchallenge.databinding.UserDetailBinding
import javax.inject.Inject
import javax.inject.Provider

class UserDetailFragment @Inject constructor(provider: Provider<UserDetailViewModel>) : Fragment() {

    private val viewModel by viewModels<UserDetailViewModel> {
        object : ViewModelProvider.Factory {

            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return provider.get() as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            arguments?.getInt(ARG_ITEM_ID)?.let {
                if (it > -1) {
                    viewModel.loadUser(it)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = UserDetailBinding.inflate(inflater, container, false).run {
        viewModel = this@UserDetailFragment.viewModel
        root
    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "item_id"
    }
}