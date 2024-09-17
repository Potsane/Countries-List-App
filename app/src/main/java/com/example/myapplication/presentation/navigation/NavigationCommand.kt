package com.example.myapplication.presentation.navigation

import androidx.navigation.NavDirections

sealed class NavigationCommand{
    data class ToDirection(val directions: NavDirections) : NavigationCommand()
    data object Back : NavigationCommand()
}