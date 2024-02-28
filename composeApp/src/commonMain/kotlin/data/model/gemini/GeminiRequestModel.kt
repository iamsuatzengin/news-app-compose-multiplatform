package data.model.gemini


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GeminiRequestModel(
    @SerialName("contents")
    val contents: List<Content?>? = null
) {
    companion object {
        fun textModel(text: String): GeminiRequestModel {
            return GeminiRequestModel(
                listOf(Content(listOf(Part(text = text))))
            )
        }
    }
}
