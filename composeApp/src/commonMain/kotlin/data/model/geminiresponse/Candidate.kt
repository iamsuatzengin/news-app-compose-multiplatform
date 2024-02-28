package data.model.geminiresponse


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Candidate(
    @SerialName("content")
    val content: Content? = null,
    @SerialName("finishReason")
    val finishReason: String? = null,
    @SerialName("index")
    val index: Int? = null,
    @SerialName("safetyRatings")
    val safetyRatings: List<SafetyRating?>? = null
)
