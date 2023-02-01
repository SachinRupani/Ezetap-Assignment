package com.ezetap.ezetapassignmentnetworkmodule

import com.ezetap.ezetapassignmentnetworkmodule.generic.FailureCause
import io.ktor.client.features.*
import java.net.UnknownHostException

/**
 * Function to convert [ResponseException]
 * to [FailureCause]
 */
fun Exception.toFailureCause(): FailureCause {
    return when (this) {
        is ServerResponseException -> FailureCause.HttpError.InternalServerError
        is ClientRequestException -> {
            when (response.status.value) {
                400 -> FailureCause.HttpError.BadRequestError
                401 -> FailureCause.HttpError.UnauthorizedError
                403 -> FailureCause.HttpError.ForbiddenError
                404 -> FailureCause.HttpError.NotFoundError
                else -> FailureCause.HttpError.OtherHttpError
            }
        }
        is RedirectResponseException -> FailureCause.HttpError.OtherHttpError
        is UnknownHostException -> FailureCause.HttpError.UnknownHostError
        else -> FailureCause.HttpError.OtherHttpError
    }
}