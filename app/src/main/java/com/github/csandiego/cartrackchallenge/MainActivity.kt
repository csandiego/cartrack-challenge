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
            binding.usernameTextInputLayout.error = when (it) {
                MainViewModel.Error.REQUIRED -> getString(R.string.error_required)
                MainViewModel.Error.INVALID -> getString(R.string.error_invalid_username_password)
                else -> null
            }
        }

        viewModel.passwordError.observe(this) {
            binding.passwordTextInputLayout.error = if (it == MainViewModel.Error.REQUIRED)
                getString(R.string.error_required)
            else
                null
        }

        viewModel.loginSuccess.observe(this) {
            if (it) {
                viewModel.handleLoginSuccess()
            }
        }

        val countries = resources.getStringArray(R.array.countries)
        val adapter = ArrayAdapter(this, R.layout.list_item, countries)
        (binding.countryTextInputLayout.editText as AutoCompleteTextView).setAdapter(adapter)

        setContentView(binding.root)
    }
}