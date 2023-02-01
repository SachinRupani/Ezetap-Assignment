package com.ezetap.ezetapassignmentappmodule.ui.generic

import com.ezetap.ezetapassignmentnetworkmodule.generic.FailureCause

/**
 * UiState class which holds 3 states
 * Success, Failure & Loading
 */
sealed class UiState<out Data> {
    data class Success<out Data>(val data: Data) : UiState<Data>()
    data class Failure(val failure: FailureCause) : UiState<Nothing>()
    object Loading : UiState<Nothing>()

    /**
     * Get data value if the state is success
     */
    fun dataOrNull(): Data? {
        return when(this) {
            is Success -> data
            else -> null
        }
    }
}