package com.ezetap.ezetapassignmentappmodule.ui.common


import com.ezetap.ezetapassignmentappmodule.R
import com.ezetap.ezetapassignmentnetworkmodule.generic.FailureCause

/**
 * Function to get the relevant failure message
 * @param failureCause [FailureCause]
 */
fun getRelevantFailureErrorMessage(
    failureCause: FailureCause
): Int {
    return when (failureCause) {
        is FailureCause.UnknownError -> R.string.msg_unknown_error
        is FailureCause.NoContentError -> R.string.msg_no_content_found_error
        is FailureCause.HttpError -> {
            when (failureCause) {
                is FailureCause.HttpError.BadRequestError -> R.string.msg_bad_request_error
                is FailureCause.HttpError.ForbiddenError -> R.string.msg_forbidden_error
                is FailureCause.HttpError.NotFoundError -> R.string.msg_not_found_error
                is FailureCause.HttpError.UnauthorizedError -> R.string.msg_unauthorized_error
                is FailureCause.HttpError.InternalServerError -> R.string.msg_server_error
                is FailureCause.HttpError.OtherHttpError -> R.string.msg_other_http_error
                is FailureCause.HttpError.UnknownHostError -> R.string.msg_unknown_host_error
            }
        }
    }
}