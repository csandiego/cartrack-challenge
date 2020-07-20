package com.github.csandiego.cartrackchallenge

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.csandiego.cartrackchallenge.repository.CredentialRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: CredentialRepository) : ViewModel() {

    enum class Error {
        NONE, REQUIRED, INVALID
    }

    var username = ""
    private val _usernameError = MutableLiveData(Error.NONE)
    val usernameError: LiveData<Error> = _usernameError

    var password = ""
    private val _passwordError = MutableLiveData(Error.NONE)
    val passwordError: LiveData<Error> = _passwordError

    private val _loginSuccess = MutableLiveData(false)
    val loginSuccess: LiveData<Boolean> = _loginSuccess
    fun handleLoginSuccess() {
        _loginSuccess.value = false
    }

    fun login() {
        if (username.isEmpty()) {
            _usernameError.value = Error.REQUIRED
            return
        }
        if (password.isEmpty()) {
            _passwordError.value = Error.REQUIRED
            return
        }
        viewModelScope.launch {
            if (repository.validate(username, password)) {
                _loginSuccess.value = true
            } else {
                _usernameError.value = Error.INVALID
            }
        }
    }
}