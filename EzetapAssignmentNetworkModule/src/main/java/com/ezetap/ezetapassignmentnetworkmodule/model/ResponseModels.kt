package com.ezetap.ezetapassignmentnetworkmodule.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiResponseModel(
    @SerialName("logo-url") val logoUrl: String,
    @SerialName("heading-text") val headingText: String,
    @SerialName("uidata") val listUiData: List<UiDataModel>? = null
)

@Serializable
data class UiDataModel(
    @SerialName("uitype") val uiType: String, // "label" "edittext" "button"
    @SerialName("value") val valueText: String = "",
    @SerialName("hint") val hintText: String = "",
    @SerialName("key") val key: String? = null
) {

    /**
     * Property getter to get the ui type
     */
    val getUiTypeEnum: UiType
        get() {
            return when (uiType) {
                "label" -> UiType.Label
                "edittext" -> UiType.TextField
                "button" -> UiType.Button
                else -> UiType.Unrecognized
            }
        }

    /**
     * Property getter to check if the text field is a phone field
     */
    val isPhoneField: Boolean
        get() = key == "text_phone" && getUiTypeEnum == UiType.TextField
}

@Serializable
enum class UiType(val type: String) {
    Label(type = "label"),
    TextField(type = "edittext"),
    Button(type = "button"),
    Unrecognized(type = "")
}