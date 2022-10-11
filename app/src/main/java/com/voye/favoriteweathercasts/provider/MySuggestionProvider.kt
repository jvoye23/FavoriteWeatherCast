package com.voye.favoriteweathercasts.provider

import android.content.SearchRecentSuggestionsProvider

class MySuggestionProvider: SearchRecentSuggestionsProvider() {
    init {
        setupSuggestions(AUTHORITY, MODE)
    }

    companion object {
        const val AUTHORITY = "com.voye.favoriteweathercasts.provider.MySuggestionProvider"
        const val MODE: Int = SearchRecentSuggestionsProvider.DATABASE_MODE_QUERIES
    }
}