package com.github.csandiego.cartrackchallenge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.activity.viewModels
import androidx.lifecycle.observe
import com.github.csandiego.cartrackchallenge.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.usernameError.observe(this) {
            when (it) {
                MainViewModel.Error.REQUIRED -> binding.usernameTextInputLayout.error =
                    getString(R.string.error_required)
                MainViewModel.Error.INVALID -> binding.usernameTextInputLayout.error =
                    getString(R.string.error_invalid_username_password)
                else -> binding.usernameTextInputLayout.error = null
            }
        }

        viewModel.passwordError.observe(this) {
            when (it) {
                MainViewModel.Error.REQUIRED -> binding.passwordTextInputLayout.error =
                    getString(R.string.error_required)
                else -> binding.passwordTextInputLayout.error = null
            }
        }

        val countries = resources.getStringArray(R.array.countries)
        val adapter = ArrayAdapter(this, R.layout.list_item, countries)
        (binding.countryTextInputLayout.editText as AutoCompleteTextView).setAdapter(adapter)

        setContentView(binding.root)
    }
}