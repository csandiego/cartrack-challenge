package com.github.csandiego.cartrackchallenge

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Provider

@AndroidEntryPoint
class UserDetailActivity : AppCompatActivity() {

    @Inject
    lateinit var provider: Provider<UserDetailFragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.fragmentFactory = object : FragmentFactory() {

            override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
                return provider.get()
            }
        }
        super.onCreate(savedInstanceState)
        // TODO: Call finish() if in two-pane mode
        setContentView(R.layout.activity_user_detail)
        setSupportActionBar(findViewById(R.id.detail_toolbar))


        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don"t need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            val fragment = provider.get().apply {
                arguments = Bundle().apply {
                    putInt(
                        UserDetailFragment.ARG_ITEM_ID,
                        intent.getIntExtra(UserDetailFragment.ARG_ITEM_ID, -1)
                    )
                }
            }

            supportFragmentManager.beginTransaction()
                .add(R.id.user_detail_container, fragment)
                .commit()
        }
    }
}