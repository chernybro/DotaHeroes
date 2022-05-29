package com.chernybro.wb51.presentation.models

sealed class ScreenState<T>(val data: T? = null, val error: String? = null) {
    class Success<T>(data: T) : ScreenState<T>(data)
    class Error<T>(error: String, data: T? = null) : ScreenState<T>(data, error)
    class Loading<T>(data: T? = null) : ScreenState<T>(data)
}
