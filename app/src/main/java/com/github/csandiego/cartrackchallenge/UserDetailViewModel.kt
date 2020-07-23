package com.github.csandiego.cartrackchallenge

import androidx.lifecycle.ViewModel
import com.github.csandiego.cartrackchallenge.data.User
import com.github.csandiego.cartrackchallenge.repository.UserRepository
import javax.inject.Inject

class UserDetailViewModel @Inject constructor(private val repository: UserRepository) : ViewModel() {

    private var _user: User? = null
    val user: User? get() = _user

    fun loadUser(index: Int) {
        _user = repository.getUser(index)
    }
}