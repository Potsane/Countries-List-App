package com.example.myapplication.presentation.country.uicommands

import com.example.myapplication.domain.model.AppError

data class ShowError(val error: AppError)

data class ShowRefreshingSnackbar(val message: String = "Refreshing Data...")