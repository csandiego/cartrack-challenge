package com.github.csandiego.cartrackchallenge

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.csandiego.cartrackchallenge.repository.UserRepository

class UserListViewModel @ViewModelInject constructor(repository: UserRepository) : ViewModel() {

    val users = repository.getUsersAsLiveData(viewModelScope)
}