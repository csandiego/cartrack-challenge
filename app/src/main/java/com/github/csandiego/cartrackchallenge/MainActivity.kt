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

        val binding = ActivityMainBinding.inflate(layoutInflater).also {
            it.viewModel = this.viewModel
            it.lifecycleOwner = this

            (it.countryTextInputLayout.editText as? AutoCompleteTextView)?.setAdapter(
                ArrayAdapter(
                    this,
                    R.layout.list_item,
                    resources.getStringArray(R.array.countries)
                )
            )
        }

        with(viewModel) {
            usernameError.observe(this@MainActivity) {
                binding.usernameTextInputLayout.error = when (it) {
                    MainViewModel.Error.REQUIRED -> getString(R.string.error_required)
                    MainViewModel.Error.INVALID -> getString(R.string.error_invalid_username_password)
                    else -> null
                }
            }

            passwordError.observe(this@MainActivity) {
                binding.passwordTextInputLayout.error = when (it) {
                    MainViewModel.Error.REQUIRED -> getString(R.string.error_required)
                    else -> null
                }
            }

            loginSuccess.observe(this@MainActivity) {
                if (it) {
                    viewModel.handleLoginSuccess()
                }
            }
        }

        setContentView(binding.root)
    }
}