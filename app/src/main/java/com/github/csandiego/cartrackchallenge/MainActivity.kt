package com.github.csandiego.cartrackchallenge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.github.csandiego.cartrackchallenge.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        val countries = resources.getStringArray(R.array.countries)
        val adapter = ArrayAdapter(this, R.layout.list_item, countries)
        (binding.countryTextInputLayout.editText as AutoCompleteTextView).setAdapter(adapter)
        setContentView(binding.root)
    }
}