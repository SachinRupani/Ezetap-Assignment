package com.ezetap.ezetapassignmentnetworkmodule

import android.util.Log
import com.ezetap.ezetapassignmentnetworkmodule.generic.ApiResult
import com.ezetap.ezetapassignmentnetworkmodule.generic.FailureCause
import com.ezetap.ezetapassignmentnetworkmodule.model.ApiResponseModel
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.features.observer.*
import io.ktor.http.*

const val TIME_OUT_MILLIS = 15000L

/**
 * API Interface to fetch the ui data from the REST API
 */
interface ApiService {

    /**
     * Function to fetch the JSON data in the form of
     * [ApiResponseModel]
     * @param apiUrl Remote URL to call
     * @return [ApiResult] Success [ApiResponseModel] Failure [FailureCause]
     */
    suspend fun fetchCustomUi(apiUrl: String): ApiResult<ApiResponseModel, FailureCause>


    companion object {

        /**
         * Function to provide the implementation
         * of [ApiService]
         * @return [ApiServiceImpl]
         */
        fun create(): ApiService {
            return ApiServiceImpl(
                client = HttpClient(Android) {
                    // Logging
                    install(Logging) {
                        logger = object : Logger {
                            override fun log(message: String) {
                                Log.v("Logger_Ktor =>", message)
                            }

                        }
                        level = LogLevel.ALL
                    }

                    // JSON
                    install(JsonFeature) {
                        serializer = KotlinxSerializer(json)
                        //or serializer = KotlinxSerializer()
                    }

                    // Timeout
                    install(HttpTimeout) {
                        requestTimeoutMillis = TIME_OUT_MILLIS
                        connectTimeoutMillis = TIME_OUT_MILLIS
                        socketTimeoutMillis = TIME_OUT_MILLIS
                    }

                    install(ResponseObserver) {
                        onResponse {
                            Log.d("ResponseStatusCode_Ktor=>", it.status.value.toString())
                        }
                    }

                    // Apply to all requests
                    defaultRequest {
                        // Content Type
                        contentType(ContentType.Application.Json)

                    }
                }
            )
        }

        private val json = kotlinx.serialization.json.Json {
            ignoreUnknownKeys = true
            isLenient = true
            encodeDefaults = false
            prettyPrint = true
        }
    }
}