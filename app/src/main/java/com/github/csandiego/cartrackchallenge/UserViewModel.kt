package com.github.csandiego.cartrackchallenge

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.github.csandiego.cartrackchallenge.repository.UserRepository

class UserViewModel @ViewModelInject constructor(
    repository: UserRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val users = repository.getUsersAsLiveData(viewModelScope)

    val selected: LiveData<Int> = savedStateHandle.getLiveData("selected")

    val user = selected.map {
        users.value!![it]
    }

    private val _present = MutableLiveData(false)
    val present: LiveData<Boolean> = _present
    fun presentationHandled() {
        _present.value = false
    }

    fun select(index: Int) {
        if (selected.value == index)
            return
        savedStateHandle["selected"] = index
        _present.value = true
    }
}