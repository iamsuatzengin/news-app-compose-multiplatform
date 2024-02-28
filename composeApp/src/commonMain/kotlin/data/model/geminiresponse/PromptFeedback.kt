package data.model.geminiresponse


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PromptFeedback(
    @SerialName("safetyRatings")
    val safetyRatings: List<SafetyRating>? = null
)