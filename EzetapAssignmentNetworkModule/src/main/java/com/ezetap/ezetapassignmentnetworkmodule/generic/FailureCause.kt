package com.ezetap.ezetapassignmentnetworkmodule.generic


sealed class FailureCause {

    sealed class HttpError: FailureCause() {
        // 500
        object InternalServerError : HttpError()

        // 401
        object UnauthorizedError : HttpError()

        //400
        object BadRequestError : HttpError()

        //403
        object ForbiddenError : HttpError()

        //404
        object NotFoundError : HttpError()

        // Unknown Host
        object UnknownHostError: HttpError()

        // 3xx or other http error
        object OtherHttpError : HttpError()
    }

    object NoContentError: FailureCause()

    object UnknownError: FailureCause()


}