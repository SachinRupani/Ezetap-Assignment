package com.ezetap.ezetapassignmentnetworkmodule

import android.util.Log
import com.ezetap.ezetapassignmentnetworkmodule.generic.ApiResult
import com.ezetap.ezetapassignmentnetworkmodule.generic.FailureCause
import com.ezetap.ezetapassignmentnetworkmodule.model.ApiResponseModel
import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * API Interface Implementation of [ApiService]
 * to fetch the API data
 */
class ApiServiceImpl(
    private val client: HttpClient
) : ApiService {
    override suspend fun fetchCustomUi(apiUrl: String): ApiResult<ApiResponseModel, FailureCause> {
        return withContext(Dispatchers.IO) {
            Log.d("fetchCustomUi","============================")
            try {
                val response: ApiResponseModel = client.get {
                    url(urlString = apiUrl)
                }

                Log.d("fetchCustomUi", "ApiResponseModel => $response")

                // Return Success
                ApiResult.Success(data = response)
            } catch (ex: Exception) {
                Log.d("fetchCustomUi", "ExceptionOccurred => $ex")
                // Return Failure
                ApiResult.Failure(failureCause = ex.toFailureCause())
            }
        }
    }

}