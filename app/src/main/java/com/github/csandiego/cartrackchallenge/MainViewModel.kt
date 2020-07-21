package com.github.csandiego.cartrackchallenge

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.csandiego.cartrackchallenge.repository.CredentialRepository
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(private val repository: CredentialRepository) :
    ViewModel() {

    enum class Error {
        NONE, REQUIRED, INVALID
    }

    private var _username = ""
    var username
        get() = _username
        set(value) {
            _username = value
            _usernameError.value = if (value.isEmpty()) Error.REQUIRED else Error.NONE
        }
    private val _usernameError = MutableLiveData(Error.NONE)
    val usernameError: LiveData<Error> = _usernameError

    private var _password = ""
    var password
        get() = _password
        set(value) {
            _password = value
            _passwordError.value = if (value.isEmpty()) Error.REQUIRED else Error.NONE
        }
    private val _passwordError = MutableLiveData(Error.NONE)
    val passwordError: LiveData<Error> = _passwordError

    private val _loginSuccess = MutableLiveData(false)
    val loginSuccess: LiveData<Boolean> = _loginSuccess
    fun handleLoginSuccess() {
        _loginSuccess.value = false
    }

    fun login() {
        _usernameError.value = if (_username.isEmpty()) Error.REQUIRED else Error.NONE
        _passwordError.value = if (_password.isEmpty()) Error.REQUIRED else Error.NONE
        if (_usernameError.value != Error.NONE || _passwordError.value != Error.NONE)
            return
        viewModelScope.launch {
            if (repository.validate(username, password)) {
                _loginSuccess.value = true
            } else {
                _usernameError.value = Error.INVALID
            }
        }
    }
}