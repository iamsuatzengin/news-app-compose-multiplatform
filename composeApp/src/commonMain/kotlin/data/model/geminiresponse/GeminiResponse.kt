package data.model.geminiresponse


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GeminiResponse(
    @SerialName("candidates")
    val candidates: List<Candidate>? = null,
    @SerialName("promptFeedback")
    val promptFeedback: PromptFeedback? = null
) {

    val responseAsText: String
        get() {
            val stringBuilder = StringBuilder()

            candidates?.forEach { candidate ->
                candidate.content?.parts?.forEach { part ->
                    stringBuilder.append(part?.text)
                }
            }

            return stringBuilder.toString()
        }
}
