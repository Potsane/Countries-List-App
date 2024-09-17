package com.example.myapplication.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.example.myapplication.presentation.navigation.NavigationCommand
import com.example.myapplication.util.livedata.toSingleEvent
import kotlinx.coroutines.launch

open class CountriesAppBaseViewModel : ViewModel() {

    private val _uiCommands = MutableLiveData<Any>().toSingleEvent() as MutableLiveData<Any>
    val uiCommands: LiveData<Any> = _uiCommands

    private val _navigationCommands = MutableLiveData<NavigationCommand>().toSingleEvent()
            as MutableLiveData<NavigationCommand>
    val navigationCommands: LiveData<NavigationCommand> = _navigationCommands

    protected fun postUiCommand(command: Any) {
        _uiCommands.postValue(command)
    }

    protected fun goBack() {
        _navigationCommands.postValue(NavigationCommand.Back)
    }

    protected fun navigate(directions: NavDirections) {
        _navigationCommands.postValue(NavigationCommand.ToDirection(directions))
    }

    protected fun withProgress(block: suspend () -> Unit) {
        viewModelScope.launch {
            _uiCommands.postValue(ShowProgress(true))
            block()
            _uiCommands.postValue(ShowProgress(false))
        }
    }
}