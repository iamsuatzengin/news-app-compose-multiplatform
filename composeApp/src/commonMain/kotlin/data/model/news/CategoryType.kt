package data.model.news

enum class CategoryType {
    BUSINESS,
    ENTERTAINMENT,
    GENERAL,
    HEALTH,
    SCIENCE,
    SPORTS,
    TECHNOLOGY;

    companion object {
        fun getCategoryQuery(categoryType: CategoryType): String {
            val category = entries.firstOrNull { it == categoryType } ?: GENERAL

            return category.name.lowercase()
        }
    }
}
