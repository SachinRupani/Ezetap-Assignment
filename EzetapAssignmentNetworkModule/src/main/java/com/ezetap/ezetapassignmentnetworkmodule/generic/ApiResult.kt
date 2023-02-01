package com.ezetap.ezetapassignmentnetworkmodule.generic


/**
 * Sealed class for checking results
 * either for success
 * or for failure
 */
sealed class ApiResult<out Success, out Failure> {
    /**
     * Success
     * @param data generic data to be used
     */
    data class Success<out Success>(val data: Success) : ApiResult<Success, Nothing>()

    /**
     * Failure
     * @param failureCause Cause of failure
     */
    data class Failure<out Failure>(val failureCause: Failure) : ApiResult<Nothing, Failure>()
}