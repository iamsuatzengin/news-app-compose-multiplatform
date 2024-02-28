package ui.detail

data class DetailUiState(
    val isLoading: Boolean = false,
    val generatedText: String = "",
    val errorMessage: String = ""
)
