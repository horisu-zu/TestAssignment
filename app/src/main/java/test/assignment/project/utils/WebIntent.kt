package test.assignment.project.utils

import android.content.Context
import android.util.Log
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri

object WebIntent {
    fun openUrlCustomTab(context: Context, url: String) {
        try {
            val customTabsIntent = CustomTabsIntent.Builder()
                .setShowTitle(true)
                .build()
            customTabsIntent.launchUrl(context, url.toUri())
        } catch (e: Exception) {
            Log.d("WebIntent", "Error opening $url: ${e.message}")
        }
    }
}